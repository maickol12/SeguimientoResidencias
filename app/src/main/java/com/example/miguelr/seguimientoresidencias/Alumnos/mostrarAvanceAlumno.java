package com.example.miguelr.seguimientoresidencias.Alumnos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Cascarones.cascaronAvance;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.cartaAceptacion;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.cartaDePresentacion;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.reportesDeResidencias;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.solicitudDeResidencias;
import com.example.miguelr.seguimientoresidencias.R;
import com.example.miguelr.seguimientoresidencias.adaptadores.rvMostrarAvanceAlumnoAdapter;
import com.example.miguelr.seguimientoresidencias.eventos.ClickListener;
import com.example.miguelr.seguimientoresidencias.eventos.RecyclerTouchListener;

import java.util.ArrayList;

/**
 * Created by miguelr on 30/03/2018.
 */

public class mostrarAvanceAlumno extends AppCompatActivity {
    private Toolbar toolbar;
    private Alumnos alumnos,alumno;
    private RecyclerView rvMostrarAvanceAlumno;
    private ArrayList<cascaronAvance> avances;
    private rvMostrarAvanceAlumnoAdapter adapder;
    private solicitudDeResidencias sdr;
    private cartaDePresentacion cdp;
    private cartaAceptacion ca;
    private reportesDeResidencias reportes;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_avance_alumno);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneral);
        rvMostrarAvanceAlumno = (RecyclerView) findViewById(R.id.rvMostrarAvanceAlumno);
        sdr = new solicitudDeResidencias(this);
        cdp = new cartaDePresentacion(this);
        ca = new cartaAceptacion(this);
        reportes = new reportesDeResidencias(this);
        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            int idAlumno = extras.getInt("idAlumno");
            alumnos = new Alumnos(this);
            alumno = alumnos.buscarAlumnoPorId(idAlumno);

            if(alumno==null){
                onBackPressed();
                Toast.makeText(this,"Alumno no encontrado",Toast.LENGTH_LONG).show();
                return;
            }
        }else{
            onBackPressed();
            Toast.makeText(this,"Alumno no encontrado",Toast.LENGTH_LONG).show();
            return;
        }


        avances = llenarAvanceAlumno();
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rvMostrarAvanceAlumno.setLayoutManager(lim);
        adapder = new rvMostrarAvanceAlumnoAdapter(this,avances);
        rvMostrarAvanceAlumno.setAdapter(adapder);
        oyenteLista();


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(alumno.getvNombreAlumno()+" "+alumno.getvApellidoPaterno());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void lanzarActividad(Class<?> clase,String idReporte){
        Intent intent = new Intent(this,clase);
        intent.putExtra("idReporte",idReporte);
        startActivity(intent);
    }
    public void oyenteLista(){
        rvMostrarAvanceAlumno.addOnItemTouchListener(new RecyclerTouchListener(this, rvMostrarAvanceAlumno, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
               if(avances.get(position).getEsreporte()==1){
                   lanzarActividad(mostrarReporteActivity.class,avances.get(position).getIdAvance());
               }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public ArrayList<cascaronAvance> llenarAvanceAlumno(){
        solicitudDeResidencias sdrActual;

        String idAlumno = alumno.getiIdAlumno();
        String idSolicitud = "0";

        sdrActual = sdr.obtenerSolicituPorUsuario(idAlumno);

        ArrayList<cascaronAvance> avances = new ArrayList<>();
        cascaronAvance avance = new cascaronAvance();

        if(sdrActual!=null){
            boolean solicitudAcapetada = false;
            avance.setTitulo("Solicitud");
            avance.setEsreporte(0);
            idSolicitud = sdrActual.getiIdSolicitudDeResidencias();
            if(sdrActual.getiAprobadoPorJefeDeCarrera().equalsIgnoreCase("1") && sdrActual.getiAprobadoPorAcademia().equalsIgnoreCase("1")){
                avance.setMensaje("Tu solicitud fue aprobada con exito...");
                avance.setAprobado(1);
                solicitudAcapetada = true;
            }else{
                avance.setMensaje("Tu solicitud no ha sido aprobada...");
                avance.setAprobado(1);
                solicitudAcapetada = false;
            }
            avances.add(avance);
            if(solicitudAcapetada){
                avance = new cascaronAvance();
                avance.setEsreporte(0);
                avance.setTitulo("Aprobacion");
                avance.setMensaje("Tu solicitud fue aprobada con exito...");
                avance.setAprobado(1);
                avances.add(avance);
            }else{
                avance = new cascaronAvance();
                avance.setEsreporte(0);
                avance.setTitulo("Aprobacion");
                avance.setMensaje("Tu solitud no fue aprobada...");
                avance.setAprobado(0);
                avances.add(avance);
            }
        }else{
            avance.setTitulo("Solicitud");
            avance.setMensaje("No se encontro ninguna Solicitud por el momento...");
            avance.setAprobado(0);
            avance.setEsreporte(0);
            avances.add(avance);

            avance = new cascaronAvance();
            avance.setTitulo("Aprobacion");
            avance.setMensaje("No se encontro ningunga solicitud para verificar si se aprobo...");
            avance.setAprobado(0);
            avances.add(avance);
        }





        cartaDePresentacion cdpActual = null;

        cdpActual = cdp.obtenerCartaPresentacionPorSolicitud(idSolicitud);
        if(cdpActual!=null){
            avance = new cascaronAvance();
            avance.setEsreporte(0);
            avance.setTitulo("Carta de presentacion");
            avance.setMensaje("No se encontro ninguna carta de presentacion por el momento...");
            avance.setAprobado(1);
            avances.add(avance);
        }else{
            avance = new cascaronAvance();
            avance.setEsreporte(0);
            avance.setTitulo("Carta de presentacion");
            avance.setMensaje("No se encontro ninguna carta de presentacion...");
            avance.setAprobado(0);
            avances.add(avance);
        }


        cartaAceptacion cartaAceptacion = null;

        cartaAceptacion = ca.buscarCartaDeAceptacionPorSolicitud(idSolicitud);

        if(cartaAceptacion!=null){
            avance = new cascaronAvance();
            avance.setEsreporte(0);
            avance.setTitulo("Carta de aceptacion");
            avance.setMensaje("La carta de aceptacion fue encontrada con exito...");
            avance.setAprobado(1);
            avances.add(avance);
        }else{
            avance = new cascaronAvance();
            avance.setEsreporte(0);
            avance.setTitulo("Carta de aceptacion");
            avance.setMensaje("La carta de aceptacion no fue encontrada con exito...");
            avance.setAprobado(0);
            avances.add(avance);
        }


        ArrayList<reportesDeResidencias> listaReportes = null;

        if(idSolicitud.equalsIgnoreCase("0")){
            avance = new cascaronAvance();
            avance.setTitulo("Reportes...");
            avance.setMensaje("No se encontro ninguna solicitud de residencias aun....");
            avance.setAprobado(0);
            avances.add(avance);
        }else{
            listaReportes = reportes.obtenerReportesPorSolicitud(idSolicitud);

            if(listaReportes!=null){
                for(int i = 0;i<listaReportes.size();i++){
                    reportesDeResidencias rep = listaReportes.get(i);
                    avance = new cascaronAvance();
                    avance.setTitulo("Reporte "+rep.getvNumeroReporte());
                    avance.setEsreporte(1);
                    avance.setIdAvance(rep.getiIdReporte());
                    if(Integer.parseInt(rep.getiAprobadoJefeDeCarrera())==1 && Integer.parseInt(rep.getiAprobadoAcesorInterno())==1 && Integer.parseInt(rep.getiHojaAsesoria())==1){
                        avance.setMensaje("Reporte aceptado...");
                        avance.setAprobado(1);
                    }else{
                        avance.setMensaje("Reporte no aceptado...");
                        avance.setAprobado(0);
                    }



                    avances.add(avance);
                }

            }

        }


        return avances;
    }

    public boolean onCreateOptionsMenu(Menu menu){

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
               onBackPressed();
            break;
        }
        return true;
    }
}
