package com.sitema.blog.sistema_blog_spring_boot.servicio;

import com.sitema.blog.sistema_blog_spring_boot.dto.PublicacionDTO;
import com.sitema.blog.sistema_blog_spring_boot.dto.PublicacionRespuesta;

import java.util.List;

public interface PublicacionServicio {

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    public PublicacionDTO obtenerPubicacionPorId(Long id);

    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id);

    public void eliminarPublicacion(Long id);
}
