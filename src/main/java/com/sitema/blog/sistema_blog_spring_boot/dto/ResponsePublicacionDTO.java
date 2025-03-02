package com.sitema.blog.sistema_blog_spring_boot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ResponsePublicacionDTO {

    private List<PublicacionTituloDTO> contenido;
    private int numeroPagina;
    private int medidaDePagina;
    private Long totalElementos;
    private int totalPaginas;
    private boolean ultima;

    public ResponsePublicacionDTO() {

    }

    public List<PublicacionTituloDTO> getContenido() {
        return contenido;
    }

    public void setContenido(List<PublicacionTituloDTO> contenido) {
        this.contenido = contenido;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public int getMedidaDePagina() {
        return medidaDePagina;
    }

    public void setMedidaDePagina(int medidaDePagina) {
        this.medidaDePagina = medidaDePagina;
    }

    public Long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(Long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }
}


