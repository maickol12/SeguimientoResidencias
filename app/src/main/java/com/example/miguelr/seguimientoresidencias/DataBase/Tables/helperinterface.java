package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.ContentValues;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Alumnos;

/**
 * Created by miguelr on 16/07/2018.
 */

public interface helperinterface {
    public boolean guardar();
    public boolean borrar();
    public boolean buscar();
    public ContentValues contentValues();
}
