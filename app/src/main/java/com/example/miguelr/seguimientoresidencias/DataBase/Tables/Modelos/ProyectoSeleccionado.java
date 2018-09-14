package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.MproyectoSeleccionado;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

/**
 * Created by miguelr on 29/07/2018.
 */

public class ProyectoSeleccionado {
    private Context context;
    private Common common;
    private SQLiteDatabase db;


    public int idProyectoSeleccionado;
    public int idBancoProyecto;
    public int idAlumno;
    public int idPeriodo;
    public int idOpcion;
    public int idGiro;
    public int idEstado;
    public int idSector;
    public String vNombreProyecto;
    public String vDescripcion;
    public String vDependencia;
    public String vMotivoNoAceptacion;
    public int bCartaAceptacion;
    public int bCartaPresentacion;
    public int bSolicitud;
    public int bReporte1;
    public int bReporte2;
    public int bReporte3;

    public ProyectoSeleccionado(Context context){
        this.common = new Common(context);
        this.db     = this.common.databaseWritable();
    }

    public int obtenerIdProyectoSeleccionado(int idAlumno){
        String sql = "SELECT "+MproyectoSeleccionado.idProyectoSeleccionado+" FROM "+MproyectoSeleccionado.table+" WHERE "+MproyectoSeleccionado.idAlumno+" = "+idAlumno;
        Cursor c = db.rawQuery(sql,null);
        int idProyectoSeleccionado = 0;
        if(c.moveToFirst()){
            do{
                idProyectoSeleccionado = c.getInt(c.getColumnIndex(MproyectoSeleccionado.idProyectoSeleccionado));
            }while(c.moveToNext());
        }
        return idProyectoSeleccionado;
    }

    public boolean borrar(int idAlumno){
        String sql = "DELETE FROM "+MproyectoSeleccionado.table+" WHERE "+MproyectoSeleccionado.idAlumno+" = "+idAlumno;
        db.rawQuery(sql,null);
        return true;
    }

    public boolean guardar(){
        ContentValues cv = new ContentValues();
        cv.put(MproyectoSeleccionado.idProyectoSeleccionado,getIdProyectoSeleccionado());
        cv.put(MproyectoSeleccionado.idBancoProyecto,getIdBancoProyecto());
        cv.put(MproyectoSeleccionado.idAlumno,getIdAlumno());
        cv.put(MproyectoSeleccionado.idPeriodo,getIdPeriodo());
        cv.put(MproyectoSeleccionado.idOpcion,getIdOpcion());
        cv.put(MproyectoSeleccionado.idGiro,getIdGiro());
        cv.put(MproyectoSeleccionado.idEstado,getIdEstado());
        cv.put(MproyectoSeleccionado.idSector,getIdSector());
        /*cv.put(MproyectoSeleccionado.vNombreProyecto,getvNombreProyecto());
        cv.put(MproyectoSeleccionado.vDescripcion,getvDescripcion());
        cv.put(MproyectoSeleccionado.vDependencia,getvDependencia());*/
        cv.put(MproyectoSeleccionado.vMotivoNoAceptacion,getvMotivoNoAceptacion());
        //cv.put(MproyectoSeleccionado.bCartaAceptacion,getbCartaAceptacion());
        cv.put(MproyectoSeleccionado.bSolicitud,getbSolicitud());
        cv.put(MproyectoSeleccionado.bReporte1,getbReporte1());
        cv.put(MproyectoSeleccionado.bReporte2,getbReporte2());
        cv.put(MproyectoSeleccionado.bReporte3,getbReporte3());
        return db.insert(MproyectoSeleccionado.table,null,cv)>0;
    }
    public void cerrarDB(){
        this.db.close();
    }

    public int getIdProyectoSeleccionado() {
        return idProyectoSeleccionado;
    }

    public void setIdProyectoSeleccionado(int idProyectoSeleccionado) {
        this.idProyectoSeleccionado = idProyectoSeleccionado;
    }

    public int getIdBancoProyecto() {
        return idBancoProyecto;
    }

    public void setIdBancoProyecto(int idBancoProyecto) {
        this.idBancoProyecto = idBancoProyecto;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public int getIdGiro() {
        return idGiro;
    }

    public void setIdGiro(int idGiro) {
        this.idGiro = idGiro;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }

    public String getvNombreProyecto() {
        return vNombreProyecto;
    }

    public void setvNombreProyecto(String vNombreProyecto) {
        this.vNombreProyecto = vNombreProyecto;
    }

    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }

    public String getvDependencia() {
        return vDependencia;
    }

    public void setvDependencia(String vDependencia) {
        this.vDependencia = vDependencia;
    }

    public String getvMotivoNoAceptacion() {
        return vMotivoNoAceptacion;
    }

    public void setvMotivoNoAceptacion(String vMotivoNoAceptacion) {
        this.vMotivoNoAceptacion = vMotivoNoAceptacion;
    }

    public int getbCartaAceptacion() {
        return bCartaAceptacion;
    }

    public void setbCartaAceptacion(int bCartaAceptacion) {
        this.bCartaAceptacion = bCartaAceptacion;
    }

    public int getbCartaPresentacion() {
        return bCartaPresentacion;
    }

    public void setbCartaPresentacion(int bCartaPresentacion) {
        this.bCartaPresentacion = bCartaPresentacion;
    }

    public int getbSolicitud() {
        return bSolicitud;
    }

    public void setbSolicitud(int bSolicitud) {
        this.bSolicitud = bSolicitud;
    }

    public int getbReporte1() {
        return bReporte1;
    }

    public void setbReporte1(int bReporte1) {
        this.bReporte1 = bReporte1;
    }

    public int getbReporte2() {
        return bReporte2;
    }

    public void setbReporte2(int bReporte2) {
        this.bReporte2 = bReporte2;
    }

    public int getbReporte3() {
        return bReporte3;
    }

    public void setbReporte3(int bReporte3) {
        this.bReporte3 = bReporte3;
    }
}
