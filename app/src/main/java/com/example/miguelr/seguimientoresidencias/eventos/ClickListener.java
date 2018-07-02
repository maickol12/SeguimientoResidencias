package com.example.miguelr.seguimientoresidencias.eventos;

import android.view.View;

/**
 * Created by miguelr on 25/03/2018.
 */


public interface ClickListener{
    public void onClick(View view, int position);
    public void onLongClick(View view,int position);
}