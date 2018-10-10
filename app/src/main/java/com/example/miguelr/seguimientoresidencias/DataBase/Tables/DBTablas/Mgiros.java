package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Mgiros {
    public static String table  = "giros";
    public static String idGiro = "idGiro";
    public static String vGiro  = "vGiro";

    public static String createTable(){
        return "CREATE TABLE "+table+"(" +
                ""+idGiro+" INTEGER," +
                ""+vGiro+" TEXT)";
    }
    public static String deleteTable(){
        return "DROP TABLE IF EXISTS "+table;
    }
}
