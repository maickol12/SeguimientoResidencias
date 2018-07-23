package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mcarreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.helperinterface;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguelr on 14/03/2018.
 */

public class Carreras implements helperinterface {
    private int idCarrera;
    private String vCarrera;
    private int bActive;
    private Context context;
    private SQLiteDatabase mDB;
    private Common common;
    public Carreras(){}
    public Carreras(Context context){
        this.context = context;
        this.common  = new Common(context);
    }

    public void abrirDB(){
        mDB = common.databaseWritable();
    }
    public void cerrarDB(){
        mDB.close();
    }


    public List<Carreras> obtenerTodasLasCarrera(){
        SQLiteDatabase db = common.databaseReadeable();

        String sql = "SELECT "+Mcarreras.idCarrera+","+Mcarreras.vCarrera+","+Mcarreras.bActivo+" FROM "+Mcarreras.table;

        Cursor c = db.rawQuery(sql,null);

        ArrayList<Carreras> carreras = null;
        if(c.moveToFirst()){
            carreras = new ArrayList<>();
            Carreras ca = null;
            do{
                ca = new Carreras();
                ca.setIdCarrera(c.getInt(c.getColumnIndex(Mcarreras.idCarrera)));
                ca.setvCarrera(c.getString(c.getColumnIndex(Mcarreras.vCarrera)));
                carreras.add(ca);
            }while (c.moveToNext());
        }
        return carreras;
    }




    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getvCarrera() {
        return vCarrera;
    }

    public void setvCarrera(String vCarrera) {
        this.vCarrera = vCarrera;
    }

    public int getbActive() {
        return bActive;
    }

    public void setbActive(int bActive) {
        this.bActive = bActive;
    }

    public ArrayList<Carreras> obtenerCarreras(){
        ArrayList<Carreras> resultCarreras = null;
        String sql = "SELECT "+Mcarreras.idCarrera+","+Mcarreras.vCarrera+" FROM "+Mcarreras.table;
        Cursor c = mDB.rawQuery(sql,null);
        if(c.moveToFirst()){
            resultCarreras = new ArrayList<>();
            Carreras car = null;
                do{
                    car = new Carreras();
                    car.setIdCarrera(c.getInt(0));
                    car.setvCarrera(c.getString(1));
                    resultCarreras.add(car);
            }while (c.moveToNext());
        }
        return resultCarreras;
    }

    @Override
    public boolean guardar() {
        ContentValues cv = new ContentValues();
        cv.put(Mcarreras.idCarrera,getIdCarrera());
        cv.put(Mcarreras.vCarrera,getvCarrera());
        cv.put(Mcarreras.bActivo,1);

        long i = common.databaseWritable().insert(Mcarreras.table,null,cv);

        return i>0;
    }
    @Override
    public boolean borrar() {
        return mDB.delete(Mcarreras.table,null,null)>0;
    }

    @Override
    public boolean buscar() {
        return false;
    }

    @Override
    public ContentValues contentValues() {
        return null;
    }

    @Override
    public String toString(){
        return getvCarrera();
    }

}
