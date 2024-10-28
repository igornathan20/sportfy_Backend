package com.sportfy.sportfy.dtos;

import java.time.OffsetDateTime;

public record ApoioSaudeResponseDto(
        Long idApoioSaude,
        String nome,
        String email,
        String telefone,
        String descricao,
        OffsetDateTime dataPublicacao,
        Long idAdministrador,
        boolean ativo
) {}

