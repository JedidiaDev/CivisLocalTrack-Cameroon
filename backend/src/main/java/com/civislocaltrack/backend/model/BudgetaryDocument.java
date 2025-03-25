package com.civislocaltrack.backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "budgetary_document")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "category_document", discriminatorType = DiscriminatorType.STRING)
public class BudgetaryDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "intitule_document")
    protected String intitule;

    @Column(name = "url")
    protected String url;

    @Column(name = "type_document")
    protected String type;

    @Column(name = "publication_date")
    protected LocalDate publicationDate;

    @Column(name = "upload_date")
    protected LocalDateTime uploadDate;

    @Column(name = "size_document")
    protected Long size;

    @Column(name = "category_document", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private CategoryDocument categoryDocument;

    @ManyToOne
    @JoinColumn(name = "id_exercise", foreignKey = @ForeignKey(name = "fk_document_budget_exercise"))
    private BudgetExercise year;

    @ManyToOne
    @JoinColumn(name = "id_council", foreignKey = @ForeignKey(name = "fk_document_council"))
    private Council council;

    // Définition des catégories de documents
    public enum CategoryDocument {
        BUDGET, COMPTE_ADMIN, COMPTE_GESTION, AUTRE
    }

    @PrePersist
    public void setDefaultCategory() {
        if (this.categoryDocument == null && this.getClass() == BudgetaryDocument.class) {
            this.categoryDocument = CategoryDocument.AUTRE; // Défaut pour les docs génériques
        }
    }

}