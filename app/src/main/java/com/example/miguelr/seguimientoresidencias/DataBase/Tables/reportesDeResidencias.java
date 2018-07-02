package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.Helper.config;

import java.util.ArrayList;

/**
 * Created by miguelr on 08/04/2018.
 */

public class reportesDeResidencias {
    private String TableName = "reportesDeResidencias";
    public String iIdReporte = "iIdReporte";
    public String dFechaCaptura = "dFechaCaptura";
    public String iIdSolicitudfk = "iIdSolicitudfk";
    public String iAprobadoAcesorInterno = "iAprobadoAcesorInterno";
    public String iAprobadoJefeDeCarrera = "iAprobadoJefeDeCarrera";
    public String iHojaAsesoria = "iHojaAsesoria";
    public String vNumeroReporte  = "vNumeroReporte";
    public String vDescripcion = "vDescripcion";
    public String dFechaEntrega = "dFechaEntrega";
    public String bActive = "bActive";
    private String dFechaLimite = "dFechaLimite";
    public Context context;


    public reportesDeResidencias(){}
    public reportesDeResidencias(Context context){
        this.context = context;
    }

    public ArrayList<reportesDeResidencias> obtenerReportesPorSolicitud(String idSolicitud){
        ArrayList<reportesDeResidencias> reportesDeResidenciasList = null;
        SQLiteDatabase db = null;
        String sql = "SELECT "+this.getdFechaCaptura()+","+this.getiAprobadoAcesorInterno()+","+this.getiAprobadoJefeDeCarrera()+"," +
                     ""+this.getiHojaAsesoria()+","+this.getvNumeroReporte()+"," +this.getiIdReporte()+" "+
                     "FROM "+this.getTableName()+" WHERE "+this.getiIdSolicitudfk()+" = '"+idSolicitud+"'" +
                     " ORDER BY "+this.getvNumeroReporte();
        Cursor c = null;
        try{
            db = dbRedeable();
            c = db.rawQuery(sql,null);
            if(c.moveToFirst()){
                reportesDeResidenciasList = new ArrayList<>();
                reportesDeResidencias reporte = null;
                do{
                    reporte = new reportesDeResidencias();
                    reporte.setdFechaCaptura(c.getString(0));
                    reporte.setiAprobadoAcesorInterno(c.getString(1));
                    reporte.setiAprobadoJefeDeCarrera(c.getString(2));
                    reporte.setiHojaAsesoria(c.getString(3));
                    reporte.setvNumeroReporte(c.getString(4));
                    reporte.setiIdReporte(c.getString(5));
                    reportesDeResidenciasList.add(reporte);

                }while (c.moveToNext());
            }
        }catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        return reportesDeResidenciasList;
    }

    public boolean llenarDefault(){
        try{
            SQLiteDatabase db = dbWritable();
            db.delete(this.getTableName(),null,null);
            ContentValues cv = new ContentValues();
            cv.put(this.getiIdReporte(),"1");
            cv.put(this.getiIdSolicitudfk(),"1");
            cv.put(this.getiAprobadoAcesorInterno(),"1");
            cv.put(this.getiAprobadoJefeDeCarrera(),"1");
            cv.put(this.getiHojaAsesoria(),"1");
            cv.put(this.getvNumeroReporte(),"1");
            cv.put(this.getdFechaCaptura(),"26-04-2018");
            cv.put(this.getdFechaLimite(),"31-04-2018");
            cv.put(this.getvDescripcion(),"Entrega del reporte numero uno sin ningun inconveniente");
            cv.put(this.getdFechaEntrega(),"30-04-2018");

            db.insert(this.getTableName(),null,cv);

            cv.put(this.getiIdReporte(),"2");
            cv.put(this.getiIdSolicitudfk(),"1");
            cv.put(this.getiAprobadoAcesorInterno(),"0");
            cv.put(this.getiAprobadoJefeDeCarrera(),"0");
            cv.put(this.getiHojaAsesoria(),"1");
            cv.put(this.getvNumeroReporte(),"2");
            cv.put(this.getdFechaCaptura(),"26-05-2018");
            cv.put(this.getdFechaLimite(),"31-05-2018");
            cv.put(this.getvDescripcion(),"Entrega del reporte numero tres sin ningun inconveniente");
            cv.put(this.getdFechaEntrega(),"24-05-2018");

            db.insert(this.getTableName(),null,cv);

            cv.put(this.getiIdReporte(),"3");
            cv.put(this.getiIdSolicitudfk(),"1");
            cv.put(this.getiAprobadoAcesorInterno(),"1");
            cv.put(this.getiAprobadoJefeDeCarrera(),"1");
            cv.put(this.getiHojaAsesoria(),"1");
            cv.put(this.getvNumeroReporte(),"3");
            cv.put(this.getdFechaCaptura(),"26-06-2018");
            cv.put(this.getdFechaLimite(),"20-06-2018");
            cv.put(this.getvDescripcion(),"Entrega del reporte numero dos con algunos inconvenientes y no se acepto");
            cv.put(this.getdFechaEntrega(),"28-06-2018");

            db.insert(this.getTableName(),null,cv);

            return true;
        }catch (Exception e){
            return false;
        }


    }

