package com.example.miguelr.seguimientoresidencias.escogerProyecto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Giros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Opciones;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Periodos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Sectores;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.bancoProyectos;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.sessionHelper;
import com.example.miguelr.seguimientoresidencias.R;

import java.util.ArrayList;

/**
 * Created by miguelr on 23/07/2018.
 */

public class escogerProyecto extends AppCompatActivity{
    private Toolbar toolbar;
    private Spinner SProyecto,SPeriodos,SOpciones,SGiros,SSector;
    private bancoProyectos bancoProy;
    private Periodos periodos;
    private Opciones opciones;
    private Giros giros;
    private Sectores sectores;
    private sessionHelper session;
    private Common common;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.escoger_proyecto);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneral);
        SProyecto = (Spinner) findViewById(R.id.SProyecto);
        SPeriodos = (Spinner) findViewById(R.id.SPeriodos);
        SOpciones = (Spinner) findViewById(R.id.SOpciones);
        SGiros    = (Spinner) findViewById(R.id.SGiro);
        SSector   = (Spinner) findViewById(R.id.SSector);

        bancoProy   = new bancoProyectos(this);
        periodos    = new Periodos(this);
        opciones    = new Opciones(this);
        giros       = new Giros(this);
        sectores    = new Sectores(this);
        common      = new Common(this);

        session = new sessionHelper(this);

        configurarToolbar();
        cargarControles();
    }
    ArrayList<bancoProyectos> arrayBancoProyectos;
    ArrayList<Periodos> arrayPeriodos;
    ArrayList<Opciones> arrayOpciones;
    ArrayList<Giros>  arrayGiros;
    ArrayList<Sectores> arraySectores;

    private void cargarControles(){

        arrayBancoProyectos  =  bancoProy.obtenerProyectos(session.obtenerIdCarrera());
        if(arrayBancoProyectos!=null){
            ArrayAdapter<bancoProyectos> spinnerArrayAdapter = new ArrayAdapter<bancoProyectos>
                    (this, android.R.layout.simple_spinner_item,arrayBancoProyectos); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            SProyecto.setAdapter(spinnerArrayAdapter);
        }

        arrayPeriodos=  periodos.obtenerPeriodos();
        if(arrayPeriodos!=null){

            ArrayAdapter<Periodos> adapterPer = new ArrayAdapter<Periodos>
                    (this, android.R.layout.simple_spinner_item,arrayPeriodos); //selected item will look like a spinner set from XML
            adapterPer.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            SPeriodos.setAdapter(adapterPer);
        }

        arrayOpciones =  opciones.obtenerOpciones();
        if(arrayOpciones!=null){

            ArrayAdapter<Opciones> adapterOpc = new ArrayAdapter<Opciones>
                    (this, android.R.layout.simple_spinner_item,arrayOpciones); //selected item will look like a spinner set from XML
            adapterOpc.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            SOpciones.setAdapter(adapterOpc);
        }

        arrayGiros =  giros.obtenerGiros();
        if(arrayGiros!=null){

            ArrayAdapter<Giros> adapterGir = new ArrayAdapter<Giros>
                    (this, android.R.layout.simple_spinner_item,arrayGiros); //selected item will look like a spinner set from XML
            adapterGir.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            SGiros.setAdapter(adapterGir);
        }

        arraySectores =  sectores.obtenerSectores();
        if(arraySectores!=null){

            ArrayAdapter<Sectores> adapterGir = new ArrayAdapter<Sectores>
                    (this, android.R.layout.simple_spinner_item,arraySectores); //selected item will look like a spinner set from XML
            adapterGir.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            SSector.setAdapter(adapterGir);
        }
    }

    public void guardarProyectoSeleccionado(View v){
        int idBancoProyecto,idAlumno,idPeriodo,idOpcion,idGiro,idEstado,idSector;
        idBancoProyecto = arrayBancoProyectos.get(SProyecto.getSelectedItemPosition()).getIdbancoProyecto();
        idAlumno        = session.obtenerIdAlumno();
        idPeriodo       = arrayPeriodos.get(SPeriodos.getSelectedItemPosition()).getIdPeriodo();
        idOpcion        = arrayOpciones.get(SOpciones.getSelectedItemPosition()).getIdOpcion();
        idGiro          = arrayGiros.get(SGiros.getSelectedItemPosition()).getIdGiro();
        idEstado        = 1;
        idSector        = arraySectores.get(SSector.getSelectedItemPosition()).getIdSector();
        this.common.asyncEncogerProyecto(idBancoProyecto,idAlumno,idPeriodo,idOpcion,idGiro,idEstado,idSector);
    }

    public void configurarToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                onBackPressed();
                break;
        }
        return true;
    }
}
