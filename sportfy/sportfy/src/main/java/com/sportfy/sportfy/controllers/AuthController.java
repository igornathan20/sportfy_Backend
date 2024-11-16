package com.sportfy.sportfy.controllers;

import com.sportfy.sportfy.dtos.DadosAuthDto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    AuthService authService;

    @PostMapping("/efetuarLogin")
    @PermitAll
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid DadosAuthDto dados) {
        try {
            Object token = authService.efetuarLogin(dados);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (BadCredentialsException | UsernameNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
