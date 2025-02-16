package com.sitema.blog.sistema_blog_spring_boot.repositorio;

import com.sitema.blog.sistema_blog_spring_boot.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositorio extends JpaRepository<Rol,Long> {

    Optional<Rol> findByNombre(String nombre);

}
