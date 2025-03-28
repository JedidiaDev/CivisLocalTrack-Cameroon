package com.civislocaltrack.backend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/api/budgetary-documents")
// @RequestMapping("/api/documents")
public class BudgetaryDocumentController {

    @Autowired
    private BudgetaryDocumentService budgetaryDocumentService;

    @PostMapping("/upload")
    public ResponseEntity<BudgetaryDocument> uploadBudgetaryDocument(
            @RequestParam("fichier") MultipartFile fichier,
            @RequestParam("categoryDocument") CategoryDocument categoryDocument,
            @ModelAttribute BudgetaryDocument budgetaryDocument // Récupère les autres champs du formulaire
    ) {
        try {
            BudgetaryDocument uploadedDocument = budgetaryDocumentService.uploadBudgetaryDocument(budgetaryDocument, fichier, categoryDocument);
            return new ResponseEntity<>(uploadedDocument, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return new ResponseEntity<>("Erreur lors de l'upload du fichier", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // @PostMapping("/upload")
    // public ResponseEntity<?> uploadDocument(
    //         @RequestParam("fichier") MultipartFile fichier,
    //         @RequestParam("categoryDocument") String categoryDocumentName) throws IOException {

    //     // Convertir la chaîne de caractères en enum
    //     CategoryDocument categoryDocument;
    //     try {
    //         categoryDocument = CategoryDocument.valueOf(categoryDocumentName.toUpperCase());
    //     } catch (IllegalArgumentException e) {
    //         throw new IllegalArgumentException("Catégorie de document invalide : " + categoryDocumentName);
    //     }

    //     // BudgetaryDocument budgetaryDocument = new BudgetaryDocument();
    //     BudgetaryDocument budgetaryDocument;
    //     if (categoryDocument == CategoryDocument.BUDGET) {
    //         budgetaryDocument = new Budget();
    //     } else if (categoryDocument == CategoryDocument.COMPTE_ADMIN) {
    //         budgetaryDocument = new AdminAccount();
    //     } else if (categoryDocument == CategoryDocument.COMPTE_GESTION) {
    //         budgetaryDocument = new ManagementAccount();
    //     } else {
    //         budgetaryDocument = new BudgetaryDocument();
    //     }
    //     return ResponseEntity.ok(budgetaryDocumentService.uploadBudgetaryDocument(budgetaryDocument, fichier, categoryDocument));
    // }
    
}
