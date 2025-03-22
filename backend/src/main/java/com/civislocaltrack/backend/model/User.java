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

    //Getters and Setters
    public Long getIdUser() {
        return id_user;
    }

    public String getNameUser() {
        return name_user;
    }

    public void setNameUser(String name_user) {
        this.name_user = name_user;
    }

    public String getMailUser() {
        return mail_user;
    }

    public void setMailUser(String mail_user) {
        this.mail_user = mail_user;
    }

    public int getTelUser() {
        return tel_user;
    }

    public void setTelUser(int tel_user) {
        this.tel_user = tel_user;
    }

    public String getPasswordUser() {
        return password_user;
    }

    public void setPasswordUser(String password_user) {
        this.password_user = password_user;
    }

    public Role getRole() {
        return role;
    }

    
}
