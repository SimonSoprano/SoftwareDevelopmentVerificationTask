package com.example.softwaredevelopmentverificationtask.Threads;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

public class T2 extends Thread{
   private boolean doStop;
   private int b_frequencyRefresh;
   private Activity activity;

    public T2(String name , Activity activity, int b_frequencyRefresh) {
        super(name);
        this.activity = activity;
        this.b_frequencyRefresh = b_frequencyRefresh;
    }

    @Override
    public void run() {


        while (!doStop){
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = activity.registerReceiver(null, ifilter);
            batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            T3.set_B_toList(String.valueOf(batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,0))+ "%");
            Log.i("Thread2",String.valueOf(batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,0)));

            try {
                Thread.sleep(b_frequencyRefresh);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void doStop(){
        this.doStop=true;
    }
}
