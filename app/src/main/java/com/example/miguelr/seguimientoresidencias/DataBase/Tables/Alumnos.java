package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.Helper.config;

import java.util.ArrayList;

/**
 * Created by miguelr on 10/03/2018.
 */

public class Alumnos {
    private String NameTable = "alumnos";
    private String iIdAlumno = "iIdAlumno";
    private String vNombreAlumno = "vNombreAlumno";
    private String vApellidoPaterno = "vApellidoPaterno";
    private String vApellidoMaterno = "vApellidoMaterno";
    private String vMatricula = "vMatricula";
    private String vCorreo = "vCorreo";
    private String vContrasenia = "vContrasenia";
    private String iIdCarrerafk = "iIdCarrerafk";
    private String iServicioSocial = "iServicioSocial";
    private String iAsignaturasEnEspecial = "iAsignaturasEnEspecial";
    private String iCreditosRequeridos = "iCreditosRequeridos";
    private String bSexo = "iSexo";
    private String iCreditos = "iCreditos";
    private String dInicioServicio = "dInicioServicio";
    private String dFinServicio = "dFinServicio";
    private String vUsuarioCaptura = "vUsuarioCaptura";
    private String dFechaCaptura = "dFechaCaptura";
    private String vUsuarioModifica = "vUsuarioModifica";
    private String dFechaModifica = "dFechaModifica";
    private String bActive = "bActive";

    private Context context;
    private Carreras carrera;
    private solicitudDeResidencias solicitud;

    private static final String PREFERENCES_FILE = "SessionUsuario";
    private static final String ID_ALUMNO = "idAlumno";


    public Alumnos(Context context){
        setContext(context);
    }

    public Alumnos(){

    }

    public Alumnos(String iIdAlumno, String vNombreAlumno, String vApellidoPaterno, String vApellidoMaterno, String vMatricula, String vCorreo, String vContrasenia, String iIdCarrerafk, String iServicioSocial, String iAsignaturasEnEspecial, String iCreditosRequeridos, String bSexo, String dInicioServicio, String dFinServicio, String vUsuarioCaptura, String dFechaCaptura, String vUsuarioModifica, String dFechaModifica, String bActive) {
        this.iIdAlumno = iIdAlumno;
        this.vNombreAlumno = vNombreAlumno;
        this.vApellidoPaterno = vApellidoPaterno;
        this.vApellidoMaterno = vApellidoMaterno;
        this.vMatricula = vMatricula;
        this.vCorreo = vCorreo;
        this.vContrasenia = vContrasenia;
        this.iIdCarrerafk = iIdCarrerafk;
        this.iServicioSocial = iServicioSocial;
        this.iAsignaturasEnEspecial = iAsignaturasEnEspecial;
        this.iCreditosRequeridos = iCreditosRequeridos;
        this.bSexo = bSexo;
        this.dInicioServicio = dInicioServicio;
        this.dFinServicio = dFinServicio;
        this.vUsuarioCaptura = vUsuarioCaptura;
        this.dFechaCaptura = dFechaCaptura;
        this.vUsuarioModifica = vUsuarioModifica;
        this.dFechaModifica = dFechaModifica;
        this.bActive = bActive;
    }

    public int inicioSession(String usuario,String contrasenia){
        SQLiteDatabase db = dbRedeable();

        try{
            String sql =
                    "SELECT iIdAlumno FROM "+getNameTable()+" " +
                    "WHERE ("+getvCorreo()+" = '"+usuario+"' OR "+getvMatricula()+" = '"+usuario+"') " +
                    "AND "+getvContrasenia()+" = '"+contrasenia+"'";
            Cursor c = db.rawQuery(sql,null);
            Log.d("sql",sql);

            if(c.moveToFirst()){
                return c.getInt(0);
            }else{
                return -1;
            }

        }catch (Exception e){
            Log.d("error",e.getMessage());
            return -2;
        }

    }

