package com.civislocaltrack.backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "admin_account")
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("COMPTE_ADMIN")
public class AdminAccount extends BudgetaryDocument{

    @Column(name = "total_expenditure")
    private double totalExpenditure;

    @Column(name = "total_revenue")
    private double totalRevenue;

    @Column(name = "allocated_amount")
    private double allocatedAmount;

    @OneToMany(mappedBy = "adminAccount" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TypeDocument> typeDocument;

}
