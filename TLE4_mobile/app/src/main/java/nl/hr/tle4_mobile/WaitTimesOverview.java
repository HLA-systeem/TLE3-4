package nl.hr.tle4_mobile;

import android.*;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class WaitTimesOverview extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Handler handler = new Handler();

    private RelativeLayout layoutWaiters;
    private ArrayList<Long> userWaitTimes;
    private Long totalWaitTime;
    private CountDownTimer countDownPartial;
    private CountDownTimer countDownFinal;

    private TextView flightStats;

    private NotificationCompat.Builder noti;
    private int notiID;
    private Uri notiSound;

    private GoogleApiClient googleApi;
    private Location aLocLast;
    private LocationRequest aLocReq;
    private String userLat;
    private String userLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wait_times_overview);

        if (this.googleApi == null) { //maak het GoogleAPIclient object als deze er niet is.
            this.googleApi = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        this.createLocationRequest();


        this.totalWaitTime = 0L;
        this.userWaitTimes = new ArrayList();

        if(Constants.luggageID1 != null) {

        }

        if(Constants.luggageID2 != null){

        }

        this.noti = new NotificationCompat.Builder(this);
        this.notiID = 999;
        this.noti.setAutoCancel(true);
        this.notiSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //new LugLocRequest();
        Constants.luggageTag = "DOC2F973";
        Constants.luggageArrival = "whatever";


        this.checkLugs();
    }

    private void checkLugs(){
        //roepLugLocReqAan
        this.getTimesByTag();
        this.handler.postDelayed(new Runnable() {
            public void run() {
                WaitTimesOverview.this.checkLugs();
            }
        }, 5000);
    }

    private void getTimesByTag(){
        if(!Constants.luggageID2.equals("")){
            if (Constants.luggageTag.equals(Constants.luggageID1)){
                Long waitTime1 = Constants.getluggageIDs().get(Constants.luggageID1);
                this.userWaitTimes.add(waitTime1);
                this.startTimers();
            }
            if( Constants.luggageTag.equals(Constants.luggageID2)){
                Long waitTime2 = Constants.getluggageIDs().get(Constants.luggageID2);
                this.userWaitTimes.add(waitTime2);
                this.startTimers();
            }
        }
        else{
            if (!Constants.luggageID1.equals("")){
                if (Constants.luggageTag.equals(Constants.luggageID1)){
                    Long waitTime1 = Constants.getluggageIDs().get(Constants.luggageID1);
                    this.userWaitTimes.add(waitTime1);
                    this.startTimers();
                }
            }
        }
    }


    public void showNotification(){
        this.noti.setSmallIcon(R.mipmap.ic_launcher);
        this.noti.setTicker("So this is what they call a ticker.");
        this.noti.setWhen(System.currentTimeMillis());
        this.noti.setContentTitle("Your luggage is arriving!");
        this.noti.setContentText("Press this to see the timer.");
        this.noti.setSound(this.notiSound);

        Intent in = new Intent(this, WaitTimesOverview.class);
        PendingIntent inPen = PendingIntent.getActivity(this, 0 ,in, PendingIntent.FLAG_UPDATE_CURRENT);
        this.noti.setContentIntent(inPen);



        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(this.notiID, this.noti.build() );

    }


    private void startTimers(){
        this.layoutWaiters = (RelativeLayout)findViewById(R.id.layout_waiters);

        this.startUserTimer();
        this.countDownPartial.start();
        this.countDownFinal.start();

        this.showOtherTimers();
    }


    private void startUserTimer(){

        final TextView timeTillNext = (TextView)findViewById(R.id.text_tillNext);
        final TextView timeTillTotal = (TextView)findViewById(R.id.text_totalWaitTime);

        int currentLug = 0;
        if(currentLug < this.userWaitTimes.size()){
            this.countDownPartial = new CountDownTimer(this.userWaitTimes.get(currentLug),1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    timeTillNext.setText("Your next luggage will arrive in: \n" + (millisUntilFinished / 1000) + " Seconds");
                    //send time and long + lat;
                }

                @Override
                public void onFinish() {
                    WaitTimesOverview.this.showNotification();
                    countDownPartial.start();
                }
            };
            currentLug+=1;
        }

        for(Long time : this.userWaitTimes){
            this.totalWaitTime += time;
        }

        this.countDownFinal = new CountDownTimer(this.totalWaitTime,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timeTillTotal.setText("For all your luggage,\n you will have to wait: " + (millisUntilFinished / 1000) + " Seconds");
            }

            @Override
            public void onFinish() {
                timeTillTotal.setText("All your luggage has arrived, thank you for your patience.");
                WaitTimesOverview.this.layoutWaiters.removeView(timeTillNext);
                //send request to remove user.
            }
        };
    }

    private void showOtherTimers(){
        //getLocation of nearby users + there partial wait time;
        //show it on the relative layout.
        //add/substract dp's on the x/y postion based on the long&lat differences.
    }

    private void createLocationRequest() {
        this.aLocReq = new LocationRequest();
        this.aLocReq.setInterval(5500);
        this.aLocReq.setFastestInterval(5000);
        this.aLocReq.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.aLocLast = LocationServices.FusedLocationApi.getLastLocation(this.googleApi);

        if (this.aLocLast != null) {
            System.out.println("Locatie is: " + aLocLast.toString());
            this.userLat = String.valueOf(aLocLast.getLatitude());
            this.userLong = String.valueOf(aLocLast.getLongitude());
        } else {
            startLocationUpdate();
        }
    }

    public void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permission problem in startLocationUpdate");
            return;
        }
        System.out.println("Now requesting location..");
        LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApi, this.aLocReq, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("Connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        this.aLocLast = location;
        System.out.println("Locatie is: " + aLocLast.toString());
        this.userLat = String.valueOf(aLocLast.getLatitude());
        this.userLong = String.valueOf(aLocLast.getLongitude());
        //retrieve new data.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                this.startLocationUpdate();
            } else {
                this.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}
