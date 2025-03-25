package com.civislocaltrack.backend.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.civislocaltrack.backend.model.BudgetaryDocument;

@Repository
public interface BudgetaryDocumentRepository extends JpaRepository<BudgetaryDocument, Long> {
    Optional<BudgetaryDocument> findById(Long id);
    boolean existsById(Long id);
    
}
