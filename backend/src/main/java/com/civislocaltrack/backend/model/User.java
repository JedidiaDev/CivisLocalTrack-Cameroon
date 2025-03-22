package com.civislocaltrack.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    private String name_user;
    private String mail_user;
    private int tel_user;
    private String password_user;

    @ManyToOne
    @JoinColumn(name = "id_role", foreignKey = @ForeignKey(name = "fk_user_role"))
    private Role role;

    
}
