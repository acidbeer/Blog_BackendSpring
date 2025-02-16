package com.sitema.blog.sistema_blog_spring_boot.dto;

import com.sitema.blog.sistema_blog_spring_boot.entidades.Comentario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class PublicacionDTO {

    private Long Id;

    @NotEmpty
    @Size(min = 2,message = "el titulo de la publicacion debe tener al menos 2 caracteres")
    private String titulo;
    @NotEmpty
    @Size(min = 10,message = "La descripccion de la publicacion deberia tener al menos 10 caracteres")
    private String descripcion;

    private String contenido;
    private Set<Comentario> comentarios;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public PublicacionDTO(){

    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    public PublicacionDTO(Long id, String titulo, String descripcion, String contenido) {
        Id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }
}
