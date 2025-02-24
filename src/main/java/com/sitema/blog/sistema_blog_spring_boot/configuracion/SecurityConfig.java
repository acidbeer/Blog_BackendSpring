package com.sitema.blog.sistema_blog_spring_boot.configuracion;

import com.sitema.blog.sistema_blog_spring_boot.seguridad.CustomUserDetailsService;
import com.sitema.blog.sistema_blog_spring_boot.seguridad.JwtAuthenticationEntryPoint;
import com.sitema.blog.sistema_blog_spring_boot.seguridad.JwtAuthenticationFilter;
import com.sitema.blog.sistema_blog_spring_boot.seguridad.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          CustomUserDetailsService customUserDetailsService,
                          JwtTokenProvider jwtTokenProvider) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()    ))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        // Cualquier usuario puede acceder a los métodos GET
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                        // Cualquier usuario puede acceder a la autenticación (login, registro)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Cualquier usuario autenticado puede crear comentarios
                        .requestMatchers(HttpMethod.POST, "/api/publicaciones/*/comentarios").authenticated()

                        // Solo ADMIN puede crear, modificar o eliminar publicaciones
                        .requestMatchers(HttpMethod.POST, "/api/publicaciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/publicaciones/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/publicaciones/**").hasRole("ADMIN")

                        // Solo ADMIN puede modificar o eliminar comentarios
                        .requestMatchers(HttpMethod.PUT, "/api/publicaciones/*/comentarios/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/publicaciones/*/comentarios/*").hasRole("ADMIN")

                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Nueva forma de configurar CORS en Spring Boot 3
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Permite el frontend de React
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
