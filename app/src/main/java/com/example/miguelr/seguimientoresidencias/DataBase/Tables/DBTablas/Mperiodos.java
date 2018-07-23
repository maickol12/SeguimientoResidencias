package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Mperiodos {
    public static String table      = "periodos";
    public static String idPeriodo  = "idPeriodo";
    public static String vPeriodo   = "vPeriodo";

    public static String createTable(){
        return "CREATE TABLE "+table+"(" +
                ""+idPeriodo+" INTEGER," +
                ""+vPeriodo+" TEXT)";
    }
    public static String deleteTable(){
        return "DELETE TABLE "+table;
    }
}
