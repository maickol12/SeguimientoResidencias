package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.Helper.config;

/**
 * Created by miguelr on 08/04/2018.
 */

public class expedienteFinal {
    public String iIdExpedienteFinal = "iIdExpedienteFinal";
    public String vDescripcion = "vDescripcion";
    public String iProcesoFinalizado = "iProcesoFinalizado";
    public String iCalificacion = "iCalificacion";
    public String iIdSolicitudResidenciasfk = "iIdSolicitudResidenciasfk";
    public String TableName =  "expedienteFinal";
    private Alumnos alumno;
    private solicitudDeResidencias solicitud;
    public Context context;

    public expedienteFinal(){}
    public expedienteFinal(Context context){
        this.context = context;
    }

    public boolean llenarExpedienteDefault(){
        ContentValues cv = new ContentValues();
        cv.put(getiIdExpedienteFinal(),1);
        cv.put(getvDescripcion(),"Con algunos contratiempos en un reporte pero todo se termino con exito");
        cv.put(getiProcesoFinalizado(),1);
        cv.put(getiCalificacion(),90);
        cv.put(getiIdSolicitudResidenciasfk(),1);
        SQLiteDatabase db = dbWritable();
        db.delete(this.getTableName(),null,null);
        try{
            return db.insert(getTableName(),null,cv)>0;
        }catch (Exception e){
            return false;
        }
    }

    public expedienteFinal obtenerExpedientePorAlumno(int idAlumno){
       String sql = " SELECT al.vNombreAlumno,al.vApellidoPaterno,al.vApellidoMaterno,al.vMatricula,sol.dFechaAprobacion,ef.vDescripcion,ef.iProcesoFinalizado," +
               "        ef.iCalificacion,al.iSexo,al.iCreditos,al.dInicioServicio,al.dFinServicio,ca.vNombreCarrera FROM alumnos AS al " +
               "        INNER JOIN solicitudDeResidencias AS sol ON(sol.iIdAlumnofk = al.iIdAlumno)" +
               "        INNER JOIN expedienteFinal AS ef ON(ef.iIdSolicitudResidenciasfk = sol.iIdSolicitudDeResidencias)" +
               "        INNER JOIN carreras AS ca ON(al.iIdCarrerafk = ca.iIdcarrera)" +
               "        WHERE al.iIdAlumno = '"+idAlumno+"'";
       expedienteFinal exp = null;
       try {
           SQLiteDatabase db = dbRedeable();
           Cursor c = db.rawQuery(sql, null);

           if(c.moveToFirst()){
               exp = new expedienteFinal();
               exp.setAlumno(new Alumnos());
               exp.getAlumno().setvNombreAlumno(c.getString(0));
               exp.getAlumno().setvApellidoPaterno(c.getString(1));
               exp.getAlumno().setvApellidoMaterno(c.getString(2));
               exp.getAlumno().setvMatricula(c.getString(3));
               exp.setSolicitud(new solicitudDeResidencias());
               exp.getSolicitud().setdFechaAprobacion(c.getString(4));
               exp.setvDescripcion(c.getString(5));
               exp.setiProcesoFinalizado(c.getString(6));
               exp.setiCalificacion(c.getString(7));
               exp.getAlumno().setbSexo(c.getString(8));
               exp.getAlumno().setiCreditos(c.getString(9));
               exp.getAlumno().setdInicioServicio(c.getString(10));
               exp.getAlumno().setdFinServicio(c.getString(11));
               exp.getAlumno().setCarrera(new Carreras());
               exp.getAlumno().getCarrera().setvNombreCarrea(c.getString(12));
           }
       }catch (Exception e){
           Log.d("error",e.getMessage());
       }


       return exp;
    }

    public SQLiteDatabase dbRedeable(){
        DataBaseStructure help = new DataBaseStructure(context, config.dbName,null,config.versionDB);
        SQLiteDatabase db = help.getReadableDatabase();
        return db;
    }
    public SQLiteDatabase dbWritable(){
        DataBaseStructure help = new DataBaseStructure(context, config.dbName,null,config.versionDB);
        SQLiteDatabase db = help.getWritableDatabase();
        return db;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getiIdExpedienteFinal() {
        return iIdExpedienteFinal;
    }

    public void setiIdExpedienteFinal(String iIdExpedienteFinal) {
        this.iIdExpedienteFinal = iIdExpedienteFinal;
    }

    public Alumnos getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumnos alumno) {
        this.alumno = alumno;
    }

    public solicitudDeResidencias getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(solicitudDeResidencias solicitud) {
        this.solicitud = solicitud;
    }

    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }

    public String getiProcesoFinalizado() {
        return iProcesoFinalizado;
    }

    public void setiProcesoFinalizado(String iProcesoFinalizado) {
        this.iProcesoFinalizado = iProcesoFinalizado;
    }

    public String getiCalificacion() {
        return iCalificacion;
    }

    public void setiCalificacion(String iCalificacion) {
        this.iCalificacion = iCalificacion;
    }

    public String getiIdSolicitudResidenciasfk() {
        return iIdSolicitudResidenciasfk;
    }

    public void setiIdSolicitudResidenciasfk(String iIdSolicitudResidenciasfk) {
        this.iIdSolicitudResidenciasfk = iIdSolicitudResidenciasfk;
    }
}
