package com.example.miguelr.seguimientoresidencias.MenuArchivos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


import com.example.miguelr.seguimientoresidencias.R;


/**
 * Created by miguelr on 07/08/2018.
 */

public class menuArchivos extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageReporte3,imageReporte2,imageReporte1,imageCartaAceptacion,imageCartaPresentacion;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.menu_archivos);

        toolbar     = (Toolbar) findViewById(R.id.toolbarGeneral);

        imageReporte1           = (ImageView) findViewById(R.id.imageReporte1);
        imageReporte2           = (ImageView) findViewById(R.id.imageReporte2);
        imageReporte3           = (ImageView) findViewById(R.id.imgreporte3);
        imageCartaAceptacion    = (ImageView) findViewById(R.id.imageCartaAceptacion);
        imageCartaPresentacion     = (ImageView) findViewById(R.id.imageCartaPresentacion);




        // Later.. stop the animation
       // splash.setAnimation(null);
        configurations();
    }
    public void configurations(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);

        imageReporte1.startAnimation(anim);
        imageReporte2.startAnimation(anim);
        imageReporte3.startAnimation(anim);
        imageCartaAceptacion.startAnimation(anim);
        imageCartaPresentacion.startAnimation(anim);


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
}
