package com.spp.ddoots;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class EDCWebservice {
private static final String ALLOWED_URI_CHARS = null;
Context mcontext;

public EDCWebservice(Context mcontext) {
	super();
	this.mcontext = mcontext;
}


public String httpGetMethod(String method) throws ClientProtocolException, IOException{
	String result = null;
	
	DefaultHttpClient httpClient= new DefaultHttpClient();
	
	HttpGet httpGet= new HttpGet(method);
	
	httpGet.setHeader("Accept", "application/xml");
	httpGet.setHeader("Content-Type", "application/xml");
	
	HttpResponse httpResponse=null;
	
	httpResponse= httpClient.execute(httpGet);
	
	result= TextHelper.GetText(httpResponse);
	
	return result;
} 

// Login
public boolean LoginAuth(String url) throws ClientProtocolException, IOException{
	boolean loginAuthResult=false; 
	String loginResult=null;
	
	loginResult= httpGetMethod(url);
	
	try {
		XmlPullParserFactory xmlPullFactory = XmlPullParserFactory.newInstance();
		xmlPullFactory.setNamespaceAware(true);
		XmlPullParser parser= xmlPullFactory.newPullParser();
		parser.setInput(new StringReader(loginResult));
		int eventType=parser.getEventType();
		while(eventType!=parser.END_DOCUMENT){
			
			if(eventType == XmlPullParser.START_DOCUMENT) {
	              System.out.println("Start document");
	          } else if(eventType == XmlPullParser.END_DOCUMENT) {
	              System.out.println("End document");
	          } else if(eventType == XmlPullParser.START_TAG) {
	              System.out.println("Start tag "+parser.getName());
	          } else if(eventType == XmlPullParser.END_TAG) {
	              System.out.println("End tag "+parser.getName());
	          } else if(eventType == XmlPullParser.TEXT) {
	        	 
	        	  if(parser.getText().equalsIgnoreCase("true")){
	        		  loginAuthResult=true;
	        	  }else{
	        		  loginAuthResult=false;
	        	  }
	              System.out.println("Text "+parser.getText());
	          }
	          eventType = parser.next();
	         }
		
		
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return loginAuthResult;
}
// Getting study data by username
public String Get(String url,int categoryId) throws ClientProtocolException, IOException{
	
	FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(mcontext);
	    SQLiteDatabase db = mDbHelper.getWritableDatabase();
	    ContentValues values;
	    String result;
	result=httpGetMethod(url);
	
	JSONObject jsonObj = null;
	try {
	    jsonObj = XML.toJSONObject(result);
	    Log.d("mylog", jsonObj.toString());
	    JSONObject jsonGetTable= jsonObj.getJSONObject("urlset");
//	    
	    if((jsonGetTable.get("rest")) instanceof JSONArray){
	    	 JSONArray jsonArrayTable=(JSONArray)(jsonGetTable.get("rest"));
	    	for(int i=0;i<jsonArrayTable.length();i++){
	    		JSONObject objJson= jsonArrayTable.getJSONObject(i);
	    		values = new ContentValues();
	    		values.put(FeedReaderDbHelper.RESTAURANT_ID,objJson.getInt("id"));
	    	    values.put(FeedReaderDbHelper.RESTAURANT_LOGO,objJson.getString("logo"));
	    	    values.put(FeedReaderDbHelper.RESTAURANT_LOCATION,objJson.getString("loc"));
	    	    values.put(FeedReaderDbHelper.RESTAURANT_NAME,objJson.getString("name"));
	    	    values.put(FeedReaderDbHelper.RESTAURANT_CAT_ID_FK,categoryId);

	    	     db.insert(
	   	             FeedReaderDbHelper.RESTAURANTS_TABLE_NAME,
	   	             null,values);
	    		
	    	}

	    }else{ 
	    	JSONObject objJson= (JSONObject) (jsonGetTable.get("rest"));
	    	values = new ContentValues();
    		values.put(FeedReaderDbHelper.RESTAURANT_ID,objJson.getInt("id"));
    	    values.put(FeedReaderDbHelper.RESTAURANT_LOGO,objJson.getString("logo"));
    	    values.put(FeedReaderDbHelper.RESTAURANT_LOCATION,objJson.getString("loc"));
    	    values.put(FeedReaderDbHelper.RESTAURANT_NAME,objJson.getString("name"));

    	    db.insert(
   	             FeedReaderDbHelper.RESTAURANTS_TABLE_NAME,
   	             null,values);
		  
	    }
	   
	} catch (JSONException e) {
	    Log.e("JSON exception", e.getMessage());
	    e.printStackTrace();
	} 
	 return "";
}

public String GetCategories(String url) throws ClientProtocolException, IOException{
	
	FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(mcontext);
	    SQLiteDatabase db = mDbHelper.getWritableDatabase();

	    ContentValues values;
	    String result;
	result=httpGetMethod(url);
	
	JSONObject jsonObj = null;
	try {
	    jsonObj = XML.toJSONObject(result);
	    Log.d("mylog", jsonObj.toString());
	    JSONObject jsonGetTable= jsonObj.getJSONObject("urlset");
//	    
	    if((jsonGetTable.get("cat")) instanceof JSONArray){
	    	 JSONArray jsonArrayTable=(JSONArray)(jsonGetTable.get("cat"));
	    	for(int i=0;i<jsonArrayTable.length();i++){
	    		JSONObject objJson= jsonArrayTable.getJSONObject(i);
	    		values = new ContentValues();
	    		values.put(FeedReaderDbHelper.RESTAURANT_CAT_ID,objJson.getInt("id"));
	    	    values.put(FeedReaderDbHelper.RESTAURANT_CATNAME,objJson.getString("name"));
	    	   

	    	     db.insert(
	   	             FeedReaderDbHelper.RESTAURANT_CAT_TABLE_NAME,
	   	             null,values);
	    		
	    	}

	    }else{ 
	    	JSONObject objJson= (JSONObject) (jsonGetTable.get("cat"));
	    	values = new ContentValues();
    		values.put(FeedReaderDbHelper.RESTAURANT_ID,objJson.getInt("id"));
    	    values.put(FeedReaderDbHelper.RESTAURANT_LOGO,objJson.getString("logo"));
    

    	    db.insert(
   	             FeedReaderDbHelper.RESTAURANT_CAT_TABLE_NAME,
   	             null,values);
		  
	    }
	   
	} catch (JSONException e) {
	    Log.e("JSON exception", e.getMessage());
	    e.printStackTrace();
	} 
	 return jsonObj.toString();
}

public List<RestaurantModel> ReadRestaurantsData(String url) throws ClientProtocolException, IOException{
	
	FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(mcontext);
	    SQLiteDatabase db = mDbHelper.getReadableDatabase();
	    List<RestaurantModel> rList = new ArrayList<RestaurantModel>();
	    
	    

	 try{
		 
		 String[] projection = {
		    		FeedReaderDbHelper.RESTAURANT_ID,
		    		FeedReaderDbHelper.RESTAURANT_LOCATION,
		    		FeedReaderDbHelper.RESTAURANT_LOGO,		    		
		    		FeedReaderDbHelper.RESTAURANT_NAME

		    	    };

		    	// How you want the results sorted in the resulting Cursor
		    	String sortOrder =
		    			FeedReaderDbHelper.RESTAURANT_ID ;

		    	Cursor cursor = db.query(
		    			FeedReaderDbHelper.RESTAURANTS_TABLE_NAME,  // The table to query
		    	    projection,                               // The columns to return
		    	    null,                                // The columns for the WHERE clause
		    	    null,                            // The values for the WHERE clause
		    	    null,                                     // don't group the rows
		    	    null,                                     // don't filter by row groups
		    	    sortOrder                                 // The sort order
		    	    );
		    	
		    	RestaurantModel aRestaurant;
		    	while (cursor.moveToNext()) {
		    		aRestaurant = new RestaurantModel(cursor.getInt(
				    	    cursor.getColumnIndexOrThrow(FeedReaderDbHelper.RESTAURANT_ID)),cursor.getString(
					    	    cursor.getColumnIndexOrThrow(FeedReaderDbHelper.RESTAURANT_NAME)),cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderDbHelper.RESTAURANT_LOCATION)),
					    	    		cursor.getString(
					    	    cursor.getColumnIndexOrThrow(FeedReaderDbHelper.RESTAURANT_LOGO)));
		    		rList.add(aRestaurant);
		    	}
		    	
  
	} catch (Exception e) {
	    Log.e("JSON exception", e.getMessage());
	    e.printStackTrace();
	}
	return rList; 

	
	 
}
public List<CategoryModel> ReadCategoryData(String url) throws ClientProtocolException, IOException{
	
	FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(mcontext);
	    SQLiteDatabase db = mDbHelper.getReadableDatabase();
	    List<CategoryModel> rList = new ArrayList<CategoryModel>();

	 try{
		 
		 String[] projection = {
		    		FeedReaderDbHelper.RESTAURANT_CAT_ID,
		    		FeedReaderDbHelper.RESTAURANT_CATNAME
		    		};

		    	// How you want the results sorted in the resulting Cursor
		    	String sortOrder =
		    			FeedReaderDbHelper.RESTAURANT_CAT_ID ;

		    	Cursor cursor = db.query(
		    			FeedReaderDbHelper.RESTAURANT_CAT_TABLE_NAME,  // The table to query
		    	    projection,                               // The columns to return
		    	    null,                                // The columns for the WHERE clause
		    	    null,                            // The values for the WHERE clause
		    	    null,                                     // don't group the rows
		    	    null,                                     // don't filter by row groups
		    	    sortOrder                                 // The sort order
		    	    );
		    	
		    	CategoryModel aCat;
		    	while (cursor.moveToNext()) {
		    		aCat = new CategoryModel(cursor.getInt(
				    	    cursor.getColumnIndexOrThrow(FeedReaderDbHelper.RESTAURANT_CAT_ID)),cursor.getString(
					    	    cursor.getColumnIndexOrThrow(FeedReaderDbHelper.RESTAURANT_CATNAME)));
		    		rList.add(aCat);
		    	}
		    	
  
	} catch (Exception e) {
	    Log.e("JSON exception", e.getMessage());
	    e.printStackTrace();
	}
	return rList; 

	
	 
}
//// Getting study data by Study ID
//public List<EDCStudyIdTable> GetStudyIdDetails(String url) throws ClientProtocolException, IOException{
//	
//	List<EDCStudyIdTable> iDTableList= new ArrayList<EDCStudyIdTable>() ;
//	
//	String result;
//	result=httpGetMethod(url);
//	
//	
//	JSONObject jsonObj = null;
//	try {
//	    jsonObj = XML.toJSONObject(result);
//	    JSONObject jsonGetTable= jsonObj.getJSONObject("GetSiteDataResponse").getJSONObject("GetSiteDataResult");
//	    
//	    if(jsonGetTable.get("a:SiteMetaData") instanceof JSONArray){
//	    	 JSONArray jsonArrayTable=jsonObj.getJSONObject("GetSiteDataResponse").getJSONObject("GetSiteDataResult").getJSONArray("a:SiteMetaData");
//	    	for(int i=0;i<jsonArrayTable.length();i++){
//	    		JSONObject json= jsonArrayTable.getJSONObject(i);
//		    	EDCStudyIdTable edcTabelData= new EDCStudyIdTable(json.getInt("a:arms"),json.getString("a:country"),json.getString("a:location"),json.getString("a:sitedescription"),json.getInt("a:siteid"),json.getString("a:sitename"),json.getInt("a:subjects"));
//		    	iDTableList.add(edcTabelData);
//		    }
//	    }else{
//	    	JSONObject json=jsonGetTable.getJSONObject("a:SiteMetaData");
//	    	EDCStudyIdTable edcTabelData= new EDCStudyIdTable(json.getInt("a:arms"),json.getString("a:country"),json.getString("a:location"),json.getString("a:sitedescription"),json.getInt("a:siteid"),json.getString("a:sitename"),json.getInt("a:subjects"));
//		    	iDTableList.add(edcTabelData);
//		  
//	    }
//	    
//	} catch (JSONException e) {
//	    Log.e("JSON exception", e.getMessage());
//	    e.printStackTrace();
//	} 
//
//	
//	return iDTableList;
//}
//
////Getting subject data by Study ID and Site Name
//public List<EDCSubjectDataTable> GetSubjectDetails(String url) throws ClientProtocolException, IOException, URISyntaxException{
//	
//	List<EDCSubjectDataTable> subjectDataTableList= new ArrayList<EDCSubjectDataTable>() ;
//	
//	String result;
//	
//	
//	result=httpGetMethod(url);
//	
//	
//	JSONObject jsonObj = null;
//	try {
//	    jsonObj = XML.toJSONObject(result);
//	    JSONObject jsonGetTable= jsonObj.getJSONObject("GetSubjectDataResponse").getJSONObject("GetSubjectDataResult");
//	   
//	  
//	    if(jsonGetTable.get("a:SubjectMetaData") instanceof JSONArray){
//	    	 JSONArray jsonArrayTable=jsonObj.getJSONObject("GetSubjectDataResponse").getJSONObject("GetSubjectDataResult").getJSONArray("a:SubjectMetaData");
//	    	for(int i=0;i<jsonArrayTable.length();i++){
//	    		JSONObject json= jsonArrayTable.getJSONObject(i);
//	    		EDCSubjectDataTable edcTabelData= new EDCSubjectDataTable(json.getString("a:armname"),json.getString("a:sitename"),json.getInt("a:subjectid"));
//		    	subjectDataTableList.add(edcTabelData);
//		    }
//	    }else{
//	    	JSONObject json=jsonGetTable.getJSONObject("a:SubjectMetaData");
//	    	EDCSubjectDataTable edcTabelData= new EDCSubjectDataTable(json.getString("a:armname"),json.getString("a:sitename"),json.getInt("a:subjectid"));
//	         subjectDataTableList.add(edcTabelData);
//		  
//	    }
//	    
//	} catch (JSONException e) {
//	    Log.e("JSON exception", e.getMessage());
//	    e.printStackTrace();
//	} 
//
//	
//	return subjectDataTableList;
//}
//
//
//public List<EDCVisitDataTable> GetVisitData(String url) throws ClientProtocolException, IOException{
//List<EDCVisitDataTable> visitDataTableList= new ArrayList<EDCVisitDataTable>() ;
//	
//	String result;
//	
//	
//	result=httpGetMethod(url);
//	
//	
//	JSONObject jsonObj = null;
//	try {
//	    jsonObj = XML.toJSONObject(result);
//	    JSONObject jsonGetTable= jsonObj.getJSONObject("GetVisitDataResponse").getJSONObject("GetVisitDataResult");
//	   
//	  
//	    if(jsonGetTable.get("a:VisitMetaData") instanceof JSONArray){
//	    	 JSONArray jsonArrayTable=jsonObj.getJSONObject("GetVisitDataResponse").getJSONObject("GetVisitDataResult").getJSONArray("a:VisitMetaData");
//	    	for(int i=0;i<jsonArrayTable.length();i++){
//	    		JSONObject json= jsonArrayTable.getJSONObject(i);
//	    		EDCVisitDataTable edcTabelData= new EDCVisitDataTable(json.getInt("a:forms"),json.getString("a:visitname"));
//	    		visitDataTableList.add(edcTabelData);
//		    }
//	    }else{
//	    	JSONObject json=jsonGetTable.getJSONObject("a:SubjectMetaData");
//	    	EDCVisitDataTable edcTabelData= new EDCVisitDataTable(json.getInt("a:forms"),json.getString("a:visitname"));
//	    	visitDataTableList.add(edcTabelData);
//		  
//	    }
//	    
//	} catch (JSONException e) {
//	    Log.e("JSON exception", e.getMessage());
//	    e.printStackTrace();
//	} 
//
//	
//	return visitDataTableList;
//}
//
//public ArrayList<EDCMasterViewTable> GetForomData(String url) throws ClientProtocolException, IOException{
//ArrayList<EDCMasterViewTable> foromDataTableList= new ArrayList<EDCMasterViewTable>() ;
//	
//	String result;
//	
//	
//	result=httpGetMethod(url);
//	
//	
//	JSONObject jsonObj = null;
//	try {
//	    jsonObj = XML.toJSONObject(result);
//	    JSONObject jsonGetTable= jsonObj.getJSONObject("GetFormDataResponse").getJSONObject("GetFormDataResult");
//	   
//	  
//	    if(jsonGetTable.get("a:string") instanceof JSONArray){
//	    	 JSONArray jsonArrayTable=jsonObj.getJSONObject("GetFormDataResponse").getJSONObject("GetFormDataResult").getJSONArray("a:string");
//	    	for(int i=0;i<jsonArrayTable.length();i++){
//	    		
//	    		EDCMasterViewTable edcTabelData= new EDCMasterViewTable(jsonArrayTable.get(i).toString(),i);
//	    		foromDataTableList.add(edcTabelData);
//		    }
//	    }else{
//	    	JSONObject json=jsonGetTable.getJSONObject("a:string");
//	    	EDCMasterViewTable edcTabelData= new EDCMasterViewTable(json+"",0);
//	    	foromDataTableList.add(edcTabelData);
//		  
//	    }
//	    
//	} catch (JSONException e) {
//	    Log.e("JSON exception", e.getMessage());
//	    e.printStackTrace();
//	} 
//
//	
//	return foromDataTableList;
//}
//
//
//public ArrayList<EDCMainDetailsTable> GetDetailsForomData(String url) throws ClientProtocolException, IOException{
//ArrayList<EDCMainDetailsTable> foromDataTableList= new ArrayList<EDCMainDetailsTable>() ;
//	
//	String result;
//	
//	
//	result=httpGetMethod(url);
//	
//	
//	JSONObject jsonObj = null;
//	try {
//	    jsonObj = XML.toJSONObject(result);
//	    JSONObject jsonGetTable= jsonObj.getJSONObject("GetXMLsResponse").getJSONObject("GetXMLsResult").getJSONObject("a:FormMetaData");
//	   
//	   String jsonResult= jsonGetTable.getString("a:formdata");
//	   JSONObject formJson= XML.toJSONObject(jsonResult);
//	 String form= formJson.getJSONObject("form").getString("fieldset");
//	JSONObject jsonObject= new JSONObject(form);
//	JSONArray fieldJsonArray=jsonObject.getJSONArray("field");
//	JSONObject fieldJsonObject;
//	for (int i=0;i<fieldJsonArray.length();i++){
//		fieldJsonObject= fieldJsonArray.getJSONObject(i);
//		int minLength;
//	    String gui_label;
//	    String charecteristics;
//	    String visible;
//	    int grp_name;
//	    String indent;
//	    String ctrl_name;
//	    String mantissa;
//	    String ed_chk;
//	    String cd_lst;
//	    String allowpartial;
//	    int maxlength;
//	    String data_type;
//	    String ctrl_type;
//	    String description;
//	    String src;
//	    String required;
//	    String dt_st_label;
//		
//		if(fieldJsonObject.has("minlength")&&!fieldJsonObject.isNull("minlength")){
//			minLength=fieldJsonObject.getInt("minlength");
//		}else{
//			minLength=0;
//		}
//
//		if(fieldJsonObject.has("gui_label")&&!fieldJsonObject.isNull("gui_label")){
//			gui_label=fieldJsonObject.getString("gui_label");
//		}else{
//			gui_label="default";
//		}
//		if(fieldJsonObject.has("charecteristics")&&!fieldJsonObject.isNull("charecteristics")){
//			charecteristics=fieldJsonObject.getString("charecteristics");
//		}else{
//			charecteristics="default";
//		}
//		if(fieldJsonObject.has("visible")&&!fieldJsonObject.isNull("visible")){
//			visible=fieldJsonObject.getString("visible");
//		}else{
//			visible="default";
//		}
//		if(fieldJsonObject.has("grp_name")&&!fieldJsonObject.isNull("grp_name")){
//			grp_name=fieldJsonObject.getInt("grp_name");
//		}else{
//			grp_name=-1;
//		}
//		if(fieldJsonObject.has("indent")&&!fieldJsonObject.isNull("indent")){
//			indent=fieldJsonObject.getString("indent");
//		}else{
//			indent="default";
//		}
//		if(fieldJsonObject.has("ctrl_name")&&!fieldJsonObject.isNull("ctrl_name")){
//			ctrl_name=fieldJsonObject.getString("ctrl_name");
//		}else{
//			ctrl_name="default";
//		}
//		if(fieldJsonObject.has("mantissa")&&!fieldJsonObject.isNull("mantissa")){
//			mantissa=fieldJsonObject.getString("mantissa");
//		}else{
//			mantissa="default";
//		}
//		if(fieldJsonObject.has("ed_chk")&&!fieldJsonObject.isNull("ed_chk")){
//			ed_chk=fieldJsonObject.getString("ed_chk");
//		}else{
//			ed_chk="default";
//		}
//		if(fieldJsonObject.has("cd_lst")&&!fieldJsonObject.isNull("cd_lst")){
//			cd_lst=fieldJsonObject.getString("cd_lst");
//		}else{
//			cd_lst="default"; 
//		}
//		if(fieldJsonObject.has("allowpartial")&&!fieldJsonObject.isNull("allowpartial")){
//			allowpartial=fieldJsonObject.getString("allowpartial");
//		}else{
//			allowpartial="default";
//		}
//		if(fieldJsonObject.has("maxlength")&&!fieldJsonObject.isNull("maxlength")){
//			maxlength=fieldJsonObject.getInt("maxlength");
//		}else{
//			maxlength=-1;
//		}
//		if(fieldJsonObject.has("data_type")&&!fieldJsonObject.isNull("data_type")){
//			data_type=fieldJsonObject.getString("data_type");
//		}else{
//			data_type="default";
//		}
//		if(fieldJsonObject.has("ctrl_type")&&!fieldJsonObject.isNull("ctrl_type")){
//			ctrl_type=fieldJsonObject.getString("ctrl_type");
//		}else{
//			ctrl_type="default";
//		}
//		if(fieldJsonObject.has("description")&&!fieldJsonObject.isNull("description")){
//			description=fieldJsonObject.getString("description");
//		}else{
//			description="default"; 
//		}
//		if(fieldJsonObject.has("src")&&!fieldJsonObject.isNull("src")){
//			src=fieldJsonObject.getString("src");
//		}else{
//			 
//			src="default";
//		}
//		if(fieldJsonObject.has("required")&&!fieldJsonObject.isNull("required")){
//			required=fieldJsonObject.getString("required");
//		}else{
//			required="default";
//		}
//		if(fieldJsonObject.has("dt_st_label")&&!fieldJsonObject.isNull("dt_st_label")){
//			dt_st_label=fieldJsonObject.getString("dt_st_label");
//		}else{
//			dt_st_label="default";
//		}
//		
//		
//		EDCMainDetailsTable edcTableData= new EDCMainDetailsTable( minLength,  gui_label,
//				 charecteristics,  visible,  grp_name,
//				 indent,  ctrl_name,  mantissa,  ed_chk,
//				 cd_lst,  allowpartial,  maxlength,
//				 data_type,  ctrl_type,  description,  src,
//				 required,  dt_st_label);
//		foromDataTableList.add(edcTableData);
//	}
//	    
//	} catch (JSONException e) {
//	    Log.e("JSON exception", e.getMessage());
//	    e.printStackTrace();
//	} 
//
//	
//	return foromDataTableList;
//}
//
}
