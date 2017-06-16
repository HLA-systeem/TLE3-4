package nl.hr.tle4_mobile;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GPS extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient googleApi;

    private Location aLocLast;
    private LocationRequest aLocReq;
    private String userLat;
    private String userLong;


    public GPS(){ //kan zjn dat de context de main activvity moet zijn.

        if (this.googleApi == null) { //maak het GoogleAPIclient object als deze er niet is.
            this.googleApi = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        this.createLocationRequest();
    }

    private void createLocationRequest() {
        this.aLocReq = new LocationRequest();
        this.aLocReq.setInterval(5500);
        this.aLocReq.setFastestInterval(5000);
        this.aLocReq.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
