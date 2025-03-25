package com.civislocaltrack.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "management_account")
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("COMPTE_GESTION")
public class ManagementAccount extends BudgetaryDocument {

    @Column(name = "initial_budget")
    private double initialBudget;

    @Column(name = "forecast_budget")
    private double forecastBudget;

    @Column(name = "special_authorization")
    private double specialAuthorization;

    @Column(name = "payment")
    private double payment;

    @Column(name = "settlement_made")
    private double settlementMade;

    @ManyToOne
    @JoinColumn(name = "id_nomenclature", foreignKey = @ForeignKey(name = "fk_management_account_nomenclature"))
    private Nomenclature nomenclature;
    
}