    public reportesDeResidencias obtenerReportePorId(String idReporte){
        SQLiteDatabase db = dbRedeable();

        String sql = "SELECT "+this.getiIdReporte()+","+this.getvNumeroReporte()+","+this.getdFechaCaptura()+","+this.getdFechaEntrega()
                     +","+this.getdFechaLimite()+","+this.getvDescripcion()+" " +
                      "FROM "+this.getTableName()+" WHERE "+this.getiIdReporte()+" = '"+idReporte+"'";
        Cursor c = null;
        reportesDeResidencias rep = null;
        try{
            c = db.rawQuery(sql,null);
            if(c.moveToFirst()){
                rep = new reportesDeResidencias();
                rep.setiIdReporte(c.getString(0));
                rep.setvNumeroReporte(c.getString(1));
                rep.setdFechaCaptura(c.getString(2));
                rep.setdFechaEntrega(c.getString(3));
                rep.setdFechaLimite(c.getString(4));
                rep.setvDescripcion(c.getString(5));
                c.close();
            }
        }catch (Exception e){
            rep = null;
        }

        db.close();
        return rep;
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

    public String getiIdReporte() {
        return iIdReporte;
    }

    public void setiIdReporte(String iIdReporte) {
        this.iIdReporte = iIdReporte;
    }



    public String getdFechaCaptura() {
        return dFechaCaptura;
    }

    public void setdFechaCaptura(String dFechaCaptura) {
        this.dFechaCaptura = dFechaCaptura;
    }


    public String getiIdSolicitudfk() {
        return iIdSolicitudfk;
    }

    public String getdFechaLimite() {
        return dFechaLimite;
    }

    public void setdFechaLimite(String dFechaLimite) {
        this.dFechaLimite = dFechaLimite;
    }

    public void setiIdSolicitudfk(String iIdSolicitudfk) {
        this.iIdSolicitudfk = iIdSolicitudfk;
    }

    public String getiAprobadoAcesorInterno() {
        return iAprobadoAcesorInterno;
    }

    public void setiAprobadoAcesorInterno(String iAprobadoAcesorInterno) {
        this.iAprobadoAcesorInterno = iAprobadoAcesorInterno;
    }

    public String getiAprobadoJefeDeCarrera() {
        return iAprobadoJefeDeCarrera;
    }

    public void setiAprobadoJefeDeCarrera(String iAprobadoJefeDeCarrera) {
        this.iAprobadoJefeDeCarrera = iAprobadoJefeDeCarrera;
    }

    public String getiHojaAsesoria() {
        return iHojaAsesoria;
    }

    public void setiHojaAsesoria(String iHojaAsesoria) {
        this.iHojaAsesoria = iHojaAsesoria;
    }

    public String getvNumeroReporte() {
        return vNumeroReporte;
    }

    public void setvNumeroReporte(String vNumeroReporte) {
        this.vNumeroReporte = vNumeroReporte;
    }

    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }

    public String getdFechaEntrega() {
        return dFechaEntrega;
    }

    public void setdFechaEntrega(String dFechaEntrega) {
        this.dFechaEntrega = dFechaEntrega;
    }

    public String getbActive() {
        return bActive;
    }

    public void setbActive(String bActive) {
        this.bActive = bActive;
    }
}
