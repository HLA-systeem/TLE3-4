package nl.hr.tle4_mobile;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class FlightActivity extends AppCompatActivity implements View.OnClickListener{
    private SideMenu sideMenu;

    private EditText flightNum;
    private EditText tagInput1;
    private EditText tagInput2;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_flight);

        this.sideMenu = new SideMenu(this);

        if(Constants.flightInfo != null){
            this.getFlightInfromation();
        }

        this.flightNum = (EditText)findViewById(R.id.flightNum);
        this.tagInput1 = (EditText)findViewById(R.id.input_lugID1);
        this.tagInput2 = (EditText)findViewById(R.id.input_lugID2);
        this.search = (Button)findViewById(R.id.button_search);

        this.search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_search:
                this.sideMenu.getDL().openDrawer(Gravity.START);
                Constants.flight = this.flightNum.getText().toString();
                Constants.luggageID1 = this.tagInput1.getText().toString();
                System.out.println(Constants.luggageID1);
                if(FlightActivity.this.tagInput2.getText().toString() != null){
                    Constants.luggageID2 = this.tagInput2.getText().toString();
                }
                this.getFlightInfromation();

                Intent wto = new Intent(this, WaitTimesOverview.class);
                this.startActivity(wto);

                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(this.sideMenu.getToggle().onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFlightInfo(){

    }

    private void getFlightInfromation(){
        SchipholApi schipholApi = new SchipholApi(this);
        schipholApi.execute("https://api.schiphol.nl/public-flights/flights?app_id=51e64f75&app_key=e7aa5d807f1406029fe3b79dd35e65ef&flightname=" + Constants.flight + "&includedelays=false&page=0&sort=%2Bscheduletime");
    }
}
