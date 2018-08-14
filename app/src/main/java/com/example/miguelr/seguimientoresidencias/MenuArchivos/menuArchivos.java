package com.example.miguelr.seguimientoresidencias.MenuArchivos;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.R;
import com.example.miguelr.seguimientoresidencias.menuPrincipal.Model.archivos;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by miguelr on 07/08/2018.
 */

public class menuArchivos extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageReporte3,imageReporte2,imageReporte1,imageCartaAceptacion,imageCartaPresentacion;
    private TextView cartaPresentacionMensaje;
    private ArrayList<archivos> archivosSeleccionados;
    private int TIPO_DE_ARCHIVO;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.menu_archivos);

        toolbar     = (Toolbar) findViewById(R.id.toolbarGeneral);

        imageReporte1               = (ImageView) findViewById(R.id.imageReporte1);
        imageReporte2               = (ImageView) findViewById(R.id.imageReporte2);
        imageReporte3               = (ImageView) findViewById(R.id.imgreporte3);
        imageCartaAceptacion        = (ImageView) findViewById(R.id.imageCartaAceptacion);
        imageCartaPresentacion      = (ImageView) findViewById(R.id.imageCartaPresentacion);
        cartaPresentacionMensaje    = (TextView)  findViewById(R.id.cartaPresentacionMensaje);

        archivosSeleccionados       = new ArrayList<>();



        // Later.. stop the animation
       // splash.setAnimation(null);
        configurations();
    }
    public void configurations(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);

        imageReporte1.startAnimation(anim);
        imageReporte2.startAnimation(anim);
        imageReporte3.startAnimation(anim);
        imageCartaAceptacion.startAnimation(anim);
        imageCartaPresentacion.startAnimation(anim);


    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_menu_archivos, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public void escogerProyecto(View v){
        if(v.getId() == R.id.imageCartaPresentacion){
            TIPO_DE_ARCHIVO = 1;
            Common common = new Common(this);
            AlertDialog.Builder builder =  common.dialogoGeneral("ITSA","Seguro que deseas subir la carta de preentación?");

            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showFileChooser();
                }
            });
            builder.setNegativeButton("NO",null);

            builder.show();
        }
    }
    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("maickol", "File Uri: " + uri.toString());
                    // Get the path

                    Log.d("maickol", "File Path: " + uri.getPath());

                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload

                    boolean isSelected = false;
                    for(archivos archivo : archivosSeleccionados){
                        if(archivo.getTipoArchivo() == TIPO_DE_ARCHIVO){
                            isSelected = true;
                        }
                    }

                    if(!isSelected){
                        archivos archivo = new archivos();
                        archivo.setTipoArchivo(TIPO_DE_ARCHIVO);
                        archivo.setRuta(uri);
                        archivosSeleccionados.add(archivo);
                        Toast.makeText(menuArchivos.this,"Recuerda guardar todos los cambios...",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(menuArchivos.this,"El archivo que estas intentando seleccionar ya se encuentra una vez..",Toast.LENGTH_LONG).show();
                    }


                    if(TIPO_DE_ARCHIVO==1){
                        imageCartaPresentacion.clearAnimation();
                    }

                    cartaPresentacionMensaje.setVisibility(View.VISIBLE);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class uploadFile extends AsyncTask<Void,Void,Void>{

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(menuArchivos.this);
            dialog.setMessage("Espera");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }
}