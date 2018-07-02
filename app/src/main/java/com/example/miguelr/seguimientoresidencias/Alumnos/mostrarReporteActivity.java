package com.example.miguelr.seguimientoresidencias.Alumnos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.reportesDeResidencias;
import com.example.miguelr.seguimientoresidencias.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by miguelr on 29/04/2018.
 */

public class mostrarReporteActivity extends AppCompatActivity {
    private reportesDeResidencias reporte,Creporte;
    private TextView TVreporte,TVfechaEntrega,TVfechaLimite,TVDescripcion,TVATiempo;
    private CheckBox CBATiempo;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_mostar_reporte);
        TVreporte = (TextView) findViewById(R.id.reporte);
        TVfechaEntrega = (TextView) findViewById(R.id.fechaEntrega);
        TVfechaLimite = (TextView) findViewById(R.id.fechaLimite);
        TVDescripcion = (TextView) findViewById(R.id.TVDescripcion);
        CBATiempo =(CheckBox) findViewById(R.id.CBATiempo);
        TVATiempo = (TextView) findViewById(R.id.TVATiempo);

        Creporte = new reportesDeResidencias(this);
        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            String idReporte = extras.getString("idReporte");
            reporte = Creporte.obtenerReportePorId(idReporte);
            if(reporte==null){
                Toast.makeText(this,"Reporte no encontrado",Toast.LENGTH_LONG).show();
                onBackPressed();
                return;
            }
            plasmarReporte();
        }

    }

    private void plasmarReporte(){
        if(reporte!=null){
            TVDescripcion.setText(reporte.getvDescripcion());
            TVfechaEntrega.setText(reporte.getdFechaEntrega());
            TVfechaLimite.setText(reporte.getdFechaLimite());
            TVreporte.setText(reporte.getvNumeroReporte());
            if(compararFechas(reporte.getdFechaEntrega(),reporte.getdFechaLimite())){
                CBATiempo.setChecked(true);
                TVATiempo.setText("Entregado a tiempo");
            }else{
                CBATiempo.setChecked(false);
                TVATiempo.setText("No se entrego a tiempo");
            }
        }
    }
    public boolean compararFechas(String fechaUno,String fechaDos){
        int resultado = -2;
        try{
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date dateUno = format.parse(fechaUno);
            Date dateDos = format.parse(fechaDos);
            resultado = dateUno.compareTo(dateDos);
        }catch (Exception e){
            Log.d("fechas",e.getMessage());
        }
        if(resultado==1){
            return false;
        }else{
            return true;
        }

    }
}
