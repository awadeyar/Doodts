package com.spp.ddoots;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.spp.ddoots.MainActivity.FetchXML;



public class DisplayMessageActivity extends ActionBarActivity {
	  EDCWebservice serviceObject;
		FetchXML xmlFetcher;
		Spinner spinnerForLocation;
		AlertDialog.Builder b;
		List<RestaurantModel> reslist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		 b = new Builder(this);
		 try{
			    serviceObject = new EDCWebservice(getApplicationContext());
			    reslist = serviceObject.ReadRestaurantsData("");
			   // addItemsOnSpinner();
				//serviceObject.ReadCategoryData("");
			    }
			    catch(Exception e){
			    	
			    }
		 List<String> list = new ArrayList<String>();		 
		 
		 for (int i=0;i < reslist.size();i++) {
			 
			 list.add(reslist.get(i).location);		
			 }

		    b.setTitle("Choose Locality");
		    String[] arr = list.toArray(new String[list.size()]);
		    b.setItems(arr, new OnClickListener() {

		        @Override
		        public void onClick(DialogInterface dialog, int which) {

		            dialog.dismiss();
		            switch(which){
		            case 0:
		                //onZipRequested();
		                break;
		            case 1:
		                //onCategoryRequested();
		                break;
		            }
		        }

		    });

		    ImageView imgFavorite = (ImageView) findViewById(R.id.dropImage);
		    imgFavorite.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		           
		        	b.show();
		        }
		    });	    
	}
//	 public void addItemsOnSpinner() {
//		 
//			spinnerForLocation = (Spinner)findViewById(R.id.spinnerForLocation);
//			List<String> list = new ArrayList<String>();
//			 try{
//				    serviceObject = new EDCWebservice(getApplicationContext());
//				    reslist = serviceObject.ReadRestaurantsData("");
//				   // addItemsOnSpinner();
//					//serviceObject.ReadCategoryData("");
//				    }
//				    catch(Exception e){
//				    	
//				    }
//			
//			 list.add("Select Your Location");
//			 for (int i=0;i < reslist.size();i++) {
//				 
//				list.add(reslist.get(i).location);
//			}
//			 
//			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//				R.layout.spinner_item, list);
//			dataAdapter.setDropDownViewResource(R.layout.spinner_item);
//			spinnerForLocation.setAdapter(dataAdapter);
//		  }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
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
	
	//asynch taks
	
	
	
}

