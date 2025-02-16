package com.sitema.blog.sistema_blog_spring_boot.repositorio;

import com.sitema.blog.sistema_blog_spring_boot.entidades.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {

}
