package com.example.miguelr.seguimientoresidencias.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Carreras;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.R;
import com.github.clans.fab.FloatingActionButton;


/**
 * Created by miguelr on 12/03/2018.
 */

public class PerfilActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private Common common;
    private Alumnos alumno;
    private TextView nombreAlumno;
    private TextView TVcarrera;
    private TextView creditos;
    private ImageView IVestatus;
    private ProgressBar progressBar;
    private Carreras carreras;
    private Button btnLogin;
    private TextView txtProgress;
    private FloatingActionButton FBmostrarAlumnos,FBmostrarExpedienteFinal,FBmostrarAvance;
    private int idAlumnoSession;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.acitivity_perfil);

        common = new Common(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarMasDetalles);
        nombreAlumno = (TextView) findViewById(R.id.nombreAlumno);
        creditos = (TextView) findViewById(R.id.creditos);
        IVestatus = (ImageView) findViewById(R.id.estatus);
        progressBar = (ProgressBar) findViewById(R.id.progressBarAvance);
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        TVcarrera = (TextView) findViewById(R.id.carrera);
        FBmostrarAlumnos = (FloatingActionButton) findViewById(R.id.FBmostrarAlumnos);
        FBmostrarExpedienteFinal = (FloatingActionButton) findViewById(R.id.FBmostrarExpedienteFinal);
        FBmostrarAvance = (FloatingActionButton) findViewById(R.id.FBmostrarAvance);


        carreras = new Carreras(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        alumno = new Alumnos(PerfilActivity.this);


    }





    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                common.dialogoSalir();
            break;
            case R.id.mostrarAlumnos:

                break;
        }
        return true;
    }


    public void onBackPressed(){
        common.dialogoSalir();
    }


    private void iniciarActividad(Class<?> clase){
        Intent intent = new Intent(PerfilActivity.this,clase);
        startActivity(intent);
    }
}
