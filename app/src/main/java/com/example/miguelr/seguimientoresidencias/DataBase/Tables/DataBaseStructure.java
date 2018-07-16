package com.example.miguelr.seguimientoresidencias.DataBase.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CallLog;
import android.util.Log;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Malumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.Mcarreras;

/**
 * Created by miguelr on 06/02/2018.
 */

public class DataBaseStructure extends SQLiteOpenHelper{


    /****************************************************************************************************************
     * FIN TABLA DE Usuarios
     ****************************************************************************************************************/
    private Alumnos Calumnos = new Alumnos();
    private Carreras Ccarreras = new Carreras();
    private cartaDePresentacion Cpresentacion = new cartaDePresentacion();
    private solicitudDeResidencias Csolicitud = new solicitudDeResidencias();
    private cartaAceptacion Caceptacion = new cartaAceptacion();
    private expedienteFinal Cexpediente = new expedienteFinal();
    private reportesDeResidencias Creporte = new reportesDeResidencias();
    private usuarios Cusuarios = new usuarios();
    /*****************************************************************************************************************+
     * CREACION DE TABLAS
     ********************************************************************************************************************/

    private String alumnos = "" +
            "CREATE TABLE "+Calumnos.getNameTable()+"(" +
            Calumnos.getiIdAlumno()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Calumnos.getvNombreAlumno()+" TEXT NOT NULL," +
            Calumnos.getvApellidoPaterno()+" TEXT NOT NULL," +
            Calumnos.getvApellidoMaterno()+" TEXT NOT NULL," +
            Calumnos.getvMatricula()+" TEXT NOT NULL," +
            Calumnos.getvCorreo()+" TEXT," +
            Calumnos.getvContrasenia()+" TEXT NOT NULL," +
            Calumnos.getiIdCarrerafk()+" INTEGER NOT NULL," +
            Calumnos.getiServicioSocial()+" INTEGER NOT NULL DEFAULT 1," +
            Calumnos.getiAsignaturasEnEspecial()+" INTEGERT NOT NULL DEFAULT 0," +
            Calumnos.getiCreditosRequeridos()+" INTEGER NOT NULL DEFAULT 1," +
            Calumnos.getiCreditos()+" INTEGER DEFAULT 0," +
            Calumnos.getbSexo()+" TEXT," +
            Calumnos.getdInicioServicio()+" TEXT," +
            Calumnos.getdFinServicio()+" TEXT," +
            Calumnos.getbActive()+" INTEGER DEFAULT 1," +
            "FOREIGN KEY("+Calumnos.getiIdCarrerafk()+")REFERENCES "+Ccarreras.getTableName()+"("+Ccarreras.getiIdCarrera()+")" +
            ")";


    private String carreras = "" +
            "CREATE TABLE "+Ccarreras.getTableName()+"(" +
            Ccarreras.getiIdCarrera()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Ccarreras.getvNombreCarrera()+" TEXT NOT NULL," +
            Ccarreras.getiCreditos()+" INTEGER," +
            Ccarreras.getbActive()+" INTEGER DEFAULT 1" +
            ")";


    private String cartaPresentacion = "CREATE TABLE "+Cpresentacion.getTableName()+"(" +
            Cpresentacion.getIdCartaPresentacion()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Cpresentacion.getIidSolicitudfk()+" INTEGER," +
            Cpresentacion.getvDescripcion()+" TEXT,"+
            Cpresentacion.getbActive()+" INTEGER DEFAULT 1," +
            Cpresentacion.getdFechaCaptura()+" TEXT,"+
            "FOREIGN KEY("+Cpresentacion.getIidSolicitudfk()+")REFERENCES "+Csolicitud.getTableName()+"("+Csolicitud.getiIdSolicitudDeResidencias()+")" +
            ")";


    private String cartaAceptacion = "CREATE TABLE "+Caceptacion.getTableName()+"(" +
            Caceptacion.getIdcartaAceptacion()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Caceptacion.getIdSolicitud()+" INTEGER," +
            Caceptacion.getvDescripcion()+" TEXT,"+
            Caceptacion.getbActive()+" INTEGER DEFAULT 1," +
            Caceptacion.getdFechaCaptura()+" TEXT,"+
            "FOREIGN KEY("+Caceptacion.getIdSolicitud()+")REFERENCES "+Csolicitud.getTableName()+"("+Csolicitud.getiIdSolicitudDeResidencias()+")" +
            ")";


    private String solicitudDeResidencias = "CREATE TABLE "+Csolicitud.getTableName()+"(" +
            Csolicitud.getiIdSolicitudDeResidencias()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Csolicitud.getiIdAlumnofk()+" INTEGER," +
            Csolicitud.getiAprobadoPorAcademia()+" INTEGER DEFAULT 1," +
            Csolicitud.getiAprobadoPorJefeDeCarrera()+" INTEGER DEFAULT 1," +
            Csolicitud.getdFechaAprobacion()+" TEXT," +
            "FOREIGN KEY("+Csolicitud.getiIdAlumnofk()+") REFERENCES "+Calumnos.getNameTable()+"("+Calumnos.getiIdAlumno()+")" +
            ")";

