package com.example.miguelr.seguimientoresidencias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguelr.seguimientoresidencias.Alumnos.expedienteFinalActivity;
import com.example.miguelr.seguimientoresidencias.Alumnos.mostrarAvanceAlumno;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Cascarones.Carreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.cartaAceptacion;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.cartaDePresentacion;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.expedienteFinal;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.reportesDeResidencias;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.solicitudDeResidencias;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.usuarios;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Alumnos.mostrarAlumnosActivity;
import com.example.miguelr.seguimientoresidencias.Login.MainActivity;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;


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
    private cartaAceptacion ca;
    private cartaDePresentacion cp;
    private expedienteFinal ex;
    private solicitudDeResidencias sol;
    private reportesDeResidencias reportes;
    private TextView txtProgress;
    private usuarios users;
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
        ca = new cartaAceptacion(this);
        cp = new cartaDePresentacion(this);
        ex = new expedienteFinal(this);
        sol = new solicitudDeResidencias(this);
        reportes = new reportesDeResidencias(this);
        users = new usuarios(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        alumno = new Alumnos(PerfilActivity.this);

       Bundle b = getIntent().getExtras();
       boolean Bolroot = false;
       if(b!=null){
            try{
                int root = b.getInt("root");
                if(root==1){
                    Bolroot = true;
                }else{
                    Bolroot = false;
                }
            }catch (Exception e){
                Log.d("error",e.getMessage());
                Bolroot = false;
            }
       }

        if(!Bolroot){
            idAlumnoSession = alumno.obtenerIdSession();
            FBmostrarAlumnos.setEnabled(false);
        }else{
            try{
                idAlumnoSession = b.getInt("idAlumno");
            }catch (Exception e){
                Log.d("error",e.getMessage());
                idAlumnoSession = 0;
            }
        }

        if(idAlumnoSession>0){
            alumno = alumno.buscarAlumnoPorId(idAlumnoSession);
            if(alumno!=null){
                nombreAlumno.setText(alumno.getvNombreAlumno()+" "+alumno.getvApellidoPaterno()+" "+alumno.getvApellidoMaterno());
                TVcarrera.setText(alumno.getCarrera().getvCarrera());
                creditos.setText(alumno.getiCreditos());
                if(alumno.getSolicitud()!=null){
                    if(alumno.getSolicitud().getiAprobadoPorJefeDeCarrera().equalsIgnoreCase("1") && alumno.getSolicitud().getiAprobadoPorAcademia().equalsIgnoreCase("1")){
                        IVestatus.setImageDrawable(getResources().getDrawable(R.drawable.aprobado));
                    }else{
                        IVestatus.setImageDrawable(getResources().getDrawable(R.drawable.noaprobado));
                    }
                }
                iniciarProgreso();
            }
        }else{
            iniciarActividad(MainActivity.class);
            this.finish();
        }
    }



    public void iniciarProgreso() {
        solicitudDeResidencias sdrActual;

        String idAlumno = alumno.getiIdAlumno();
        String idSolicitud = "0";
        int porcentaje = 0;
        sdrActual = sol.obtenerSolicituPorUsuario(idAlumno);


        if (sdrActual != null) {
            boolean solicitudAcapetada = false;

            idSolicitud = sdrActual.getiIdSolicitudDeResidencias();
            if (
                    sdrActual.getiAprobadoPorJefeDeCarrera().equalsIgnoreCase("1") &&
                            sdrActual.getiAprobadoPorAcademia().equalsIgnoreCase("1")
                    ) {
                solicitudAcapetada = true;
                porcentaje = 10;
            } else {
                solicitudAcapetada = false;
            }

            boolean cartaPresentacion = false;
            if (solicitudAcapetada) {
                cartaDePresentacion cdpActual = null;
                cdpActual = cp.obtenerCartaPresentacionPorSolicitud(idSolicitud);
                if (cdpActual != null) {
                    porcentaje = 25;
                    cartaPresentacion = true;
                }


                cartaAceptacion cartaAceptacion = null;

                cartaAceptacion = ca.buscarCartaDeAceptacionPorSolicitud(idSolicitud);
                boolean cartaAc = false;
                if (cartaAceptacion != null) {
                    if (cartaPresentacion) {
                        porcentaje = 40;
                        cartaAc = true;
                    }
                }


                if (cartaAc) {
                    ArrayList<reportesDeResidencias> listaReportes = null;
                    listaReportes = reportes.obtenerReportesPorSolicitud(idSolicitud);

                    if (listaReportes != null) {
                        for (int i = 0; i < listaReportes.size(); i++) {
                            reportesDeResidencias rep = listaReportes.get(i);
                            if (Integer.parseInt(rep.getiAprobadoJefeDeCarrera()) == 1 && Integer.parseInt(rep.getiAprobadoAcesorInterno()) == 1 && Integer.parseInt(rep.getiHojaAsesoria()) == 1) {
                                porcentaje += 20;
                            }
                        }
                    }
                }
            }
            progressBar.setProgress(porcentaje);
            txtProgress.setText(""+porcentaje+" %");
        }
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
                iniciarActividad(mostrarAlumnosActivity.class);
                break;
        }
        return true;
    }
    public void mostrarAlumnos(View v){
        iniciarActividad(mostrarAlumnosActivity.class);
    }
    public void mostrarExpediente(View v){
        Toast.makeText(this,"En contrucción",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,expedienteFinalActivity.class);
        intent.putExtra("idAlumno",idAlumnoSession);
        startActivity(intent);
    }

    public void mostrarAvance(View v){
        Intent intent = new Intent(this, mostrarAvanceAlumno.class);
        intent.putExtra("idAlumno",idAlumnoSession);
        startActivity(intent);

    }
    public void onBackPressed(){
        common.dialogoSalir();
    }

    @Override
    public void finish() {
        super.finish();
        Alumnos al = new Alumnos(this);
        usuarios us = new usuarios(this);
        al.cerrarSession();
        us.cerrarSession();
        progressBar.setProgress(0);
    }
    private void iniciarActividad(Class<?> clase){
        Intent intent = new Intent(PerfilActivity.this,clase);
        startActivity(intent);
    }
}
