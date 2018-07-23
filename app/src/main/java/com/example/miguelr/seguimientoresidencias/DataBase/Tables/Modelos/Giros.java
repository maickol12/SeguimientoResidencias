package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mgiros;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Giros {
    private Context context;
    private Common  common;
    private SQLiteDatabase db;
    private int idGiro;
    private String vGiro;
    public Giros(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.db      = this.common.databaseWritable();
    }
    public boolean borrar() {
        return db.delete(Mgiros.table,null,null)>0;
    }

    public boolean guardar(){
        ContentValues cv = new ContentValues();
        cv.put(Mgiros.idGiro,getIdGiro());
        cv.put(Mgiros.vGiro,getvGiro());
        return db.insert(Mgiros.table,null,cv)>0;
    }
    public void cerrarDB(){
        this.db.close();
    }

    public int getIdGiro() {
        return idGiro;
    }

    public void setIdGiro(int idGiro) {
        this.idGiro = idGiro;
    }

    public String getvGiro() {
        return vGiro;
    }

    public void setvGiro(String vGiro) {
        this.vGiro = vGiro;
    }
}
