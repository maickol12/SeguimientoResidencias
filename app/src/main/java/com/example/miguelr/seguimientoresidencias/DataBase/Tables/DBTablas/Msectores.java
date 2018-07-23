package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.helperinterface;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Msectores {
    public static String table      = "sectores";
    public static String idSector   = "idSector";
    public static String vSector    = "vSector";

    public static String createTable(){
        return "CREATE TABLE "+table+"(" +
                ""+idSector+" INTEGER," +
                ""+vSector+" TEXT)";
    }
    public static String deleteTable(){
        return "DELETE TABLE "+table;
    }
}
