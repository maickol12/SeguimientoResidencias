package com.example.miguelr.seguimientoresidencias.MenuArchivos;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.ArchivoSeleccionado;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.ProyectoSeleccionado;
import com.example.miguelr.seguimientoresidencias.Helper.Common;
import com.example.miguelr.seguimientoresidencias.Helper.Tools;
import com.example.miguelr.seguimientoresidencias.Helper.ViewAnimation;
import com.example.miguelr.seguimientoresidencias.Helper.config;
import com.example.miguelr.seguimientoresidencias.Helper.sessionHelper;
import com.example.miguelr.seguimientoresidencias.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by miguelr on 07/08/2018.
 */

public class menuArchivos extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageReporte3,imageReporte2,imageReporte1,imageCartaAceptacion,imageCartaPresentacion;
    private TextView cartaPresentacionMensaje;
    private int TIPO_DE_ARCHIVO;
    private ArchivoSeleccionado archivoSel;
    private ProyectoSeleccionado proySelec;
    private Common common;
    private sessionHelper sessionHelper;

    private View parent_view;

    private NestedScrollView nested_scroll_view;
    private ImageButton bt_toggle_carta_presentacion,bt_toggle_carta_aceptacion,bt_toggle_carta_reporte_1,bt_toggle_carta_reporte_2,bt_toggle_carta_reporte_3,bt_toggle_input;
    private Button bt_hide_carta_presentacion,bt_hide_carta_aceptacion,bt_hide_carta_reporte_1,bt_hide_carta_reporte_2,bt_hide_carta_reporte_3,bt_save_input, bt_hide_input;
    private View lyt_expand_carta_presentacion,lyt_expand_carta_aceptacion,lyt_expand_reporte_1,lyt_expand_reporte_2,lyt_expand_reporte_3, lyt_expand_input;

    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.menu_archivos);

        toolbar     = (Toolbar) findViewById(R.id.toolbarGeneral);
        parent_view = findViewById(android.R.id.content);
        /*imageReporte1               = (ImageView) findViewById(R.id.imageReporte1);
        imageReporte2               = (ImageView) findViewById(R.id.imageReporte2);
        imageReporte3               = (ImageView) findViewById(R.id.imgreporte3);
        imageCartaAceptacion        = (ImageView) findViewById(R.id.imageCartaAceptacion);
        imageCartaPresentacion      = (ImageView) findViewById(R.id.imageCartaPresentacion);
        cartaPresentacionMensaje    = (TextView)  findViewById(R.id.cartaPresentacionMensaje);
        */



        archivoSel                  = new ArchivoSeleccionado(this);
        common                      = new Common(this);
        proySelec                   = new ProyectoSeleccionado(this);
        sessionHelper               = new sessionHelper(this);


        initComponent();
        configurations();
    }
    public void configurations(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);

        /*imageReporte1.startAnimation(anim);
        imageReporte2.startAnimation(anim);
        imageReporte3.startAnimation(anim);
        imageCartaAceptacion.startAnimation(anim);
        imageCartaPresentacion.startAnimation(anim);*/


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
            case R.id.guardarCambios:
                guardarInformacion();
                break;
        }
        return true;
    }

    public void escogerArhivo(View v){
        switch (v.getId()){
            case R.id.imageCartaPresentacion:
                TIPO_DE_ARCHIVO = 4;
                break;
            case R.id.imageCartaAceptacion:
                TIPO_DE_ARCHIVO = 9;
                break;
            case R.id.imageReporte1:
                TIPO_DE_ARCHIVO = 5;
                break;
            case R.id.imageReporte2:
                TIPO_DE_ARCHIVO = 6;
                break;
            case R.id.imgreporte3:
                TIPO_DE_ARCHIVO = 7;
                break;
        }

        if(proySelec.obtenerIdProyectoSeleccionado(sessionHelper.obtenerIdAlumno())>0){

            AlertDialog.Builder builder =  common.dialogoGeneral("ITSA","Seguro que deseas subir el archivo?");

            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showFileChooser();
                }
            });
            builder.setNegativeButton("NO",null);

            builder.show();
        }else{
            common.dialogoGeneral("ITSA","No has seleccionado ningún proyecto , debes de seleccionar uno..").show();
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

                    Uri uri = data.getData();
                    File sourceFile = new File(uri.toString());

                  /*  if(TIPO_DE_ARCHIVO==4){
                        imageCartaPresentacion.clearAnimation();
                    }*/

                    //new UploadFileAsync(uri.getPath()).execute();
                    int idAlumno = sessionHelper.obtenerIdAlumno();
                    archivoSel.setvRuta(uri.getPath());
                    archivoSel.setdDate(config.getDateTime());
                    archivoSel.setUUID(config.generateUUID()+"."+getFileExtension(sourceFile));
                    archivoSel.setIdAlumno(idAlumno);
                    int proySel = proySelec.obtenerIdProyectoSeleccionado(idAlumno);
                    archivoSel.setIdProyectoSeleccionado(proySel);
                    archivoSel.setbSyncData(0);
                    archivoSel.setbSyncFile(0);
                    archivoSel.setTipoArchivo(TIPO_DE_ARCHIVO);

                    if(!archivoSel.buscar()){
                        if(archivoSel.guardar()){
                            Toast.makeText(this,"Archivo guardado con exito en la base de datos local",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(this,"No se pudo guardar el archivo",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(this,"No puedes volver a cargar el mismo archivo",Toast.LENGTH_LONG).show();
                    }

                    //cartaPresentacionMensaje.setVisibility(View.VISIBLE);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private void guardarInformacion(){
        ArrayList<ArchivoSeleccionado> archivoSeleccionados = archivoSel.obtenerArchivosSincronizar();
        boolean someData = false;
        if(archivoSeleccionados!=null){
            for(ArchivoSeleccionado as : archivoSeleccionados){
                common.asyncFile(as.getvRuta(),as.getUUID());
            }
            someData = true;
        }

       /* ArrayList<ArchivoSeleccionado> dataArchivosSync = archivoSel.obtenerInformacionArchivosSincronizar();
        if(dataArchivosSync!=null){
            for(ArchivoSeleccionado as:dataArchivosSync){
                common.asynDataFiles(as.getUUID(),as.getIdAlumno(),as.getTipoArchivo(),as.getIdProyectoSeleccionado());
            }
            someData = true;
        }*/
        if(!someData){
            common.dialogoGeneral("ITSA","La información ya se encuentra sincronizada").show();
        }
    }


    private void initComponent() {

        // CARTA DE PRESENTACION
        bt_toggle_carta_presentacion        = (ImageButton) findViewById(R.id.bt_toggle_text_carta_presentacion);
        bt_hide_carta_presentacion          = (Button) findViewById(R.id.bt_hide_text_carta_presentacion);
        lyt_expand_carta_presentacion       = (View) findViewById(R.id.lyt_expand_text_carta_presentacion);
        lyt_expand_carta_presentacion.setVisibility(View.GONE);

        setOnClickIB(bt_toggle_carta_presentacion,lyt_expand_carta_presentacion);
        setOnClickB(bt_toggle_carta_presentacion,bt_hide_carta_presentacion,lyt_expand_carta_presentacion);

        bt_toggle_carta_aceptacion        = (ImageButton) findViewById(R.id.bt_toggle_text_carta_aceptacion);
        bt_hide_carta_aceptacion         = (Button) findViewById(R.id.bt_hide_text_carta_aceptacion);
        lyt_expand_carta_aceptacion       = (View) findViewById(R.id.lyt_expand_text_carta_aceptacion);
        lyt_expand_carta_aceptacion.setVisibility(View.GONE);


        setOnClickIB(bt_toggle_carta_aceptacion,lyt_expand_carta_aceptacion);
        setOnClickB(bt_toggle_carta_aceptacion,bt_hide_carta_aceptacion,lyt_expand_carta_aceptacion);

        bt_toggle_carta_reporte_1        = (ImageButton) findViewById(R.id.bt_toggle_text_carta_reporte_1);
        bt_hide_carta_reporte_1         = (Button) findViewById(R.id.bt_hide_text_carta_reporte_1);
        lyt_expand_reporte_1       = (View) findViewById(R.id.lyt_expand_text_carta_reporte_1);
        lyt_expand_reporte_1.setVisibility(View.GONE);


        setOnClickIB(bt_toggle_carta_reporte_1,lyt_expand_reporte_1);
        setOnClickB(bt_toggle_carta_reporte_1,bt_hide_carta_reporte_1,lyt_expand_reporte_1);

        bt_toggle_carta_reporte_2        = (ImageButton) findViewById(R.id.bt_toggle_text_carta_reporte_2);
        bt_hide_carta_reporte_2        = (Button) findViewById(R.id.bt_hide_text_carta_reporte_2);
        lyt_expand_reporte_2       = (View) findViewById(R.id.lyt_expand_text_carta_reporte_2);
        lyt_expand_reporte_2.setVisibility(View.GONE);


        setOnClickIB(bt_toggle_carta_reporte_2,lyt_expand_reporte_2);
        setOnClickB(bt_toggle_carta_reporte_2,bt_hide_carta_reporte_2,lyt_expand_reporte_2);

        bt_toggle_carta_reporte_3        = (ImageButton) findViewById(R.id.bt_toggle_text_carta_reporte_3);
        bt_hide_carta_reporte_3        = (Button) findViewById(R.id.bt_hide_text_carta_reporte_3);
        lyt_expand_reporte_3       = (View) findViewById(R.id.lyt_expand_text_carta_reporte_3);
        lyt_expand_reporte_3.setVisibility(View.GONE);


        setOnClickIB(bt_toggle_carta_reporte_3,lyt_expand_reporte_3);
        setOnClickB(bt_toggle_carta_reporte_3,bt_hide_carta_reporte_3,lyt_expand_reporte_3);

        // input section
        bt_toggle_input = (ImageButton) findViewById(R.id.bt_toggle_input);
        bt_hide_input = (Button) findViewById(R.id.bt_hide_input);
        bt_save_input = (Button) findViewById(R.id.bt_save_input);
        lyt_expand_input = (View) findViewById(R.id.lyt_expand_input);
        lyt_expand_input.setVisibility(View.GONE);

        bt_toggle_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionInput(bt_toggle_input,lyt_expand_input);
            }
        });

        bt_hide_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionInput(bt_toggle_input,lyt_expand_input);
            }
        });

        bt_save_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Data saved", Snackbar.LENGTH_SHORT).show();
                toggleSectionInput(bt_toggle_input,lyt_expand_input);
            }
        });

        // nested scrollview
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);
    }

    private void setOnClickB(final ImageButton btnCount,final Button btn,final View lyt){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionText(btnCount,lyt);
            }
        });
    }
    private void setOnClickIB(final ImageButton btn,final View lyt){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSectionText(btn,lyt);
            }
        });
    }

    private void toggleSectionText(View view,final View lyt) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    private void toggleSectionInput(View view,final View lytExpand) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lytExpand, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lytExpand);
                }
            });
        } else {
            ViewAnimation.collapse(lyt_expand_input);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }


}
