package nl.hr.tle4_mobile;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class FlightStatusActivity extends AppCompatActivity{

    String API_KEY = "e7aa5d807f1406029fe3b79dd35e65ef";
    String API_ID = "51e64f75";
    String flightName = "KL0808";

    TextView timer;
    String schipholData;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_flight_status);

        this.timer= (TextView)findViewById(R.id.text_timer);
        SchipholApi as = new SchipholApi(this);
        as.execute("https://api.schiphol.nl/public-flights/flights?app_id=51e64f75&app_key=e7aa5d807f1406029fe3b79dd35e65ef&flightname=" + this.flightName + "&includedelays=false&page=0&sort=%2Bscheduletime");

        this.countDownTimer = new CountDownTimer(60*4000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                
            }

            @Override
            public void onFinish() {

            }
        };
        new TimerCountdown(this.timer,10.00);
    }

    public void setInfo(String results){
        this.schipholData = results;
    }
}
