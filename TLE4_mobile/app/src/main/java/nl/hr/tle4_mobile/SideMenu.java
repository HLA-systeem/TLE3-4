package nl.hr.tle4_mobile;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

public class SideMenu{
    private DrawerLayout dl;
    private Toolbar bar;
    private ActionBarDrawerToggle toggle;
    private AppCompatActivity activity;

    private NavigationView nv;

    public SideMenu(final AppCompatActivity activity){
        this.activity = activity;
        this.bar = (Toolbar) activity.findViewById(R.id.navigation_actionbar);
        this.activity.setSupportActionBar(this.bar);

        this.dl = (DrawerLayout) activity.findViewById(R.id.layout_drawer);
        this.toggle = new ActionBarDrawerToggle(activity,this.dl,R.string.open,R.string.close);
        this.dl.addDrawerListener(this.toggle);

        this.activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        this.nv = (NavigationView) activity.findViewById(R.id.navigationview);
        this.nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId()){
                    case R.id.nav_flightInfo:
                        SideMenu.this.dl.closeDrawer(Gravity.START);
                        break;
                    case R.id.nav_owt:
                        Intent wto = new Intent(activity, WaitTimesOverview.class);
                        activity.startActivity(wto);
                        break;
                }
                return false;
            }
        });
    }

    public ActionBarDrawerToggle getToggle(){
        return this.toggle;
    }

    public DrawerLayout getDL(){
        return this.dl;
    }
}
