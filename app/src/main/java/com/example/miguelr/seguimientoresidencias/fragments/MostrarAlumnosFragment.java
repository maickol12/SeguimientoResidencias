package com.example.miguelr.seguimientoresidencias.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.miguelr.seguimientoresidencias.Alumnos.mostrarAvanceAlumno;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.Alumnos;
import com.example.miguelr.seguimientoresidencias.DataBase.Tables.usuarios;
import com.example.miguelr.seguimientoresidencias.PerfilActivity;
import com.example.miguelr.seguimientoresidencias.R;
import com.example.miguelr.seguimientoresidencias.adaptadores.rvMostrarAlumnosAdapter;
import com.example.miguelr.seguimientoresidencias.eventos.ClickListener;
import com.example.miguelr.seguimientoresidencias.eventos.RecyclerTouchListener;

import java.util.ArrayList;

/**
 * Created by miguelr on 25/03/2018.
 */

public class MostrarAlumnosFragment extends Fragment {
    private RecyclerView rvMostrarAlumnos;
    private ArrayList<Alumnos> ALalumnos;
    private Alumnos alumnos;
    private int idCarrera = 0;
    private rvMostrarAlumnosAdapter adapter;
    private usuarios users;


    public static MostrarAlumnosFragment newInstance(int idCarrea){
        MostrarAlumnosFragment fragment = new MostrarAlumnosFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("idCarrera",idCarrea);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle b){
        super.onCreate(b);

        //LO UTILIZAMOS PARA QUE NOS PONGA EN MENU EN EL FRAGMENT Y ASI PUEDAMOS TOMAR
        //EL SEARCH VIEW
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("maickol","---"+query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = "%"+newText+"%";
                ALalumnos.clear();
                ArrayList<Alumnos> nuevos = alumnos.obtenerTodosLosAlumnosPorCarreraYLike(idCarrera,newText);
                ALalumnos.addAll(nuevos);
                adapter.notifyDataSetChanged();

                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup group,Bundle saveInstances){
        final Context context = getActivity();
        View view = inflater.inflate(R.layout.fragment_mostrar_alumnos,null);
        Bundle bundle = getArguments();

        if(bundle!=null){
            idCarrera = bundle.getInt("idCarrera");
        }



        rvMostrarAlumnos = view.findViewById(R.id.rvMostrarAlumnos);
        rvMostrarAlumnos.setHasFixedSize(true);

        alumnos = new Alumnos(context);
        users = new usuarios(context);
        ALalumnos = alumnos.obtenerTodosLosAlumnosPorCarrera(idCarrera);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMostrarAlumnos.setLayoutManager(llm);

        adapter = new rvMostrarAlumnosAdapter(context,ALalumnos);

        rvMostrarAlumnos.setAdapter(adapter);

        rvMostrarAlumnos.addOnItemTouchListener(new RecyclerTouchListener(context, rvMostrarAlumnos, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                int idAlumno = Integer.parseInt(ALalumnos.get(position).getiIdAlumno());
                lanzarActividad(mostrarAvanceAlumno.class,idAlumno,0,0);
            }

            @Override
            public void onLongClick(View view, int position) {
                try{
                    usuarios u = users.obtenerRootSession();
                    if(u!=null){
                        if(Integer.parseInt(u.getRoot())==1){
                            int idAlumno = Integer.parseInt(ALalumnos.get(position).getiIdAlumno());
                            lanzarActividad(PerfilActivity.class,idAlumno,Integer.parseInt(u.getIdUsuario()),Integer.parseInt(u.getRoot()));
                            ((Activity)context).finish();
                        }
                    }
                }catch (Exception e){
                    Log.d("error",e.getMessage());
                }
            }
        }));

        return view;
    }


    public void lanzarActividad(Class<?> clase,int idAlumno,int idUsuario,int root){
        Intent intent = new Intent(getActivity(),clase);
        intent.putExtra("idAlumno",idAlumno);
        intent.putExtra("idUsuario",idUsuario);
        intent.putExtra("root",root);
        startActivity(intent);
    }


}