    public Alumnos buscarAlumnoPorId(int idAlumno){
        SQLiteDatabase db = dbRedeable();
        try{
            Carreras carreras = new Carreras();
            solicitudDeResidencias sol = new solicitudDeResidencias();
            String sql = "";
            sql = "SELECT " +
                        "a."+this.getiIdAlumno()+"," +
                        "a."+this.getvMatricula()+"," +
                        "a."+this.getiCreditosRequeridos()+"," +
                        "a."+this.getiCreditos()+"," +
                        "a."+this.getvNombreAlumno()+"," +
                        "a."+this.getvApellidoPaterno()+", " +
                        "a."+this.getvApellidoMaterno()+","+
                        "c."+carreras.getvNombreCarrera()+","+
                        "IFNULL(s."+sol.getiAprobadoPorJefeDeCarrera()+",0) AS "+sol.getiAprobadoPorJefeDeCarrera()+" ,"+
                        "IFNULL(s."+sol.getiAprobadoPorAcademia()+",0) AS "+sol.getiAprobadoPorAcademia()+""+
                  " FROM "+this.getNameTable()+" AS a " +
                  " LEFT JOIN "+carreras.getTableName()+" AS c ON (a."+this.getiIdCarrerafk()+" = c."+carreras.getiIdCarrera()+") " +
                  " LEFT JOIN "+sol.getTableName()+" AS s ON(s."+sol.getiIdAlumnofk()+" = a."+this.getiIdAlumno()+") "+
                  " WHERE a."+this.getiIdAlumno()+" = "+idAlumno+" LIMIT 1";
            Log.d("sql",sql);
            Cursor c = db.rawQuery(sql,null);
            Alumnos alumnos = null;
            if(c.moveToFirst()){
                alumnos = new Alumnos();
                alumnos.setiIdAlumno(c.getString(0));
                alumnos.setvMatricula(c.getString(1));
                alumnos.setiCreditosRequeridos(c.getString(2));
                alumnos.setiCreditos(c.getString(3));
                alumnos.setvNombreAlumno(c.getString(4));
                alumnos.setvApellidoPaterno(c.getString(5));
                alumnos.setvApellidoMaterno(c.getString(6));
                carreras.setvNombreCarrea(c.getString(7));
                sol.setiAprobadoPorAcademia(c.getString(8));
                sol.setiAprobadoPorJefeDeCarrera(c.getString(9));
                alumnos.setCarrera(carreras);
                alumnos.setSolicitud(sol);
            }
            db.close();
            return alumnos;
        }catch (Exception e){
            Log.d("error",e.getMessage());
            return null;
        }
    }

