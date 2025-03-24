package com.civislocaltrack.backend.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "budgetary_document")
@Data
public abstract class BudgetaryDocument implements IBudgetaryDocument{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Long idDocument;

    @Column(name = "intutile_document")
    protected String intutileDocument;

    @Column(name = "url")
    protected String url;

    @Column(name = "publication_date")
    protected LocalDate publicationDate;

    @Column(name = "year")
    @ManyToOne
    @JoinColumn(name = "id_exercise", foreignKey = @ForeignKey(name = "fk_document_exercise"))
    private BudgetExercise year;
    
    private List<IBudgetaryDocument> documents;

    @Override
    public void consulter(){
        for(IBudgetaryDocument document : documents){
            document.consulter();
        }
    }

    @Override
    public void administrer(){
        for(IBudgetaryDocument document : documents){
            document.administrer();
        }
    }
}