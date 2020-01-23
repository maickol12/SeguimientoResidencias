package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

public class cascaronLineaTiempo {
    public String vTitulo;
    public String vAceptadoAI;
    public String vAceptadoAE;


    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }

    public String vDescripcion;

    public String getvTitulo() {
        return vTitulo;
    }

    public void setvTitulo(String vTitulo) {
        this.vTitulo = vTitulo;
    }

    public String getvAceptadoAI() {
        return vAceptadoAI;
    }

    public void setvAceptadoAI(String vAceptadoAI) {
        this.vAceptadoAI = vAceptadoAI;
    }

    public String getvAceptadoAE() {
        return vAceptadoAE;
    }

    public void setvAceptadoAE(String vAceptadoAE) {
        this.vAceptadoAE = vAceptadoAE;
    }
}
