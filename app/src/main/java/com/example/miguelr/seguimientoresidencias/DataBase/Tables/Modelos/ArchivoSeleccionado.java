package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.MArchivosSeleccionados;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.helperinterface;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.sessionHelper;

import java.util.ArrayList;

public class ArchivoSeleccionado implements helperinterface {
    public int idArchivo;
    public String vRuta;
    public String dDate;
    public int idAlumno;
    public int tipoArchivo;
    public String UUID;
    public int bSyncFile;
    public int bSyncData;
    public int idProyectoSeleccionado;
    private Context context;
    private        Common       common;
    private sessionHelper session;

    public ArchivoSeleccionado(){}
    public ArchivoSeleccionado(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.session = new sessionHelper(context);
    }


    public int getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(int idArchivo) {
        this.idArchivo = idArchivo;
    }

    public int getIdProyectoSeleccionado() {
        return idProyectoSeleccionado;
    }

    public void setIdProyectoSeleccionado(int idProyectoSeleccionado) {
        this.idProyectoSeleccionado = idProyectoSeleccionado;
    }

    public String getvRuta() {
        return vRuta;
    }

    public void setvRuta(String vRuta) {
        this.vRuta = vRuta;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(int tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getbSyncFile() {
        return bSyncFile;
    }

    public void setbSyncFile(int bSyncFile) {
        this.bSyncFile = bSyncFile;
    }

    public int getbSyncData() {
        return bSyncData;
    }

    public void setbSyncData(int bSyncData) {
        this.bSyncData = bSyncData;
    }
    public boolean updateBitArchivo(String UUID){
        ContentValues cv = new ContentValues();
        cv.put(MArchivosSeleccionados.bSyncFile,1);
        SQLiteDatabase db = common.databaseWritable();
        long i = db.update(MArchivosSeleccionados.table,cv,MArchivosSeleccionados.UUID+" = '"+UUID+"'",null);
        db.close();
        return i>0;
    }
    public boolean updateBitDato(String UUID){
        ContentValues cv = new ContentValues();
        cv.put(MArchivosSeleccionados.bSyncData,1);
        SQLiteDatabase db = common.databaseWritable();
        long i = db.update(MArchivosSeleccionados.table,cv,MArchivosSeleccionados.UUID+" = '"+UUID+"'",null);
        db.close();
        return i>0;
    }
    public ArchivoSeleccionado obtenerInformacionArchivo(String UUID){
        SQLiteDatabase db = common.databaseWritable();
        String sql = "SELECT "+
                MArchivosSeleccionados.tipoArchivo+ ","+
                MArchivosSeleccionados.UUID+","+
                MArchivosSeleccionados.idAlumno+","+
                MArchivosSeleccionados.idProyecto+
                " FROM "+MArchivosSeleccionados.table+" WHERE "+MArchivosSeleccionados.bSyncData+" = 0 AND "+MArchivosSeleccionados.UUID+" = '"+UUID+"'";
        Cursor c = db.rawQuery(sql,null);
        ArchivoSeleccionado as = null;
        if(c.moveToFirst()){
                as                   = new ArchivoSeleccionado();
                as.setTipoArchivo(c.getInt(c.getColumnIndex(MArchivosSeleccionados.tipoArchivo)));
                as.setUUID(c.getString(c.getColumnIndex(MArchivosSeleccionados.UUID)));
                as.setIdAlumno(c.getInt(c.getColumnIndex(MArchivosSeleccionados.idAlumno)));
                as.setIdProyectoSeleccionado(c.getInt(c.getColumnIndex(MArchivosSeleccionados.idProyecto)));
        }
        db.close();
        return as;
    }
    public ArrayList<ArchivoSeleccionado> obtenerInformacionArchivosSincronizar(){
        ArrayList<ArchivoSeleccionado> archivoSeleccionados = null;
        SQLiteDatabase db = common.databaseWritable();
        String sql = "SELECT "+
                MArchivosSeleccionados.tipoArchivo+ ","+
                MArchivosSeleccionados.UUID+","+
                MArchivosSeleccionados.idAlumno+","+
                MArchivosSeleccionados.idProyecto+
                " FROM "+MArchivosSeleccionados.table+" WHERE "+MArchivosSeleccionados.bSyncData+" = 0";
        Cursor c = db.rawQuery(sql,null);
        ArchivoSeleccionado as = null;
        if(c.moveToFirst()){
           do{
               archivoSeleccionados = new ArrayList<>();
               as                   = new ArchivoSeleccionado();
               as.setTipoArchivo(c.getInt(c.getColumnIndex(MArchivosSeleccionados.tipoArchivo)));
               as.setUUID(c.getString(c.getColumnIndex(MArchivosSeleccionados.UUID)));
               as.setIdAlumno(c.getInt(c.getColumnIndex(MArchivosSeleccionados.idAlumno)));
               as.setIdProyectoSeleccionado(c.getInt(c.getColumnIndex(MArchivosSeleccionados.idProyecto)));
               archivoSeleccionados.add(as);
           }while(c.moveToNext());
        }
        db.close();
        return archivoSeleccionados;
    }

    public ArrayList<ArchivoSeleccionado> obtenerArchivosSincronizar(){
        ArrayList<ArchivoSeleccionado> archivoSeleccionados = null;
        SQLiteDatabase db = common.databaseWritable();
        String sql = "SELECT "+
                        MArchivosSeleccionados.tipoArchivo+ ","+
                        MArchivosSeleccionados.vRuta+","+
                        MArchivosSeleccionados.UUID+
                    " FROM "+MArchivosSeleccionados.table+" WHERE "+MArchivosSeleccionados.bSyncFile+" = 0";
        Cursor c = db.rawQuery(sql,null);
        ArchivoSeleccionado as = null;
        if(c.moveToFirst()){
            archivoSeleccionados = new ArrayList<>();
           do{
               as                   = new ArchivoSeleccionado();
               as.setvRuta(c.getString(c.getColumnIndex(MArchivosSeleccionados.vRuta)));
               as.setTipoArchivo(c.getInt(c.getColumnIndex(MArchivosSeleccionados.tipoArchivo)));
               as.setUUID(c.getString(c.getColumnIndex(MArchivosSeleccionados.UUID)));
               archivoSeleccionados.add(as);
           }while (c.moveToNext());
        }
        db.close();
        return archivoSeleccionados;
    }

    @Override
    public boolean guardar() {
        SQLiteDatabase db = common.databaseWritable();
        long i  = db.insert(MArchivosSeleccionados.table,null,this.contentValues());
        db.close();
        return i>0;
    }


    @Override
    public boolean borrar() {
        return false;
    }

    @Override
    public boolean buscar() {
        SQLiteDatabase db = common.databaseReadeable();
        String sql = "SELECT "+MArchivosSeleccionados.idArchivo+ " FROM "+MArchivosSeleccionados.table+" WHERE "+MArchivosSeleccionados.idAlumno+" = "+session.obtenerIdAlumno()+" AND "+MArchivosSeleccionados.tipoArchivo+" = "+this.tipoArchivo;
        long i = db.rawQuery(sql,null).getCount();
        return i>0;
    }

    @Override
    public ContentValues contentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MArchivosSeleccionados.vRuta,this.getvRuta());
        cv.put(MArchivosSeleccionados.dDate,this.getdDate());
        cv.put(MArchivosSeleccionados.idAlumno,this.getIdAlumno());
        cv.put(MArchivosSeleccionados.tipoArchivo,this.getTipoArchivo());
        cv.put(MArchivosSeleccionados.idProyecto,this.getIdProyectoSeleccionado());
        cv.put(MArchivosSeleccionados.UUID,this.getUUID());
        cv.put(MArchivosSeleccionados.bSyncData,this.getbSyncData());
        cv.put(MArchivosSeleccionados.bSyncFile,this.getbSyncFile());
        return cv;
    }
}
