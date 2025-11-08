package com.primaria.app.DTO;

public class CambiarEstatusDTO {
    
    private String estatus;

    public CambiarEstatusDTO() {}

    public CambiarEstatusDTO(String estatus) {
        this.estatus = estatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}