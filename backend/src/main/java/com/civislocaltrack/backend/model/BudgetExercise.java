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
    @Column(name = "id_exercise")
    private Long id;

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
