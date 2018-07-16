package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;


/**
 * Created by miguelr on 15/07/2018.
 */

public class Malumnos {
    private static String table                         =   "alumnos";
    private static String idAlumno                      =   "idAlumno";
    private static String idCarrera                     =   "idCarrera";
    private static String idUsuario                     =   "idUsuario";
    private static String bSexo                         =   "bUsuario";
    private static String vNumeroControl                =   "vNumeroControl";
    private static String vNombre                       =   "vNombre";
    private static String vApellidoPaterno              =   "vApellidoPaterno";
    private static String vApellidoMaterno              =   "vApellidoMaterno";
    private static String vSemestre                     =   "vSemestre";
    private static String vPlanEstudios                 =   "vPlanEstudios";
    private static String dFechaIngreso                 =   "dFechaIngreso";
    private static String dFechaTermino                 =   "dFechaTermino";
    private static String iCreditosTotales              =   "iCreditosTotales";
    private static String iCreditosAcumulados           =   "iCreditosAcumulados";
    private static String fPorcentaje                   =   "fPorcentaje";
    private static String iPeriodo                      =   "iPeriodo";
    private static String fPromedio                     =   "fPromedio";
    private static String vSituacion                    =   "vSituacion";
    private static String bServicioSocial               =   "bServicioSocial";
    private static String bActividadesComplementarias   =   "bActividadesComplementarias";
    private static String bMateriasEspecial             =   "bMateriasEspecial";
    private static String vCorreoInstitucional          =   "vCorreoInstitucional";
    private static String dFechaNacimiento              =   "dFechaNacimiento";



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
