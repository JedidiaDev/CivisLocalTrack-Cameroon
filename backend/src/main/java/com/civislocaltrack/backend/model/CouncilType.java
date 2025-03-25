package com.civislocaltrack.backend.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "council_type")
public class CouncilType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_council_type", nullable = false)
    private Long id;

    @Column(name = "name_council_type", nullable = false)
    private String name;

    @Column(name = "description_council_type")
    private String description;

    @OneToMany(mappedBy = "councilType", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Council> councils;
}
