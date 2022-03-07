package com.example.softwaredevelopmentverificationtask.Threads;

import android.util.Log;

import com.example.softwaredevelopmentverificationtask.Utils.TelnetClient;

import java.util.HashMap;

public class T3 extends Thread{
    private int C_numOfActivation;
    public static volatile String[] list ;
    private boolean doStop;
    private volatile String URL;

    public T3(String name , int c_numOfActivation, String URL) {
        super(name);
        this.C_numOfActivation = c_numOfActivation;
        list= new String[c_numOfActivation];
        this.URL=URL;
    }
    public static void set_A_toList(String A_location){
        list[0]=A_location;
    }
    public static void set_B_toList(String B_batteryLevel){
        list[1]=B_batteryLevel;
    }

    @Override
    public void run() {

        while(!doStop){

            boolean t3_activate = true;
            Log.i("Thread3",this.list[0]+"  :  "+this.list[1]);

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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("Thread3",currentThread().getName()+" <--- stoped " );
    }
    public void doStop(){
        this.doStop=true;
        Log.i("Thread3",currentThread().getName()+" <--- stoped " );
    }
}
