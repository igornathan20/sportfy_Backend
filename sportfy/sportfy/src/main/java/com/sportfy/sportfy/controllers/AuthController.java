package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.DadosAuthDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sportfy.sportfy.services.AuthService;

@RestController
@RequestMapping(value = "/login")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/efetuarLogin")
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid DadosAuthDto dados) {
        try {
            Object token = authService.efetuarLogin(dados);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
