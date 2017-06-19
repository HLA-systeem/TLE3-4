package nl.hr.tle4_mobile;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {


    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;
    private EditText flightNumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.dl = (DrawerLayout) findViewById(R.id.layout_drawer);
        this.toggle = new ActionBarDrawerToggle(this,dl,R.string.open,R.string.close);
        this.dl.addDrawerListener(this.toggle);
        this.toggle.syncState();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_search);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void returnFlight(View v){
        flightNumb = (EditText)findViewById(R.id.flightNum);
        Intent i = new Intent(SearchActivity.this, FlightStatusActivity.class);
        i.putExtra("flight number", flightNumb.getText().toString().replace(" ",""));
        startActivity(i);
    }

}
