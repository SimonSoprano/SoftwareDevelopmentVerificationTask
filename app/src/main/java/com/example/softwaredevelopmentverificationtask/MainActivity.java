package com.example.softwaredevelopmentverificationtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.softwaredevelopmentverificationtask.ThreadsManager.ThreadsManager;

public class MainActivity extends AppCompatActivity {

    //created by Semen Sapronov

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void start(View view){
        if (!ThreadsManager.areTreadsWorking()) {
            ThreadsManager.activateGPSandBatteryServerRepeater(
                    1000, 1000, 2, "https://search.maven.org/search?q=a:simple-icons", MainActivity.this);
        }else {
            Log.i("Threads","threads are already working");
        }
    }

    public void stop(View vie){
        ThreadsManager.stopGPSandBatteryServerRepeater();
    }
}


   /* Software Development Verification Task

    The purpose of this Task is to examine your problem solving skills, programing skills, as
        well as to reveal your coding style.
        Task Description
        In this Task, you will have to create a simple, demonstrative program for Android (or
        iOS), which:
        • contains a screen with two buttons: "START" and "STOP",
        • when the user presses the START button, 3 threads - say T1, T2 and T3 - should
        be created:
                  o  within T1, the GPS location of the device is collected repeatedly every A
                     seconds, and the results (as a string) are handed over to T3,
                  o  within T2, the percentage usage of the device's battery is collected
                     repeatedly every B seconds, and the results (as a string) are handed over
                      to T3,
                  o  Every time when either the GPS location or the battery usage information
                     is collected in T1 or T2, and the results are handed over and stored by T3
                     in a list. If the number of items in this list exceeds C, T3 is activated. It
                     collects all string into one data unit and send to the server (URL) via
                     HTTP. The list is then emptied.
                  o  A, B, C and URL are parameters.
        • when the users presses STOP, all threads are just stopped / destroyed.
        • if the user pressed START while the threads have been created before, the action
        is just ignored.
        Additional information The application is only for demonstrational purpose. There is
        no need to pay too much attention on testing. Please provide us with the source codes. */