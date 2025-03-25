package com.civislocaltrack.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nomenclature")
@Data
public class Nomenclature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nomenclature")
    private Long id;

    @Column(name = "code_nomenclature")
    private String code;

    @Column(name = "label_nomenclature")
    private String label;

    @Column(name = "description_nomenclature")
    private String description;

    @Column(name = "type_nomenclature")
    private String type;

    @Column(name = "status_nomenclature")
    private String status;
    
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
