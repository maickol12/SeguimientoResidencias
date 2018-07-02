package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.Helper.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguelr on 14/03/2018.
 */

public class Carreras {
    private String TableName = "carreras";
    private String iIdCarrera = "iIdcarrera";
    private String vNombreCarrera = "vNombreCarrera";
    private String iCreditos = "iCreditos";
    private String bActive = "bActive";
    private Context context;

    public Carreras(){}
    public Carreras(Context context){
        this.context = context;
    }

    public String getTableName() {
        return TableName;
    }

    public List<Carreras> obtenerTodasLasCarrera(){
        SQLiteDatabase db = dbRedeable();

        String sql = "SELECT * FROM "+getTableName();

        Cursor c = db.rawQuery(sql,null);

        ArrayList<Carreras> carreras = null;
        if(c.moveToFirst()){
            carreras = new ArrayList<>();
            do{
                Carreras ca = new Carreras(context);
                ca.setvNombreCarrea(c.getString(c.getColumnIndex(this.getvNombreCarrera())));
                ca.setiIdCarrera(c.getString(c.getColumnIndex(this.getiIdCarrera())));
                carreras.add(ca);
            }while (c.moveToNext());
        }
        return carreras;
    }

    public void LlenarCarrerasDefault(){
        SQLiteDatabase db = dbWritable();

        db.delete(this.getTableName(),null,null);
        String[] car = {"Sistemas","Informatica","Civil","Gestion","Industrial","Contabilidad","Innovacion"};
        ContentValues cv = new ContentValues();
        for(int i = 0;i<car.length;i++){
            String nombre = car[i];
            cv.put(this.getiIdCarrera(),(i+1));
            cv.put(this.getvNombreCarrera(),nombre);
            cv.put(this.getiCreditos(),90+(i+1));
            long inser = db.insert(this.getTableName(),null,cv);
            Log.d("insert",""+inser);
        }
    }

    public Carreras obtenerCarreraPorId(int idCarrera){
        SQLiteDatabase db = dbRedeable();
        String sql = "SELECT * FROM "+getTableName()+" WHERE "+getiIdCarrera()+" = '"+idCarrera+"'";

        Cursor c = db.rawQuery(sql,null);
        Carreras carrera = null;
        if(c.moveToFirst()){
            carrera = new Carreras();
            carrera.setvNombreCarrea(c.getString(c.getColumnIndex(this.getvNombreCarrera())));
            carrera.setbActive(c.getString(c.getColumnIndex(this.getbActive())));
            carrera.setiCreditos(c.getString(c.getColumnIndex(this.getiCreditos())));
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

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getiIdCarrera() {
        return iIdCarrera;
    }

    public void setiIdCarrera(String iIdCarrera) {
        this.iIdCarrera = iIdCarrera;
    }

    public String getvNombreCarrera() {
        return vNombreCarrera;
    }

    public void setvNombreCarrea(String vNombreCarrea) {
        this.vNombreCarrera = vNombreCarrea;
    }

    public String getiCreditos() {
        return iCreditos;
    }

    public void setiCreditos(String iCreditos) {
        this.iCreditos = iCreditos;
    }

    public String getbActive() {
        return bActive;
    }

    public void setbActive(String bActive) {
        this.bActive = bActive;
    }
}
