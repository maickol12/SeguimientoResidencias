package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;

import android.util.Log;

public class MEstados {
    public static String table    = "estados";
    public static String idEstado = "idEstado";
    public static String vEstado  =  "vEstado";
    public static String bactivo  =  "bActivo";

    public static String createTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS "+table+"(" +
                        ""+idEstado+" INTEGER,"+
                        ""+vEstado+"   TEXT," +
                        ""+bactivo+"  INTEGER)";

        return sql;
    }


    public static String deleteTable() {
        return "DROP TABLE IF EXISTS "+table;
    }
}
