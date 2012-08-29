package org.taptwo.android.widget.viewflow.example;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.taptwo.android.widget.viewflow.database0.DatabaseHandler;
import org.taptwo.android.widget.viewflow.database0.Repport;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class HomeActivity extends DashBoardActivity implements LocationListener {
    /** Called when the activity is first created. */
	Location loc;
	static double lat = 0;
    static double lng = 0;
	static int lat0;
	static int lng0;
	static String text;
	private LocationManager lm = null;
	DatabaseHandler db ;
	private ListView listView;
	JSONObject repports = null;
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	ArrayList<HashMap<String, String>> contactList;
	JSONParser jParser;
	JSONArray json;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dash);
		db= new DatabaseHandler(this);
		if (isOnline()){
			db.droprepots();
			 contactList = new ArrayList<HashMap<String, String>>();
			 jParser = new JSONParser();
			 json = jParser.getJSONFromUrl();
			 lastest();
	
			} 
		    lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
	    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
	    	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0,
	    		this);
	 //       setHeader(getString(R.string.HomeActivityTitle), false, true);
	       
	        lat0=(int) (lat*1E6);
	        lng0=(int) (lng*1E6);
	
		 final Button button_rapports = (Button) findViewById(R.id.btn_rapports);
		 button_rapports.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 
            	/* Toast.makeText(getApplicationContext(), "rapports", Toast.LENGTH_SHORT).show();
             */
            	 Intent i = new Intent(getApplicationContext(),MainActivity.class); 
                 startActivity(i);	
             }
         });
         
         final Button button_reglages = (Button) findViewById(R.id.btn_reglages);
         button_reglages.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 
            	 Intent i = new Intent(getApplicationContext(),MainActivity.class); 
                 startActivity(i);	
             }
         });
         final Button button_note = (Button) findViewById(R.id.btn_note);
         button_note.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 
            	 Intent i = new Intent(getApplicationContext(),Reporter.class); 
                 startActivity(i);	
             }
         });
         
         final Button button_contact = (Button) findViewById(R.id.btn_contact);
         button_contact.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 
            	 Toast.makeText(getApplicationContext(), "contact", Toast.LENGTH_SHORT).show();
             }
         });
	}
	@Override
	public void onLocationChanged(Location location) {
		lat = location.getLatitude();
		lng = location.getLongitude();
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	  public static int getLat0() {
			return lat0;
		}

		public static void setLat0(int lat0) {
			HomeActivity.lat0 = lat0;
		}

		public static int getLng0() {
			return lng0;
		}

		public static void setLng0(int lng0) {
			HomeActivity.lng0 = lng0;
		}

		public static String getText() {
			return text;
		}

		public static void setText(String text) {
			HomeActivity.text = text;
		}
	private Handler handler = new Handler() {

	  	  public void handleMessage(android.os.Message msg) {

	  	  
	  	   if(msg.what == 1) {

	  	    Geocoder geoCoder1 = new Geocoder(
	  	      getBaseContext(), Locale.getDefault());
	  	    try {

	  	     GeoPoint p1 = new GeoPoint(lat0,lng0);
	  	     List<Address> addresses1 = geoCoder1.getFromLocation(
	  	       p1.getLatitudeE6()  / 1E6, 
	  	       p1.getLongitudeE6() / 1E6, 1);


	  	     String AdresseLocation;
				if (addresses1.size() > 0) 
	  	     {
	  	      AdresseLocation="";
	  	      for (int i=0; i<3; 
	  	        i++){

	  	       AdresseLocation += addresses1.get(0).getAddressLine(i) + "  ";
	  	      }
	  	     }else{AdresseLocation="Adresse Actuelle inconnue";}

				text=AdresseLocation;
	  	  
	  	    }
	  	    catch (IOException e) {  

	  	     e.printStackTrace();
	  	    } 


	  	   }

	  	  };

	  	 };
	  	 
	  	 
	  	public boolean isOnline() {
	  	    ConnectivityManager cm =
	  	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	  	    return cm.getActiveNetworkInfo() != null && 
	  	       cm.getActiveNetworkInfo().isConnectedOrConnecting();
	  	}
	  	 public void lastest(){
		    	
		    	listView=(ListView)findViewById(R.id.listviewperso);
		    	
				
				Log.i("GET RESPONSEccc",json.toString());
				try {
					
					Log.i("GET RESPONSE","3");
					
					for(int i=0;i<json.length();i++){
						Log.i("00","0");
						repports = json.getJSONObject(i);
					Log.i("GET RESPONSE000000",""+i);
					// looping through All 
					   Repport rp = new Repport();
						// Storing each json item in variable
						//int id = repports.getInt("service_request_id");
						rp.setTitle(repports.getString("title"));
						rp.setAdresse(repports.getString("address"));
						rp.setRequested_datetime(repports.getString("requested_datetime"));
						rp.setUpdated_datetime(repports.getString("updated_datetime"));
						rp.setDescription(repports.getString("description"));
						rp.setAdresse(repports.getString("address"));
						rp.setService_code(Integer.parseInt(repports.getString("service_code")));
						rp.setLat((float)repports.getDouble("lon"));
						rp.setLon((float)repports.getDouble("lat"));
						rp.setService_code(Integer.parseInt(repports.getString("service_code")));
						db.insertion(rp);
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
					//map.put(TAG_ID, );
					map.put(TAG_NAME, repports.getString("address"));
					map.put(TAG_EMAIL,repports.getString("description"));
					//map.put(TAG_PHONE_MOBILE, mobile);

						// adding HashList to ArrayList
						contactList.add(map);}
				//	}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
		    	
		    }
}
