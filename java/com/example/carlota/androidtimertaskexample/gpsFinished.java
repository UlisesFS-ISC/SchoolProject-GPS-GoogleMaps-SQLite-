package com.example.carlota.androidtimertaskexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.widget.Toast;
import android.content.Context;

public class gpsFinished extends Activity {
    TextView textInitial;
    TextView textFinal;
    TextView textDistance;
    TextView textTime;

    Button btnHome,btnMap,btnViewAll,btnDel;
    Double startLat;
    Double endLat;
    Double startLon;
    Double endLon;
    public dbhelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_finished);
        myDb= new dbhelper(this);
        textFinal = (TextView) findViewById(R.id.resultsEndLoc);
        textInitial = (TextView) findViewById(R.id.resultsInitLoc);

        textTime = (TextView) findViewById(R.id.resultsTime);
        textDistance = (TextView) findViewById(R.id.resultsDistance);
        Bundle i = getIntent().getExtras();

         startLat=i.getDouble("startLat");
         endLat=i.getDouble("endLat");
         startLon=i.getDouble("startLon");
         endLon=i.getDouble("endLon");


        String startCoord = ":"+startLat+",  "+startLon;
        String finalCoord = ":"+endLat+",  "+endLon;

        textFinal.append(startCoord);
        textInitial.append(finalCoord);
        textTime.append(i.getString("time"));
        textDistance.append(i.getString("distance"));


        btnHome= (Button)findViewById(R.id.btnExit);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIntro = new Intent(gpsFinished.this, AndroidTimerTaskExample.class);
                startActivity(intentIntro);

            }
        });
        btnMap= (Button)findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMaps = new Intent(gpsFinished.this, MapsActivity.class);
                Bundle extras = new Bundle();
                extras.putDouble("startLat", startLat);
                extras.putDouble("startLon", startLon);
                extras.putDouble("endLat",endLat);
                extras.putDouble("endLon", endLon);
                intentMaps.putExtras(extras);
                startActivity(intentMaps);

            }
        });



        btnDel = (Button)findViewById(R.id.btnDel);
        btnDel.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        myDb.deleteData();
                    }

                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gps_finished, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
