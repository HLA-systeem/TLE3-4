package nl.hr.tle4_mobile;


import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

public class SideMenu implements View.OnClickListener{
    private DrawerLayout dl;
    private Toolbar bar;
    private ActionBarDrawerToggle toggle;
    private AppCompatActivity activity;

    //private MenuView.ItemView

    public SideMenu(AppCompatActivity activity){
        this.activity = activity;
        this.bar = (Toolbar) activity.findViewById(R.id.navigation_actionbar);
        this.activity.setSupportActionBar(this.bar);

        this.dl = (DrawerLayout) activity.findViewById(R.id.layout_drawer);
        this.toggle = new ActionBarDrawerToggle(activity,dl,R.string.open,R.string.close);

        this.dl.addDrawerListener(this.toggle);
        this.toggle.syncState();
        this.activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //activity.getSupportActionBar().setIcon(R.mipmap.ic_menu_white_24dp);
    }

    public ActionBarDrawerToggle getToggle(){
        return this.toggle;
    }

    public DrawerLayout getDL(){
        return this.dl;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.nav_flightInfo:
                this.dl.closeDrawer(Gravity.END);
                break;
            case R.id.nav_owt:
                Intent wto = new Intent(this.activity, WaitTimesOverview.class);
                this.activity.startActivity(wto);
                break;
            case R.id.nav_passTimeActs:
                Intent acts = new Intent(this.activity, ActivietiesActivity.class);
                this.activity.startActivity(acts);
                break;
            case R.id.nav_settings:
                //Intent settings = new Intent(this, SettingsActivity.class);
                //this.activity.startActivity(settings);
                break;
        }
    }
}
