package com.civislocaltrack.backend.model;

import java.util.List;

public abstract class BudgetaryDocument implements IBudgetaryDocument{

    protected String intutileDocument;
    protected String url;
    protected String publicationDate;
    private List<IBudgetaryDocument> documents;
    public static final BudgetExercise date = BudgetExercise.getInstance();

}