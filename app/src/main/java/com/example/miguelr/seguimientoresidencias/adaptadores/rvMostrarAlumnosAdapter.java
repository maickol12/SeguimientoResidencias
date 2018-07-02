package com.example.miguelr.seguimientoresidencias.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Alumnos;
import com.example.miguelr.seguimientoresidencias.R;

import java.util.ArrayList;

/**
 * Created by miguelr on 24/03/2018.
 */

public class rvMostrarAlumnosAdapter extends RecyclerView.Adapter<rvMostrarAlumnosAdapter.mostrarAlumnosHolder> {
    private Context context;
    private ArrayList<Alumnos> alumnos;
    public rvMostrarAlumnosAdapter(Context context,ArrayList<Alumnos> alumnos){
        this.context = context;
        this.alumnos = alumnos;
    }

    public static class mostrarAlumnosHolder extends RecyclerView.ViewHolder{
        private TextView nombre,carrera,matricula;
        private ImageView sexo;
        public mostrarAlumnosHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.TVNombreAlumno);
            carrera = (TextView) itemView.findViewById(R.id.TVCarreraAlumno);
            sexo = (ImageView) itemView.findViewById(R.id.sexoAlumno);
            matricula = (TextView) itemView.findViewById(R.id.TVMatricula);
        }
    }

    @Override
    public mostrarAlumnosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carcasa_rv_alumnos,parent,false);
        mostrarAlumnosHolder holder = new mostrarAlumnosHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(mostrarAlumnosHolder holder, int position) {
        Alumnos alumno = alumnos.get(position);
        holder.nombre.setText(alumno.getvNombreAlumno()+" "+alumno.getvApellidoPaterno()+" "+alumno.getvApellidoMaterno());
        holder.carrera.setText(alumno.getCarrera().getvNombreCarrera());
        holder.matricula.setText(alumno.getvMatricula());
        if(Integer.parseInt(alumno.getbSexo()) == 1){
            holder.sexo.setImageResource(R.drawable.men);
        }else{
            holder.sexo.setImageResource(R.drawable.woman);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }
}

