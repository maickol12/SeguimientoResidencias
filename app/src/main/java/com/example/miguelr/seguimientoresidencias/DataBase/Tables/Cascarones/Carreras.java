package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Cascarones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DataBaseStructure;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Mcarreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.helperinterface;
import com.example.miguelr.seguimientoresidencias.Helper.config;

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

    public Carreras(){}
    public Carreras(Context context){
        this.context = context;
    }

    public void abrirDB(){
        mDB  = dbWritable();
    }
    public void cerrarDB(){
        mDB.close();
    }



    public List<Carreras> obtenerTodasLasCarrera(){
        SQLiteDatabase db = dbRedeable();

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

    public void LlenarCarrerasDefault(){
        SQLiteDatabase db = dbWritable();

        db.delete(Mcarreras.table,null,null);
        String[] car = {"Sistemas","Informatica","Civil","Gestion","Industrial","Contabilidad","Innovacion"};
        ContentValues cv = new ContentValues();
        for(int i = 0;i<car.length;i++){
            String nombre = car[i];
            cv.put(Mcarreras.idCarrera,(i+1));
            cv.put(Mcarreras.vCarrera,nombre);
            cv.put(Mcarreras.iCreditos,90+(i+1));
            long inser = db.insert(Mcarreras.table,null,cv);
            Log.d("insert",""+inser);
        }
    }

    public Carreras obtenerCarreraPorId(int idCarrera){
        SQLiteDatabase db = dbRedeable();
        String sql = "SELECT "+Mcarreras.vCarrera+","+Mcarreras.bActivo+" FROM "+Mcarreras.table+" WHERE "+Mcarreras.idCarrera+" = '"+idCarrera+"'";

        Cursor c = db.rawQuery(sql,null);
        Carreras carrera = null;
        if(c.moveToFirst()){
            carrera = new Carreras();
            carrera.setvCarrera(c.getString(c.getColumnIndex(Mcarreras.vCarrera)));
            carrera.setbActive(c.getInt(c.getColumnIndex(Mcarreras.bActivo)));
        }
        return carrera;
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

    @Override
    public boolean guardar() {
        ContentValues cv = new ContentValues();
        cv.put(Mcarreras.idCarrera,getIdCarrera());
        cv.put(Mcarreras.vCarrera,getvCarrera());
        cv.put(Mcarreras.bActivo,1);

        long i = mDB.insert(Mcarreras.table,null,cv);

        return i>0;
    }
    @Override
    public boolean borrar() {
        return mDB.delete(Mcarreras.table,null,null)>0;
    }

}
