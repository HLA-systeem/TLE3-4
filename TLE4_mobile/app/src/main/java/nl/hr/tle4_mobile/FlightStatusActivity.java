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
import android.widget.TextView;

public class FlightStatusActivity extends AppCompatActivity{

    String API_KEY = "e7aa5d807f1406029fe3b79dd35e65ef";
    String API_ID = "51e64f75";
    String flightName = "KL0808";
    String schipholData;

    TextView timer;
    CountDownTimer countDown;
    TextView timer2;
    CountDownTimer countDown2;
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
        Button actButt = (Button)findViewById(R.id.actButt);

        Intent i = getIntent();
        flightName = i.getStringExtra("flight number");

        SchipholApi as = new SchipholApi(this);
        as.execute("https://api.schiphol.nl/public-flights/flights?app_id=51e64f75&app_key=e7aa5d807f1406029fe3b79dd35e65ef&flightname=" + this.flightName + "&includedelays=false&page=0&sort=%2Bscheduletime");

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
        this.timer= (TextView)findViewById(R.id.yourTime);
        this.countDown = new CountDownTimer(60*500,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                FlightStatusActivity.this.timer.setText("Estimated luggage arrival: \r\n" + (millisUntilFinished  / 1000) + " Seconds");
            }

            @Override
            public void onFinish() {
                FlightStatusActivity.this.timer.setText("Your luggage will arrive now.");
                FlightStatusActivity.this.showNotification();
            }
        };


        this.timer2= (TextView)findViewById(R.id.othersTime);
        this.countDown2 = new CountDownTimer(60*2000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                FlightStatusActivity.this.timer2.setText("Others estimated luggage waiting time: \r\n" + (millisUntilFinished  / 1000) + " Seconds");
            }

            @Override
            public void onFinish() {
                FlightStatusActivity.this.timer2.setText("The other person's luggage will arrive now.");
            }
        };

        this.countDown.start();
        this.countDown2.start();
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

}
