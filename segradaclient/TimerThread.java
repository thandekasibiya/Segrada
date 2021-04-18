package com.example.segradaclient;

import android.os.Handler;

//The thread is for timing the bouncing dice on the canvas.
public class TimerThread extends Thread {


    private long Time;
    private Handler handler;
    private Runnable tick;

    public TimerThread(long Time, Runnable tick, Handler handler )
    {
        this.Time=Time;
        this.tick = tick;
        this.handler = handler;
    }
    @Override
    public void run() {
        while (true)
        {
            try {
                Thread.sleep(Time);
                handler.post(tick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}