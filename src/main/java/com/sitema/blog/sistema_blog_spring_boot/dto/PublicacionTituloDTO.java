package com.sitema.blog.sistema_blog_spring_boot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PublicacionTituloDTO {
    private Long Id;

    @NotEmpty
    @Size(min = 2,message = "el titulo de la publicacion debe tener al menos 2 caracteres")
    private String titulo;

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
}
