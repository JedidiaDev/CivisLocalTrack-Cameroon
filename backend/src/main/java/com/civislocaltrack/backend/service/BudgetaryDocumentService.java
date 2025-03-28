package com.civislocaltrack.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.civislocaltrack.backend.Execption.FileException;
import com.civislocaltrack.backend.model.BudgetaryDocument;
import com.civislocaltrack.backend.model.BudgetaryDocument.CategoryDocument;
import com.civislocaltrack.backend.repository.BudgetaryDocumentRepository;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.transaction.Transactional;

@Service
public class BudgetaryDocumentService {

    @Autowired
    private MinioClient minioClient; // Commenté car non utilisé dans cette version

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    private BudgetaryDocumentRepository budgetaryDocumentRepository;

    // private final Path rootLocation = Paths.get("uploads");

    // public BudgetaryDocumentService() {
    //     initStorageDirectory();
    // }


    // private void initStorageDirectory() {
    //     try {
    //         Files.createDirectories(rootLocation);
    //     } catch (IOException e) {
    //         throw new RuntimeException("Impossible de créer le dossier de stockage", e);
    //     }
    // }
    // private void initStorageDirectory() {
    //     try {
    //         Files.createDirectories(rootLocation);
    //     } catch (IOException e) {
    //         throw new RuntimeException("Impossible de créer le dossier de stockage", e);
    //     }
    // }

    // // public BudgetaryDocument uploadBudgetaryDocument(BudgetaryDocument budgetaryDocument) {
    // //     return budgetaryDocumentRepository.save(budgetaryDocument);
    // // }

    @Transactional
    public BudgetaryDocument uploadBudgetaryDocument(BudgetaryDocument budgetaryDocument, MultipartFile fichier, CategoryDocument categoryDocument)
            throws IOException {

        // === 1. Validation du fichier ===
        if (fichier == null || fichier.isEmpty()) {
            throw new FileException("Le fichier est requis.", FileException.FileErrorCode.EMPTY_FILE);
        }

        // Liste des types MIME autorisés (PDF, Excel, CSV)
        List<String> mimeTypesAutorises = Arrays.asList(
                "application/pdf",
                "application/vnd.ms-excel",          // XLS
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // XLSX
                "text/csv",
                "text/plain"                         // CSV (peut être rapporté comme text/plain)
        );

        // Vérification du type MIME
        String contentType = fichier.getContentType();
        if (contentType == null || !mimeTypesAutorises.contains(contentType)) {
            throw new FileException("Seuls les fichiers PDF, XLS/XLSX et CSV sont acceptés. Type reçu : " + contentType, FileException.FileErrorCode.CONTENT_FILE_NOT_AUTHORIZED); 
        }

        // Vérification de l'extension pour double sécurité
        String nomOriginal = fichier.getOriginalFilename();
        String extension = nomOriginal.substring(nomOriginal.lastIndexOf(".") + 1).toLowerCase();
        List<String> extensionsAutorisees = Arrays.asList("pdf", "xls", "xlsx", "csv");
        if (!extensionsAutorisees.contains(extension)) {
            throw new FileException("Extension de fichier non autorisée : " + extension, FileException.FileErrorCode.INVALID_FORMAT);
        }

        // === 2. Génération d'un nom de fichier unique ===
        String nomUnique = UUID.randomUUID() + "." + extension; // Ex: "a3b8f2e1.xlsx"

            // === 3. Sauvegarde sécurisée du fichier ===
            // if (!Files.exists(rootLocation)) {
            //     Files.createDirectories(rootLocation);
            // }

            // === 3. Téléverser le fichier vers Minio ===
            try (InputStream inputStream = fichier.getInputStream()) {
                // Vérifier si le bucket existe, sinon le créer
                boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                if (!isExist) {
                    // Créer le bucket s'il n'existe pas
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                }

                // Téléverser le fichier dans MinIO
                minioClient.putObject(
                    PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(nomUnique)
                        .stream(inputStream, fichier.getSize(), -1)
                        .contentType(contentType)
                        .build()
                );
            } catch (Exception e) {
                // Gérer les exceptions de téléversement
                throw new IOException("Échec du téléversement du fichier vers MinIO.", e);
                // throw new Exception("Échec du téléversement du fichier vers MinIO.", e);
            }

            // // Path cheminFichier = rootLocation.resolve(nomUnique);
            // try {
            //     Files.copy(fichier.getInputStream(), cheminFichier, StandardCopyOption.REPLACE_EXISTING);
            // } catch (IOException e) {
            //     throw new IOException("Échec de l'enregistrement du fichier.", e);
            // }

            // === 4. Mise à jour des métadonnées ===
            budgetaryDocument.setIntitule(nomUnique);
            budgetaryDocument.setOriginalName(nomOriginal);
            budgetaryDocument.setUrl("http://localhost:8900/" + bucketName + "/" + nomUnique); // URL d'accès au fichier
            budgetaryDocument.setType(contentType); // Stocke le type MIME
            budgetaryDocument.setCategoryDocument(categoryDocument);
            budgetaryDocument.setSize(fichier.getSize());
            budgetaryDocument.setUploadDate(LocalDateTime.now());

        // === 5. Sauvegarde en base ===
        return budgetaryDocumentRepository.save(budgetaryDocument);
    }    
}
