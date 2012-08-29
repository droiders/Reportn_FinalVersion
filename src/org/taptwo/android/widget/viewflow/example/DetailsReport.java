package org.taptwo.android.widget.viewflow.example;

import org.taptwo.android.widget.viewflow.database0.DatabaseHandler;
import org.taptwo.android.widget.viewflow.database0.Repport;
import org.taptwo.android.widget.viewflow.example.R;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public class DetailsReport extends SherlockActivity {

	TextView title;
	TextView description;
	TextView categorie;
	TextView date;
	TextView location;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_repport);
		getSupportActionBar().setTitle("reportn !");
		getSupportActionBar().setIcon(R.drawable.logo);
		int i = getIntent().getExtras().getInt("id");
		Log.i("allo",""+i);
		title=(TextView)findViewById(R.id.dt_incident_title);
		description=(TextView)findViewById(R.id.dt_incident_desc);
		categorie=(TextView)findViewById(R.id.dt_lbl_date);
		date=(TextView)findViewById(R.id.dt_lbl_date_time);
		location=(TextView)findViewById(R.id.incident_loc);
		DatabaseHandler db =new DatabaseHandler(this);
		Repport rp=db.getRepport(i);
		title.append(rp.getTitle());
		description.append(rp.getDescription());
		categorie.append(" : "+rp.getService_name());
		date.append(" ; "+rp.getRequested_datetime());
		location.append(" : "+rp.getAdresse());
    }

    public  boolean onCreateOptionsMenu(Menu menu) {
        
  	   menu.add(0, 0, 0, "Map").setIcon(R.drawable.maps).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
  	   menu.add(1, 1, 1, "Camera").setIcon(R.drawable.cameranv).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
  	   menu.add(1, 1, 2, "Refraiche").setIcon(R.drawable.refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
  	   menu.add(1, 1, 3, "Search").setIcon(R.drawable.search).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
   	  return true;

  }
  
}
