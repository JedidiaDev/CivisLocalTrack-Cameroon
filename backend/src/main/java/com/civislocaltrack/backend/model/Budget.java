package com.civislocaltrack.backend.model;

import java.util.List;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "budget")
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("BUDGET")
public class Budget extends BudgetaryDocument{

    @Column(name = "allocated_amount")
    private double allocatedAmount;

    @ManyToOne
    @JoinColumn(name = "id_nomenclature", foreignKey = @ForeignKey(name = "fk_budget_nomenclature"))
    private Nomenclature nomenclature;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeDocument> typeDocument;

}