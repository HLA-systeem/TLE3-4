package nl.hr.tle4_mobile;

import android.os.Handler;
import android.os.Looper;

public class Countdown implements Runnable{
    private int timeSec = 0;
    private boolean running = false;
    private Handler handler;

    Countdown(int sec){
        this.timeSec = sec;
        this.handler = new Handler(Looper.myLooper());
    }

    @Override
    public void run(){
        this.running = true;

        if (this.timeSec > 0){
            this.timeSec -= 1;
            handler.postDelayed(this, 1000);
        }
        else{
            this.running = false;
        }

    }
}

