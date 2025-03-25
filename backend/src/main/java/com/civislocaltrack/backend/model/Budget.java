package com.civislocaltrack.backend.model;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private Nomenclature nomenclatureAdopted;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeDocument> typeDocument;

}