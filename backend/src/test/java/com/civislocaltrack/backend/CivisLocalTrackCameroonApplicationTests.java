package com.civislocaltrack.backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Locale.Category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import com.civislocaltrack.backend.service.BudgetaryDocumentService;
import com.civislocaltrack.backend.service.UserService;
import com.civislocaltrack.backend.model.*;
import com.civislocaltrack.backend.model.BudgetaryDocument.CategoryDocument;

@SpringBootTest
class CivisLocalTrackCameroonApplicationTests {

	// @Test
	// void contextLoads() {
	// }
	@Autowired
    private UserService userService;

	@Autowired
	private BudgetaryDocumentService budgetaryDocumentService;

	

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setName("Jeanot");
        user.setMail("jean@test.com");
        user.setPassword("123456");

        userService.registerUser(user);

        Assertions.assertNotNull(user.getId());  // Il a bien été sauvegardé
		System.out.println("User registered successfully");
    }

	// @Test
	// public void testGetUser() {
	// 	User user = userService.getUser(1L);
	// 	Assertions.assertNotNull(user);
	// 	System.out.println("User retrieved successfully");

	// }

	@Mock
	MultipartFile fichier;

	// @Test
	// public void testUploadBudgetaryDocument() {
	// 	BudgetaryDocument budgetaryDocument = new BudgetaryDocument();
	// 	// budgetaryDocument.setCategoryDocument(CategoryDocument.BUDGET);
	// 	budgetaryDocument.setIntitule("Budget 2021");


	// 	budgetaryDocumentService.uploadBudgetaryDocument(budgetaryDocument,fichier, categoryDocument.BUDGET);
	// 	Assertions.assertNotNull(budgetaryDocument.getId());
	// 	System.out.println("Budgetary document uploaded successfully");
	// }

	@Test
	public void testUploadBudgetaryDocument() throws IOException {
        // 1. Configuration du mock
        when(fichier.isEmpty()).thenReturn(false);
        when(fichier.getContentType()).thenReturn("application/pdf");
        when(fichier.getOriginalFilename()).thenReturn("budget_2021.pdf");
        when(fichier.getInputStream()).thenReturn(mock(java.io.InputStream.class));

        // 2. Création de l'objet
        BudgetaryDocument budgetaryDocument = new BudgetaryDocument();
        budgetaryDocument.setIntitule("Budget 2021");

        // 3. Appel de la méthode
        BudgetaryDocument result = budgetaryDocumentService.uploadBudgetaryDocument(
            budgetaryDocument, 
            fichier, 
            CategoryDocument.BUDGET // Correction de la casse
        );

        // 4. Assertions
        Assertions.assertNotNull(result.getId());
		assertTrue(result.getIntitule().endsWith(".pdf")); // Vérifie l'extension
		assertNotNull(result.getUploadDate());
		System.out.println("Nom de fichier généré : " + result.getIntitule());
    }

}
