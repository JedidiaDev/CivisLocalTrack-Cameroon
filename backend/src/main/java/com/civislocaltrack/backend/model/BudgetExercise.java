package com.civislocaltrack.backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "budget_exercise")
public class BudgetExercise{
    private static BudgetExercise budgetExercise;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercise")
    private Long id_exercise;

    @Column(name = "year")
    @Temporal(TemporalType.DATE)
    private Date dateBudgetExercise;
    private List<Date> allExercises = new ArrayList<>();

    private BudgetExercise() {
        // private constructor to avoid instantiation from outside the class
    }

    public static synchronized BudgetExercise getInstance() {
        if (budgetExercise == null) 
            budgetExercise = new BudgetExercise();
        
        return budgetExercise;
    }

    public Long getId() {
        return id_exercise;
    }
    public Date getDateBudgetExercise() {
        return dateBudgetExercise;
    }

    public void addExercise() {
        allExercises.add(dateBudgetExercise);
    }

    public Date getLatestDate() {
        if (!allExercises.isEmpty()) {
            return allExercises.get(allExercises.size() - 1);
        }
        return null;
    }
}
