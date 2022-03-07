package com.example.softwaredevelopmentverificationtask.Threads;

import android.util.Log;

import com.example.softwaredevelopmentverificationtask.Utils.TelnetClient;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class T3 {
    private String name;
    private int C_numOfActivation;
    public static volatile String[] list ;
    private volatile String URL;
    private Timer timer;

    public T3(String name , int c_numOfActivation, String URL) {
        this.name = name;
        this.C_numOfActivation = c_numOfActivation;
        list = new String[c_numOfActivation];
        this.URL = URL;
    }
    public synchronized static void set_A_toList(String A_location){
        list[0]=A_location;
    }
    public synchronized static void set_B_toList(String B_batteryLevel){
        list[1]=B_batteryLevel;
    }
    private void init(){
        timer = new Timer();
    }

    public void start() {
        init();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    boolean t3_activate = true;
                    Log.i("Thread3",list[0]+"  :  "+list[1]);

                    //here we are cheking the array is complete or not
                    for (String i: list ) {
                        if(i==null){
                            t3_activate=false;
                        }
                    }
                    // if array complete and ready to send t3_activate will be true,
                    // in that way we activate HTTP request and packing data to one data object
                    if(t3_activate){

                        HashMap hashMap = new HashMap();
                        hashMap.put("latitude",list[0].split(" ")[0]);
                        hashMap.put("longitude",list[0].split(" ")[1]);
                        hashMap.put("battery",list[1]);
                        //here we are sending data with HTTP requset and geting return the String like response of our request
                        // the logic of reueqst is contained in TelnetClient class
                        Log.i("Thread3", TelnetClient.executeHTTPrequest_POST(URL,hashMap));
                        //clear data from list
                        for (int i=0; i<list.length; i++) {
                            list[i]=null;
                        }
                        Log.i("Thread3","chek list" + list[0]);
                    }

                }
            },0,3000);





    }
    public synchronized void doStop(){
        timer.cancel();
        Log.i("Thread3",name+" <--- stoped " );
    }
}
