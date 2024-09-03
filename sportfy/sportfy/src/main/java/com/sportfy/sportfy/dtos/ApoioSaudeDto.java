package com.sportfy.sportfy.dtos;

public record ApoioSaudeDto (
        Long idApoioSaude,
        String nome,
        String email,
        String telefone,
        String descricao,
        Long idAdministrador
) {}

