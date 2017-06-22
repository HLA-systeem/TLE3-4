package nl.hr.tle4_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FlightActivity extends AppCompatActivity implements View.OnClickListener{
    private SideMenu sideMenu;

    private EditText flightNum;
    private EditText tagInput1;
    private EditText tagInput2;
    private Button search;
    private TextView flightStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_flight);

        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        RelativeLayout bg = (RelativeLayout)findViewById(R.id.activity_flight);
        bg.setBackgroundColor(Color.parseColor(getPrefs.getString("appColor","#9370DB")));

        this.sideMenu = new SideMenu(this);

        this.flightNum = (EditText)findViewById(R.id.flightNum);
        this.tagInput1 = (EditText)findViewById(R.id.input_lugID1);
        this.tagInput2 = (EditText)findViewById(R.id.input_lugID2);
        this.search = (Button)findViewById(R.id.button_search);
        this.flightStatus = (TextView) findViewById(R.id.text_flightStatus);

        this.search.setOnClickListener(this);

        if(Constants.flightInfo != null){
            if(!Constants.flightInfo.equals("")){
                this.showFlightInfo();
            }
        }
    }

    //functions for the Actionbar to communicate with the SideMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle sIT){
        super.onPostCreate(sIT);
        this.sideMenu.getToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.sideMenu.getToggle().onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
                case R.id.action_openDrawer:
                    this.sideMenu.getDL().openDrawer(Gravity.START);
                    break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_search:
                Constants.flight = this.flightNum.getText().toString();
                Constants.luggageID1 = this.tagInput1.getText().toString();
                Constants.luggageID2 = this.tagInput2.getText().toString();

                this.getFlightInfromation();

                break;
        }
    }



    public void showFlightInfo(){
        this.flightStatus.setText(Constants.flightInfo);
    }



    private void getFlightInfromation(){
        SchipholApi schipholApi = new SchipholApi(this);
        schipholApi.execute("https://api.schiphol.nl/public-flights/flights?app_id=51e64f75&app_key=e7aa5d807f1406029fe3b79dd35e65ef&flightname=" + Constants.flight + "&includedelays=false&page=0&sort=%2Bscheduletime");
    }
}
