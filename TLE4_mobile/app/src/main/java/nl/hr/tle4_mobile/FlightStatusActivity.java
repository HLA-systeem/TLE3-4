package nl.hr.tle4_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FlightStatusActivity extends AppCompatActivity {

    String API_KEY = "e7aa5d807f1406029fe3b79dd35e65ef";
    String API_ID = "51e64f75";

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_status);

        tv = (TextView)findViewById(R.id.textView);
        DataHttp as = new DataHttp();
        as.execute("https://api.schiphol.nl/public-flights/flights?app_id=51e64f75&app_key=e7aa5d807f1406029fe3b79dd35e65ef&flightname=KL0808&includedelays=false&page=0&sort=%2Bscheduletime");
    }

    public void setInfo(String s){
        tv.setText(s);
    }
}
