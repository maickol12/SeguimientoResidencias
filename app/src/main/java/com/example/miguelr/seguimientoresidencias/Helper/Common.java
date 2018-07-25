package com.example.miguelr.seguimientoresidencias.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Malumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mbancoproyectos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mgiros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mopciones;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Mperiodos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DataBase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Carreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Giros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Opciones;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Periodos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.bancoProyectos;
import com.example.miguelr.seguimientoresidencias.Login.MainActivity;
import com.example.miguelr.seguimientoresidencias.R;
import com.example.miguelr.seguimientoresidencias.menuPrincipal.menuPrincipal;

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
import java.util.List;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by miguelr on 11/03/2018.
 */

public class Common {
    private Context context;
    private sessionHelper session;
    public Common(Context context){
        this.context = context;
        this.session = new sessionHelper(context);
    }

    public SQLiteDatabase databaseReadeable(){
        return new DataBase(context,config.dbName,null,config.versionDB).getReadableDatabase();
    }
    public SQLiteDatabase databaseWritable(){
        return new DataBase(context,config.dbName,null,config.versionDB).getWritableDatabase();
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
                session.logout();
                ((Activity)context).finish();
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);

            }
        });
        dialog.setNegativeButton("NO",null);
        dialog.show();
    }


    public void dialogoMensajes(String title,String message){
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

    public ProgressDialog getProgressBar(String titulo,String mensaje){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(mensaje);
        dialog.setTitle(titulo);
        dialog.setMax(100);
        dialog.setProgress(0);
        dialog.setCancelable(false);
        return dialog;
    }

    public void asyncDescargarCatalogos(){
        String url = config.url+config.WebMethodCatalogs;
        new asyncTaskDownloadCatalogs(context,1,url).execute();
    }

    public void asyncLogin(String usuario,String contrasenia){
        String url = config.url+config.WebMethodLogin;
        new asyncTaskLogin(url,usuario,contrasenia).execute();
    }

    public class asyncTaskLogin extends AsyncTask<String,String,String>{
        private String url;
        private ProgressDialog progressDialog;
        private String usuario;
        private String contrasenia;
        public asyncTaskLogin(String url,String usuario,String contrasenia){
            this.url            = url;
            this.usuario        = usuario;
            this.contrasenia    = contrasenia;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = getProgressBar("ITSA","Espera un momento , estamos comprobando la información");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String respuesta = "";
            try{
                List<NameValuePair> values = new ArrayList<>();
                values.add(new BasicNameValuePair("vUsuario",this.usuario));
                values.add(new BasicNameValuePair("vContrasena",this.contrasenia));
                final HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(this.url);
                post.setEntity(new UrlEncodedFormEntity(values));
                HttpResponse httpresponse = client.execute(post);
                respuesta = EntityUtils.toString(httpresponse.getEntity());

            }catch (Exception e){
                Log.d("error",e.getMessage());
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String json) {
            progressDialog.dismiss();
            try{
                JSONObject object = new JSONObject(json);
                JSONArray tabla1 = object.optJSONArray("tabla1");
                JSONArray tabla2 = object.optJSONArray("tabla2");


                int response = tabla1.getJSONObject(0).getInt("response");
                if(response == 200){
                    Alumnos alumnos = new Alumnos(context);

                    JSONObject jsonAlumno = tabla2.getJSONObject(0);
                    alumnos.setIdAlumno(jsonAlumno.getInt(Malumnos.idAlumno));
                    alumnos.setIdCarrera(jsonAlumno.getInt(Malumnos.idCarrera));
                    alumnos.setIdUsuario(jsonAlumno.getInt(Malumnos.idUsuario));
                    alumnos.setbSexo(jsonAlumno.getInt(Malumnos.bSexo));
                    alumnos.setvNumeroControl(jsonAlumno.getString(Malumnos.vNumeroControl));
                    alumnos.setvNombre(jsonAlumno.getString(Malumnos.vNombre));
                    alumnos.setvApellidoPaterno(jsonAlumno.getString(Malumnos.vApellidoPaterno));
                    alumnos.setvApellidoMaterno(jsonAlumno.getString(Malumnos.vApellidoMaterno));
                    alumnos.setvSemestre(isNull(jsonAlumno.getString(Malumnos.vSemestre)));
                    alumnos.setvPlanEstudios(isNull(jsonAlumno.getString(Malumnos.vPlanEstudios)));
                    alumnos.setdFechaIngreso(jsonAlumno.getString(Malumnos.dFechaIngreso));
                    alumnos.setdFechaTermino(jsonAlumno.getString(Malumnos.dFechaTermino));
                    alumnos.setiCreditosTotales(isNull(jsonAlumno.getString(Malumnos.iCreditosTotales)));
                    alumnos.setiCreditosAcumulados(isNull(jsonAlumno.getString(Malumnos.iCreditosAcumulados)));
                    alumnos.setfPorcentaje(isNullDouble(jsonAlumno.getString(Malumnos.fPorcentaje)));
                    alumnos.setiPeriodo(isNull(jsonAlumno.getString(Malumnos.iPeriodo)));
                    alumnos.setfPromedio(isNullDouble(jsonAlumno.getString(Malumnos.fPromedio)));
                    alumnos.setvSituacion(jsonAlumno.getString(Malumnos.vSituacion));
                    alumnos.setbServicioSocial(isNull(jsonAlumno.getString(Malumnos.bServicioSocial)));
                    alumnos.setbActividadesComplementarias(isNull(jsonAlumno.getString(Malumnos.bActividadesComplementarias)));
                    alumnos.setbMateriasEspecial(isNull(jsonAlumno.getString(Malumnos.bMateriasEspecial)));
                    alumnos.setvCorreoInstitucional(jsonAlumno.getString(Malumnos.vCorreoInstitucional));
                    alumnos.setdFechaNacimiento(jsonAlumno.getString(Malumnos.dFechaNacimiento));

                    if(alumnos.buscar()){
                        alumnos.borrar();
                    }
                    if(alumnos.guardar()){
                       session.createSession(alumnos);
                        redirigirMenu();
                    }else{
                        Toast.makeText(context,"Ocurrio un error al guardar el usuario",Toast.LENGTH_LONG).show();
                    }
                }else{
                    dialogErrorLogin().show();
                }
            }catch (Exception e){
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }


        }
    }
    public void redirigirMenu(){
        Intent intent = new Intent(context, menuPrincipal.class);
        context.startActivity(intent);
    }

    public class asyncTaskDownloadCatalogs extends AsyncTask<Void,Void,Void>{
        private Context context;
        private int     type;
        private String  url;
        private ProgressDialog progressDialog;
        public asyncTaskDownloadCatalogs(Context context,int type,String url){
            this.context = context;
            this.type    = type;
            this.url     = url;
        }
        @Override
        protected void onPreExecute(){
            progressDialog = getProgressBar("ITSA","Espera un momento , estamos descargando información");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... strings) {
            String respuesta = "";
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                final HttpClient Client = new DefaultHttpClient();
                HttpPost httppostreq = new HttpPost(url);
                httppostreq.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpresponse = Client.execute(httppostreq);
                respuesta = EntityUtils.toString(httpresponse.getEntity());

                JSONObject array = new JSONObject(respuesta);
                /****************************
                 * CARRERAS
                 *****************************/
                JSONArray jsonCarreras = array.optJSONArray("carreras");
                JSONObject obj = null;
                Carreras objCarreras = new Carreras(context);
                objCarreras.abrirDB();
                objCarreras.borrar();
                for (int i = 0;i<jsonCarreras.length();i++){
                    obj = jsonCarreras.getJSONObject(i);
                    objCarreras.setIdCarrera(obj.getInt("idCarrera"));
                    objCarreras.setvCarrera(obj.getString("vCarrera"));
                    if(objCarreras.guardar()){
                        Log.d("guardar","Carrera guardada");
                    }else{
                        Log.d("guardar","Carrera no guardada");
                    }
                }
                objCarreras.cerrarDB();


                /****************************
                 * Giros
                 *****************************/
                JSONArray giros = array.getJSONArray("giros");
                Giros g = new Giros(context);
                g.borrar();
                for (int i = 0;i<giros.length();i++){
                    obj = giros.getJSONObject(i);
                    g.setIdGiro(obj.getInt(Mgiros.idGiro));
                    g.setvGiro(obj.getString(Mgiros.vGiro));
                    if(g.guardar()){
                        Log.d("guardar","giro guardado");
                    }else{
                        Log.d("guardar","giro no guardado");
                    }
                }

                /****************************
                 * periodos
                 *****************************/
                JSONArray periodos = array.getJSONArray("periodos");
                Periodos per = new Periodos(context);
                per.borrar();
                for (int i = 0;i<periodos.length();i++){
                    obj = periodos.getJSONObject(i);
                    per.setIdPeriodo(obj.getInt(Mperiodos.idPeriodo));
                    per.setvPeriodo(obj.getString(Mperiodos.vPeriodo));
                    if(per.guardar()){
                        Log.d("guardar","periodo guardado");
                    }else{
                        Log.d("guardar","periodo no guardado");
                    }
                }
                per.cerrarDB();

                /****************************
                 * opciones
                 *****************************/
                JSONArray opciones = array.getJSONArray("opciones");
                Opciones op = new Opciones(context);
                op.borrar();
                for (int i = 0;i<opciones.length();i++){
                    obj = opciones.getJSONObject(i);
                    op.setIdOpcion(obj.getInt(Mopciones.idOpcion));
                    op.setvOpcion(obj.getString(Mopciones.vOpcion));
                    if(op.guardar()){
                        Log.d("guardar","opcion guardado");
                    }else{
                        Log.d("guardar","opcion no guardado");
                    }
                }
                op.cerrarDB();
                /****************************
                 * bancoProyectos
                 *****************************/
                JSONArray bancoPro = array.getJSONArray("bancoProyectos");
                bancoProyectos bp  = new bancoProyectos(context);
                bp.borrar();
                for (int i = 0;i<bancoPro.length();i++){
                    obj = bancoPro.getJSONObject(i);
                    bp.setIdbancoProyecto(obj.getInt(Mbancoproyectos.idbancoProyecto));
                    bp.setIdEmpresa(obj.getInt(Mbancoproyectos.idEmpresa));
                    bp.setIdCarrera(obj.getInt(Mbancoproyectos.idCarrera));
                    bp.setIdEstado(obj.getInt(Mbancoproyectos.idEstado));
                    bp.setvNombreProyecto(obj.getString(Mbancoproyectos.vNombreProyecto));
                    bp.setvDescripcion(obj.getString(Mbancoproyectos.vDescripcion));
                    bp.setvDependencia(obj.getString(Mbancoproyectos.vDependencia));
                    bp.setiTotalResidentes(obj.getInt(Mbancoproyectos.iTotalResidentes));
                    if(bp.guardar()){
                        Log.d("guardar","bancoProyecto guardado");
                    }else{
                        Log.d("guardar","bancoProyecto no guardado");
                    }
                }
                bp.cerrarDB();

            }catch (Exception e){
                Log.d("error",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args){
            progressDialog.dismiss();
        }
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
                        dialogoMensajes(titulo,mensaje);
                    }catch (Exception e){
                        Log.d("mensajeerror",e.getMessage());
                    }
                }
            }
        }
    }

    public int isNull(String dato){
        int response = 0;
        if(!dato.equalsIgnoreCase("null")){
            response = Integer.parseInt(dato);
        }
        return response;
    }
    public double isNullDouble(String dato){
        double response = 0;
        if(!dato.equalsIgnoreCase("null")){
            response = Integer.parseInt(dato);
        }
        return response;
    }


}
