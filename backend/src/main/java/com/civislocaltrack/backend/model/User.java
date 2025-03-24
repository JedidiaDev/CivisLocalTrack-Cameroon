package com.civislocaltrack.backend.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long id;

    @Column(name = "name_user", nullable = false)
    private String name;

    @Column(name = "mail_user")
    private String mail;

    @Column(name = "tel_user")
    private int tel;

    @Column(name = "password_user", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_role", foreignKey = @ForeignKey(name = "fk_user_role"))
    private Role role;
    
}