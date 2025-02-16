package com.sitema.blog.sistema_blog_spring_boot.excepciones;

import org.springframework.http.HttpStatus;

public class BlogAppExceptions extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus estado;
    private String mesage;

    public BlogAppExceptions(HttpStatus estado, String mesage) {
        super();
        this.estado = estado;
        this.mesage = mesage;
    }

    public BlogAppExceptions(HttpStatus estado, String mesage, String mesage1) {
        super();
        this.estado = estado;
        this.mesage = mesage;
        this.mesage = mesage1;
    }

    public HttpStatus getEstado() {
        return estado;
    }

    public void setEstado(HttpStatus estado) {
        this.estado = estado;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }
}