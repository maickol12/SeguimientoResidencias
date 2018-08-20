package com.example.miguelr.seguimientoresidencias.Helper;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by miguelr on 13/02/2018.
 */

public class config {
    public static int versionDB                         = 16;
    public static String dbName                         = "ServicioSocial";
    public static String url                            = "http://residenciasitsa.diplomadosdelasep.com.mx/wssegres/";
    public static String WebMethodMessages              = "getMessages";
    public static String WebMethodCatalogs              = "getCatalogs";
    public static String WebMethoduploadDataFromFile    = "uploadDataFromFile";
    public static String WebMethodSaveUsuer             = "registrarAlumno";
    public static String WebMehodEscogerProy            = "escogerProyecto";
    public static String WebMethodLogin                 = "login";

    /**********************************************************************************************************
     * METODO PARA RESPALDAR UNA BASE DE DATOS DE SQLITE V2
     ***********************************************************************************************************/

    public static String getDateTime(){
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return df.format(currentTime).toString();
    }
    public static String getDate(){
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(currentTime).toString();
    }
    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
