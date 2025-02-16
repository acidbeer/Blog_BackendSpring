package com.sitema.blog.sistema_blog_spring_boot.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;


    private final CustomUserDetailsService customUserDetailsService;

    // Inyección a través del constructor en lugar de @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Obtenemos el token de la solicitud
        String token = obtenerJWTdeLaSolicitud(request);

        //Validamos el token
        if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)){

            //Obtenemos el username del token
            String username = jwtTokenProvider.obtenerUserNameDelJWT(token);

            // cargamos el usuario asociado al token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationtoken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
            authenticationtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //Establecemos la seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationtoken);
        }
        filterChain.doFilter(request,response);

    }

    //Bearertoken de Acceso
    private String obtenerJWTdeLaSolicitud(HttpServletRequest request){
        String bearerToken= request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }

}
