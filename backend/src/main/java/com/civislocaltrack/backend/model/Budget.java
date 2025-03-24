package com.civislocaltrack.backend.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Component
@Entity
@Table(name = "budget")
@Data
public class Budget extends BudgetaryDocument{
    
    @Id
    @Column(name = "id_budget", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBudget;



    @Column(name = "allocated_amount")
    private double allocatedAmount;

    @Column(name = "nomenclature_adopted")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nomenclature", foreignKey = @ForeignKey(name = "fk_budget_nomenclature"))
    private Nomenclature nomenclatureAdopted;

}