package com.civislocaltrack.backend.model;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Component
@Entity
@Table(name = "management_account")
@Data
public class AdminAccount extends BudgetaryDocument{
    
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

    @OneToMany(mappedBy = "management_account" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TypeDocument> typeDocument;

}
