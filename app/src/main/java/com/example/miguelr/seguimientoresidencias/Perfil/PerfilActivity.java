package com.example.miguelr.seguimientoresidencias.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
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
import com.mikhaellopez.circularimageview.CircularImageView;


/**
 * Created by miguelr on 12/03/2018.
 */

public class PerfilActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private Common common;
    private Alumnos alumno;
    private TextView TVNombreAlumno,tvCarrera,tvProyectoSeleccionado;
    private FloatingActionButton FBmostrarAlumnos,FBmostrarExpedienteFinal,FBmostrarAvance;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.acitivity_perfil);

        common = new Common(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneralDez);

       /* FBmostrarAlumnos = (FloatingActionButton) findViewById(R.id.FBmostrarAlumnos);
        FBmostrarExpedienteFinal = (FloatingActionButton) findViewById(R.id.FBmostrarExpedienteFinal);
        FBmostrarAvance = (FloatingActionButton) findViewById(R.id.FBmostrarAvance);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout            = (AppBarLayout) findViewById(R.id.appBarLayout);







        configurarToolbar();
*/
        TVNombreAlumno = (TextView)  findViewById(R.id.TVNombreAlumnoPerfil);
        tvCarrera      = (TextView)  findViewById(R.id.TVCarreraPerfil);
        alumno = new Alumnos(PerfilActivity.this);
        alumno = alumno.obtenerAlumnoSession();
        tvProyectoSeleccionado  = (TextView) findViewById(R.id.tvProyectoSeleccionado);

        if(alumno!=null){
            TVNombreAlumno.setText(alumno.getvNombre());
            tvCarrera.setText(alumno.getNombreCarrera());
            tvProyectoSeleccionado.setText(alumno.getProyectoSeleccionado());
        }
       initToolbar();
       initComponent();

    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initComponent() {
        final CircularImageView image = (CircularImageView) findViewById(R.id.image);
        final CollapsingToolbarLayout collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
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

  /*public void configurarToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(alumno.getvNombre());

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(alumno.getvNombre());
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }*/


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
