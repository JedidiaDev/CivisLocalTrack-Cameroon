package com.civislocaltrack.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "council")
public class Council {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_council", nullable = false)
    private Long id;

    @Column(name = "social_reason", nullable = false)
    private String name;

    @Column(name = "address_council")
    private String address;

    @Column(name = "tel_council")
    private String phone;

    @Column(name = "mail_council")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "description")
    private String description;

    @Column(name = "logo")
    private String logo;

    private float area;

    private float population;

    private float density;

    @Enumerated(EnumType.STRING)
    private CouncilType councilType;

    // @ManyToOne
    // @JoinColumn(name = "type_document_id")
    // private TypeDocument typeDocument;

    // @ManyToOne
    // @JoinColumn(name = "budget_id")
    // private Budget budget;

    // @ManyToOne
    // @JoinColumn(name = "admin_account_id")
    // private AdminAccount adminAccount;

    // @ManyToOne
    // @JoinColumn(name = "management_account_id")
    // private ManagementAccount managementAccount;

    public Council() {
    }

    public Council(String name, String address, String phone, String email, String website, String description, String logo, TypeDocument typeDocument, Budget budget, AdminAccount adminAccount, ManagementAccount managementAccount) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.description = description;
        this.logo = logo;
        // this.typeDocument = typeDocument;
        // this.budget = budget;
        // this.adminAccount = adminAccount;
        // this.managementAccount = managementAccount;
    }

    
}
