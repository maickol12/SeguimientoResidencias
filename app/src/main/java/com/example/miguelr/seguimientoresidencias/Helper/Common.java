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
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DBTablas.Msectores;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.DataBase;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.ArchivoSeleccionado;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Carreras;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Estados;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Giros;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Opciones;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Periodos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.ProyectoSeleccionado;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Sectores;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.bancoProyectos;
import com.example.miguelr.seguimientoresidencias.Login.MainActivity;
import com.example.miguelr.seguimientoresidencias.MenuArchivos.menuArchivos;
import com.example.miguelr.seguimientoresidencias.R;
import com.example.miguelr.seguimientoresidencias.escogerProyecto.escogerProyecto;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private ArchivoSeleccionado archivoSeleccionado;
    public Common(Context context){
        this.context             = context;
        this.session             = new sessionHelper(context);

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
    public AlertDialog.Builder dialogoGeneral(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("SI",null);
        return builder;
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

    public void asyncFile(String vRuta,String UUID){
        new UploadFileAsync(vRuta,UUID).execute();
    }
    public void asynDataFiles(String UUID,int idAlumno,int idTipoDocumento,int idProyectoSeleccionado){
        new asyncTaskSyncDataDocuments(idProyectoSeleccionado,idAlumno,idTipoDocumento,UUID).execute();
    }

    public void asyncLogin(String usuario,String contrasenia){
        String url = config.url+config.WebMethodLogin;
        new asyncTaskLogin(url,usuario,contrasenia).execute();
    }

    public void asyncEncogerProyecto(int idBancoProyecto,int idAlumno,int idPeriodo,int idOpcion,int idGiro,int idEstado, int idSector){
        new asyncTaskEscogerProyecto(idBancoProyecto,idAlumno,idPeriodo,idOpcion,idGiro,idEstado,idSector).execute();
    }

    public class asyncTaskEscogerProyecto extends AsyncTask<Void,String,String>{
        private ProgressDialog dialog;
        private int idBancoProyecto,idAlumno,idPeriodo,idOpcion,idGiro,idEstado,idSector;
        public asyncTaskEscogerProyecto(int idBancoProyecto,int idAlumno,int idPeriodo,int idOpcion,int idGiro,int idEstado, int idSector){
            this.idBancoProyecto = idBancoProyecto;
            this.idAlumno        = idAlumno;
            this.idPeriodo       = idPeriodo;
            this.idOpcion        = idOpcion;
            this.idGiro          = idGiro;
            this.idEstado        = idEstado;
            this.idSector        = idSector;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = getProgressBar("ITSA","Espera un momento , estamos comprobando la información");
            dialog.show();
        }

        @Override
        protected  String doInBackground(Void... voids) {
            String json = "[]";
            try{
                List<NameValuePair> values = new ArrayList<>();
                values.add(new BasicNameValuePair("idBancoProyecto",String.valueOf(idBancoProyecto)));
                values.add(new BasicNameValuePair("idAlumno",String.valueOf(idAlumno)));
                values.add(new BasicNameValuePair("idPeriodo",String.valueOf(idPeriodo)));
                values.add(new BasicNameValuePair("idOpcion",String.valueOf(idOpcion)));
                values.add(new BasicNameValuePair("idGiro",String.valueOf(idGiro)));
                values.add(new BasicNameValuePair("idEstado",String.valueOf(idEstado)));
                values.add(new BasicNameValuePair("idSector",String.valueOf(idSector)));

                final HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(config.url+config.WebMehodEscogerProy);
                post.setEntity(new UrlEncodedFormEntity(values));
                HttpResponse response = client.execute(post);
                json = EntityUtils.toString(response.getEntity());

            }catch (Exception e){
                Log.d("error",e.getMessage());
                json = "[]";
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            try{
                JSONObject object = new JSONObject(json);
                JSONArray table1 = object.getJSONArray("tabla1");
                int response = table1.getJSONObject(0).getInt("response");

                JSONArray table2 = object.getJSONArray("tabla2");
                JSONObject obj = table2.getJSONObject(0);

                int idAlumno = obj.getInt("idAlumno");

                ProyectoSeleccionado proyecto = new ProyectoSeleccionado(context);
                proyecto.setIdProyectoSeleccionado(obj.getInt("idProyectoSeleccionado"));
                proyecto.setIdBancoProyecto(obj.getInt("idbancoProyecto"));
                proyecto.setIdAlumno(idAlumno);
                proyecto.setIdPeriodo(obj.getInt("idPeriodo"));
                proyecto.setIdOpcion(obj.getInt("idOpcion"));
                proyecto.setIdGiro(obj.getInt("idGiro"));
                proyecto.setIdEstado(obj.getInt("idEstado"));
                proyecto.setIdSector(obj.getInt("idSector"));
                /*proyecto.setvNombreProyecto(obj.getString("vNombreProyecto"));
                proyecto.setvDescripcion(obj.getString("vDescripcion"));
                proyecto.setvDependencia(obj.getString("vDependencia"));*/
                proyecto.setvMotivoNoAceptacion(obj.getString("vMotivoNoAceptacion"));
               // proyecto.setbCartaAceptacion(obj.getInt("bCartAceptacion"));
                proyecto.setbCartaPresentacion(obj.getInt("bCartaPresentacion"));
                proyecto.setbSolicitud(obj.getInt("bSolicitud"));
                proyecto.setbReporte1(obj.getInt("bReporte1"));
                proyecto.setbReporte2(obj.getInt("bReporte2"));
                proyecto.setbReporte3(obj.getInt("bReporte3"));

                if(response==200){
                    if(proyecto.guardar()){
                        dialogoMensajes("ITSA","Proyecto seleccionado correctamente");
                    }else{
                        dialogoMensajes("ERROR ITSA","Ocurrio algo inesperado");
                    }
                }else if(response == 500){
                    if(proyecto.obtenerIdProyectoSeleccionado(idAlumno)==0){
                        if(proyecto.guardar()){
                            Log.d("save","si");
                        }else{
                            Log.d("save","no");
                        }
                    }else{
                        Log.d("save","ya guardado");
                    }
                    dialogoMensajes("ITSA","Ya cuentas con un proyecto seleccionado");
                }


                proyecto.cerrarDB();


            }catch (Exception e){
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
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
                JSONArray tabla3 = object.getJSONArray("tabla3");
                JSONArray tabla4 = object.getJSONArray("tabla4");


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

                    if(tabla3.length()>0){
                        ArchivoSeleccionado as = new ArchivoSeleccionado(context);
                        as.borrar(alumnos.getIdAlumno());
                        for(int i = 0;i<tabla3.length();i++){
                            object = tabla3.getJSONObject(i);
                            as = new ArchivoSeleccionado(context);
                            as.setIdArchivo(object.getInt("idDocumento"));
                            as.setvRuta(object.getString("vRuta"));
                            as.setIdArchivo(object.getInt("idAlumno"));
                            as.setIdProyectoSeleccionado(object.getInt("idProyectoSeleccionado"));
                            as.setTipoArchivo(object.getInt("idTipoDocumento"));
                            as.setbSyncFile(1);
                            as.setbSyncData(1);
                            as.setUUID(object.getString("vNombre"));

                            as.guardar();
                        }

                    }

                    if(tabla4.length()>0) {
                        ProyectoSeleccionado ps = new ProyectoSeleccionado(context);
                        ps.borrar(alumnos.getIdAlumno());


                        object = tabla4.getJSONObject(0);
                        ps.setIdProyectoSeleccionado(object.getInt("idProyectoSeleccionado"));
                        ps.setIdBancoProyecto(object.getInt("idbancoProyecto"));
                        ps.setIdAlumno(object.getInt("idAlumno"));
                        ps.setIdPeriodo(object.getInt("idPeriodo"));
                        ps.setIdOpcion(object.getInt("idOpcion"));
                        ps.setIdGiro(object.getInt("idGiro"));
                        ps.setIdEstado(object.getInt("idEstado"));
                        ps.setIdSector(object.getInt("idSector"));
                        ps.setbCartaAceptacion(object.getInt("bCartaAceptacion"));
                        ps.setbCartaPresentacion(object.getInt("bCartaPresentacion"));
                        ps.setbSolicitud(object.getInt("bSolicitud"));
                        ps.setbReporte1(object.getInt("bReporte1"));
                        ps.setbReporte1(object.getInt("bReporte2"));
                        ps.setbReporte1(object.getInt("bReporte3"));

                        ps.guardar();
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

    public class asyncTaskSyncDataDocuments extends AsyncTask<Void,Void,Void>{
        private int idProyectoSeleccionado,idAlumno,idTipoDocumento;
        private String UUID;
        private ProgressDialog dialog;

        public asyncTaskSyncDataDocuments(int idProyectoSeleccionado,int idAlumno,int idTipoDocumento,String UUID){
            this.idProyectoSeleccionado = idProyectoSeleccionado;
            this.idAlumno               = idAlumno;
            this.idTipoDocumento        = idTipoDocumento;
            this.UUID                   = UUID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context);
            this.dialog.setMessage("Espera un momento porfavor");
            this.dialog.setTitle("ITSA");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String respuesta = "";
            try{
                List<NameValuePair> values = new ArrayList<>();
                values.add(new BasicNameValuePair("idProyectoSeleccionado",""+this.idProyectoSeleccionado));
                values.add(new BasicNameValuePair("idTipoDocumento",""+this.idTipoDocumento));
                values.add(new BasicNameValuePair("UUID",this.UUID));
                values.add(new BasicNameValuePair("idAlumno",""+this.idAlumno));
                final HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(config.url+config.WebMethoduploadDataFromFile);
                post.setEntity(new UrlEncodedFormEntity(values));
                HttpResponse httpresponse = client.execute(post);
                respuesta = EntityUtils.toString(httpresponse.getEntity());
                Log.d("respuesta",respuesta);
                JSONObject json = new JSONObject(respuesta);
                JSONArray jsonTabla1 = json.getJSONArray("tabla1");
                JSONArray jsonTabla2 = json.getJSONArray("tabla2");

                JSONObject objt2 = jsonTabla2.getJSONObject(0);
                String UUID = objt2.getString("UUID").toString();
                archivoSeleccionado = new ArchivoSeleccionado(context);
                if(archivoSeleccionado.updateBitDato(UUID)){
                    Log.d("bitData","data sincronizada con exito");
                }else{
                    Log.d("bitData","Ocurrio un error al actualizar el bit de data");
                }

            }catch (Exception e){
                Log.d("error",e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.dialog.dismiss();
        }
    }
    public void redirigirMenu(){
        Intent intent = new Intent(context, menuPrincipal.class);
        ((MainActivity)context).finish();
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
                g.cerrarDB();
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
                 * Sectores
                 *****************************/
                JSONArray sectores = array.getJSONArray("sectores");
                Sectores sec = new Sectores(context);
                sec.borrar();
                for (int i = 0;i<sectores.length();i++){
                    obj = sectores.getJSONObject(i);
                    sec.setIdSector(obj.getInt(Msectores.idSector));
                    sec.setvSector(obj.getString(Msectores.vSector));
                    if(sec.guardar()){
                        Log.d("guardar","sector guardado");
                    }else{
                        Log.d("guardar","sector no guardado");
                    }
                }
                sec.cerrarDB();

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
                  //  bp.setvDependencia(obj.getString(Mbancoproyectos.vDependencia));
                    bp.setiTotalResidentes(obj.getInt(Mbancoproyectos.iTotalResidentes));
                    if(bp.guardar()){
                        Log.d("guardar","bancoProyecto guardado");
                    }else{
                        Log.d("guardar","bancoProyecto no guardado");
                    }
                }
                bp.cerrarDB();

                /****************************
                 * estados
                 *****************************/
                JSONArray estados = array.getJSONArray("estados");
                Estados e = new Estados(context);
                e.borrar();
                for (int i = 0;i<estados.length();i++){
                    obj = estados.getJSONObject(i);
                    e.setIdEstado(obj.getInt("idEstado"));
                    e.setvEstado(obj.getString("vEstado"));
                    e.setBactivo(obj.getInt("bActivo"));

                    if(e.guardar()){
                        Log.d("guardar","estados guardado");
                    }else{
                        Log.d("guardar","estados no guardado");
                    }
                }


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

    private class UploadFileAsync extends AsyncTask<String, Void, String> {
        private String vFile,UUID;
        private ProgressDialog dialog;
        private int subidadExito = 0;
        private StringBuffer stringBuffer;
        public UploadFileAsync(String vFile,String UUID){
            this.vFile = vFile;
            this.UUID  =  UUID;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Espera un momento porfavor");
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
                        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);



                        conn.setRequestProperty("bill", sourceFileUri);




                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\"" + UUID +"\"" + lineEnd);
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
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                        String mensaje = conn.getResponseMessage();
                        int serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn
                                .getResponseMessage();

                        if (serverResponseCode == 200) {
                            subidadExito = 1;
                            archivoSeleccionado = new ArchivoSeleccionado(context);
                            archivoSeleccionado.updateBitArchivo(UUID);

                            archivoSeleccionado = archivoSeleccionado.obtenerInformacionArchivo(UUID);
                            String respuesta = "";
                            try{
                                List<NameValuePair> values = new ArrayList<>();
                                values.add(new BasicNameValuePair("idProyectoSeleccionado",""+archivoSeleccionado.idProyectoSeleccionado));
                                values.add(new BasicNameValuePair("idTipoDocumento",""+archivoSeleccionado.tipoArchivo));
                                values.add(new BasicNameValuePair("UUID",archivoSeleccionado.UUID));
                                values.add(new BasicNameValuePair("idAlumno",""+archivoSeleccionado.idAlumno));
                                final HttpClient client = new DefaultHttpClient();
                                HttpPost post = new HttpPost(config.url+config.WebMethoduploadDataFromFile);
                                post.setEntity(new UrlEncodedFormEntity(values));
                                HttpResponse httpresponse = client.execute(post);
                                respuesta = EntityUtils.toString(httpresponse.getEntity());
                                Log.d("respuesta",respuesta);
                                JSONObject json = new JSONObject(respuesta);
                                JSONArray jsonTabla1 = json.getJSONArray("tabla1");
                                JSONArray jsonTabla2 = json.getJSONArray("tabla2");

                                JSONObject objt2 = jsonTabla2.getJSONObject(0);
                                String UUID = objt2.getString("UUID").toString();
                                archivoSeleccionado = new ArchivoSeleccionado(context);
                                if(archivoSeleccionado.updateBitDato(UUID)){
                                    Log.d("bitData","data sincronizada con exito");
                                }else{
                                    Log.d("bitData","Ocurrio un error al actualizar el bit de data");
                                }

                            }catch (Exception e){
                                Log.d("error",e.getMessage());
                            }

                        }


                        // close the streams //
                        fileInputStream.close();


                        dos.flush();
                        dos.close();

                    } catch (Exception e) {
                        Log.d("mikolerror", e.getMessage());
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
            if(subidadExito==1){
                //Toast.makeText(context, "Subida de archivo completada. ", Toast.LENGTH_SHORT).show();
                dialogoGeneral("ITSA","Archivos subidos con exito").show();
            }
            dialog.dismiss();
        }



        @Override
        protected void onProgressUpdate(Void... values) {
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
