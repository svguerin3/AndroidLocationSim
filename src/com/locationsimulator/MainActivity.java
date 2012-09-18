package com.locationsimulator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;
import com.locationsimulator.MyLocation.LocationResult;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */

    LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, false, true, true, 0, 5);

        // Submit-button logic
        final Button submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText latEditText = (EditText) findViewById(R.id.lat_entry);
                EditText longEditText = (EditText) findViewById(R.id.long_entry);
                String inputLat = latEditText.getText().toString();
                String inputLong = longEditText.getText().toString();

                if (inputLat.length() > 0 && inputLong.length() > 0) {
                    Location mLocation = new Location(LocationManager.GPS_PROVIDER);
                    //mLocation.setLatitude(28.0F);   // TESTING
                    //mLocation.setLongitude(-82.0F); // TESTING
                    mLocation.setLatitude(Float.valueOf(inputLat));
                    mLocation.setLongitude(Float.valueOf(inputLong));
                    mLocation.setTime(System.currentTimeMillis());

                    locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
                    locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mLocation);

                    refreshTheLocation();

                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextView gpsTextView = (TextView) findViewById(R.id.gps_label);
                            gpsTextView.setText("Getting new GPS info from device...");
                        }
                    });
                } else {

                }
            }
        });

        refreshTheLocation();
    }

    public void refreshTheLocation() {
        LocationResult locationResult = new LocationResult(){
            @Override
            public void gotLocation(Location location){
                final Location finalLoc = location;

                if (location != null) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextView gpsTextView = (TextView) findViewById(R.id.gps_label);
                            gpsTextView.setText("GPS DATA FOUND:\nLatitude: " + finalLoc.getLatitude() + "\nLongitude: " + finalLoc.getLongitude());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextView gpsTextView = (TextView) findViewById(R.id.gps_label);
                            gpsTextView.setText("GPS data not found");
                        }
                    });
                }
            }
        };
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
    }
}