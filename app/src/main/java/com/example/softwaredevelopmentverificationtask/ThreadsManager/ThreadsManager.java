package com.example.softwaredevelopmentverificationtask.ThreadsManager;

import android.app.Activity;
import android.content.Context;

import com.example.softwaredevelopmentverificationtask.Threads.T1;
import com.example.softwaredevelopmentverificationtask.Threads.T2;
import com.example.softwaredevelopmentverificationtask.Threads.T3;

public class ThreadsManager {
    private static T1 t1;
    private static T2 t2;
    private static T3 t3;
    private static boolean threadsAreWorks;
    public static void activateGPSandBatteryServerRepeater(int A_frequencyT1, int B_frequencyT2, int C_indexToActivate, String URL, Activity activity){
         t3 = new T3("T3",C_indexToActivate,URL);
         t3.start();
         t1 = new T1("T1",activity,A_frequencyT1);
         t1.start();
         t2 = new T2("T2",activity,B_frequencyT2);
         t2.start();
    }
    //stop all secondary threads
    public static void stopGPSandBatteryServerRepeater(){
        if (t1!=null) {
            t1.doStop();
            t1=null;
        }
        if (t2!=null) {
            t2.doStop();
            t2=null;
        }
        if (t3!=null) {
            t3.doStop();
            t3=null;
        }

    }
    //chek the threads, are active or not
    public static boolean areTreadsWorking(){
        if(t3!=null && t2!=null && t1!=null){
            threadsAreWorks = true;
        }
        return threadsAreWorks;
    }

}
