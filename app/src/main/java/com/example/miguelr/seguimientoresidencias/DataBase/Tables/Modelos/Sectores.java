package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mgiros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mperiodos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Msectores;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

import java.util.ArrayList;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Sectores {
    private Context context;
    private Common common;
    private SQLiteDatabase db;
    private int idSector;
    private String vSector;
    public Sectores(){}
    public Sectores(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.db      = this.common.databaseWritable();
    }
    public ArrayList<Sectores> obtenerSectores(){
        String sql = "SELECT "+Msectores.idSector+","+Msectores.vSector+
                " FROM "+Msectores.table;
        ArrayList<Sectores> sectores = null;
        try{
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst()){
                sectores = new ArrayList<>();
                do{
                    Sectores sec = new Sectores();
                    sec.setIdSector(c.getInt(0));
                    sec.setvSector(c.getString(1));
                    sectores.add(sec);
                }while (c.moveToNext());
            }
        }catch (Exception e){

        }
        return sectores;
    }
    public boolean borrar() {
        return db.delete(Msectores.table,null,null)>0;
    }
    public boolean guardar(){
        ContentValues cv = new ContentValues();
        cv.put(Msectores.idSector,getIdSector());
        cv.put(Msectores.vSector,getvSector());
        return db.insert(Msectores.table,null,cv)>0;
    }

    public void cerrarDB(){
        this.db.close();
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

    public String toString(){
        return this.vSector;
    }
}
