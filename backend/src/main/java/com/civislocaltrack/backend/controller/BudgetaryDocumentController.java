package com.civislocaltrack.backend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.civislocaltrack.backend.model.AdminAccount;
import com.civislocaltrack.backend.model.Budget;
import com.civislocaltrack.backend.model.BudgetaryDocument;
import com.civislocaltrack.backend.model.ManagementAccount;
import com.civislocaltrack.backend.model.BudgetaryDocument.CategoryDocument;
import com.civislocaltrack.backend.service.BudgetaryDocumentService;

@RestController
@RequestMapping("/api/documents")
public class BudgetaryDocumentController {

    @Autowired
    private BudgetaryDocumentService budgetaryDocumentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("fichier") MultipartFile fichier,
            @RequestParam("categoryDocument") String categoryDocumentName) throws IOException {

        // Convertir la chaîne de caractères en enum
        CategoryDocument categoryDocument;
        try {
            categoryDocument = CategoryDocument.valueOf(categoryDocumentName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Catégorie de document invalide : " + categoryDocumentName);
        }

        // BudgetaryDocument budgetaryDocument = new BudgetaryDocument();
        BudgetaryDocument budgetaryDocument;
        if (categoryDocument == CategoryDocument.BUDGET) {
            budgetaryDocument = new Budget();
        } else if (categoryDocument == CategoryDocument.COMPTE_ADMIN) {
            budgetaryDocument = new AdminAccount();
        } else if (categoryDocument == CategoryDocument.COMPTE_GESTION) {
            budgetaryDocument = new ManagementAccount();
        } else {
            budgetaryDocument = new BudgetaryDocument();
        }
        return ResponseEntity.ok(budgetaryDocumentService.uploadBudgetaryDocument(budgetaryDocument, fichier, categoryDocument));
    }
    
}
