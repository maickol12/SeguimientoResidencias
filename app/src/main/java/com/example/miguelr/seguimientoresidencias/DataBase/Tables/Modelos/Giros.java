package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mgiros;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

import java.util.ArrayList;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Giros {
    private Context context;
    private Common  common;
    private SQLiteDatabase db;
    private int idGiro;
    private String vGiro;
    public Giros(){}
    public Giros(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.db      = this.common.databaseWritable();
    }
    public ArrayList<Giros> obtenerGiros(){
        String sql = "SELECT "+Mgiros.idGiro+","+Mgiros.vGiro+
                " FROM "+Mgiros.table;
        ArrayList<Giros> giros = null;
        try{
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst()){
                giros = new ArrayList<>();
                do{
                    Giros gir = new Giros();
                    gir.setIdGiro(c.getInt(0));
                    gir.setvGiro(c.getString(1));
                    giros.add(gir);
                }while (c.moveToNext());
            }
        }catch (Exception e){

        }
        return giros;
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

    public String toString(){
        return this.vGiro;
    }
}
