package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.DadosAuthDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import com.sportfy.sportfy.services.AuthService;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin(
        origins = {"http://*", "http://localhost:8100", "http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/efetuarLogin")
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid DadosAuthDto dados) {
        try {
            Object token = authService.efetuarLogin(dados);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch (BadCredentialsException e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e){
            System.out.println("Erro " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
