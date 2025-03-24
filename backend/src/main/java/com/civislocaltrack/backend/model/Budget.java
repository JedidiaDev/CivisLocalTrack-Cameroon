package com.civislocaltrack.backend.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "budget")
public class Budget extends BudgetaryDocument{
    
    @Id
    @Column(name = "id_budget")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBudget;



    @Column(name = "allocated_amount")
    private double allocatedAmount;

    @Column(name = "nomenclature_adopted")
    @ManyToOne
    @JoinColumn(name = "id_nomenclature", foreignKey = @ForeignKey(name = "fk_budget_nomenclature"))
    private Nomenclature nomenclatureAdopted;
    
}
