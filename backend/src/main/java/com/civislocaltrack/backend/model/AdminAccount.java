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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Component
@Entity
@Table(name = "admin_account")
@Data
@PrimaryKeyJoinColumn(name = "id_budgetary_document")
public class AdminAccount extends BudgetaryDocument{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin_account", nullable = false, unique = true, updatable = false)
    private Long idAdminAccount;

    @Column(name = "total_expenditure")
    private double totalExpenditure;

    @Column(name = "total_revenue")
    private double totalRevenue;

    @Column(name = "allocated_amount")
    private double allocatedAmount;

    @OneToMany(mappedBy = "admin_account" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TypeDocument> typeDocument;

}
