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

public class T1 extends Thread{
    private volatile boolean doStop = false;
    private volatile Activity activity;
    private static volatile Location mLocation = new Location(LocationManager.GPS_PROVIDER);
    private volatile int frequencyA;


     //main constructor of the class which get parameters
     //  1 is a name of current thread,    2 is a context,        3 is a duration in milis whish set frequency of cheking the geolocation
    public T1(String name, Activity activity, int frequencyA) {
        super(name);
        this.activity = activity;
        this.frequencyA = frequencyA;
    }


    // the main method of the thread "T1" which contained main logic of the thread
    @Override
    public void run() {
        SupportLocation supportLocation= new SupportLocation();
        while (!doStop) {
           mLocation= supportLocation.getMyCurrentLocation(activity);
            if (mLocation.getLongitude()!=0.0) {
                T3.set_A_toList(String.valueOf(mLocation.getLatitude())+" "+String.valueOf(mLocation.getLongitude()));
            }
            Log.i("MyThread1", currentThread().getName()+"  ------> " +String.valueOf(mLocation.getLatitude()) + String.valueOf(mLocation.getLongitude()));
            try {
                Thread.sleep(frequencyA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //method which stops the current thread
    public void doStop() {
        this.doStop = true;
        Log.i("MyThread1",currentThread().getName()+" <--- stoped");
    }



}
