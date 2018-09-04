package com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Malumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mcarreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.helperinterface;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.sessionHelper;


/**
 * Created by miguelr on 10/03/2018.
 */

public class Alumnos implements helperinterface {
    private int           idAlumno;
    private int           idCarrera;
    private int           idUsuario;
    private int           bSexo;
    private String        vNumeroControl;
    private String        vNombre;
    private String        vApellidoPaterno;
    private String        vApellidoMaterno;
    private int           vSemestre;
    private int           vPlanEstudios;
    private String        dFechaIngreso;
    private String        dFechaTermino;
    private int           iCreditosTotales;
    private int           iCreditosAcumulados;
    private double        fPorcentaje;
    private int           iPeriodo;
    private double        fPromedio;
    private String        vSituacion;
    private int           bServicioSocial;
    private int           bActividadesComplementarias;
    private int           bMateriasEspecial;
    private String        vCorreoInstitucional;
    private String        dFechaNacimiento;

    private        Context      context;
    private        Common       common;
    private        sessionHelper session;

    public Alumnos(Context context){
        this.context = context;
        this.common  = new Common(context);
        this.session = new sessionHelper(context);
    }


    @Override
    public boolean guardar() {
        SQLiteDatabase db = common.databaseWritable();
        long i  = db.insert(Malumnos.table,null,this.contentValues());
        db.close();
        return i>0;
    }

    @Override
    public boolean borrar() {
        SQLiteDatabase db = common.databaseWritable();
        long i = db.delete(Malumnos.table,Malumnos.idAlumno+" = "+getIdAlumno(),null);
        db.close();
        return i>0;
    }

    @Override
    public boolean buscar() {
        SQLiteDatabase db = common.databaseReadeable();
        String sql = "SELECT "+Malumnos.idAlumno+ " FROM "+Malumnos.table+" WHERE "+Malumnos.idAlumno+" = "+this.getIdAlumno();
        long i = db.rawQuery(sql,null).getCount();
        return i>0;
    }

    public String getNombreCarrera(){
        SQLiteDatabase db = common.databaseReadeable();
        String sql = "SELECT "+ Mcarreras.idCarrera+ ","+Mcarreras.vCarrera+" FROM "+Mcarreras.table+" WHERE "+Mcarreras.idCarrera+" = "+this.getIdCarrera();
        Cursor c = db.rawQuery(sql,null);
        String nombreCarrera = "";
        if(c.moveToFirst()){
            nombreCarrera = c.getString(c.getColumnIndex(Mcarreras.vCarrera));
        }
        return nombreCarrera;
    }

    public Alumnos obtenerAlumnoSession() {
        int idAlumno = session.obtenerIdAlumno();
        String sql = "SELECT "+
                         Malumnos.idAlumno+","+
                         Malumnos.vNombre+","+
                         Malumnos.vApellidoPaterno+","+
                         Malumnos.vApellidoMaterno+","+
                         Malumnos.vNumeroControl+", "+
                         Malumnos.idCarrera+" "+
                     "FROM "+Malumnos.table+" WHERE "+Malumnos.idAlumno+" = "+idAlumno;
        Cursor c = null;
        Alumnos al = null;
        SQLiteDatabase db = common.databaseReadeable();
        try{
            c = db.rawQuery(sql,null);
            if(c.moveToFirst()){
               al = new Alumnos(context);
               al.setIdAlumno(c.getInt(c.getColumnIndex(Malumnos.idAlumno)));
               al.setvNombre(c.getString(c.getColumnIndex(Malumnos.vNombre)));
               al.setvApellidoPaterno(c.getString(c.getColumnIndex(Malumnos.vApellidoPaterno)));
               al.setvApellidoMaterno(c.getString(c.getColumnIndex(Malumnos.vApellidoMaterno)));
               al.setvNumeroControl(c.getString(c.getColumnIndex(Malumnos.vNumeroControl)));
               al.setIdCarrera(c.getInt(c.getColumnIndex(Malumnos.idCarrera)));
            }
        }catch (Exception e){
            Log.d("error",e.getMessage());
        }

        return al;
    }

