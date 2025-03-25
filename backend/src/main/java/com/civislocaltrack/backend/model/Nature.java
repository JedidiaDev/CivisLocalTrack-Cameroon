package com.civislocaltrack.backend.model;

import lombok.Data;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "nature")
@Data
public class Nature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nature")
    private Long id;

    @Column(name = "name_nature")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id_nature")
    private Nature parentNature;

    @OneToMany(mappedBy = "parentNature", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Nature> childrenNatures;

    @ManyToOne
    @JoinColumn(name = "type_document_id")
    private TypeDocument typeDocument;
    
}
