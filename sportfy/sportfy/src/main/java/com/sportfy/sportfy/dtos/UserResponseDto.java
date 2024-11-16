package com.sportfy.sportfy.dtos;

public record UserResponseDto(
    Long idUsuario, 
    String username, 
    String role
) {
}
