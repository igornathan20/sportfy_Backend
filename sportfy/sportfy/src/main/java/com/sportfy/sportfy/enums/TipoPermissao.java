package com.sportfy.sportfy.enums;

public enum TipoPermissao {
    ACADEMICO("ACADEMICO"),
    ADMINISTRADOR("ADMINISTRADOR");

    private final String tipoPermissao;

    TipoPermissao(String tipoPermissao) {
        this.tipoPermissao = tipoPermissao;
    }

    public String getTipoPermissao() {
        return tipoPermissao;
    }

    @Override
    public String toString() {
        return tipoPermissao;
    }
}
