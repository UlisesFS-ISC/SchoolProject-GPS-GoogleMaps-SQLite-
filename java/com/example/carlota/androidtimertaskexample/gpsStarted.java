package com.example.carlota.androidtimertaskexample;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;


public class gpsStarted extends Activity {
    /**
     * Called when the activity is first created.
     */
    GPSTracker gps;
    private TextView textTimer;
    private Button startButton;
    private Button pauseButton;
    private Button endButton;
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    boolean initset=false;

    TextView gpsStatus;
    TextView initLocation;
    TextView currentLocation;
    TextView textDistance;

    Location initial;
    Location current;
    Location lastLocation;
    double startlatitude;
    double startlongitude;
    double lastLat=0;
    double lastLon=0;
    double totDistance=0;
    int checkTimeUpdate=0;
   public dbhelper myDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_started);
        myDb= new dbhelper(this);
        textTimer = (TextView) findViewById(R.id.textTimer);
        gpsStatus = (TextView) findViewById(R.id.Status);
        initLocation = (TextView) findViewById(R.id.startLoc);

        currentLocation = (TextView) findViewById(R.id.currentLoc);
        textDistance = (TextView) findViewById(R.id.currentDistance);
        gps = new GPSTracker(gpsStarted.this);

        // check if GPS enabled




        startButton = (Button) findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod, 0);

            }
        });

        pauseButton = (Button) findViewById(R.id.btnPause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);

            }
        });

        endButton = (Button) findViewById(R.id.btnEnd);
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);
                initial.setLatitude(startlatitude);
                initial.setLongitude(startlongitude);
                //String sentInitial = ":"+ initial.getLatitude()+",  "+initial.getLongitude();
               // String sentFinal = ":"+ current.getLatitude()+",  "+current.getLongitude();

                Intent intentSystem = new Intent(gpsStarted.this, gpsFinished.class);
/*

                intentSystem.putExtra("time", textTimer.getText());
                intentSystem.putExtra("initial",sentInitial);
                intentSystem.putExtra("ending", sentFinal);
                intentSystem.putExtra("distance",sentDistance);
                */

                Bundle extras = new Bundle();
                extras.putString("time", (String) textTimer.getText());
               // extras.putString("initial", sentInitial);
                //extras.putString("ending", sentFinal);
                extras.putString("distance", ":"+ totDistance + "  mts");

                extras.putDouble("startLat", startlatitude);
                extras.putDouble("startLon", startlongitude);
                extras.putDouble("endLat",current.getLatitude());
                extras.putDouble("endLon", current.getLongitude());
                intentSystem.putExtras(extras);

                startActivity(intentSystem);

            }
        });


    if (gps.canGetLocation()) {



        gpsStatus.setText("GPS enabled");

        startTime = SystemClock.uptimeMillis();
        myHandler.postDelayed(updateTimerMethod, 0);
        startButton.setVisibility(View.VISIBLE);


        if (!initset) {
            startlatitude = gps.getLatitude();
            startlongitude = gps.getLongitude();
            initial = new Location("Initial");
            lastLocation= new Location("LastUsed");
            lastLocation.setLatitude(gps.getLatitude());
            lastLocation.setLongitude(gps.getLongitude());
            initLocation.setText("Starting Location: " + startlatitude + ",   " + startlongitude);
            initset=true;
        }


    }
    }

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;









            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (finalTime % 1000);

                checkTimeUpdate=seconds;
                current = gps.getLocation();
                currentLocation.setText("Current Location: " + current.getLatitude() + ",   " + current.getLongitude());
                textDistance.setText("Distance:" + totDistance + "  mts");
                if(current.getLatitude()!=lastLat || current.getLongitude() != lastLon){
                    totDistance+= current.distanceTo(lastLocation);
                    lastLat=current.getLatitude();
                    lastLon=current.getLongitude();
                    boolean isInserted = myDb.insertData(textTimer.getText().toString(),
                            current.getLatitude(),
                            current.getLongitude(),
                            (double)totDistance);
                    if(isInserted ==true)
                        Toast.makeText(gpsStarted.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(gpsStarted.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    Toast.makeText(gpsStarted.this, "cambio cordenada", Toast.LENGTH_LONG).show();
                }
               /* boolean isInserted = myDb.insertData(textTimer.getText().toString(),
                        current.getLatitude(),
                        current.getLongitude(),
                        (double)initial.distanceTo(current));
                if(isInserted ==true)
                    Toast.makeText(gpsStarted.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(gpsStarted.this,"Data not Inserted",Toast.LENGTH_LONG).show();*/





            textTimer.setText("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliseconds));
            myHandler.postDelayed(this, 0);
        }

    };


}


