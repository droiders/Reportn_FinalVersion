package org.taptwo.android.widget.viewflow.database0;

import java.util.ArrayList;
import java.util.List;





import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "reportndb";

	// Repports table name
	private static final String TABLE_REPORT = "report";;

	// Repports Table Columns names
	private static final String TAG_ID = "service_request_id";
	private static final String TAG_STATUS="status";
	private static final String TAG_TITLE="title";
	private static final String TAG_SERVICE_CODE="service_code";
	private static final String TAG_SERVICE_NAME="service_name";
	private static final String TAG_REQUEST_DATE="requested_datetime";
	private static final String TAG_UPDATE="updated_datetime";
	private static final String TAG_LON="lon";
	private static final String TAG_LAT="lat";
	private static final String TAG_ADRESSE="adresse";
	private static final String TAG_DESCRIPTION="description";
	
	private static final String REPORT_TABLE_CREATE="CREATE TABLE " + TABLE_REPORT + " (" +
			TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			TAG_TITLE + " TEXT , " +
		    TAG_STATUS + " TEXT , " +
		    TAG_SERVICE_CODE  + " INTEGER, " +
		    TAG_SERVICE_NAME +  " TEXT, " +
		    TAG_DESCRIPTION +  " TEXT, " +
		    TAG_REQUEST_DATE + " DATETIME, " +
		    TAG_UPDATE + " DATE, " +
		    TAG_LON + " DOUBLE, " +
		    TAG_LAT + " DOUBLE, " +            
		    TAG_ADRESSE +" TEXT " + ")";
	
	 public List<Repport> getAllRepports() {
			List<Repport> RepportList = new ArrayList<Repport>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_REPORT;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					Repport repport = new Repport();
					
					repport.setTitle(cursor.getString(1));
					repport.setStatus(cursor.getString(2));
					repport.setService_code(Integer.parseInt(cursor.getString(3)));
					repport.setService_name(cursor.getString(4));
					repport.setDescription(cursor.getString(5));
					repport.setRequested_datetime(cursor.getString(6));
					repport.setUpdated_datetime(cursor.getString(7));
					repport.setLat((float)cursor.getDouble(8));
					repport.setLon((float)cursor.getDouble(9));
					repport.setAdresse(cursor.getString(10));
					// Adding Repport to list
					RepportList.add(repport);
				} while (cursor.moveToNext());
			}
			//return Repport list
			db.close();
					return RepportList;
	 }
	 public DatabaseHandler(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

 public void onCreate(SQLiteDatabase db) {
	      Log.i("00","51");
	        db.execSQL(REPORT_TABLE_CREATE);
	        Log.i("00","51");
	    }
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // Drop older table if existed
	        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
	 
	        // Create tables again
	        onCreate(db);
	    }
	 public void insertion(Repport rp){
		 SQLiteDatabase db = this.getWritableDatabase();
		 ContentValues values=new ContentValues();
		 values.put(TAG_TITLE, rp.getTitle());
		 values.put(TAG_STATUS,rp.getStatus());
		 values.put(TAG_SERVICE_CODE,rp.getService_code());
		 values.put(TAG_SERVICE_NAME,rp.getService_name());
		 values.put(TAG_DESCRIPTION, rp.getDescription());
		 values.put(TAG_REQUEST_DATE, rp.getUpdated_datetime());
		 values.put(TAG_UPDATE, rp.getUpdated_datetime());
		 values.put(TAG_LAT,rp.getLat());
		 values.put(TAG_LON,rp.getLon());
		 values.put(TAG_ADRESSE, rp.getAdresse());
		 db.insert(TABLE_REPORT, null, values);
		 db.close();
	 }
	 


	
	 
		public void deleteRepport(Repport Repport) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			db.delete(TABLE_REPORT, " service_request_id " + " = ?",
					new String[] { String.valueOf(Repport.getService_request_id()) });
			db.close();
		}
		public int getRepportsCount() {
			String countQuery = "SELECT  * FROM " + TABLE_REPORT;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			cursor.close();

			// return count
			return cursor.getCount();
		}

		public Repport getRepport(int id) {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.query(TABLE_REPORT, new String[] {TAG_ID,TAG_TITLE, TAG_STATUS , TAG_SERVICE_CODE , TAG_SERVICE_NAME , TAG_DESCRIPTION ,   TAG_REQUEST_DATE , TAG_UPDATE , TAG_LAT , TAG_LON ,  TAG_ADRESSE }, TAG_ID+ "=?",
					new String[] { String.valueOf(id) }, null, null, null, null);
			if (cursor != null)
				cursor.moveToFirst();

			Repport repport = new Repport();
			repport.setTitle(cursor.getString(2));
			repport.setStatus(cursor.getString(3));
			repport.setService_code(Integer.parseInt(cursor.getString(4)));
			repport.setService_name(cursor.getString(5));
			repport.setDescription(cursor.getString(6));
			repport.setRequested_datetime(cursor.getString(7));
			repport.setUpdated_datetime(cursor.getString(8));
			repport.setLat((float)cursor.getDouble(9));
			repport.setLon((float)cursor.getDouble(10));
			repport.setAdresse(cursor.getString(11));
			// return contact
			return repport;

		}
		public void droprepots(){
			
			SQLiteDatabase db = this.getReadableDatabase();
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
			 onCreate(db);
		}
}
