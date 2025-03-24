package com.civislocaltrack.backend.model;

/* Cette interface est la porte unique permettant de manipuler
 * les elements du model depuis le controleur et forme ainsi un 
 * contrat avec ce dernier.
 */

public interface IBudgetaryDocument {
    public void consulter();
    public void administrer();
}
