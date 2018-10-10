package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Mopciones {
    public static String table      = "opciones";
    public static String idOpcion   = "idOpcion";
    public static String vOpcion    = "vOpcion";

    public static String createTable(){
        return "CREATE TABLE "+table+"("+
                idOpcion+" INTEGER,"+
                vOpcion+" TEXT)";
    }
    public static String deleteTable(){
        return "DROP TABLE IF EXISTS "+table;
    }
}
