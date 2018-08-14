package com.example.miguelr.seguimientoresidencias.menuPrincipal.Model;

import android.net.Uri;

public class archivos {
    /* tipoArchivo
         1-carta Presentacion
         2-carta de aceptacion
         3-reporte 1
         4-reporte 2
         5-reporte 3
     */
    public int tipoArchivo;
    public Uri ruta;

    public int getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(int tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }


    public Uri getRuta() {
        return ruta;
    }

    public void setRuta(Uri ruta) {
        this.ruta = ruta;
    }
}
