package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mbancoproyectos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.helperinterface;
import com.example.miguelr.seguimientoresidencias.Helper.Common;

import java.util.ArrayList;

/**
 * Created by miguelr on 23/07/2018.
 */

public class bancoProyectos implements helperinterface {
    private int idbancoProyecto;
    private int idEmpresa;
    private int idCarrera;
    private int idEstado;
    private String vNombreProyecto;
    private String vDescripcion;
    private String vDependencia;
    private int iTotalResidentes;
    private SQLiteDatabase mDB;
    private Common common;
    private Context context;
    public bancoProyectos(){

    }
    public bancoProyectos(Context context){
        this.common = new Common(context);
        this.mDB    = this.common.databaseWritable();
    }
    public ArrayList<bancoProyectos> obtenerProyectos(int idCarrera){
        String sql = "SELECT "+Mbancoproyectos.idbancoProyecto+","+Mbancoproyectos.vNombreProyecto+
                     " FROM "+Mbancoproyectos.table+" WHERE "+Mbancoproyectos.idCarrera+" = "+idCarrera;
        ArrayList<bancoProyectos> proyectos = null;
        try{
            Cursor c = mDB.rawQuery(sql,null);
            if(c.moveToFirst()){
                proyectos = new ArrayList<>();
                do{
                    bancoProyectos ban = new bancoProyectos();
                    ban.setIdbancoProyecto(c.getInt(0));
                    ban.setvNombreProyecto(c.getString(1));
                    proyectos.add(ban);
                }while (c.moveToNext());
            }
        }catch (Exception e){

        }
        return proyectos;
    }


    public int getIdbancoProyecto() {
        return idbancoProyecto;
    }

    public void setIdbancoProyecto(int idbancoProyecto) {
        this.idbancoProyecto = idbancoProyecto;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
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

    public int getiTotalResidentes() {
        return iTotalResidentes;
    }

    public void setiTotalResidentes(int iTotalResidentes) {
        this.iTotalResidentes = iTotalResidentes;
    }


    @Override
    public boolean guardar() {
        long i = common.databaseWritable().insert(Mbancoproyectos.table,null,contentValues());

        return i>0;
    }

    @Override
    public boolean borrar() {

        return mDB.delete(Mbancoproyectos.table,null,null)>0;
    }
    public void cerrarDB(){
        mDB.close();
    }

    @Override
    public boolean buscar() {
        return false;
    }

    @Override
    public ContentValues contentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Mbancoproyectos.idbancoProyecto,getIdbancoProyecto());
        cv.put(Mbancoproyectos.idEmpresa,getIdEmpresa());
        cv.put(Mbancoproyectos.idCarrera,getIdCarrera());
        cv.put(Mbancoproyectos.idEstado,getIdEstado());
        cv.put(Mbancoproyectos.vNombreProyecto,getvNombreProyecto());
        cv.put(Mbancoproyectos.vDescripcion,getvDescripcion());
        cv.put(Mbancoproyectos.vDependencia,getvDependencia());
        cv.put(Mbancoproyectos.iTotalResidentes,getiTotalResidentes());
        return cv;
    }

    public String toString(){
        return this.vNombreProyecto;
    }
}
