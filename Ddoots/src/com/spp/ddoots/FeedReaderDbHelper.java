package com.spp.ddoots;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DD.db";

    public static final String RESTAURANTS_TABLE_NAME = "RESTAURANTS";
    public static final String RESTAURANT_ID = "ID";
    public static final String RESTAURANT_LOGO = "LOGO";
    public static final String RESTAURANT_LOCATION  = "LOCATION";
    public static final String RESTAURANT_NAME  = "NAME";
    public static final String RESTAURANT_CAT_ID_FK = "CATID_FK";

    
    public static final String RESTAURANT_CAT_TABLE_NAME = "RESTAURANTS_CAT";
    public static final String RESTAURANT_CAT_ID = "ID";
    public static final String RESTAURANT_CATNAME = "CAT_NAME";

 
    
    private static final String SQL_CREATE_RESTAURANTS = "CREATE TABLE RESTAURANTS (ID INTEGER,LOGO TEXT,LOCATION TEXT,NAME TEXT,CATID_FK INTEGER)";
    private static final String SQL_CREATE_RESTAURANTS_CAT = "CREATE TABLE RESTAURANTS_CAT (ID INTEGER,CAT_NAME TEXT)";


    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + RESTAURANT_CAT_TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES3 =
            "DROP TABLE IF EXISTS " + RESTAURANTS_TABLE_NAME;
    

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
    	
    	db.execSQL(SQL_DELETE_ENTRIES);
    	db.execSQL(SQL_DELETE_ENTRIES3);

        db.execSQL(SQL_CREATE_RESTAURANTS);
        db.execSQL(SQL_CREATE_RESTAURANTS_CAT);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
