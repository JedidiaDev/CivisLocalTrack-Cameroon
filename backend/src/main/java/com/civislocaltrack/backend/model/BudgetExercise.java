package com.civislocaltrack.backend.model;

import org.springframework.stereotype.Component;
import lombok.Data;

import jakarta.persistence.*;


@Entity
@Table(name = "budget_exercise")
@Component
@Data
public class BudgetExercise{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercise")
    private Long id;

    @Column(name = "year")
    @Temporal(TemporalType.DATE)
    private Integer year;

}
