package com.civislocaltrack.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.civislocaltrack.backend.model.BudgetaryDocument;
import com.civislocaltrack.backend.model.BudgetaryDocument.CategoryDocument;

@Repository
public interface BudgetaryDocumentRepository extends JpaRepository<BudgetaryDocument, Long> {
    List<BudgetaryDocument> findByCategoryDocument(CategoryDocument category);
    
}