    @Override
    public ContentValues contentValues() {
        ContentValues cv = new ContentValues();
        cv.put("idAlumno",this.getIdAlumno());
        cv.put("idCarrera",this.getIdCarrera());
        cv.put("idUsuario",this.getIdAlumno());
        cv.put("bSexo",this.getbSexo());
        cv.put("vNumeroControl",this.getvNumeroControl());
        cv.put("vNombre",this.getvNombre());
        cv.put("vApellidoPaterno",this.getvApellidoPaterno());
        cv.put("vApellidoMaterno",this.getvApellidoMaterno());
        cv.put("vSemestre",this.getvSemestre());
        cv.put("vPlanEstudios",this.getvPlanEstudios());
        cv.put("dFechaIngreso",this.getdFechaIngreso());
        cv.put("dFechaTermino",this.getdFechaTermino());
        cv.put("iCreditosTotales",this.getiCreditosTotales());
        cv.put("iCreditosAcumulados",this.getiCreditosAcumulados());
        cv.put("fPorcentaje",this.getfPorcentaje());
        cv.put("iPeriodo",this.getiPeriodo());
        cv.put("fPromedio",this.getfPromedio());
        cv.put("vSituacion",this.getvSituacion());
        cv.put("bServicioSocial",this.getbServicioSocial());
        cv.put("bActividadesComplementarias",this.getbActividadesComplementarias());
        cv.put("bMateriasEspecial",this.getbMateriasEspecial());
        cv.put("vCorreoInstitucional",this.getvCorreoInstitucional());
        cv.put("dFechaNacimiento",this.getdFechaNacimiento());
        return cv;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getbSexo() {
        return bSexo;
    }

    public void setbSexo(int bSexo) {
        this.bSexo = bSexo;
    }

    public String getvNumeroControl() {
        return vNumeroControl;
    }

    public void setvNumeroControl(String vNumeroControl) {
        this.vNumeroControl = vNumeroControl;
    }

    public String getvNombre() {
        return vNombre;
    }

    public void setvNombre(String vNombre) {
        this.vNombre = vNombre;
    }

    public String getvApellidoPaterno() {
        return vApellidoPaterno;
    }

    public void setvApellidoPaterno(String vApellidoPaterno) {
        this.vApellidoPaterno = vApellidoPaterno;
    }

    public String getvApellidoMaterno() {
        return vApellidoMaterno;
    }

    public void setvApellidoMaterno(String vApellidoMaterno) {
        this.vApellidoMaterno = vApellidoMaterno;
    }

    public int getvSemestre() {
        return vSemestre;
    }

    public void setvSemestre(int vSemestre) {
        this.vSemestre = vSemestre;
    }

    public int getvPlanEstudios() {
        return vPlanEstudios;
    }

    public void setvPlanEstudios(int vPlanEstudios) {
        this.vPlanEstudios = vPlanEstudios;
    }

    public String getdFechaIngreso() {
        return dFechaIngreso;
    }

    public void setdFechaIngreso(String dFechaIngreso) {
        this.dFechaIngreso = dFechaIngreso;
    }

    public String getdFechaTermino() {
        return dFechaTermino;
    }

    public void setdFechaTermino(String dFechaTermino) {
        this.dFechaTermino = dFechaTermino;
    }

    public int getiCreditosTotales() {
        return iCreditosTotales;
    }

    public void setiCreditosTotales(int iCreditosTotales) {
        this.iCreditosTotales = iCreditosTotales;
    }

    public int getiCreditosAcumulados() {
        return iCreditosAcumulados;
    }

    public void setiCreditosAcumulados(int iCreditosAcumulados) {
        this.iCreditosAcumulados = iCreditosAcumulados;
    }

    public double getfPorcentaje() {
        return fPorcentaje;
    }

    public void setfPorcentaje(double fPorcentaje) {
        this.fPorcentaje = fPorcentaje;
    }

    public int getiPeriodo() {
        return iPeriodo;
    }

    public void setiPeriodo(int iPeriodo) {
        this.iPeriodo = iPeriodo;
    }

    public double getfPromedio() {
        return fPromedio;
    }

    public void setfPromedio(double fPromedio) {
        this.fPromedio = fPromedio;
    }

    public String getvSituacion() {
        return vSituacion;
    }

    public void setvSituacion(String vSituacion) {
        this.vSituacion = vSituacion;
    }

    public int getbServicioSocial() {
        return bServicioSocial;
    }

    public void setbServicioSocial(int bServicioSocial) {
        this.bServicioSocial = bServicioSocial;
    }

    public int getbActividadesComplementarias() {
        return bActividadesComplementarias;
    }

    public void setbActividadesComplementarias(int bActividadesComplementarias) {
        this.bActividadesComplementarias = bActividadesComplementarias;
    }

    public int getbMateriasEspecial() {
        return bMateriasEspecial;
    }

    public void setbMateriasEspecial(int bMateriasEspecial) {
        this.bMateriasEspecial = bMateriasEspecial;
    }

    public String getvCorreoInstitucional() {
        return vCorreoInstitucional;
    }

    public void setvCorreoInstitucional(String vCorreoInstitucional) {
        this.vCorreoInstitucional = vCorreoInstitucional;
    }

    public String getdFechaNacimiento() {
        return dFechaNacimiento;
    }

    public void setdFechaNacimiento(String dFechaNacimiento) {
        this.dFechaNacimiento = dFechaNacimiento;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }
}
