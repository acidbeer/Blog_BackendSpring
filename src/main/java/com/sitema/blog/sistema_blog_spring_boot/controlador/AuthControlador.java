package com.sitema.blog.sistema_blog_spring_boot.controlador;

import com.sitema.blog.sistema_blog_spring_boot.dto.LoginDTO;
import com.sitema.blog.sistema_blog_spring_boot.dto.RegistroDTO;
import com.sitema.blog.sistema_blog_spring_boot.entidades.Rol;
import com.sitema.blog.sistema_blog_spring_boot.entidades.Usuario;
import com.sitema.blog.sistema_blog_spring_boot.repositorio.RolRepositorio;
import com.sitema.blog.sistema_blog_spring_boot.repositorio.UsuarioRepositorio;
import com.sitema.blog.sistema_blog_spring_boot.seguridad.CustomUserDetails;
import com.sitema.blog.sistema_blog_spring_boot.seguridad.JWTAuthResponseDTO;
import com.sitema.blog.sistema_blog_spring_boot.seguridad.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Obtenemos el token de JWTtoken provider
        String token = jwtTokenProvider.generarToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }


    @PostMapping("/registrar/admin")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO){

        if (usuarioRepositorio.existsByUsername(registroDTO.getUsername())){

            return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepositorio.existsByEmail(registroDTO.getEmail())){

            return new ResponseEntity<>("Ese Email de usuario ya existe",HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepositorio.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepositorio.save(usuario);
        return new ResponseEntity<>("Usuario Registrado exitosamente",HttpStatus.OK);
    }

    @PostMapping("/registar/user")
    public ResponseEntity<?>registrarUser(@RequestBody RegistroDTO registroDTO){

        if (usuarioRepositorio.existsByUsername(registroDTO.getUsername())){

            return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepositorio.existsByEmail(registroDTO.getEmail())){

            return new ResponseEntity<>("Ese Email de usuario ya existe",HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepositorio.findByNombre("ROLE_USER").get();
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepositorio.save(usuario);
        return new ResponseEntity<>("Usuario Registrado exitosamente",HttpStatus.OK);
    }
}
