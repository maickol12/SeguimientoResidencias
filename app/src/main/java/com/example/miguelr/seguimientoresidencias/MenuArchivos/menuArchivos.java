package com.example.miguelr.seguimientoresidencias.MenuArchivos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
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
        intent.setType("file/*");
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

                    new UploadFileAsync(uri.getPath()).execute();


                    cartaPresentacionMensaje.setVisibility(View.VISIBLE);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private class UploadFileAsync extends AsyncTask<String, Void, String> {
        private String vFile;
        private ProgressDialog dialog;
        public UploadFileAsync(String vFile){
            this.vFile = vFile;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(menuArchivos.this);
            dialog.setMessage("espera");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                //String sourceFileUri = "/mnt/sdcard/abc.png";
                String sourceFileUri = this.vFile;

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                File sourceFile = new File(sourceFileUri);

                if (sourceFile.isFile()) {

                    try {
                        String upLoadServerUri = "http://residenciasitsa.diplomadosdelasep.com.mx/wssegres/uploadFile";

                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("bill", sourceFileUri);

                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math
                                    .min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                        int serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn
                                .getResponseMessage();

                        if (serverResponseCode == 200) {

                            // messageText.setText(msg);
                            Toast.makeText(menuArchivos.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();

                            // recursiveDelete(mDirectory1);

                        }

                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                        // dialog.dismiss();
                        e.printStackTrace();

                    }
                    // dialog.dismiss();

               } // End else block


            } catch (Exception ex) {
                // dialog.dismiss();
                Log.d("mikolerror", ex.getMessage());
                ex.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
        }



        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
