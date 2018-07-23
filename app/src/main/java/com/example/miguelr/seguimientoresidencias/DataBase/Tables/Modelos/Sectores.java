package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mgiros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mperiodos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Msectores;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Sectores {
    private Context context;
    private Common common;
    private SQLiteDatabase db;
    private int idSector;
    private String vSector;
    public Sectores(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.db      = this.common.databaseWritable();
    }
    public boolean borrar() {
        return db.delete(Msectores.table,null,null)>0;
    }
    public boolean guardar(){
        ContentValues cv = new ContentValues();
        cv.put(Msectores.idSector,getIdSector());
        cv.put(Msectores.vSector,getvSector());
        return db.insert(Mperiodos.table,null,cv)>0;
    }

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }

    public String getvSector() {
        return vSector;
    }

    public void setvSector(String vSector) {
        this.vSector = vSector;
    }
}
