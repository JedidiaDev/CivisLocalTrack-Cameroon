package com.civislocaltrack.backend.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;
import lombok.Data;

@Component
@Entity
@Table(name = "budget")
@PrimaryKeyJoinColumn(name = "id_budgetary_document", referencedColumnName = "id_budgetary_document")
@Data
public class Budget extends BudgetaryDocument{
    
    @Id
    @Column(name = "id_budget", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBudget;

    @ManyToOne
    @JoinColumn(name = "id_nomenclature")
    private Nomenclature nomenclature;

    @Column(name = "allocated_amount")
    private double allocatedAmount;
}