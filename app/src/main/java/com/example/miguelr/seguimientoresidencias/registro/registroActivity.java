package com.example.miguelr.seguimientoresidencias.registro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.miguelr.seguimientoresidencias.R;

/**
 * Created by miguelr on 16/07/2018.
 */

public class registroActivity extends AppCompatActivity{
    private Toolbar toolbar;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.layout_registro);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneral);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    public boolean onCreateOptionsMenu(Menu menu){

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
