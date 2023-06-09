package com.example.refactory.component;

import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;

public class Timer extends Handler {

    private static Timer timer;
    private ArrayList<TickListener> tickListenersList;

    private Timer(){
        tickListenersList = new ArrayList<>();
        sendMessageDelayed(obtainMessage(), 1000);
    }

    /**
     * Handle the timer
     * @param m
     */
    @Override
    public void handleMessage(Message m){

        notifyObserver();
        sendMessageDelayed(obtainMessage(), 33);
    }


    /**
     * Singleton method
     * @return
     */
    public static Timer getTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        return timer;
    }

    /**
     * Add object to list
     * @param t
     */
    public void register(TickListener t) {
        tickListenersList.add(t);
    }

    /**
     * Remove object from list
     * @param t
     */
    public void deregister(TickListener t) {
        tickListenersList.remove(t);
    }

    /**
     * Notify object
     */
    public void notifyObserver() {
        for (TickListener t : tickListenersList) {
            t.tick();
        }
    }
}
