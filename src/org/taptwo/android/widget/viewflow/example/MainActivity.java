package org.taptwo.android.widget.viewflow.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.viewflow.database0.DatabaseHandler;
import org.taptwo.android.widget.viewflow.database0.Repport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;



public class MainActivity extends SherlockActivity {
	
	private ViewFlow viewFlow;
	Button button;
	ViewPager mViewPager;
	
	List<Repport> RepportList = new ArrayList<Repport>();

	private static final String TAG_NAME = "name";
	private static final String TAG_DESCRIPTION = "email";
	private static final String TAG_NAME1 = "name";
	private static final String TAG_DESCRIPTION1 = "email";
	private static final String TAG_ID = "id";

	// repports JSONArray
	JSONObject repports = null;
	
	ArrayList<HashMap<String, String>> contactList;

	ArrayList<HashMap<String, String>> listreppport;
	JSONParser jParser;
	
	JSONArray json;
	
	private ListView listView;
	private ListView listView2;
	DatabaseHandler db ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.title_layout);
		
		db= new DatabaseHandler(this);
		
		ActionBar bar = getSupportActionBar();

		
		getSupportActionBar().setTitle("reportn !");
		getSupportActionBar().setIcon(R.drawable.logo);
	
		
		
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        DiffAdapter adapter = new DiffAdapter(this);
        viewFlow.setAdapter(adapter);
		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		viewFlow.setFlowIndicator(indicator);
		insertitem();

	}

		private void insertitem(){
			 Location locationA = new Location("point A");

			  locationA.setLatitude(HomeActivity.lat);
			  locationA.setLongitude(HomeActivity.lng);
			listView=(ListView)findViewById(R.id.listviewperso);
			 listView2=(ListView)findViewById(R.id.listView1);
			DatabaseHandler db =new DatabaseHandler(this);
			RepportList=db.getAllRepports();
			listreppport= new ArrayList<HashMap<String, String>>();
			contactList = new ArrayList<HashMap<String, String>>();
			int j=0;
			
			while (j <RepportList.size()) {
				Log.i("000", ""+j);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(TAG_ID,String.valueOf(RepportList.get(j).getService_request_id()));
				map.put(TAG_NAME, RepportList.get(j).getAdresse());
				map.put(TAG_DESCRIPTION,RepportList.get(j).getDescription().toString());
				
				Location locationB = new Location("point B");

				  locationB.setLatitude((double)RepportList.get(j).getLat());
				  locationB.setLongitude((double)RepportList.get(j).getLon());
				  double distance = locationA.distanceTo(locationB);
				  Log.i("distance", ""+distance);
				  if (distance < 50000){
					  HashMap<String, String> map2 = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
					//	map.put(TAG_ID, id);
					    map2.put(TAG_ID,String.valueOf(RepportList.get(j).getService_request_id()));
						map2.put(TAG_NAME1, RepportList.get(j).getAdresse());
						map2.put(TAG_DESCRIPTION1, RepportList.get(j).getDescription().toString());
						contactList.add(map2);
				  }
				  listreppport.add(map);
				j++;
				
			}
				ListAdapter madapter = new SimpleAdapter(this, listreppport,R.layout.list_item,new String[] { TAG_NAME, TAG_DESCRIPTION }, new int[] {
						R.id.title, R.id.email_label });

				listView.setAdapter(madapter);	
				ListAdapter madapter2 = new SimpleAdapter(this, contactList,R.layout.list_item,new String[] { TAG_NAME1, TAG_DESCRIPTION1}, new int[] {
						R.id.title, R.id.email_label });

				listView2.setAdapter(madapter2);	
				//repports = null;
				
			
		

	       listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
	        	@SuppressWarnings("unchecked")
	         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
					//on récupère la HashMap contenant les infos de notre item (titre, description, img)
	        		HashMap<String, String> map0 = (HashMap<String, String>) listView.getItemAtPosition(position);
	        		Log.i("sss",map0.toString());
	        		int i = Integer.valueOf(map0.get("id"));
	        		Intent intent = new Intent(MainActivity.this, DetailsReport.class);
	        		intent.putExtra("id",i);
	        		startActivity(intent);
	        		
	        	
	        	}
	         });
	       listView2.setOnItemClickListener(new OnItemClickListener() {
				@Override
	        	@SuppressWarnings("unchecked")
	         	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
					//on récupère la HashMap contenant les infos de notre item (titre, description, img)
	        		HashMap<String, String> map0 = (HashMap<String, String>) listView2.getItemAtPosition(position);
	        		int i = Integer.valueOf(map0.get("id"));
	        		Intent intent = new Intent(MainActivity.this, DetailsReport.class);
	        		intent.putExtra("id",i);
	        		startActivity(intent);
	        	
	        	}
	         });
		}
	 
	 
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		viewFlow.onConfigurationChanged(newConfig);
	}
	
    public  boolean onCreateOptionsMenu(Menu menu) {
        
 	   menu.add(0, 0, 0, "Refraiche").setIcon(R.drawable.refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
 	   menu.add(1, 1, 1, "Map").setIcon(R.drawable.maps).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
 	   menu.add(1, 1, 2, "Plus").setIcon(R.drawable.plus).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
  	  
 	  /* SubMenu sub = menu.addSubMenu("Catgéorie");
         sub.add(0, 1, 0, "Accident");
         sub.add(0, 2, 0, "Violence");
         sub.add(0, 3, 0, "Pollution");
         sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    */
     return true;

 }
    public boolean onOptionsItemSelected(MenuItem item) {
        String string=(String) item.getTitle();
        if(string.equals("Map")){
            //Toast.makeText(this, "Map", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(),ShowMap.class); 
            startActivity(i);
        }
        if(string.equals("Plus")){
            //Toast.makeText(this, "Camera", Toast.LENGTH_LONG).show();
        	Intent i = new Intent(getApplicationContext(),Reporter.class); 
            startActivity(i);
        }
        if(string.equals("Refrech")){
            //Toast.makeText(this, "Refrech", Toast.LENGTH_LONG).show();
        }

        return true;
    } 
    public static class TabsAdapter extends FragmentPagerAdapter implements
	ActionBar.TabListener, ViewPager.OnPageChangeListener
{
private final Context mContext;
private final ActionBar mActionBar;
private final ViewPager mViewPager;
private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

static final class TabInfo
{
	private final Class<?> clss;
	private final Bundle args;

	TabInfo(Class<?> _class, Bundle _args)
	{
		clss = _class;
		args = _args;
	}
}

public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager)
{
	super(activity.getSupportFragmentManager());
	mContext = activity;
	mActionBar = activity.getSupportActionBar();
	mViewPager = pager;
	mViewPager.setAdapter(this);
	mViewPager.setOnPageChangeListener(this);

}

public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args)
{
	TabInfo info = new TabInfo(clss, args);
	tab.setTag(info);
	tab.setTabListener(this);
	mTabs.add(info);
	mActionBar.addTab(tab);
	notifyDataSetChanged();
}

@Override
public int getCount()
{
	return mTabs.size();
}

@Override
public Fragment getItem(int position)
{
	TabInfo info = mTabs.get(position);
	return Fragment.instantiate(mContext, info.clss.getName(),
			info.args);
}

public void onPageScrolled(int position, float positionOffset,
		int positionOffsetPixels)
{
}

public void onPageSelected(int position)
{
	mActionBar.setSelectedNavigationItem(position);
}

public void onPageScrollStateChanged(int state)
{
}

public void onTabSelected(Tab tab, FragmentTransaction ft)
{
	Object tag = tab.getTag();
	for (int i = 0; i < mTabs.size(); i++)
	{
		if (mTabs.get(i) == tag)
		{
			mViewPager.setCurrentItem(i);
		}
	}
}

public void onTabUnselected(Tab tab, FragmentTransaction ft)
{
}

public void onTabReselected(Tab tab, FragmentTransaction ft)
{
}
}
}
	
