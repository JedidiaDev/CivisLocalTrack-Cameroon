package com.civislocaltrack.backend.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nomenclature")
@Data
public class Nomenclature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nomenclature", nullable = false)
    private Long id;

    @Column(name = "label_nomenclature")
    private String label;

    @OneToMany(mappedBy = "nomenclature" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Budget> budgets;

    @OneToMany(mappedBy = "nomenclature" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ManagementAccount> managementAccounts;
}
