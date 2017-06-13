package nl.hr.tle4_mobile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightStatusActivity extends AppCompatActivity{

    String API_KEY = "e7aa5d807f1406029fe3b79dd35e65ef";
    String API_ID = "51e64f75";
    String flightName = "KL0808";
    String schipholData;

    RelativeLayout layoutWaiters;
    ArrayList<Long> userWaitTimes;
    Long totalWaitTime;
    CountDownTimer countDownPartial;
    CountDownTimer countDownFinal;

    TextView flightStats;

    private NotificationCompat.Builder noti;
    private int notiID;
    private Uri notiSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_flightstatus);

        Intent i = getIntent();
        flightName = i.getStringExtra("flight number");

        SchipholApi as = new SchipholApi(this);
        as.execute("https://api.schiphol.nl/public-flights/flights?app_id=51e64f75&app_key=e7aa5d807f1406029fe3b79dd35e65ef&flightname=" + this.flightName + "&includedelays=false&page=0&sort=%2Bscheduletime");

        this.userWaitTimes = new ArrayList();
        this.userWaitTimes.add(1000L *60L); //deze bagage tijden zullen dynamisch worden toegevoegd. //millisecs (1000ms*60ms = 1 minuut) // L == long datatype
        this.userWaitTimes.add(3000L *60L);
        this.userWaitTimes.add(1500L *60L);

        this.noti = new NotificationCompat.Builder(this);
        this.notiID = 999;
        this.noti.setAutoCancel(true);
        this.notiSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        this.startTimers();
    }

    public void setInfo(String results){
        this.schipholData = results;
        this.flightStats = (TextView)findViewById(R.id.FlightStatus);
        flightStats.setText(this.schipholData);

    }

    private void startTimers(){
        this.layoutWaiters = (RelativeLayout)findViewById(R.id.layout_waiters);

        this.startUserTimer();
        this.countDownPartial.start();
        this.countDownFinal.start();

        this.showOtherTimers();
    }

    public void showNotification(){
        this.noti.setSmallIcon(R.mipmap.ic_launcher);
        this.noti.setTicker("So this is what they call a ticker.");
        this.noti.setWhen(System.currentTimeMillis());
        this.noti.setContentTitle("Your luggage is arriving!");
        this.noti.setContentText("Press this to see the timer.");
        this.noti.setSound(this.notiSound);

        Intent in = new Intent(this, FlightStatusActivity.class);
        PendingIntent inPen = PendingIntent.getActivity(this, 0 ,in, PendingIntent.FLAG_UPDATE_CURRENT);
        this.noti.setContentIntent(inPen);



        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(this.notiID, this.noti.build() );

    }

    public void findAct(View v){
        Intent i = new Intent(this, ActivietiesActivity.class);
        startActivity(i);
    }

    private void startUserTimer(){

        final TextView timeTillNext = (TextView)findViewById(R.id.text_tillNext);
        int currentLug = 0;
        final List uWTL = Arrays.asList(this.userWaitTimes);
        if(currentLug < uWTL.size()){
            currentLug+=1;
            this.countDownPartial = new CountDownTimer(uWTL.indexOf(currentLug),1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    timeTillNext.setText("Your next luggage will arrive in: \n" + (millisUntilFinished / 1000) + " Seconds");
                    //send time and long + lat;
                }

                @Override
                public void onFinish() {
                    timeTillNext.setText("Some of your luggage arrives now.");
                    FlightStatusActivity.this.showNotification();
                }
            };
        }

        for(Long time : this.userWaitTimes){
            this.totalWaitTime += time;
        }

        this.countDownFinal = new CountDownTimer( this.totalWaitTime,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timeTillNext.setText("For all your luggage, you will have to wait: " + (millisUntilFinished / 1000) + " Seconds");
            }

            @Override
            public void onFinish() {
                timeTillNext.setText("All your luggage has arrived, thank you for your patience.");
                FlightStatusActivity.this.layoutWaiters.removeView(timeTillNext);
                //send request to remove user.
            }
        };
    }

    private void showOtherTimers(){
        //getLocation of nearby users + there partial wait time;
        //show it on the relative layout.
        //add/substract dp's on the x/y postion based on the long&lat differences.
    }

}
