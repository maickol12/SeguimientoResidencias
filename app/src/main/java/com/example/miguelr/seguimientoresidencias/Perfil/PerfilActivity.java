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
    private TextView TVNombreAlumno,tvCarrera;
    private FloatingActionButton FBmostrarAlumnos,FBmostrarExpedienteFinal,FBmostrarAvance;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.acitivity_perfil);

        common = new Common(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneralDez);

        FBmostrarAlumnos = (FloatingActionButton) findViewById(R.id.FBmostrarAlumnos);
        FBmostrarExpedienteFinal = (FloatingActionButton) findViewById(R.id.FBmostrarExpedienteFinal);
        FBmostrarAvance = (FloatingActionButton) findViewById(R.id.FBmostrarAvance);

        TVNombreAlumno = (TextView)  findViewById(R.id.TVNombreAlumno);
        tvCarrera      = (TextView)  findViewById(R.id.tvCarrera);

        alumno = new Alumnos(PerfilActivity.this);
        alumno = alumno.obtenerAlumnoSession();

        if(alumno!=null){
            TVNombreAlumno.setText(alumno.getvNombre());
            tvCarrera.setText(alumno.getNombreCarrera());
        }

        configurarToolbar();



    }


    public void configurarToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(alumno.getvNombre());
    }


    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                //common.dialogoSalir();
                onBackPressed();
            break;
            case R.id.mostrarAlumnos:

                break;
        }
        return true;
    }


   /* public void onBackPressed(){
        common.dialogoSalir();
    }
*/

    private void iniciarActividad(Class<?> clase){
        Intent intent = new Intent(PerfilActivity.this,clase);
        startActivity(intent);
    }
}
