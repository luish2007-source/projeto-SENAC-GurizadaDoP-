package com.nossoprojeto.petshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // aplica para todos os endpoints da API
                        .allowedOrigins("http://localhost:8080") // permite o front local
                        .allowedMethods("GET", "POST", "PUT", "DELETE"); // métodos permitidos
            }
        };
    }
}