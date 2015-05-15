package com.spp.ddoots;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    EDCWebservice serviceObject;
	FetchXML xmlFetcher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FetchXML obj = new FetchXML();
		obj.execute("");
	    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//	    View decorView = getWindow().getDecorView();
//	 // Hide the status bar.
//	 int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//	 decorView.setSystemUiVisibility(uiOptions);
//	 // Remember that you should never show the action bar if the
//	 // status bar is hidden, so hide that too if necessary.
//	 ActionBar actionBar = getActionBar();
//	 actionBar.hide();
	    
//	    Thread background = new Thread() {
//            public void run() {
//                 
//                try {
//                    // Thread will sleep for 5 seconds
//                    sleep(5*1000);
//                     
//                    // After 5 seconds redirect to another intent
//                   
//                     
//                    //Remove activity
//                    finish();
//                     
//                } catch (Exception e) {
//                 
//                }
//            }
//        };
//         
//        // start thread
//        background.start();
	    
	    // xml fetch		
	    //xml fetch end

	    // location fetch
	    
//	    LocationResult locationResult = new LocationResult(){
//	        @Override
//	        public void gotLocation(Location location){
//	            //Got the location!
//	        	 double lat = location.getLatitude();
//	             double lng = location.getLongitude(); 
//	        	 Toast.makeText(getApplicationContext(),"gps",
//	       			   Toast.LENGTH_LONG).show();	
//	        
//	        }
//	    };
//	    MyLocation myLocation = new MyLocation();
//	    myLocation.getLocation(this, locationResult);
//	    
//	    Toast.makeText(getApplicationContext(),"gps2",
//    			   Toast.LENGTH_LONG).show();
	    //shared_preference like userDefaults in ios
//	    SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
//	    SharedPreferences.Editor editor = sharedPref.edit();
//	    editor.putString("myvalue", "10");
//	    editor.putInt("cv",55);
//	    editor.commit();
	    
	    int count = 10;
	    int foo = 20;
	    SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    SharedPreferences.Editor editor=saved_values.edit();
	        editor.putInt("count",count);
	                editor.putInt("foo",foo);
	        editor.commit();
	        
	        
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		 // Inflate the menu items for use in the action bar
	   return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		 switch (item.getItemId()) {
	        case R.id.action_search:
	            //openSearch();
	        	Toast.makeText(getApplicationContext(), "setting",
	        			   Toast.LENGTH_LONG).show();	        	
	            return true;
	        case R.id.action_settings:
	        	Toast.makeText(getApplicationContext(), "start",
	        			   Toast.LENGTH_LONG).show();
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public void sendMessage(View view) {
		
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		
		//intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);

	    // Do something in response to button
	}
	class FetchXML extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			//
		
				serviceObject = new EDCWebservice(getApplicationContext());
			    try{
			    	
			    FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getApplicationContext());
				SQLiteDatabase db = mDbHelper.getWritableDatabase();
				
				//delete tables to clear data
				db.delete(FeedReaderDbHelper.RESTAURANTS_TABLE_NAME, null, null);
			    db.delete(FeedReaderDbHelper.RESTAURANT_CAT_TABLE_NAME, null, null);

			    	
			    serviceObject.GetCategories("http://deliverydoots.com/android007/cat/cat.xml");
			    List<CategoryModel> categories = new ArrayList<CategoryModel>();
			    categories = serviceObject.ReadCategoryData("");
			    
			    for (CategoryModel categoryModel : categories) {
					
			    	int index = categoryModel.getId();
			    	
			    	if(index==1) //condition for selecting only current xml
			    	{
			    		serviceObject.Get("http://deliverydoots.com/android007/rest/"+index+".xml",index);
			    	}
			    	
				}
			    
			    
			    
			  //Toast.makeText(getApplicationContext(), jsonFile, Toast.LENGTH_LONG).show();
			    } catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "";
					
			
			
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try{
			
			 Intent i=new Intent(getBaseContext(),DisplayMessageActivity.class);
             startActivity(i);
			
			}
			catch(Exception e){
				
			}
			 
			//Toast.makeText(DisplayMessageActivity.this, "successfully QUERIED"+result,
				//	Toast.LENGTH_LONG).show();
			 SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		        int count = saved_values.getInt("count", -1);
			    Toast.makeText(getApplicationContext(),"hi",
		 			   Toast.LENGTH_LONG).show();	
			    
			    // write to storage
			    String filename = "myfile";
			    String string = "Hello world!";
			    FileOutputStream outputStream;

			    try {
			      outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			      outputStream.write(string.getBytes());
			      outputStream.close();
			    } catch (Exception e) {
			      e.printStackTrace();
			    }
			    
			    //write to database
			    
			    
			   
			    	
			    	//database deletion
			    	
//			    	// Define 'where' part of query.
//			    	String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
//			    	// Specify arguments in placeholder order.
//			    	String[] selectionArgs = { String.valueOf(rowId) };
//			    	// Issue SQL statement.
//			    	db.delete(table_name, selection, selectionArgs);
			    	
//			    	
//			    	//updation for database
//			    	
//			    	SQLiteDatabase db2 = mDbHelper.getReadableDatabase();
//
//			    	// New value for one column
//			    	ContentValues values2 = new ContentValues();
//			    	values.put(FeedReaderDbHelper.COLUMN_NAME_TITLE, "updated value");
//
//			    	// Which row to update, based on the ID
//			    	String selection = FeedReaderDbHelper.COLUMN_ID;
//			    	String[] selectionArgs = { "23" };

			    	//db2.update(FeedReaderDbHelper.cTABLE_NAME,values2,FeedReaderDbHelper.COLUMN_ID+"="+2, null);
			    	
			    	Toast.makeText(getApplicationContext(),"data retrieved from database",
				 			   Toast.LENGTH_LONG).show();
		}
		
	}
}
