package com.sitema.blog.sistema_blog_spring_boot.configuracion;

import com.sitema.blog.sistema_blog_spring_boot.seguridad.CustomUserDetailsService;
import com.sitema.blog.sistema_blog_spring_boot.seguridad.JwtAuthenticationFilter;
import com.sitema.blog.sistema_blog_spring_boot.seguridad.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
    }
}
