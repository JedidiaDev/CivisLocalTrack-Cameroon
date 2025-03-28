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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.civislocaltrack.backend.model.BudgetaryDocument;
import com.civislocaltrack.backend.model.BudgetaryDocument.CategoryDocument;
import com.civislocaltrack.backend.repository.BudgetaryDocumentRepository;

import jakarta.transaction.Transactional;

@Service
public class BudgetaryDocumentService {

    @Autowired
    private BudgetaryDocumentRepository budgetaryDocumentRepository;

    private final Path rootLocation = Paths.get("uploads");

    public BudgetaryDocumentService() {
        initStorageDirectory();
    }


    private void initStorageDirectory() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer le dossier de stockage", e);
        }
    }

    // public BudgetaryDocument uploadBudgetaryDocument(BudgetaryDocument budgetaryDocument) {
    //     return budgetaryDocumentRepository.save(budgetaryDocument);
    // }

    @Transactional
    public BudgetaryDocument uploadBudgetaryDocument(BudgetaryDocument budgetaryDocument, MultipartFile fichier, CategoryDocument categoryDocument)
        throws IOException {
            // === 1. Validation du fichier ===
            if (fichier == null || fichier.isEmpty()) {
                throw new IllegalArgumentException("Le fichier est requis.");
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
                throw new IllegalArgumentException(
                    "Seuls les fichiers PDF, XLS/XLSX et CSV sont acceptés. Type reçu : " + contentType
                );
            }

            // Vérification de l'extension pour double sécurité
            String nomOriginal = fichier.getOriginalFilename();
            String extension = nomOriginal.substring(nomOriginal.lastIndexOf(".") + 1).toLowerCase();
            List<String> extensionsAutorisees = Arrays.asList("pdf", "xls", "xlsx", "csv");
            if (!extensionsAutorisees.contains(extension)) {
                throw new IllegalArgumentException("Extension de fichier non autorisée : " + extension);
            }

            // === 2. Génération d'un nom de fichier unique ===
            String nomUnique = UUID.randomUUID() + "." + extension; // Ex: "a3b8f2e1.xlsx"

            // === 3. Sauvegarde sécurisée du fichier ===
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            Path cheminFichier = rootLocation.resolve(nomUnique);
            try {
                Files.copy(fichier.getInputStream(), cheminFichier, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Échec de l'enregistrement du fichier.", e);
            }

            // === 4. Mise à jour des métadonnées ===
            budgetaryDocument.setIntitule(nomUnique);
            budgetaryDocument.setOriginalName(nomOriginal);
            budgetaryDocument.setUrl(cheminFichier.toString());
            budgetaryDocument.setType(contentType); // Stocke le type MIME
            budgetaryDocument.setCategoryDocument(categoryDocument);
            budgetaryDocument.setSize(fichier.getSize());
            budgetaryDocument.setUploadDate(LocalDateTime.now());

            // === 5. Sauvegarde en base ===
            return budgetaryDocumentRepository.save(budgetaryDocument);
        }


    
}
