package com.example.miguelr.seguimientoresidencias.menuPrincipal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Alumnos;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.sessionHelper;
import com.example.miguelr.seguimientoresidencias.Login.MainActivity;
import com.example.miguelr.seguimientoresidencias.MenuArchivos.menuArchivos;
import com.example.miguelr.seguimientoresidencias.Perfil.PerfilActivity;
import com.example.miguelr.seguimientoresidencias.R;
import com.example.miguelr.seguimientoresidencias.cartaPresentacion.cartaPresentacion;
import com.example.miguelr.seguimientoresidencias.escogerProyecto.escogerProyecto;

/**
 * Created by miguelr on 22/07/2018.
 */

public class menuPrincipal extends AppCompatActivity{
    private Toolbar toolbar;
    private Common common;
    private Alumnos alumno;
    private sessionHelper session;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.menu_principal);
        session = new sessionHelper(this);
        common = new Common(this);
        alumno = new Alumnos(this);

        configurarToolbar();
        buscarMensajesPorAlumno();
    }
    private void configurarToolbar(){
        alumno = alumno.obtenerAlumnoSession();

        toolbar = (Toolbar) findViewById(R.id.toolbarGeneral);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(alumno.getvNombre());
    }
    public void onClickPerfil(View view){
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            break;
        }
        return true;
    }
    public void escogerProyecto(View view){
        Intent intent = new Intent(this,escogerProyecto.class);
        startActivity(intent);
    }
    public void menuArchivosClick(View view){
        Intent intent = new Intent(this,menuArchivos.class);
        startActivity(intent);
    }
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ITSA");
        builder.setMessage("¿Estás seguro de que deseas salir?");
        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                session.logout();
                menuPrincipal.this.finish();
                Intent intent = new Intent(menuPrincipal.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }
    public void buscarMensajesPorAlumno(){
        try{
            int idAlumnoLoged = session.obtenerIdAlumno();
             common.asyncMessagesPorAlumno(idAlumnoLoged);
        }catch (Exception e){

        }
    }
}
