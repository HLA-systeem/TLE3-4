package nl.hr.tle4_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    Button search;
    EditText flightNumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_search);
        search = (Button)findViewById(R.id.search);

    }

    public void findFlight(View v){
        flightNumb = (EditText)findViewById(R.id.flightNum);
        Intent i = new Intent(SearchActivity.this, FlightStatusActivity.class);
        i.putExtra("flight number", flightNumb.getText().toString().replace(" ",""));
        startActivity(i);
    }

}