    public boolean guardarSession(int idAlumno){
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ID_ALUMNO,idAlumno);
            editor.apply();
            return true;
        }catch (Exception e){
            Log.d("error",e.getMessage());
            return false;
        }
    }
    public boolean cerrarSession(){
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ID_ALUMNO);
            editor.commit();
            return true;
        }catch (Exception e){
            Log.d("error",e.getMessage());
            return false;
        }
    }

    public int obtenerIdSession(){
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
            int idUsuario = sharedPreferences.getInt(ID_ALUMNO,-1);
            return idUsuario;
        }catch (Exception e){
            return -1;
        }
    }

    public int comprobarSession(){

        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
            int idUsuario = sharedPreferences.getInt(ID_ALUMNO,-1);
            return idUsuario;
        }catch (Exception e){
            return -1;
        }
    }

    public void llenarDatos(){
        SQLiteDatabase db = dbWritable();

        db.delete("alumnos",null,null);
        ContentValues values = new ContentValues();
        values.put(iIdAlumno,1);
        values.put(vNombreAlumno,"maickol");
        values.put(vApellidoMaterno,"rodriguez");
        values.put(vApellidoPaterno,"cornejo");
        values.put(vMatricula,"14020463");
        values.put(vCorreo,"al14020463@itsa.edu.mx");
        values.put(vContrasenia,"123");
        values.put(iIdCarrerafk,1);
        values.put(iCreditos,90);
        values.put(iServicioSocial,1);
        values.put(iAsignaturasEnEspecial,0);
        values.put(iCreditosRequeridos,1);
        values.put(bSexo,1);
        values.put(dInicioServicio,"10-03-2018");
        values.put(dFinServicio,"10-06-2018");
        long j = db.insert(NameTable,null,values);

        values.put(iIdAlumno,2);
        values.put(vNombreAlumno,"miguel angel");
        values.put(vMatricula,"14020464");
        values.put(vCorreo,"al14020464@itsa.edu.mx");
        values.put(vApellidoMaterno,"gomez");
        values.put(vApellidoPaterno,"medina");
        values.put(iIdCarrerafk,1);
        db.insert(NameTable,null,values);

        values.put(iIdAlumno,3);
        values.put(vNombreAlumno,"jose luis");
        values.put(vApellidoMaterno,"lopez");
        values.put(vCorreo,"al14020465@itsa.edu.mx");
        values.put(vMatricula,"14020465");
        values.put(vApellidoPaterno,"salto");
        values.put(iIdCarrerafk,1);
        db.insert(NameTable,null,values);

        values.put(iIdAlumno,4);
        values.put(vNombreAlumno,"uriel");
        values.put(vMatricula,"14020466");
        values.put(vCorreo,"al14020466@itsa.edu.mx");
        values.put(vApellidoMaterno,"salto");
        values.put(vApellidoPaterno,"equihua");
        values.put(iIdCarrerafk,1);
        db.insert(NameTable,null,values);

        values.put(iIdAlumno,5);
        values.put(vNombreAlumno,"guillermo");
        values.put(vApellidoMaterno,"sanchez");
        values.put(vMatricula,"14020467");
        values.put(vCorreo,"al14020467@itsa.edu.mx");
        values.put(vApellidoPaterno,"garcia");
        values.put(iIdCarrerafk,1);
        db.insert(NameTable,null,values);

        values.put(iIdAlumno,6);
        values.put(vNombreAlumno,"cristal");
        values.put(vApellidoMaterno,"oseguera");
        values.put(vCorreo,"al14020468@itsa.edu.mx");
        values.put(vMatricula,"14020468");
        values.put(bSexo,0);
        values.put(vApellidoPaterno,"medina");
        values.put(iIdCarrerafk,1);
        db.insert(NameTable,null,values);

        values.put(iIdAlumno,7);
        values.put(vNombreAlumno,"karolina");
        values.put(bSexo,0);
        values.put(vMatricula,"14020469");
        values.put(vCorreo,"al14020469@itsa.edu.mx");
        values.put(vApellidoMaterno,"equihua");
        values.put(vApellidoPaterno,"equihua");
        values.put(iIdCarrerafk,6);
        db.insert(NameTable,null,values);

        values.put(iIdAlumno,8);
        values.put(vNombreAlumno,"ramiro");
        values.put(bSexo,1);
        values.put(vMatricula,"14020470");
        values.put(vCorreo,"al14020470@itsa.edu.mx");
        values.put(vApellidoPaterno,"perez");
        values.put(vApellidoMaterno,"garcia");
        values.put(iIdCarrerafk,7);
        db.insert(NameTable,null,values);

        String[] nombres = {"luis","mariana","gonzalo","isabel","elizabet","aristides","carlos","vanesa","daniel","fernando"};
        String[] apellidos = {"sanchez","romero","equihua","sandoval","mendez","calderon","beltran","peñaloza","ontiveros","arreguin"};

        for(int i = 9;i<50;i++){
            values.put(iIdAlumno,i);
            int index = (int)(Math.random()*10);
            values.put(vNombreAlumno,nombres[index]);
            values.put(bSexo,1);
            values.put(vMatricula,"1402041"+i+"");
            values.put(vCorreo,"al1402041"+i+"@itsa.edu.mx");
            int index2 = (int)(Math.random()*10);
            values.put(vApellidoPaterno,apellidos[index2]);
            int index3 = (int)(Math.random()*10);
            values.put(vApellidoMaterno,apellidos[index3]);
            int carrera = (int)(Math.random()*8);
            values.put(iIdCarrerafk,carrera);
            Log.d("insertado","si "+carrera+" "+index);
            db.insert(NameTable,null,values);
        }

        solicitudDeResidencias sr = new solicitudDeResidencias();
        ContentValues srv = new ContentValues();
        srv.put(sr.getiIdAlumnofk(),1);
        srv.put(sr.getiAprobadoPorAcademia(),1);
        srv.put(sr.getiAprobadoPorJefeDeCarrera(),1);
        srv.put(sr.getdFechaAprobacion(),"01-04-2018");

        db.insert(sr.getTableName(),null,srv);

    }

    public ArrayList<Alumnos> obtenerTodosLosAlumnosPorCarrera(int idCarrera){

        SQLiteDatabase db = dbRedeable();
        Carreras carreras = new Carreras(context);
        ArrayList<Alumnos> alumnos = new ArrayList<>();

        String query = "SELECT * FROM "+getNameTable()+" AS al " +
                       "INNER JOIN "+carreras.getTableName()+" ca ON(al."+getiIdCarrerafk()+" = ca."+carreras.getiIdCarrera()+") " +
                       "WHERE "+carreras.getiIdCarrera()+" = "+idCarrera;

        Cursor c = db.rawQuery(query,null);
        carreras  = carreras.obtenerCarreraPorId(idCarrera);
        if(c.moveToFirst()){
            do{
                Alumnos al = new Alumnos();
                al.setiIdAlumno(c.getString(c.getColumnIndex(this.getiIdAlumno())));
                al.setvMatricula(c.getString(c.getColumnIndex(this.getvMatricula())));
                al.setbSexo(c.getString(c.getColumnIndex(this.getbSexo())));
                al.setvNombreAlumno(c.getString(c.getColumnIndex(this.getvNombreAlumno())));
                al.setvApellidoPaterno(c.getString(c.getColumnIndex(this.getvApellidoPaterno())));
                al.setvApellidoMaterno(c.getString(c.getColumnIndex(this.getvApellidoMaterno())));
                al.setCarrera(carreras);
                alumnos.add(al);
            }while (c.moveToNext());
        }

        return alumnos;
    }
    public ArrayList<Alumnos> obtenerTodosLosAlumnosPorCarreraYLike(int idCarrera,String like){

        SQLiteDatabase db = dbRedeable();
        Carreras carreras = new Carreras(context);
        ArrayList<Alumnos> alumnos = new ArrayList<>();

        String query = "SELECT * FROM "+getNameTable()+" AS al " +
                "INNER JOIN "+carreras.getTableName()+" ca ON(al."+getiIdCarrerafk()+" = ca."+carreras.getiIdCarrera()+") " +
                "WHERE "+carreras.getiIdCarrera()+" = "+idCarrera+" AND al."+getvMatricula()+" LIKE '"+like+"'";
        Log.d("sql",query);
        Cursor c = db.rawQuery(query,null);
        carreras  = carreras.obtenerCarreraPorId(idCarrera);
        if(c.moveToFirst()){
            do{
                Alumnos al = new Alumnos();
                al.setiIdAlumno(c.getString(c.getColumnIndex(this.getiIdAlumno())));
                al.setvMatricula(c.getString(c.getColumnIndex(this.getvMatricula())));
                al.setbSexo(c.getString(c.getColumnIndex(this.getbSexo())));
                al.setvNombreAlumno(c.getString(c.getColumnIndex(this.getvNombreAlumno())));
                al.setvApellidoPaterno(c.getString(c.getColumnIndex(this.getvApellidoPaterno())));
                al.setvApellidoMaterno(c.getString(c.getColumnIndex(this.getvApellidoMaterno())));
                al.setCarrera(carreras);
                alumnos.add(al);
            }while (c.moveToNext());
        }

        return alumnos;
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



    public String getiCreditos() {
        return iCreditos;
    }

    public void setiCreditos(String iCreditos) {
        this.iCreditos = iCreditos;
    }

    public String getNameTable() {
        return NameTable;
    }

    public void setNameTable(String nameTable) {
        NameTable = nameTable;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getiIdAlumno() {
        return iIdAlumno;
    }

    public void setiIdAlumno(String iIdAlumno) {
        this.iIdAlumno = iIdAlumno;
    }

    public String getvNombreAlumno() {
        return vNombreAlumno;
    }

    public void setvNombreAlumno(String vNombreAlumno) {
        this.vNombreAlumno = vNombreAlumno;
    }

    public Carreras getCarrera() {
        return carrera;
    }

    public void setCarrera(Carreras carrera) {
        this.carrera = carrera;
    }

    public String getvApellidoPaterno() {
        return vApellidoPaterno;
    }

    public void setvApellidoPaterno(String vApellidoPaterno) {
        this.vApellidoPaterno = vApellidoPaterno;
    }

    public solicitudDeResidencias getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(solicitudDeResidencias solicitud) {
        this.solicitud = solicitud;
    }

    public String getvApellidoMaterno() {
        return vApellidoMaterno;
    }

    public void setvApellidoMaterno(String vApellidoMaterno) {
        this.vApellidoMaterno = vApellidoMaterno;
    }

    public String getvMatricula() {
        return vMatricula;
    }

    public void setvMatricula(String vMatricula) {
        this.vMatricula = vMatricula;
    }

    public String getvCorreo() {
        return vCorreo;
    }

    public void setvCorreo(String vCorreo) {
        this.vCorreo = vCorreo;
    }

    public String getvContrasenia() {
        return vContrasenia;
    }

    public void setvContrasenia(String vContrasenia) {
        this.vContrasenia = vContrasenia;
    }

    public String getiIdCarrerafk() {
        return iIdCarrerafk;
    }

    public void setiIdCarrerafk(String iIdCarrerafk) {
        this.iIdCarrerafk = iIdCarrerafk;
    }

    public String getiServicioSocial() {
        return iServicioSocial;
    }

    public void setiServicioSocial(String iServicioSocial) {
        this.iServicioSocial = iServicioSocial;
    }

    public String getiAsignaturasEnEspecial() {
        return iAsignaturasEnEspecial;
    }

    public void setiAsignaturasEnEspecial(String iAsignaturasEnEspecial) {
        this.iAsignaturasEnEspecial = iAsignaturasEnEspecial;
    }

    public String getiCreditosRequeridos() {
        return iCreditosRequeridos;
    }

    public void setiCreditosRequeridos(String iCreditosRequeridos) {
        this.iCreditosRequeridos = iCreditosRequeridos;
    }

    public String getbSexo() {
        return bSexo;
    }

    public void setbSexo(String bSexo) {
        this.bSexo = bSexo;
    }

    public String getdInicioServicio() {
        return dInicioServicio;
    }

    public void setdInicioServicio(String dInicioServicio) {
        this.dInicioServicio = dInicioServicio;
    }

    public String getdFinServicio() {
        return dFinServicio;
    }

    public void setdFinServicio(String dFinServicio) {
        this.dFinServicio = dFinServicio;
    }

    public String getvUsuarioCaptura() {
        return vUsuarioCaptura;
    }

    public void setvUsuarioCaptura(String vUsuarioCaptura) {
        this.vUsuarioCaptura = vUsuarioCaptura;
    }

    public String getdFechaCaptura() {
        return dFechaCaptura;
    }

    public void setdFechaCaptura(String dFechaCaptura) {
        this.dFechaCaptura = dFechaCaptura;
    }

    public String getvUsuarioModifica() {
        return vUsuarioModifica;
    }

    public void setvUsuarioModifica(String vUsuarioModifica) {
        this.vUsuarioModifica = vUsuarioModifica;
    }

    public String getdFechaModifica() {
        return dFechaModifica;
    }

    public void setdFechaModifica(String dFechaModifica) {
        this.dFechaModifica = dFechaModifica;
    }

    public String getbActive() {
        return bActive;
    }

    public void setbActive(String bActive) {
        this.bActive = bActive;
    }
}
