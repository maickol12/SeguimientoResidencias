package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;

public class MArchivosSeleccionados {
    public static String table              = "archivoSeleccionado";
    public static String idArchivo          = "idArchivo";
    public static String vRuta              = "vRuta";
    public static String dDate              = "dDate";
    public static String idAlumno           = "idAlumno";
    public static String tipoArchivo        = "tipoArchivo";
    public static String idProyecto         = "idProyecto";
    public static String UUID               = "UUID";
    public static String bSyncFile          = "bSyncFile";
    public static String bSyncData          = "bSyncData";

    public static String createTable(){
        String sql =
                "CREATE TABLE IF NOT EXISTS "+table+"(" +
                        ""+idArchivo+"  INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        ""+vRuta+"   TEXT," +
                        ""+dDate+"  TEXT," +
                        ""+idAlumno+"    INTEGER,"+
                        ""+idProyecto+"  INTEGER,"+
                        ""+tipoArchivo+"    INTEGER,"+
                        ""+bSyncFile+"    INTEGER DEFAULT 0,"+
                        ""+bSyncData+"    INTEGER DEFAULT 0,"+
                        ""+UUID+"    INTEGER)";
        return sql;
    }
    public static String deleteTable() {
        return "DROP TABLE IF EXISTS "+table;
    }
}
