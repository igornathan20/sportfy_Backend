package com.sportfy.sportfy.models;

public enum Roles {
    ACADEMICO("Academico"),
    ADMIN("Administrador"),
    MASTER("Master");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
