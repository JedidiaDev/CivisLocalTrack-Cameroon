package com.civislocaltrack.backend.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "type_document")
@Data
public class TypeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_document")
    private Long id;

    @Column(name = "name_type_document")
    private String name;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "admin_account_id")
    private AdminAccount adminAccount;

    @OneToMany(mappedBy = "typeDocument", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Nature> natures;



    
}
