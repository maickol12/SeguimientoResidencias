package com.example.miguelr.seguimientoresidencias;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miguelr.seguimientoresidencias.Alumnos.mostrarAlumnosActivity;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Carreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.cartaAceptacion;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.cartaDePresentacion;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.expedienteFinal;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.reportesDeResidencias;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.solicitudDeResidencias;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.usuarios;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
    private EditText ETusuario,ETcontrasenia;
    private String Susuario,Scontrasenia;
    private Common common;
    private  Alumnos alumnos;
    private Carreras carreras;
    private Button btnLogin;
    private cartaAceptacion ca;
    private cartaDePresentacion cp;
    private expedienteFinal ex;
    private solicitudDeResidencias sol;
    private reportesDeResidencias reportes;
    private usuarios users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETusuario = (EditText) findViewById(R.id.usuario);
        ETcontrasenia = (EditText) findViewById(R.id.contrasenia);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                respaldarBaseDatos();
                return true;
            }
        });

        common = new Common(this);
        alumnos = new Alumnos(this);
        users = new usuarios(this);

        common.solicitarPermisosEscritura();

        if (alumnos.comprobarSession() > 0) {
            Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
            startActivity(intent);
            finish();
        }
        int idUsuario = users.comprobarSession();
        if(idUsuario>0){
            Intent intent = new Intent(MainActivity.this,mostrarAlumnosActivity.class);
            startActivity(intent);
            finish();
        }

        carreras = new Carreras(this);
        ca = new cartaAceptacion(this);
        cp = new cartaDePresentacion(this);
        ex = new expedienteFinal(this);
        sol = new solicitudDeResidencias(this);
        reportes = new reportesDeResidencias(this);


        carreras.LlenarCarrerasDefault();
        alumnos.llenarDatos();
       // respaldarBaseDatos();

        if (sol.llenarSolicitudesPorDefault()) {
            Log.d("dbmaickol", "SOL si");
        } else {
            Log.d("dbmaickol", "SOL NO");
        }

        if (ca.llenarDefaultCartaAceptacion()) {
            Log.d("dbmaickol", "Carta Aceptacion si");
        } else {
            Log.d("dbmaickol", "Carta Aceptacion no");
        }

        if (cp.llenarDefaultCartaPresentacion()) {
            Log.d("dbmaickol", "Carta Presentacion si");
        } else {
            Log.d("dbmaickol", "Carta Presentacion no");
        }
        if (ex.llenarExpedienteDefault()) {
            Log.d("dbmaickol", "Expediente si");
        } else {
            Log.d("dbmaickol", "Expediente no");
        }

        if(reportes.llenarDefault()){
            Log.d("dbmaickol", "Reportes si");
        }else{
            Log.d("dbmaickol", "Reportes no");
        }
        if(users.llenarUsuariosDefault()){
            Log.d("dbmaickol", "Usuario si ");
        }else{
            Log.d("dbmaickol", "Usuario no");
        }

    }






    /*****************************************************************************************************
     * ESTE METODO SE LLAMA CUANDO EL USUARIO DE PERMISOS ALO QUE SE REQUIERE, O CUANDO EL USUARIO DENIEGA
     * LOS PERMISOS
     * @param requestCode
     * @param permissions
     * @param grantResults
     ******************************************************************************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        common.dialogoRecomendacion();
                    }
                }

                return;
            }
        }
    }
    /*****************************************************************************************************
     * METODO QUE COMPRUEBA SI EL USUARIO EXISTE EN LA BASE DE DATOS LOCAL
     ******************************************************************************************************/
    public void inicioDeSession(View v){

        Susuario = ETusuario.getText().toString().trim();
        Scontrasenia = ETcontrasenia.getText().toString().trim();
        int idAlumno = alumnos.inicioSession(Susuario,Scontrasenia);
        if(idAlumno>0){
            if(alumnos.guardarSession(idAlumno)){
                Intent intent = new Intent(MainActivity.this,PerfilActivity.class);
                startActivity(intent);
                finish();
            }else{
                common.dialogErrorLogin().show();
            }

        }else{
            int idUsuario = users.buscarUsuario(Susuario,Scontrasenia);
            if(idUsuario!=0){
                if(users.guardarSession(idUsuario)){
                    Intent intent = new Intent(MainActivity.this,mostrarAlumnosActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    common.dialogErrorLogin().show();
                }
            }else{
                common.dialogErrorLogin().show();
            }
        }



    }

    /*****************************************************************************************************
     * METODO PARA RESPALDAR LA BASE DE DATOS
     ******************************************************************************************************/
    public void respaldarBaseDatos(){


        try {
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String currentDBPath = "/data/data/" + this.getPackageName() + "/databases/" + config.dbName;
            String backupDBPath = "servicio.db";

            File currentDB = new File(currentDBPath);
            File backupDB = new File(downloadsDir, backupDBPath);

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
            Toast.makeText(this,":)",Toast.LENGTH_LONG).show();
            Log.d("respaldodb","Base de datos respaldada "+currentDBPath+" "+backupDBPath+" "+downloadsDir.getAbsolutePath());
        } catch (Exception ex) {
            Log.d("respaldodbError","Ocurrio un error al respaldar la base de datos "+ex.getMessage()+" "+config.dbName);
        }
    }
}
