package com.getmygps;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import com.getmygps.MyLocation.LocationResult;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
