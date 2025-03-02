package com.sitema.blog.sistema_blog_spring_boot.repositorio;

import com.sitema.blog.sistema_blog_spring_boot.dto.PublicacionTituloDTO;
import com.sitema.blog.sistema_blog_spring_boot.entidades.Publicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {


    Page<Publicacion> findAllBy(Pageable pageable);
}
