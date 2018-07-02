package com.example.miguelr.seguimientoresidencias.Configuraciones;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.example.miguelr.seguimientoresidencias.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by miguelr on 12/05/2018.
 */

public class ConfiguracionesActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener dialogDate;
    private Calendar myCalendar;
    private boolean fechaInicio;
    private TextView TVfechaInicio,TVfechaFin;
    private Toolbar toolbar;

    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_configuraciones);
        iniciarDatePicker();
        fechaInicio     = false;
        TVfechaInicio   = (TextView) findViewById(R.id.COTVfechaInicio);
        TVfechaFin      = (TextView) findViewById(R.id.COTVfechaFin);
        toolbar         = (Toolbar) findViewById(R.id.toolbarGeneral);

        setSupportActionBar(toolbar);
        plasmarActualDate();
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void plasmarActualDate(){
        String timeStamp = new SimpleDateFormat("MM/dd/yy").format(Calendar.getInstance().getTime());
        TVfechaInicio.setText(timeStamp);
        TVfechaFin.setText(timeStamp);
    }

    public void escogerFecha(View v) {
        switch (v.getId()){
            case R.id.COfechaInicio:
                fechaInicio = true;
                break;
            case R.id.COfechaFin:
                fechaInicio = false;
                break;
        }
        new DatePickerDialog(ConfiguracionesActivity.this, dialogDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void iniciarDatePicker() {
        myCalendar = Calendar.getInstance();
        dialogDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if(fechaInicio){
                    TVfechaInicio.setText(sdf.format(myCalendar.getTime()));
                }else{
                    TVfechaFin.setText(sdf.format(myCalendar.getTime()));
                }

            }

        };
    }
    public void descararInformacion(View view){
        String fechaInicio = TVfechaInicio.getText().toString().trim().replace("/","-");
        String fechaFin    = TVfechaFin.getText().toString().trim().replace("/","-");
        if(!fechaInicio.isEmpty() && !fechaFin.isEmpty() ){
            boolean res = compararFechas(fechaInicio,fechaFin);
            if(res){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Información");

                LayoutInflater inflater = LayoutInflater.from(ConfiguracionesActivity.this);
                View v = (View) inflater.inflate(R.layout.descargar_informacion_alerta,null);

                builder.setView(v);

                TextView fi,ff;

                fi = (TextView) v.findViewById(R.id.INFOfechaInicio);
                ff = (TextView) v.findViewById(R.id.INFOTVfechaFin);

                fi.setText(fechaInicio);
                ff.setText(fechaFin);

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ConfiguracionesActivity.this, "En Contrucción...", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("NO",null);
                builder.show();
            }else{
                Toast.makeText(this,"La fecha inicio no puede ser mayor ala fecha fin...",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Campos vacios",Toast.LENGTH_LONG).show();
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

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
