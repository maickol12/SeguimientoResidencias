package com.example.miguelr.seguimientoresidencias.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Carreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.cascaronLineaTiempo;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.ServicioMensajes;
import com.example.miguelr.seguimientoresidencias.R;
import com.example.miguelr.seguimientoresidencias.adaptadores.rvLineaDelTiempo;
import com.github.clans.fab.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;


/**
 * Created by miguelr on 12/03/2018.
 */

public class PerfilActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private Common common;
    private Alumnos alumno;
    private TextView TVNombreAlumno,tvCarrera,tvProyectoSeleccionado;
    private RecyclerView rvLinea;
    private rvLineaDelTiempo adapter;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.acitivity_perfil);

        common = new Common(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneralDez);

        rvLinea                     = findViewById(R.id.rvLineaDelTiempo);
        TVNombreAlumno              =  findViewById(R.id.TVNombreAlumnoPerfil);
        tvCarrera                   =    findViewById(R.id.TVCarreraPerfil);
        alumno                      = new Alumnos(PerfilActivity.this);
        alumno = alumno.obtenerAlumnoSession();
        tvProyectoSeleccionado  = (TextView) findViewById(R.id.tvProyectoSeleccionado);

        ServicioMensajes ser = new ServicioMensajes();

        startService(new Intent(this,ServicioMensajes.class));



        if(alumno!=null){
            TVNombreAlumno.setText(alumno.getvNombre());
            tvCarrera.setText(alumno.getNombreCarrera());
            tvProyectoSeleccionado.setText(alumno.getProyectoSeleccionado());
        }
       initToolbar();
       initComponent();
       descargarSeguimiento();
    }

    public void cargarSeguimiento(ArrayList<cascaronLineaTiempo>  rows){
        adapter = new rvLineaDelTiempo(this,rows);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rvLinea.setLayoutManager(lim);
        rvLinea.setAdapter(adapter);
    }
    private void descargarSeguimiento(){
        common.asyncObtenerSeguimiento();
    }
    private void initToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initComponent() {
        final CircularImageView image =  findViewById(R.id.image);
        final CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        ((AppBarLayout) findViewById(R.id.app_bar_layout)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int min_height = ViewCompat.getMinimumHeight(collapsing_toolbar) * 2;
                float scale = (float) (min_height + verticalOffset) / min_height;
                image.setScaleX(scale >= 0 ? scale : 0);
                image.setScaleY(scale >= 0 ? scale : 0);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            break;
            case R.id.mostrarAlumnos:

                break;
        }
        return true;
    }

}
