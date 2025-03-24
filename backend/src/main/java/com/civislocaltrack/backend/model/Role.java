package com.civislocaltrack.backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.GenerationType;

@Data
@Entity
@Table(name="role", catalog = "Differents roles des users")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false)
    private Long id;

    @Column(name = "libelle_role")
    private String libelleRole;

    @OneToMany(mappedBy = "role", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<User> users;
}
