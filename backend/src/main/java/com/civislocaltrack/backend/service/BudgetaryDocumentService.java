package com.civislocaltrack.backend.service;

import java.io.IOException;
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

import jakarta.transaction.Transactional;

@Service
public class BudgetaryDocumentService {

    @Autowired
    private BudgetaryDocumentRepository budgetaryDocumentRepository;

    private final Path rootLocation = Paths.get("uploads");

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    private AmazonS3 s3Client;

    public BudgetaryDocumentService(){
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withEndpointConfiguration(new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new com.amazonaws.auth.AWSStaticCredentialsProvider(new com.amazonaws.auth.BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

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

        // === 3. Upload vers S3 ===
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(fichier.getSize());

        try {
            s3Client.putObject(bucketName, nomUnique, fichier.getInputStream(), metadata);
        } catch (Exception e) {
            throw new IOException("Échec de l'enregistrement du fichier dans S3" + bucketName + ".", e);
        }

        // === 4. Mise à jour des métadonnées ===
        budgetaryDocument.setIntitule(nomUnique);
        budgetaryDocument.setOriginalName(nomOriginal);
        budgetaryDocument.setUrl("https://" + endpoint + "/" + bucketName + "/" + nomUnique); // URL du fichier dans S3
        budgetaryDocument.setType(contentType); // Stocke le type MIME
        budgetaryDocument.setCategoryDocument(categoryDocument);
        budgetaryDocument.setSize(fichier.getSize());
        budgetaryDocument.setUploadDate(LocalDateTime.now());

        // === 5. Sauvegarde en base ===
        return budgetaryDocumentRepository.save(budgetaryDocument);
    }    
}
