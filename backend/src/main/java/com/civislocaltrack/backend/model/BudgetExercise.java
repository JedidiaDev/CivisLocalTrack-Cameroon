package com.civislocaltrack.backend.model;

import java.util.List;

import lombok.Data;

import jakarta.persistence.*;


@Entity
@Table(name = "budget_exercise")
@Data
public class BudgetExercise{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercise")
    private Long id;

    @Column(name = "year")
    private Integer year;

    @OneToMany(mappedBy = "year", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BudgetaryDocument> budgetaryDocuments;



}    
