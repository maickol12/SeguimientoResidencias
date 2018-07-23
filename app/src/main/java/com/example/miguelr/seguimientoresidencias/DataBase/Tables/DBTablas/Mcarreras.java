package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;



/**
 * Created by miguelr on 15/07/2018.
 */

public class Mcarreras {

    public static String table        =        "carreras";
    public static String idCarrera    =        "idCarrera";
    public static String iCreditos    =        "iCreditos";
    public static String vCarrera     =        "vCarrera";
    public static String bActivo      =        "bActivo";



    public static String createTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS "+table+"(" +
                        ""+idCarrera+"  INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        ""+vCarrera+"   TEXT," +
                        ""+iCreditos+"  INTEGER," +
                        ""+bActivo+"    INTEGER)";
        return sql;
    }


    public static String deleteTable() {
        return "DROP TABLE IF EXISTS "+table;
    }
}
