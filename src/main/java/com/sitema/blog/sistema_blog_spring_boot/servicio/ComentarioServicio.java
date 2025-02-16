package com.sitema.blog.sistema_blog_spring_boot.servicio;

import com.sitema.blog.sistema_blog_spring_boot.dto.ComentarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ComentarioServicio {

    public ComentarioDTO crearComentario(Long publicacionId,ComentarioDTO comentarioDTO);

    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId);

    public ComentarioDTO obtenerComentarioPorId(Long publicacionId,Long comentarioId);

    public ComentarioDTO actualizarcomentario(Long publicacionId, Long comentarioId,ComentarioDTO solicitudDeComentario);

    public void eliminarcomentario(Long publicacionId, Long comentarioId);
}
