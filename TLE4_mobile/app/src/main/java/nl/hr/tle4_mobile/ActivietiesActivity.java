package nl.hr.tle4_mobile;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ActivietiesActivity extends AppCompatActivity {
    Button callTaxi;
    private SideMenu sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_activieties);

        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        RelativeLayout bg = (RelativeLayout)findViewById(R.id.main_wta);
        bg.setBackgroundColor(Color.parseColor(getPrefs.getString("appColor","#9370DB")));

        this.sideMenu = new SideMenu(this);
        callTaxi = (Button)findViewById(R.id.taxiButton);

    }


    public void makeCall(View v){
        String number = "0620274833";
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + number));

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        startActivity(i);
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


}
