package com.example.miguelr.seguimientoresidencias.registro;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Carreras;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.config;
import com.example.miguelr.seguimientoresidencias.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by miguelr on 16/07/2018.
 */

public class registroActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private EditText RFechaNamimiento,RNombre,RApellidoPaterno,RApellidoMaterno,RUsuario,RContrasenia,RCorreo,RMatricula,RSemestre;
    private Spinner SpinnerCarreras;
    private ArrayList<Carreras> carrerasSpinner;
    private Common common;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.layout_registro);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneral);

        RFechaNamimiento     = (EditText) findViewById(R.id.RFechaNamimiento);
        RMatricula           = (EditText) findViewById(R.id.RMatricula);
        RNombre              = (EditText) findViewById(R.id.RNombre);
        RApellidoPaterno     = (EditText) findViewById(R.id.RApellidopaterno);
        RApellidoMaterno     = (EditText) findViewById(R.id.RApellidoMaterno);
        SpinnerCarreras      = (Spinner) findViewById(R.id.carreras);
        RUsuario             = (EditText) findViewById(R.id.RUsuario);
        RContrasenia         = (EditText) findViewById(R.id.RContrasenia);
        RCorreo              = (EditText) findViewById(R.id.RCorreo);
        RSemestre            = (EditText) findViewById(R.id.RSemestre);

        common               = new Common(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        iniciarInformacion();
    }
    public void iniciarInformacion(){
        Carreras c = new Carreras(this);
        c.abrirDB();
        carrerasSpinner = c.obtenerCarreras();
        c.cerrarDB();
        ArrayAdapter<Carreras> adapterSpinner =
                new ArrayAdapter<Carreras>(this, android.R.layout.simple_spinner_dropdown_item, carrerasSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCarreras.setAdapter(adapterSpinner);

    }
    public boolean onCreateOptionsMenu(Menu menu){

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    public void escogerFecha(View view){


        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                RFechaNamimiento.setText(diaFormateado + "-" + mesFormateado + "-" + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

    public void guardarUsuario(View v){
        new asyntaskGuardarUsuario(common.getProgressBar("Espera","Estamos guardando el registro...")).execute();
    }

    public class asyntaskGuardarUsuario extends AsyncTask<Void,Void,Void>{
        private ProgressDialog dialog;
        private int idUsuario,response;
        private String result;
        public asyntaskGuardarUsuario(ProgressDialog dialog){
            this.dialog = dialog;
            idUsuario = 0;
        }
        public void onPreExecute(){
            dialog.show();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            final HttpClient Client = new DefaultHttpClient();
            String jsoncadena = "";

            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("idCarrera", ""+carrerasSpinner.get(SpinnerCarreras.getSelectedItemPosition()).getIdCarrera()));
                params.add(new BasicNameValuePair("vNumeroControl",RMatricula.getText().toString().trim()));
                params.add(new BasicNameValuePair("vNombre",RNombre.getText().toString().trim()));
                params.add(new BasicNameValuePair("vApellidoPaterno",RApellidoPaterno.getText().toString().trim()));
                params.add(new BasicNameValuePair("vApellidoMaterno",RApellidoMaterno.getText().toString().trim()));
                params.add(new BasicNameValuePair("dFechaNacimiento",RFechaNamimiento.getText().toString().trim()));
                params.add(new BasicNameValuePair("vUsuario",RUsuario.getText().toString().trim()));
                params.add(new BasicNameValuePair("vContrasena",RContrasenia.getText().toString().trim()));
                params.add(new BasicNameValuePair("bSexo","1"));
                params.add(new BasicNameValuePair("vSemestre",RSemestre.getText().toString().trim()));
                params.add(new BasicNameValuePair("vCorreoInstitucional",RCorreo.getText().toString().trim()));
                HttpPost httppostreq = new HttpPost(config.url+config.WebMethodSaveUsuer);
                httppostreq.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpresponse = Client.execute(httppostreq);
                jsoncadena = EntityUtils.toString(httpresponse.getEntity());

                JSONObject obj = new JSONObject(jsoncadena);


                JSONArray tabla1Array = obj.optJSONArray("tabla1");
                JSONObject objTabla1 = tabla1Array.getJSONObject(0);

                response = objTabla1.getInt("response");

                if(response == 200){
                    JSONArray array = new JSONArray(objTabla1.getString("result"));
                    JSONObject res = array.getJSONObject(0);
                    idUsuario = res.getInt("idUsuario");
                }else if(response == 500){
                    result = objTabla1.getString("result");
                }
                Log.d("json",jsoncadena);
                Log.d("json",response+""+idUsuario);
            }catch (Exception e){
                Log.d("json",e.getMessage());
                idUsuario = 0;
            }
            return null;
        }
        public void onPostExecute(Void args){
            dialog.dismiss();
            if(response == 200){
                RNombre.setText("");
                RApellidoPaterno.setText("");
                RApellidoMaterno.setText("");
                RMatricula.setText("");
                RFechaNamimiento.setText("");
                RCorreo.setText("");
                RSemestre.setText("");
                RUsuario.setText("");
                RContrasenia.setText("");
                common.dialogoMensajes("ITSA","Guardado con exito");
            }else if(response == 500){
                common.dialogoMensajes("ITSA",result);
            }

        }
    }
}
