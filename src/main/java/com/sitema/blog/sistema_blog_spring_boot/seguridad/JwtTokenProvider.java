package com.sitema.blog.sistema_blog_spring_boot.seguridad;

import com.sitema.blog.sistema_blog_spring_boot.entidades.Usuario;
import com.sitema.blog.sistema_blog_spring_boot.excepciones.BlogAppExceptions;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final Key jwtSecretKey;
    private final int jwtExpirationInMs;

    public JwtTokenProvider(@Value("${app.jwt-secret}") String jwtSecret,
                            @Value("${app.jwt-expiration-milliseconds}") int jwtExpirationInMs) {

        if (jwtSecret.length() < 64) { // Validar que la clave sea segura
            throw new IllegalArgumentException("La clave JWT debe tener al menos 64 caracteres.");
        }

        this.jwtSecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generarToken(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); // ✅ Usar CustomUserDetails

        String username = userDetails.getUsername();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(username) // Email del usuario
                .claim("roles", roles) // Incluir roles en el token
                .issuedAt(fechaActual)
                .expiration(fechaExpiracion)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String obtenerUserNameDelJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Set<String> obtenerRolesDelJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles", Set.class);
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new BlogAppExceptions(HttpStatus.BAD_REQUEST, "Firma JWT no válida.");
        } catch (MalformedJwtException ex) {
            throw new BlogAppExceptions(HttpStatus.BAD_REQUEST, "Token JWT no válido.");
        } catch (ExpiredJwtException ex) {
            throw new BlogAppExceptions(HttpStatus.BAD_REQUEST, "Token JWT caducado.");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAppExceptions(HttpStatus.BAD_REQUEST, "Token JWT no compatible.");
        } catch (IllegalArgumentException ex) {
            throw new BlogAppExceptions(HttpStatus.BAD_REQUEST, "La cadena claims JWT está vacía.");
        }
    }

}
