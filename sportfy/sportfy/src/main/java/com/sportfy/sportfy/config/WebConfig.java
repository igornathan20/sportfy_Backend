package com.sportfy.sportfy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Define que todas as rotas estão habilitadas para CORS
                .allowedOrigins("http://*","http://*", "http://localhost:3000") // Especifica quais origens podem acessar a API
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Define os métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(true); // Permite o envio de cookies ou credenciais
    }
}
