package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mgiros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mopciones;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Opciones {
    private Context context;
    private Common common;
    private SQLiteDatabase db;
    private int idOpcion;
    private String vOpcion;
    public Opciones(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.db      = this.common.databaseWritable();
    }
    public boolean borrar() {
        return db.delete(Mopciones.table,null,null)>0;
    }
    public boolean guardar(){
        ContentValues cv = new ContentValues();
        cv.put(Mopciones.idOpcion,getIdOpcion());
        cv.put(Mopciones.vOpcion,getvOpcion());
        return db.insert(Mopciones.table,null,cv)>0;
    }
    public void cerrarDB(){
        this.db.close();
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getvOpcion() {
        return vOpcion;
    }

    public void setvOpcion(String vOpcion) {
        this.vOpcion = vOpcion;
    }
}
