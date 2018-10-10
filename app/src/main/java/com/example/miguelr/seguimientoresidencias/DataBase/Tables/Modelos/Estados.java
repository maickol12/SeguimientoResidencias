package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.MEstados;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Malumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.helperinterface;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.sessionHelper;

public class Estados implements helperinterface {
    private int idEstado;
    private String vEstado;
    private int bactivo;
    private SQLiteDatabase mDB;

    private Context context;
    private Common common;
    private sessionHelper session;

    public Estados(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.session = new sessionHelper(context);
        this.mDB    = this.common.databaseWritable();
    }
    @Override
    public boolean guardar() {
        long i  = mDB.insert(MEstados.table,null,this.contentValues());
        return i>0;
    }

    @Override
    public boolean borrar() {
        return  mDB.delete(MEstados.table,null,null)>0;
    }

    @Override
    public boolean buscar() {
        return false;
    }

    public void close() {
        mDB.close();
    }

    @Override
    public ContentValues contentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MEstados.idEstado,this.getIdEstado());
        cv.put(MEstados.vEstado,this.getvEstado());
        cv.put(MEstados.bactivo,this.getBactivo());
        return cv;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getvEstado() {
        return vEstado;
    }

    public void setvEstado(String vEstado) {
        this.vEstado = vEstado;
    }

    public int getBactivo() {
        return bactivo;
    }

    public void setBactivo(int bactivo) {
        this.bactivo = bactivo;
    }
}
