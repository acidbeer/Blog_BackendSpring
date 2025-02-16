package com.sitema.blog.sistema_blog_spring_boot.repositorio;

import com.sitema.blog.sistema_blog_spring_boot.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;


public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {
    public Optional<Usuario> findByEmail(String email);

    public Optional<Usuario> findByUsernameOrEmail(String username, String email);

    public Optional<Usuario> findByUsername(String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);

}
