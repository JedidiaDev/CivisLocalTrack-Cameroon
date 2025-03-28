package com.civislocaltrack.backend.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.civislocaltrack.backend.Execption.FileException;
import com.civislocaltrack.backend.model.BudgetaryDocument;
import com.civislocaltrack.backend.model.BudgetaryDocument.CategoryDocument;
import com.civislocaltrack.backend.repository.BudgetaryDocumentRepository;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class BudgetaryDocumentService {

    @Autowired
    private BudgetaryDocumentRepository budgetaryDocumentRepository;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Autowired
    private MinioClient minioClient;

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

        // === 3. Upload vers MinIO ===
        try (InputStream fileStream = fichier.getInputStream()) {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(nomUnique)
                    .stream(fileStream, fichier.getSize(), -1)
                    .contentType(contentType)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new IOException("Échec de l'enregistrement du fichier dans MinIO.", e);
        }

        // === 4. Mise à jour des métadonnées ===
        budgetaryDocument.setIntitule(nomUnique);
        budgetaryDocument.setOriginalName(nomOriginal);
        budgetaryDocument.setUrl(endpoint + "/" + bucketName + "/" + nomUnique); // URL du fichier dans MinIO
        budgetaryDocument.setType(contentType); // Stocke le type MIME
        budgetaryDocument.setCategoryDocument(categoryDocument);
        budgetaryDocument.setSize(fichier.getSize());
        budgetaryDocument.setUploadDate(LocalDateTime.now());

        // === 5. Sauvegarde en base ===
        return budgetaryDocumentRepository.save(budgetaryDocument);
    }
}
