package com.example.softwaredevelopmentverificationtask.Support;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationListenerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class SupportLocation {
    private static volatile LocationManager locationManager;
    private static volatile LocationListener locationListener;
    private static volatile Location mLocation = new Location(LocationManager.GPS_PROVIDER);




    public  Location getMyCurrentLocation(Activity activity){
        mLocation=getlocation(activity);
        if(mLocation.getLongitude()==0.0){
            if (Build.VERSION.SDK_INT>=23
                    && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                    &&ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                if(ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION )== PackageManager.PERMISSION_GRANTED
                        &&ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION )== PackageManager.PERMISSION_GRANTED){
                    FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            mLocation=location;
                        }
                    });
                }

            }else {
                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        mLocation=location;
                    }
                });
            }
        }

        return mLocation;
    }





//    @SuppressLint("MissingPermission")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==100&&grantResults[0]==RESULT_OK){
//
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
//                @Override
//                public void onLocationChanged(@NonNull Location location) {
//                    mLocation=location;
//                }
//            });
//        }
//
//    }


    protected Location getlocation(Activity activity) {

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Handler threadHandler = new Handler(Looper.getMainLooper());
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT>=23
                        && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},100);

                }else {
                    locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            mLocation=location;
                        }
                    });
                }
            }
        });

        return mLocation;

    }

}
