package com.example.carlota.androidtimertaskexample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AndroidTimerTaskExample extends Activity {
    /**
     * Called when the activity is first created.
     */
    private TextView textTimer;
    private Button startButton,aboutButton,exitButton,btnViewAll;
    public dbhelper myDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTimer = (TextView) findViewById(R.id.textTimer);
        myDb= new dbhelper(this);
        startButton = (Button) findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentSystem= new Intent(AndroidTimerTaskExample.this, gpsStarted.class);
                startActivity(intentSystem);

            }
        });

        aboutButton = (Button) findViewById(R.id.btnAbout);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(AndroidTimerTaskExample.this,"Author:Ulises Fajardo Salgado \n Age:22 \n Email:ulises.fs.isc@gmail.com ", Toast.LENGTH_LONG).show();

            }
        });

        exitButton = (Button) findViewById(R.id.btnExit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        btnViewAll= (Button)findViewById(R.id.btnViewAll);
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount()==0)
                        { //show msg
                            Toast.makeText(AndroidTimerTaskExample.this, "No Data found", Toast.LENGTH_LONG).show();
                            return;
                        }
                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append(res.getString(0)+ ") " );
                            buffer.append("Date:"+res.getString(1)+ " " );
                            buffer.append("Time:"+res.getString(2)+ ", " );
                            buffer.append("Lat:"+res.getDouble(3)+ ", " );
                            buffer.append("Lon:"+res.getDouble(4)+ ", " );
                            buffer.append("Dist:"+res.getDouble(5)+ ". \n" );
                        }
                        Toast.makeText(AndroidTimerTaskExample.this, buffer.toString(), Toast.LENGTH_LONG).show();
                        //alertMessage("Results", buffer.toString());

                    }

                }
        );


    }
}


