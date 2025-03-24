package com.civislocaltrack.backend.model;

import org.springframework.stereotype.Component;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;


@Entity
@Table(name = "budget_exercise")
@Component
@Data
public class BudgetExercise{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercise", nullable = false, unique = true, updatable = false)
    private Long id_exercise;

    @Column(name = "year")
    private Integer dateBudgetExercise;

    @Override
    public String toString() {
        return "Budget exercise of the year: "+dateBudgetExercise;
    }
}
