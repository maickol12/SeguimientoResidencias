package com.example.miguelr.seguimientoresidencias.Login;


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

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Carreras;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.config;
import com.example.miguelr.seguimientoresidencias.Helper.sessionHelper;
import com.example.miguelr.seguimientoresidencias.R;
import com.example.miguelr.seguimientoresidencias.menuPrincipal.menuPrincipal;
import com.example.miguelr.seguimientoresidencias.registro.registroActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
    private EditText ETusuario,ETcontrasenia;
    private String Susuario,Scontrasenia;
    private Common common;
    private sessionHelper session;
    private  Alumnos alumnos;
    private Carreras carreras;
    private Button btnLogin;
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

        common.solicitarPermisosEscritura();

        common.asyncMessages();
        common.asyncDescargarCatalogos();


        carreras = new Carreras(this);
        session = new sessionHelper(this);

        if(session.isLogin()){
            Intent intent = new Intent(this,menuPrincipal.class);
            startActivity(intent);
        }

    }




    public void iniciarRegistro(View view){
        Intent intent = new Intent(this,registroActivity.class);
        startActivity(intent);
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

        String usuario = ETusuario.getText().toString().trim();
        String contrasenia = ETcontrasenia.getText().toString().trim();

        common.asyncLogin(usuario,contrasenia);
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
