package com.umass.hangout.user_registeration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow requests from any origin (you can specify a specific domain instead of "*")
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*")  // Allow any origin (use specific origin like "http://localhost:3000" if needed)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")  // Allow any headers (adjust as needed)
                .allowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
    }
}