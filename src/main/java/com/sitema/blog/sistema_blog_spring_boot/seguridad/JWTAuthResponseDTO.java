package com.sitema.blog.sistema_blog_spring_boot.seguridad;

public class JWTAuthResponseDTO {
    private String tokenDeAcceso;
    private String tipoDetoken = "Bearer";

    public JWTAuthResponseDTO(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public JWTAuthResponseDTO(String tokenDeAcceso, String tipoDetoken) {
        this.tokenDeAcceso = tokenDeAcceso;
        this.tipoDetoken = tipoDetoken;
    }

    public String getTokenDeAcceso() {
        return tokenDeAcceso;
    }

    public void setTokenDeAcceso(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public String getTipoDetoken() {
        return tipoDetoken;
    }

    public void setTipoDetoken(String tipoDetoken) {
        this.tipoDetoken = tipoDetoken;
    }
}
