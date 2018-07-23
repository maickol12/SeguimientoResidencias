package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

/**
 * Created by miguelr on 31/03/2018.
 */

public class cascaronAvance {
    private String idAvance;
    private String titulo;
    private String mensaje;
    private int aprobado;
    private int esreporte;


    public int getEsreporte() {
        return esreporte;
    }

    public void setEsreporte(int esreporte) {
        this.esreporte = esreporte;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getAprobado() {
        return aprobado;
    }

    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }

    public String getIdAvance() {
        return idAvance;
    }

    public void setIdAvance(String idAvance) {
        this.idAvance = idAvance;
    }
}