    /*private String observacionesDeSolicitudResidencias = "CREATE TABLE "+TableNameObservacionesDeSolicitudResidencias+"(" +
            iIdObservaciones+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            vDescripcionObservaciones+" TEXT," +
            iIdSolicitudDeResidenciasObservaciones+" INTEGER," +
            "FOREIGN KEY ("+iIdSolicitudDeResidenciasObservaciones+") REFERENCES "+TableNameObservacionesDeSolicitudResidencias+"("+iIdObservaciones+")" +
            ")";*/

    private String expedienteFinal = "CREATE TABLE "+Cexpediente.getTableName()+"(" +
            Cexpediente.getiIdExpedienteFinal()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Cexpediente.getvDescripcion()+" TEXT," +
            Cexpediente.getiProcesoFinalizado()+" INTEGER DEFAULT 0," +
            Cexpediente.getiCalificacion()+" INTEGER DEFAULT 0," +
            Cexpediente.getiIdSolicitudResidenciasfk()+" INTEGER," +
            "FOREIGN KEY ("+Cexpediente.getiIdSolicitudResidenciasfk()+") REFERENCES "+Csolicitud.getTableName()+"("+Csolicitud.getiIdSolicitudDeResidencias()+")" +
            ")";


    private String reportesDeResidencias = "CREATE TABLE "+Creporte.getTableName()+"(" +
            Creporte.getiIdReporte()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Creporte.getiIdSolicitudfk()+" INTEGER," +
            Creporte.getiAprobadoAcesorInterno()+" INTEGER," +
            Creporte.getiAprobadoJefeDeCarrera()+" INTEGER," +
            Creporte.getiHojaAsesoria()+" INTEGER," +
            Creporte.getvNumeroReporte()+" TEXT," +
            Creporte.getvDescripcion()+" TEXT," +
            Creporte.getdFechaEntrega()+" TEXT," +
            Creporte.getdFechaCaptura()+" TEXT," +
            Creporte.getbActive()+" INTEGER DEFAULT 1," +
            Creporte.getdFechaLimite()+" TEXT, "+
            "FOREIGN KEY ("+Creporte.getiIdSolicitudfk()+") REFERENCES "+Csolicitud.getTableName()+"("+Csolicitud.getiIdSolicitudDeResidencias()+")" +
            ")";

    private String Usuarios = "CREATE TABLE "+Cusuarios.getTableName()+"(" +
            Cusuarios.getIdUsuario()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            Cusuarios.getvNombre()+" TEXT,"+
            Cusuarios.getvApellidoPaterno()+" TEXT," +
            Cusuarios.getvApellidoMaterno()+" TEXT," +
            Cusuarios.getvCorreo()+" TEXT," +
            Cusuarios.getvContrasenia()+" TEXT," +
            Cusuarios.getvUsuario()+" TEXT," +
            Cusuarios.getbActive()+" INTEGER DEFAULT 1," +
            Cusuarios.getRoot()+" INTEGER DEFAULT 0"+
            ")";


    public DataBaseStructure(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(alumnos);
        //db.execSQL(carreras);
        db.execSQL(cartaPresentacion);
        db.execSQL(solicitudDeResidencias);
        db.execSQL(expedienteFinal);
        db.execSQL(reportesDeResidencias);
        db.execSQL(Usuarios);
        db.execSQL(cartaAceptacion);

        //VERSION 2
        db.execSQL(Malumnos.createTable());
        db.execSQL(Mcarreras.createTable());


        llenarDatos(db);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+Calumnos.getNameTable());
        db.execSQL("DROP TABLE IF EXISTS "+Ccarreras.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+Cpresentacion.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+Csolicitud.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+Cexpediente.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+Creporte.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+Cusuarios.getTableName());
        db.execSQL("DROP TABLE IF EXISTS "+Caceptacion.getTableName());

        //VERSION 2
        db.execSQL(Malumnos.deleteTable());
        db.execSQL(Mcarreras.deleteTable());


        this.onCreate(db);
    }

    private void llenarDatos(SQLiteDatabase db){
        ContentValues cv2 = new ContentValues();
        cv2.put(Mcarreras.idCarrera,1);
        cv2.put(Mcarreras.vCarrera,"Sistemas");
        cv2.put(Mcarreras.iCreditos,200);
        db.insert(Mcarreras.table,null,cv2);

        ContentValues values = new ContentValues();
        values.put(Calumnos.getiIdAlumno(),"1");
        values.put(Calumnos.getvNombreAlumno(),"maickol");
        values.put(Calumnos.getvApellidoPaterno(),"rodriguez");
        values.put(Calumnos.getvApellidoMaterno(),"cornejo");
        values.put(Calumnos.getvMatricula(),"14020463");
        values.put(Calumnos.getvCorreo(),"al14020463@itsa.edu.mx");
        values.put(Calumnos.getvContrasenia(),"123");
        values.put(Calumnos.getiIdCarrerafk(),1);
        values.put(Calumnos.getiServicioSocial(),1);
        values.put(Calumnos.getiAsignaturasEnEspecial(),0);
        values.put(Calumnos.getiCreditosRequeridos(),1);
        values.put(Calumnos.getbSexo(),1);
        values.put(Calumnos.getdInicioServicio(),"10-03-2018");
        values.put(Calumnos.getdFinServicio(),"10-06-2018");
        db.insert(Calumnos.getNameTable(),null,values);


    }



}
