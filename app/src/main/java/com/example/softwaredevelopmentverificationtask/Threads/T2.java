package com.example.softwaredevelopmentverificationtask.Threads;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class T2 {
   private String name;
   private int b_frequencyRefresh;
   private Activity activity;
   private Timer timer;

    public T2(String name , Activity activity, int b_frequencyRefresh) {
        this.name=name;
        this.activity = activity;
        this.b_frequencyRefresh = b_frequencyRefresh;
    }
    private void init(){
        timer = new Timer();
    }

    public void start() {
        init();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                    Intent batteryStatus = activity.registerReceiver(null, ifilter);
                    batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                    T3.set_B_toList(String.valueOf(batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,0))+ "%");
                    Log.i("Thread2",String.valueOf(batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,0)));
                }
            },0,b_frequencyRefresh);

    }
    public void doStop(){
        timer.cancel();
        Log.i("Thread2",name+" <--- stoped");
    }


}
