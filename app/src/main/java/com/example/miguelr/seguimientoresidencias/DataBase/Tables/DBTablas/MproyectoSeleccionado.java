package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;

/**
 * Created by miguelr on 29/07/2018.
 */

public class MproyectoSeleccionado {
    public static String table                  = "proyectoSeleccionado";

    public static String idProyectoSeleccionado = "idProyectoSeleccionado";
    public static String idBancoProyecto        = "idBancoProyecto";
    public static String idAlumno               = "idAlumno";
    public static String idPeriodo              = "idPeriodo";
    public static String idOpcion               = "idOpcion";
    public static String idGiro                 = "idGiro";
    public static String idEstado               = "idEstado";
    public static String idSector               = "idSector";
    public static String vNombreProyecto        = "vNombreProyecto";
    public static String vDescripcion           = "vDescripcion";
    public static String vDependencia           = "vDependencia";
    public static String vMotivoNoAceptacion    = "vMotivoNoAceptacion";
    public static String bCartaAceptacion       = "bCartaAceptacion";
    public static String bCartaPresentacion     = "bCartaPresentacion";
    public static String bSolicitud             = "bSolicitud";
    public static String bReporte1              = "bReporte1";
    public static String bReporte2              = "bReporte2";
    public static String bReporte3              = "bReporte3";

    public static String createTable(){
        return "CREATE TABLE "+table+"("+
                idProyectoSeleccionado+" INTEGER,"+
                idBancoProyecto+"       INTEGER,"+
                idAlumno+"              INTEGER,"+
                idPeriodo+"             INTEGER,"+
                idOpcion+"              INTEGER,"+
                idGiro+"                INTEGER,"+
                idEstado+"              INTEGER,"+
                idSector+"              INTEGER,"+
                vNombreProyecto+"       TEXT,"+
                vDescripcion+"          TEXT,"+
                vDependencia+"          TEXT,"+
                vMotivoNoAceptacion+"   TEXT,"+
                bCartaAceptacion+"      INTEGER,"+
                bCartaPresentacion+"    INTEGER,"+
                bSolicitud+"            INTEGER,"+
                bReporte1+"             INTEGER,"+
                bReporte2+"             INTEGER,"+
                bReporte3+"             INTEGER)";
    }
    public static String deleteTable(){
        return "DELETE FROM "+table;
    }
}
