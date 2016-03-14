package com.mobileapps.pictureonmap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	//Table name
	public static final String TABLE_PICTURES = "pictures";
	
	//Table columns
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SNIPPET = "snippet";
	public static final String COLUMN_LAT = "latitude";
	public static final String COLUMN_LONG = "longitude";
	
	//Database name
	private static final String DATABASE_NAME = "pictures.db";
	
	//Database version, 
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "+TABLE_PICTURES+"("+COLUMN_ID+" integer primary key autoincrement, "+COLUMN_SNIPPET+" text not null, "+COLUMN_LAT+" text not null, "+COLUMN_LONG+" text not null)";
	
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	//Will be called when there is a database version mismatch
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTURES);
		onCreate(db);
	}

}