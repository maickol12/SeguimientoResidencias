package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.Helper.config;

/**
 * Created by miguelr on 01/05/2018.
 */

public class usuarios {
    private String TableName = "usuarios";
    private String idUsuario = "idUsuario";
    private String vNombre   = "vNombre";
    private String vApellidoPaterno = "vApellidoPaterno";
    private String vApellidoMaterno = "vApellidoMaterno";
    private String vCorreo = "vCorreo";
    private String vContrasenia = "vContrasenia";
    private String vUsuario = "vUsuario";
    private String bActive = "bActive";
    private String root = "root";
    private Context context;
    private static final String PREFERENCES_FILE = "SessionUsuario";

    public usuarios(){}
    public usuarios(Context context){
        this.context = context;
    }

    public boolean guardarSession(int idUsuario){
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(this.idUsuario,idUsuario);
            editor.putInt(this.getRoot(),1);
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
            editor.remove(this.getIdUsuario());
            editor.remove(this.getRoot());
            editor.commit();
            return true;
        }catch (Exception e){
            Log.d("errorU",e.getMessage());
            return false;
        }
    }

    public int comprobarSession(){

        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
            int idUsuario = sharedPreferences.getInt(this.getIdUsuario(),0);
            return idUsuario;
        }catch (Exception e){
            return -1;
        }
    }
    public usuarios obtenerRootSession(){
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
            int root = sharedPreferences.getInt(this.getRoot(),0);
            int idUsuario = sharedPreferences.getInt(this.getIdUsuario(),0);
            usuarios usuarios = new usuarios();
            usuarios.setRoot(String.valueOf(root));
            usuarios.setIdUsuario(String.valueOf(idUsuario));
            return usuarios;
        }catch (Exception e){
            return null;
        }
    }

    public int buscarUsuario(String usuario,String contrasenia){
        String sql = "SELECT "+this.getIdUsuario()+" FROM "+this.getTableName()+"" +
                " WHERE ("+this.getvCorreo()+" = '"+usuario+"' OR "+this.getvUsuario()+" = '"+usuario+"') AND "+this.getvContrasenia()+" = '"+contrasenia+"'";

        SQLiteDatabase db = dbRedeable();

        try{
            Cursor cursor = db.rawQuery(sql,null);

            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }else{
                return 0;
            }
        }catch (Exception e){
            Log.d("error",e.getMessage());
            return 0;
        }

    }
    public boolean llenarUsuariosDefault(){
        try{
            SQLiteDatabase db = dbWritable();
            db.delete(this.getTableName(),null,null);

            ContentValues cv = new ContentValues();
            cv.put(this.getIdUsuario(),"1");
            cv.put(this.getvNombre(),"Ricardo");
            cv.put(this.getvApellidoPaterno(),"Beltran");
            cv.put(this.getvApellidoMaterno(),"Peñaloza");
            cv.put(this.getvCorreo(),"ricardo@itsa.edu.mx");
            cv.put(this.getvContrasenia(),"ricardoadmin");
            cv.put(this.getvUsuario(),"ricardo");
            cv.put(this.getbActive(),"1");
            cv.put(this.getRoot(),"1");

            return db.insert(this.getTableName(),null,cv)>0;
        }catch (Exception e){
            Log.d("error",e.getMessage());
            return false;
        }
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


    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getvUsuario() {
        return vUsuario;
    }

    public void setvUsuario(String vUsuario) {
        this.vUsuario = vUsuario;
    }

    public String getbActive() {
        return bActive;
    }

    public void setbActive(String bActive) {
        this.bActive = bActive;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
