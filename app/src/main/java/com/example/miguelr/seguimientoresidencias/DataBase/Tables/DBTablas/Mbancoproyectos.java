package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;

/**
 * Created by miguelr on 23/07/2018.
 */

public class Mbancoproyectos {
    public static String table                 =        "bancoproyectos";
    public static String idbancoProyecto       =        "idbancoProyecto";
    public static String idEmpresa             =        "idEmpresa";
    public static String idCarrera             =        "idCarrera";
    public static String idEstado              =        "idEstado";
    public static String vNombreProyecto       =        "vNombreProyecto";
    public static String vDescripcion          =        "vDescripcion";
    public static String vDependencia          =        "vDependencia";
    public static String iTotalResidentes      =        "iTotalResidentes";


    public static String createTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS "+table+"(" +
                        ""+idbancoProyecto+" ,"+
                        ""+idEmpresa+"   INTEGER," +
                        ""+idCarrera+"  INTEGER," +
                        ""+idEstado+"    INTEGER,"+
                        ""+vNombreProyecto+"    TEXT,"+
                        ""+vDescripcion+"    TEXT,"+
                        ""+vDependencia+"    TEXT,"+
                        ""+iTotalResidentes+"    INTEGER)";
        return sql;
    }


    public static String deleteTable() {
        return "DROP TABLE IF EXISTS "+table;
    }
}
