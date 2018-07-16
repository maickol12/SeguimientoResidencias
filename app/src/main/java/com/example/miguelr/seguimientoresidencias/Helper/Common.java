package com.example.miguelr.seguimientoresidencias.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.CallLog;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DataBaseStructure;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.usuarios;
import com.example.miguelr.seguimientoresidencias.MainActivity;
import com.example.miguelr.seguimientoresidencias.PerfilActivity;
import com.example.miguelr.seguimientoresidencias.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by miguelr on 11/03/2018.
 */

public class Common {
    private Context context;
    public Common(Context context){
        this.context = context;
    }

    public AlertDialog dialogErrorLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setPositiveButton("SI",null);

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = (View) inflater.inflate(R.layout.login_error,null);

        builder.setView(v);

        TextView txt1 = (TextView) v.findViewById(R.id.errorLogin);
        String mensaje1 = context.getString(R.string.errorLogin);
        Spanned htmlAsApanned = Html.fromHtml(mensaje1);
        txt1.setText(htmlAsApanned);

        return builder.create();

    }
    public AlertDialog dialogNoExpediente(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((Activity)context).onBackPressed();
            }
        });

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = (View) inflater.inflate(R.layout.error_general,null);

        builder.setView(v);

        TextView txt1 = (TextView) v.findViewById(R.id.errorMensaje);
        String mensaje1 = context.getString(R.string.noExpediente);
        Spanned htmlAsApanned = Html.fromHtml(mensaje1);
        txt1.setText(htmlAsApanned);

        return builder.create();

    }

    public boolean solicitarPermisosEscritura() {
        boolean permissionWriteStorage = false;
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        if(context.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(INTERNET) == PackageManager.PERMISSION_GRANTED){
            return true;
        }

        if( ((Activity)context).shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || ((Activity)context).shouldShowRequestPermissionRationale(INTERNET)){
            dialogoRecomendacion();
        }else{
            ((Activity)context).requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,INTERNET},100);
        }
        return false;
    }

    public void dialogoRecomendacion(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getString(R.string.tituloPermisos));
        dialog.setMessage(context.getString(R.string.mensajePermisos));
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((Activity)context).requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE},100);
                }
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((Activity)context).finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        dialog.show();

    }

    public void dialogoSalir(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getString(R.string.tituloSalir));
        dialog.setMessage(context.getString(R.string.mensajeSalir));
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cerrarSession();
                ((Activity)context).finish();
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
            }
        });
        dialog.setNegativeButton("NO",null);
        dialog.show();
    }
    public void cerrarSession(){
        Alumnos alumno = new Alumnos(context);
        if(alumno.cerrarSession()){
            Log.d("cerrar","ALUMNO CERRADO");
        }else{
            Log.d("cerrar","ERROR AL CERRAR ALUMNO");
        }

        usuarios users = new usuarios(context);
        if(users.cerrarSession()){
            Log.d("cerrar","USUARIO CERRADO");
        }else{
            Log.d("cerrar","ERROR AL CERRAR USUARIO");
        }
    }

    public void dialogoMensajes(String title,String message,Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("SI",null);
        builder.show();
    }

    public void asyncMessages(){
        String url = config.url+config.WebMethodMessages;
        new asyncTask(context,1,url).execute();
    }

    public class asyncTask extends AsyncTask<String,String,String>{
        private Context context;
        private int     type;
        private String  url;
        public asyncTask(Context context,int type,String url){
            this.context = context;
            this.type    = type;
            this.url     = url;
        }
        @Override
        protected void onPreExecute(){


        }

        @Override
            protected String doInBackground(String... strings) {
            String respuesta = "";
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                final HttpClient Client = new DefaultHttpClient();
                HttpPost httppostreq = new HttpPost(url);
                httppostreq.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpresponse = Client.execute(httppostreq);
                respuesta = EntityUtils.toString(httpresponse.getEntity());
            }catch (Exception e){
                Log.d("mensajeerror",e.getMessage());
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String json){
            if(type==1){
                if(json.trim().length()>2){
                    try{
                        JSONArray array = new JSONArray(json);
                        JSONObject obj = array.getJSONObject(0);
                        String titulo = obj.getString("vTitulo");
                        String mensaje = obj.getString("vDescripcion");
                        dialogoMensajes(titulo,mensaje,context);
                    }catch (Exception e){
                        Log.d("mensajeerror",e.getMessage());
                    }
                }
            }
        }
    }
}
