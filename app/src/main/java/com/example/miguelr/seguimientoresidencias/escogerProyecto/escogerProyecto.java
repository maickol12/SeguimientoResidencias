package com.example.miguelr.seguimientoresidencias.escogerProyecto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.example.miguelr.seguimientoresidencias.R;

/**
 * Created by miguelr on 23/07/2018.
 */

public class escogerProyecto extends AppCompatActivity{
    private Toolbar toolbar;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.escoger_proyecto);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneral);
        configurarToolbar();
    }
    public void configurarToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                onBackPressed();
                break;
        }
        return true;
    }
}
