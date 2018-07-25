package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mgiros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mopciones;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mperiodos;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

import java.util.ArrayList;

/**
 * Created by miguelr on 22/07/2018.
 */

public class Periodos {
    private Context context;
    private Common common;
    private SQLiteDatabase db;
    private int idPeriodo;
    private String vPeriodo;
    public Periodos(){}
    public Periodos(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.db      = this.common.databaseWritable();
    }
    public ArrayList<Periodos> obtenerPeriodos(){
        String sql = "SELECT "+Mperiodos.idPeriodo+","+Mperiodos.vPeriodo+
                " FROM "+Mperiodos.table;
        ArrayList<Periodos> periodos = null;
        try{
            Cursor c = db.rawQuery(sql,null);
            if(c.moveToFirst()){
                periodos = new ArrayList<>();
                do{
                    Periodos per = new Periodos();
                    per.setIdPeriodo(c.getInt(0));
                    per.setvPeriodo(c.getString(1));
                    periodos.add(per);
                }while (c.moveToNext());
            }
        }catch (Exception e){

        }
        return periodos;
    }


    public boolean borrar() {
        return db.delete(Mperiodos.table,null,null)>0;
    }
    public boolean guardar(){
        ContentValues cv = new ContentValues();
        cv.put(Mperiodos.idPeriodo,getIdPeriodo());
        cv.put(Mperiodos.vPeriodo,getvPeriodo());
        return db.insert(Mperiodos.table,null,cv)>0;
    }
    public void cerrarDB(){
        this.db.close();
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getvPeriodo() {
        return vPeriodo;
    }

    public void setvPeriodo(String vPeriodo) {
        this.vPeriodo = vPeriodo;
    }
    public String toString(){
        return this.vPeriodo;
    }
}
