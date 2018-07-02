package com.example.miguelr.seguimientoresidencias.adaptadores;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Carreras;
import com.example.miguelr.seguimientoresidencias.fragments.MostrarAlumnosFragment;

import java.util.List;

/**
 * Created by miguelr on 25/03/2018.
 */

public class PGAdapter extends FragmentPagerAdapter {
    private int NUM_PAGES;
    private  String[] mensajes;
    private Carreras carreras;
    private List<Carreras> LCarreras;
    private Context context;
    public PGAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
        carreras = new Carreras(this.context);
        LCarreras = carreras.obtenerTodasLasCarrera();
        this.NUM_PAGES = LCarreras.size();
        mensajes = new String[this.NUM_PAGES];
        llenarCarreras();
    }
    private void llenarCarreras(){
        for(int i = 0;i<LCarreras.size();i++){
            Carreras carreras = LCarreras.get(i);
            mensajes[i] = carreras.getvNombreCarrera();
        }
    }

    @Override
    public Fragment getItem(int position) {
       return MostrarAlumnosFragment.newInstance((position+1));
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mensajes[position];
    }
}
