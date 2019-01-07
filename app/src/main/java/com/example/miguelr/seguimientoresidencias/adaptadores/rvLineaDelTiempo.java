package com.example.miguelr.seguimientoresidencias.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.cascaronLineaTiempo;
import com.example.miguelr.seguimientoresidencias.R;

import java.util.ArrayList;

public class rvLineaDelTiempo extends RecyclerView.Adapter<rvLineaDelTiempo.lineaHolder>{
    private ArrayList<cascaronLineaTiempo> lineaDelTiempo;
    private Context context;
    public rvLineaDelTiempo(Context context,ArrayList<cascaronLineaTiempo> lineaDelTiempo){
        this.context            = context;
        this.lineaDelTiempo     = lineaDelTiempo;
    }
    public static class lineaHolder extends RecyclerView.ViewHolder{
        private TextView rowTitulo,rowDescripcion;
        public lineaHolder(View itemView) {
            super(itemView);
            rowTitulo           =  itemView.findViewById(R.id.rowTitulo);
            rowDescripcion      =  itemView.findViewById(R.id.rowDescripcion);

        }
    }
    @NonNull
    @Override
    public lineaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row,parent,false);
        lineaHolder rvLineaHolder = new lineaHolder(view);

        return rvLineaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull lineaHolder holder, int position) {
        cascaronLineaTiempo row = lineaDelTiempo.get(position);
        holder.rowTitulo.setText(row.getvTitulo());
        holder.rowDescripcion.setText(row.getvDescripcion());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        int size = 0;
        try{
            size = lineaDelTiempo.size();
        }catch (Exception e){
            size = 0;
        }
        return size;
    }
}
