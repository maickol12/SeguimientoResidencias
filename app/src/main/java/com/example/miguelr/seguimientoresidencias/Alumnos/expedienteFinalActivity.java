package com.example.miguelr.seguimientoresidencias.Alumnos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.expedienteFinal;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.R;

/**
 * Created by miguelr on 03/05/2018.
 */

public class expedienteFinalActivity extends AppCompatActivity{
    private expedienteFinal exp,CExpediente;
    private TextView TVNombre,TVCarrera,TVCreditos,TVFechaInicio,TVFechaFin,TVDescripcion;
    private int idAlumno;
    private Common common;
    private Toolbar toolbar;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.layout_expediente_final);
        TVNombre            =               (TextView) findViewById(R.id.TVNombre);
        TVCarrera           =               (TextView) findViewById(R.id.TVCarrera);
        TVCreditos          =               (TextView) findViewById(R.id.TVCreditos);
        TVFechaInicio       =               (TextView) findViewById(R.id.TVFechaInicio);
        TVFechaFin          =               (TextView) findViewById(R.id.TVFechaFin);
        TVDescripcion       =               (TextView) findViewById(R.id.TVDescripcion);
        common              =               new Common(this);
        toolbar             =               (Toolbar) findViewById(R.id.toolbarMasDetalles);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        CExpediente = new expedienteFinal(this);
        Bundle b = getIntent().getExtras();

        if(b!=null){
            idAlumno = b.getInt("idAlumno");
            plasmarExpediente();
        }
    }

    public void plasmarExpediente(){
        exp = CExpediente.obtenerExpedientePorAlumno(idAlumno);
        if(exp!=null){
            TVNombre.setText(exp.getAlumno().getvNombreAlumno());
            TVCarrera.setText(exp.getAlumno().getCarrera().getvCarrera());
            TVCreditos.setText(exp.getAlumno().getiCreditos());
            TVFechaInicio.setText(exp.getAlumno().getdInicioServicio());
            TVFechaFin.setText(exp.getAlumno().getdFinServicio());
            TVDescripcion.setText(exp.getvDescripcion());
        }else{
            common.dialogNoExpediente().show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
