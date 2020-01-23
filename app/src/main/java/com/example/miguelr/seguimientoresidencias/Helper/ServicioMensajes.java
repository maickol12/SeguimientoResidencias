package com.example.miguelr.seguimientoresidencias.Helper;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ServicioMensajes extends Service {


    private final static String TAG = ServicioMensajes.class.getSimpleName();
    private final static int TIME = 1000;




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new TimerTask() {
                    @Override
                    public void run() {
                        Log.d("servicio","sdjksjkdskjds");
                    }
                });
            }
        };

        timer.schedule(task, 0,TIME);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}