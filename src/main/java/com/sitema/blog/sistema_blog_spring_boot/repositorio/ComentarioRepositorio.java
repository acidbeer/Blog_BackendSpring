package com.sitema.blog.sistema_blog_spring_boot.repositorio;

import com.sitema.blog.sistema_blog_spring_boot.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

    List<Comentario> findByPublicacionId(Long publicacionId);

}
