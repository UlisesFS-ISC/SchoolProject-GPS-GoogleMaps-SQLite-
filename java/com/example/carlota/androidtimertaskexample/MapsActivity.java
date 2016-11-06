package com.example.carlota.androidtimertaskexample;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Double startLat,startLon,endLat,endLon;
    public dbhelper myDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb= new dbhelper(this);
        Bundle i = getIntent().getExtras();
        startLat=i.getDouble("startLat");
        endLat=i.getDouble("endLat");
        startLon=i.getDouble("startLon");
        endLon=i.getDouble("endLon");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(endLat, endLon), 16.0f));
        Toast toast =Toast.makeText(getApplicationContext(), "Localizacion Final:" + endLat + ", " + endLon, Toast.LENGTH_SHORT);
        toast.show();
       // mMap.addMarker(new MarkerOptions().position(new LatLng(startLat, startLon)).title("Marker2"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(endLat, endLon)).title("Marker3"));
        Cursor res = myDb.getAllData();
        if(res.getCount()==0)
        { //show msg
            Toast.makeText(MapsActivity.this, "No Data found", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            while (res.moveToNext()) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(res.getDouble(3), res.getDouble(4))).title(res.getString(1) + "- "+res.getString(2)+"- "+res.getDouble(5) ));
            }
        }
    }
}
