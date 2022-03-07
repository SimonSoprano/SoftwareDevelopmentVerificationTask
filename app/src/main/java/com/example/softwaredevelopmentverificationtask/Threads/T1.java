package com.example.softwaredevelopmentverificationtask.Threads;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextParams;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.softwaredevelopmentverificationtask.MainActivity;
import com.example.softwaredevelopmentverificationtask.Support.SupportLocation;

import java.util.Timer;
import java.util.TimerTask;

public class T1 {
    private String name;
    private volatile Activity activity;
    private static volatile Location mLocation ;
    private volatile int frequencyA;
    private Timer timer;


     //main constructor of the class which get parameters
     //  1 is a name of current thread,    2 is a context,        3 is a duration in milis whish set frequency of cheking the geolocation
    public T1(String name, Activity activity, int frequencyA) {
        this.name=name;
        this.activity = activity;
        this.frequencyA = frequencyA;
    }
    private void init(){
        mLocation = new Location(LocationManager.GPS_PROVIDER);
        timer =new Timer();
    }


    // the main method of the thread "T1" which contained main logic of the thread
    public void start() {
        init();
      //  mLocation = new Location(LocationManager.GPS_PROVIDER);
        timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SupportLocation supportLocation= new SupportLocation();
                    mLocation= supportLocation.getMyCurrentLocation(activity);
                    if (mLocation.getLongitude()!=0.0) {
                        T3.set_A_toList(String.valueOf(mLocation.getLatitude())+" "+String.valueOf(mLocation.getLongitude()));
                    }
                    Log.i("Thread1", name +"  ------> " +String.valueOf(mLocation.getLatitude()) + String.valueOf(mLocation.getLongitude()));
                }
                },0,frequencyA);
    }

    //method which stops the current thread
    public void doStop() {
        timer.cancel();
        Log.i("Thread1",name+" <--- stoped");
    }




}
