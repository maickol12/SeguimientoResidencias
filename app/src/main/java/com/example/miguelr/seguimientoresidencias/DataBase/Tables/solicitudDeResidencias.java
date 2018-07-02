package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.Helper.config;

/**
 * Created by miguelr on 01/04/2018.
 */

public class solicitudDeResidencias {

    private String TableName = "solicitudDeResidencias";
    private String iIdSolicitudDeResidencias = "iIdSolicitudDeResidencias";
    private String iIdAlumnofk  = "iIdAlumnofk";
    private String iAprobadoPorAcademia = "iAprobadoPorAcademia";
    private String iAprobadoPorJefeDeCarrera = "iAprobadoPorJefeDeCarrera";
    private String dFechaAprobacion = "dFechaAprobacion";
    private Context context;

    public solicitudDeResidencias(){}
    public solicitudDeResidencias(Context context){
        this.context = context;
    }

    public boolean llenarSolicitudesPorDefault(){
        SQLiteDatabase db = dbWritable();
        db.delete(this.getTableName(),null,null);
        ContentValues cv = new ContentValues();
        cv.put(getiIdSolicitudDeResidencias(),1);
        cv.put(getiIdAlumnofk(),1);
        cv.put(getiAprobadoPorAcademia(),1);
        cv.put(getiAprobadoPorJefeDeCarrera(),1);
        cv.put(getdFechaAprobacion(),"2018-04-10");

        db.insert(getTableName(),null,cv);
        Log.d("dbmaickol", "SOL si");
        cv.put(getiIdSolicitudDeResidencias(),2);
        cv.put(getdFechaAprobacion(),"2018-03-09");
        cv.put(getiIdAlumnofk(),2);
        db.insert(getTableName(),null,cv);
        Log.d("dbmaickol", "SOL si");
        cv.put(getiIdSolicitudDeResidencias(),3);
        cv.put(getiIdAlumnofk(),3);
        cv.put(getdFechaAprobacion(),"2018-02-09");
        db.insert(getTableName(),null,cv);
        Log.d("dbmaickol", "SOL si");
        cv.put(getiIdSolicitudDeResidencias(),4);
        cv.put(getiIdAlumnofk(),4);
        cv.put(getdFechaAprobacion(),"2018-03-12");
        db.insert(getTableName(),null,cv);
        Log.d("dbmaickol", "SOL si");
        cv.put(getiIdSolicitudDeResidencias(),5);
        cv.put(getiIdAlumnofk(),5);
        cv.put(getdFechaAprobacion(),"2018-03-20");
        db.insert(getTableName(),null,cv);
        Log.d("dbmaickol", "SOL si");
        cv.put(getiIdSolicitudDeResidencias(),6);
        cv.put(getiIdAlumnofk(),6);
        cv.put(getdFechaAprobacion(),"2018-02-09");
        db.insert(getTableName(),null,cv);
        Log.d("dbmaickol", "SOL si");
        return true;
    }

    public solicitudDeResidencias obtenerSolicituPorUsuario(String idAlumno){
        SQLiteDatabase db = dbRedeable();
        solicitudDeResidencias sol = null;
        Cursor c = null;
        String sql = "SELECT "+this.getiIdSolicitudDeResidencias()+","+this.getiIdAlumnofk()+"," +
                             ""+this.getiAprobadoPorAcademia()+","+this.getiAprobadoPorJefeDeCarrera()+"," +
                             ""+this.getdFechaAprobacion()+" " +
                       " FROM "+this.getTableName()+""+
                       " WHERE "+this.getiIdAlumnofk()+" = '"+idAlumno+"'";
        try{
            c = db.rawQuery(sql,null);
            if(c.moveToFirst()){
                sol = new solicitudDeResidencias();
                sol.setiIdSolicitudDeResidencias(c.getString(0));
                sol.setiIdAlumnofk(c.getString(1));
                sol.setiAprobadoPorAcademia(c.getString(2));
                sol.setiAprobadoPorJefeDeCarrera(c.getString(3));
                sol.setdFechaAprobacion(c.getString(4));
            }
        }catch (Exception e){
            sol = null;
        }

        return sol;
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

    public String getiIdSolicitudDeResidencias() {
        return iIdSolicitudDeResidencias;
    }

    public void setiIdSolicitudDeResidencias(String iIdSolicitudDeResidencias) {
        this.iIdSolicitudDeResidencias = iIdSolicitudDeResidencias;
    }

    public String getiIdAlumnofk() {
        return iIdAlumnofk;
    }

    public void setiIdAlumnofk(String iIdAlumnofk) {
        this.iIdAlumnofk = iIdAlumnofk;
    }

    public String getiAprobadoPorAcademia() {
        return iAprobadoPorAcademia;
    }

    public void setiAprobadoPorAcademia(String iAprobadoPorAcademia) {
        this.iAprobadoPorAcademia = iAprobadoPorAcademia;
    }

    public String getiAprobadoPorJefeDeCarrera() {
        return iAprobadoPorJefeDeCarrera;
    }

    public void setiAprobadoPorJefeDeCarrera(String iAprobadoPorJefeDeCarrera) {
        this.iAprobadoPorJefeDeCarrera = iAprobadoPorJefeDeCarrera;
    }

    public String getdFechaAprobacion() {
        return dFechaAprobacion;
    }

    public void setdFechaAprobacion(String dFechaAprobacion) {
        this.dFechaAprobacion = dFechaAprobacion;
    }
}
