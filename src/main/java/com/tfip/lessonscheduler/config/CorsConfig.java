package com.tfip.lessonscheduler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for enabling Cross-Origin Resource Sharing (CORS) support
 * in the application. Implements the WebMvcConfigurer interface to customize
 * CORS mappings.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all paths
                .allowedOrigins("http://localhost:5173") // Specific origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed
                // HTTP methods
                .allowedHeaders("*") // Allowed headers
                .allowCredentials(true) // Allow sending cookies/auth headers
                .maxAge(3600); // How long the pre-flight request can be cached
    }
}
