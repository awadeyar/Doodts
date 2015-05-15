package com.spp.ddoots;

import android.R.string;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.spp.ddoots.MyLocation.LocationResult;

public class AndroidGPSTrackingActivity extends Activity {
	Button btnShowLocation;
	GPSTracker gps;
	string gps_location;
	MyLocation myLocation = new MyLocation();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_gpstracking);
		
		btnShowLocation = (Button) findViewById(R.id.buttonDropDown);
        
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
                // create class object
                gps = new GPSTracker(AndroidGPSTrackingActivity.this);
 
                // check if GPS enabled     
                if(gps.canGetLocation()){
                     
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                     
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                 
            }
        });
		
	}
	private void locationClick() {
        myLocation.getLocation(this, locationResult);
    }

    public LocationResult locationResult = new LocationResult() {
        public void gotLocation(final Location location) {
            try {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
                if (lat != 0.0 && lng != 0.0) {

                    String sLat;
                    String sLng;
                    sLat = Double.toString(lat);
                    sLng = Double.toString(lng);
                 //   gps_location = sLat+" "+sLng;
                    Toast.makeText(getBaseContext(), "We got gps location!", Toast.LENGTH_LONG)
                    .show();

                } 
            }catch (Exception e) {

            }
        }
    };
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android_gpstracking, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
