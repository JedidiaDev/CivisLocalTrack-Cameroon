package com.civislocaltrack.backend.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "management_account")
@Data
@PrimaryKeyJoinColumns({
    @PrimaryKeyJoinColumn(name = "id_nomenclature", referencedColumnName = "id_nomenclature"),
    @PrimaryKeyJoinColumn(name = "id_budgetary_document", referencedColumnName = "id_budgetary_document")
})
@Component
public class ManagementAccount extends BudgetaryDocument{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_management_account", nullable = false, unique = true, updatable = false)
    private Long idManagementAccount;

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

}
