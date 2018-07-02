package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.Context;

/**
 * Created by miguelr on 08/04/2018.
 */

public class observacionesDeSolicitudResidencias {
    public String iIdObservaciones = "iIdObservaciones";
    public String vDescripcion = "vDescripcion";
    public String iIdSolicitudDeResidencias = "iIdSolicitudDeResidencias";
    public String tableName = "observacionesDeSolicitudResidencias";
    private Context context;

    public observacionesDeSolicitudResidencias(){}
    public observacionesDeSolicitudResidencias(Context context){
        this.context = context;
    }


    public String getiIdObservaciones() {
        return iIdObservaciones;
    }

    public void setiIdObservaciones(String iIdObservaciones) {
        this.iIdObservaciones = iIdObservaciones;
    }

    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }

    public String getiIdSolicitudDeResidencias() {
        return iIdSolicitudDeResidencias;
    }

    public void setiIdSolicitudDeResidencias(String iIdSolicitudDeResidencias) {
        this.iIdSolicitudDeResidencias = iIdSolicitudDeResidencias;
    }
}
