package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Malumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mcarreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mgiros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mopciones;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mperiodos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Msectores;

/**
 * Created by miguelr on 06/02/2018.
 */

public class DataBase extends SQLiteOpenHelper{


    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //VERSION 2
        db.execSQL(Malumnos.createTable());
        db.execSQL(Mcarreras.createTable());
        db.execSQL(Mgiros.createTable());
        db.execSQL(Mopciones.createTable());
        db.execSQL(Mperiodos.createTable());
        db.execSQL(Msectores.createTable());


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //VERSION 2
        db.execSQL(Malumnos.deleteTable());
        db.execSQL(Mcarreras.deleteTable());
        db.execSQL(Mgiros.deleteTable());
        db.execSQL(Mopciones.deleteTable());
        db.execSQL(Mperiodos.deleteTable());
        db.execSQL(Msectores.deleteTable());
        this.onCreate(db);
    }




}
