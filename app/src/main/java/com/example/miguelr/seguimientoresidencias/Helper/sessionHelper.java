package com.example.miguelr.seguimientoresidencias.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Alumnos;
import com.example.miguelr.seguimientoresidencias.Login.MainActivity;

/**
 * Created by miguelr on 22/07/2018.
 */

public class sessionHelper {
    private Context context;
    private SharedPreferences sessioData;
    private SharedPreferences.Editor editor;
    private static final String PREFERENCES_FILE = "residenciasfile";
    private static final String ID_ALUMNO_KEY = "idAlumno";
    public static final String ISLOGED_KEY = "idLoged";

    public sessionHelper(Context context){
        this.context = context;
        sessioData = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }
    public void createSession(Alumnos alumnos){
        editor = sessioData.edit();
        editor.putInt(ID_ALUMNO_KEY,alumnos.getIdAlumno());
        editor.putBoolean(ISLOGED_KEY,true);
        editor.commit();
    }
    public void logout(){
        editor = sessioData.edit();
        editor.clear();
        editor.commit();
    }

    public void checkIfUserLoged(){
        if(!isLogin()){
            redirectToLogin();
        }
    }
    public int obtenerIdAlumno(){
        return sessioData.getInt(ID_ALUMNO_KEY,0);
    }
    public void redirectToLogin(){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    public boolean isLogin(){
        return sessioData.getBoolean(ISLOGED_KEY, false);
    }
}
