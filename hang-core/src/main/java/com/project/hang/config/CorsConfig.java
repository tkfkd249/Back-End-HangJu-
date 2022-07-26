package com.project.hang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowedOrigins(List.of("htt정p://localhost:8080"));
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedOrigins(List.of("http://129.154.195.162:8080"));
        config.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}