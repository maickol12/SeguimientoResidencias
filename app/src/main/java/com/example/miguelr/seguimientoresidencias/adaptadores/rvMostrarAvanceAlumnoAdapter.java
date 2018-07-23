package com.example.miguelr.seguimientoresidencias.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Modelos.cascaronAvance;
import com.example.miguelr.seguimientoresidencias.R;

import java.util.ArrayList;

/**
 * Created by miguelr on 31/03/2018.
 */

public class rvMostrarAvanceAlumnoAdapter extends RecyclerView.Adapter<rvMostrarAvanceAlumnoAdapter.rvMostrarAvanceAlumnoHolder> {

    private ArrayList<cascaronAvance> avances;
    private Context context;
    private int lastPosition = -1;


    public rvMostrarAvanceAlumnoAdapter(Context context,ArrayList<cascaronAvance> avances){
        this.context = context;
        this.avances = avances;
    }

    public static class rvMostrarAvanceAlumnoHolder extends RecyclerView.ViewHolder{
        private TextView TVTitulo,TVDescripcion;
        private ImageView IVAprobado;
        public rvMostrarAvanceAlumnoHolder(View itemView) {
            super(itemView);
            TVTitulo = (TextView) itemView.findViewById(R.id.TVTitulo);
            TVDescripcion = (TextView) itemView.findViewById(R.id.TVDescripcion);
            IVAprobado = (ImageView) itemView.findViewById(R.id.IVAprobado);
        }
    }
    @Override
    public rvMostrarAvanceAlumnoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carcasa_rv_avance,parent,false);
        rvMostrarAvanceAlumnoHolder holder = new rvMostrarAvanceAlumnoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(rvMostrarAvanceAlumnoHolder holder, int position) {
        cascaronAvance avance = avances.get(position);

        holder.TVTitulo.setText(avance.getTitulo());
        holder.TVDescripcion.setText(avance.getMensaje());

        if(avance.getAprobado()==1){
            holder.IVAprobado.setImageResource(R.drawable.circulo_verde);
        }else{
            holder.IVAprobado.setImageResource(R.drawable.circulo_rojo);
        }
        setAnimation(holder.itemView,position);

    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
           // Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return avances.size();
    }
}
