package com.sitema.blog.sistema_blog_spring_boot.seguridad;

public class JWTAuthResponseDTO {
    private String tokenDeAcceso;

    public JWTAuthResponseDTO(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public String getTokenDeAcceso() {
        return tokenDeAcceso;
    }

    public void setTokenDeAcceso(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }
}
