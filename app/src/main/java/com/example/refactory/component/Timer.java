package com.example.refactory.component;

import android.os.Handler;
import android.os.Message;

import com.example.refactory.main.ComponentView;

import java.util.ArrayList;

public class Timer extends Handler {

    ArrayList<TickListener> tickListenersList = new ArrayList<>();

    public Timer(){
        sendMessageDelayed(obtainMessage(), 1000);
    }

    /**
     * Handle the timer
     * @param m
     */
    @Override
    public void handleMessage(Message m){


        for (TickListener subscriber : tickListenersList) {
            subscriber.tick();
        }

        notifyObserver();
        sendMessageDelayed(obtainMessage(), 33);
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
