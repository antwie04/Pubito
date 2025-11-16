package com.pubito.pubito_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(nullable = false)
    private String roleName;

    public Roles(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank String getRoleName() {
        return roleName;
    }

    public void setRoleName(@NotBlank String roleName) {
        this.roleName = roleName;
    }
}
