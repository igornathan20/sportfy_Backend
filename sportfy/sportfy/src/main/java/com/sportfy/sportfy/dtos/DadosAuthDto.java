package com.sportfy.sportfy.dtos;

import jakarta.validation.constraints.*;

public record DadosAuthDto (
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 4, max = 30, message = "Username deve ter entre 4 e 30 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username deve conter apenas letras, números, sublinhados (_), hífens (-), e pontos (.)")
    String username,

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 4, max = 100, message = "Senha deve ter entre 4 e 100 caracteres")
    String password
) {
    
}
