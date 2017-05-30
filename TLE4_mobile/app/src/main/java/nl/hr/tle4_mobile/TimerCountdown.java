package nl.hr.tle4_mobile;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class TimerCountdown implements Runnable {
    private TextView timerView;
    private double time;

    private Handler handler;
    public static boolean running = false;

    public TimerCountdown(TextView timerView, double time) {
        this.timerView = timerView;
        this.time = time;
        this.handler = new Handler(Looper.myLooper());
    }

    @Override
    public void run() {
        TimerCountdown.running = true;

        if (this.time < 0) {
            this.timerView.setText(Double.toString(this.time));
            this.time -= 00.1;
            handler.postDelayed(this, 1);
        } else {
            TimerCountdown.running = false;
        }
    }
}