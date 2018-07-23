package com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas;


/**
 * Created by miguelr on 15/07/2018.
 */

public class Malumnos {
    public static String table                         =   "alumnos";
    public static String idAlumno                      =   "idAlumno";
    public static String idCarrera                     =   "idCarrera";
    public static String idUsuario                     =   "idUsuario";
    public static String bSexo                         =   "bSexo";
    public static String vNumeroControl                =   "vNumeroControl";
    public static String vNombre                       =   "vNombre";
    public static String vApellidoPaterno              =   "vApellidoPaterno";
    public static String vApellidoMaterno              =   "vApellidoMaterno";
    public static String vSemestre                     =   "vSemestre";
    public static String vPlanEstudios                 =   "vPlanEstudios";
    public static String dFechaIngreso                 =   "dFechaIngreso";
    public static String dFechaTermino                 =   "dFechaTermino";
    public static String iCreditosTotales              =   "iCreditosTotales";
    public static String iCreditosAcumulados           =   "iCreditosAcumulados";
    public static String fPorcentaje                   =   "fPorcentaje";
    public static String iPeriodo                      =   "iPeriodo";
    public static String fPromedio                     =   "fPromedio";
    public static String vSituacion                    =   "vSituacion";
    public static String bServicioSocial               =   "bServicioSocial";
    public static String bActividadesComplementarias   =   "bActividadesComplementarias";
    public static String bMateriasEspecial             =   "bMateriasEspecial";
    public static String vCorreoInstitucional          =   "vCorreoInstitucional";
    public static String dFechaNacimiento              =   "dFechaNacimiento";



    public static String createTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS "+table+"(" +
                ""+idAlumno+"                        INTEGER PRIMARY KEY AUTOINCREMENT," +
                ""+idCarrera+"                       INTEGER," +
                ""+idUsuario+"                       INTEGER," +
                ""+bSexo+"                           INTEGER," +
                ""+vNumeroControl+"                  TEXT," +
                ""+vNombre+"                         TEXT," +
                ""+vApellidoPaterno+"                TEXT," +
                ""+vApellidoMaterno+"                TEXT," +
                ""+vSemestre+"                       TEXT," +
                ""+vPlanEstudios+"                   TEXT," +
                ""+dFechaIngreso+"                   TEXT," +
                ""+dFechaTermino+"                   TEXT," +
                ""+iCreditosTotales+"                INTEGER," +
                ""+iCreditosAcumulados+"             INTEGER," +
                ""+fPorcentaje+"                     TEXT," +
                ""+iPeriodo+"                        INTEGER," +
                ""+fPromedio+"                       TEXT," +
                ""+vSituacion+"                      TEXT," +
                ""+bServicioSocial+"                 INTEGER," +
                ""+bActividadesComplementarias+"     INTEGER," +
                ""+bMateriasEspecial+"               INTEGER," +
                ""+vCorreoInstitucional+"            TEXT," +
                ""+dFechaNacimiento+"                TEXT)";
        return sql;
    }


    public static String deleteTable() {
        return "DROP TABLE IF EXISTS "+table;
    }
}
