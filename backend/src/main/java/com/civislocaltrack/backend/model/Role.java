package com.civislocaltrack.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="role", catalog = "Differents roles des users")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelleRole;

    public void setLibelleRole(String libelleRole) {
        this.libelleRole = libelleRole;
    }
    public String getLibelleRole() {
        return libelleRole;
    }
}
