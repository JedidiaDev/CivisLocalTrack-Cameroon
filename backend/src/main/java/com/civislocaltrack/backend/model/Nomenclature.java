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
import jakarta.persistence.Table;

@Entity
@Table(name = "nomenclature")
@Component
public class Nomenclature {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nomenclature", nullable = false, unique = true, updatable = false)
    private int idNomenclature;

    @Column(name = "nomenclature_name")
    private String nomenclatureName;

    @OneToMany(mappedBy = "nomenclature" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Budget> budgets;

    @OneToMany(mappedBy = "nomenclature" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ManagementAccount> managementAccounts;
}
