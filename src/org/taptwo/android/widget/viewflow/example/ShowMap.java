package org.taptwo.android.widget.viewflow.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.taptwo.android.widget.viewflow.example.R;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;



public class ShowMap extends SherlockFragmentActivity {
	
	private MapFragment mMapFragment;
	private List<Overlay> mapOverlays;
	private Drawable defaultItemDrawable;
	HomeActivity ha;
	GeoPoint pt;
	private MapController mc = null;
	MyLocationOverlay myLocation = null;
	private MyItemizedOverlay itemizedOverlay;
	private static final int REQUEST_CODE_CAMERA = 5;
	private static List<MapDataItem> sampleData = new ArrayList<MapDataItem>();
	private ImageView imagePreview;
	
	static {
		sampleData.add(new MapDataItem(24.003990, 49.837071, "http://www.brassbandwiki.com/images/0/07/Facebook_logo.png"));
		sampleData.add(new MapDataItem(36.173357,-85.869141, "http://images3.wikia.nocookie.net/__cb20091210181630/borderlands/images/thumb/6/66/Firefox_logo.png/50px-Firefox_logo.png"));
		sampleData.add(new MapDataItem(38.822591,-25.664062, "http://kubuntulove.files.wordpress.com/2008/02/amarok_logo.png"));
		sampleData.add(new MapDataItem(40.979898,-2.109375, "http://upload.wikimedia.org/wiktionary/en/thumb/6/63/Wikipedia-logo.png/50px-Wikipedia-logo.png"));
		sampleData.add(new MapDataItem(43.834527,21.09375, "http://images1.wikia.nocookie.net/__cb20110607090346/sims/images/thumb/d/d4/The_Sims_2_Open_for_Business_Logo.png/50px-The_Sims_2_Open_for_Business_Logo.png"));
		sampleData.add(new MapDataItem(46.316584,55.898438, "http://www.wikilectures.eu/images/6/63/Physiatrics_logo.png"));
		sampleData.add(new MapDataItem(49.15297,85.078125, "http://th499.photobucket.com/albums/rr360/gemmavgraham82/th_linkedin-logo.png"));
		sampleData.add(new MapDataItem(50.064192,117.421875, "https://scm.mni.thm.de/redmine/projects/cas-central-authentication-system/repository/revisions/57ec2c85712e0c163f07d46750ed97b3c409b87a/entry/fhgifb-cas-server-webapp/src/main/webapp/images/client/hudson-logo.png"));
		sampleData.add(new MapDataItem(-19.311143,130.78125, "http://images4.wikia.nocookie.net/__cb20110902215535/fallout/images/archive/2/23/20110902215722!GIMP_Logo.png"));
	}
	// We use this fragment as a pointer to the visible one, so we can hide it easily.
	private Fragment mVisible = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        getSupportActionBar().setTitle("reportn !");
		getSupportActionBar().setIcon(R.drawable.logo);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setNavigationMode(ActionBar.DISPLAY_SHOW_CUSTOM);
        setContentView(R.layout.showmap);
        Exchanger.mMapView = new MapView(this, "0txfSdKgq1jyXITrT4qEfW_cUOttal92XYm4zBA"); // TODO: Replace for API Key!
        
        setupFragments();
        showFragment(mMapFragment);
        
    }

	private void setupFragments() {
		final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		// If the activity is killed while in BG, it's possible that the
		// fragment still remains in the FragmentManager, so, we don't need to
		// add it again.
		mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(MapFragment.TAG);
        if (mMapFragment == null) {
        	mMapFragment = new MapFragment();
        	ft.add(R.id.fragment_container, mMapFragment, MapFragment.TAG);
        }
        ft.hide(mMapFragment);
        
        
        
        ft.commit();
	}

	private void showFragment(Fragment fragmentIn) {
		if (fragmentIn == null) return;
		
		final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		
		if (mVisible != null) ft.hide(mVisible);
		
		ft.show(fragmentIn).commit();
		mVisible = fragmentIn;
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	   
    	getSupportMenuInflater().inflate(R.menu.activity_main, menu);
    	
    	        return true;

	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
			    case R.id.camer:
			    	Intent i = new Intent(getBaseContext(),Reporter.class);
			    	startActivity(i);
				return true;
			    case R.id.submenu1:
			    return true;
			    case R.id.submenu2:
			        return true;
			    case R.id.submenu3:
			        return true;
    		}

            return super.onOptionsItemSelected(item);

        }
 private void setAlert(){
	 AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShowMap.this);

     // Setting Dialog Title
     alertDialog.setTitle("vous devez joindre une photo");

     // Setting Dialog Message
     alertDialog.setMessage("allez à l'emplacement de la photo");

     // Setting Icon to Dialog
//              alertDialog.setIcon(R.drawable.gd_action_bar_slideshow);

     // Setting Positive "Yes" Button
     alertDialog.setPositiveButton("camera", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int which) {
        	 Intent launchPreferencesIntent = new Intent().setClass(getBaseContext(), CameraView.class);
				// Make it a subactivity so we know when it returns
				startActivityForResult(launchPreferencesIntent, REQUEST_CODE_CAMERA);
         }
});
  // Setting Negative "NO" Button
        alertDialog.setNegativeButton("galerie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	//Intent i = new Intent(getApplicationContext(), galery.class);
    			//startActivity(i);// User pressed No button. Write Logic Here
            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Netural "Cancel" Button
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // User pressed Cancel button. Write Logic Here
            Toast.makeText(getApplicationContext(), "You clicked on Cancel",
                                Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
 }
	
	public static class Exchanger {
		// We will use this MapView always.
    	public static MapView mMapView;
    }

	public static class MapFragment extends SherlockFragment {
		
		public static final String TAG = "mapFragment";
		
	
		public MapFragment() {}
		
		@Override
		public void onCreate(Bundle arg0) {
			super.onCreate(arg0);
			setRetainInstance(true);
			
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
			// The Activity created the MapView for us, so we can do some init stuff.
			Exchanger.mMapView.setClickable(true);
			Exchanger.mMapView.setBuiltInZoomControls(true); // If you want.
			
			
	    	
		    
			return Exchanger.mMapView;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		ha=new HomeActivity();
        pt=new GeoPoint((int)(HomeActivity.lat*1E6), (int) (HomeActivity.lng*1E6));
        mc = Exchanger.mMapView.getController();
    	mc.setZoom(17);
    	mc.animateTo(pt);
	    mc.setCenter(pt);
	    
    	mapOverlays = Exchanger.mMapView.getOverlays();
		mapOverlays.clear();
		Exchanger.mMapView.postInvalidate();
		Exchanger.mMapView.removeAllViews();
		defaultItemDrawable = this.getResources().getDrawable(R.drawable.default_marker);
		defaultItemDrawable.setBounds(-defaultItemDrawable.getIntrinsicWidth() / 2, -defaultItemDrawable.getIntrinsicHeight(), defaultItemDrawable.getIntrinsicWidth() / 2, 0);
		itemizedOverlay = new MyItemizedOverlay(defaultItemDrawable);
		
		for (int i = 0; i < sampleData.size(); i++) {
			int latitude = (int) (sampleData.get(i).getLatitude() * 1E6);
			int longitude = (int) (sampleData.get(i).getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(latitude, longitude);
			OverlayItem overlayitem = new OverlayItem(point, "Title " + i, "Snippet " + i);
			itemizedOverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedOverlay);
			MapOverlayItemMarkerAsyncTask task = new MapOverlayItemMarkerAsyncTask(overlayitem,Exchanger.mMapView);
			task.execute(sampleData.get(i).getMarkerStringURL());
	}
		myLocation = new MyLocationOverlay(getApplicationContext(), Exchanger.mMapView);
    	myLocation=new MyLocationOverlay(getApplicationContext(), Exchanger.mMapView);
	    Exchanger.mMapView.getOverlays().add(myLocation);
	    myLocation.enableMyLocation();
	    
	    
	    
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// The preferences returned if the request code is what we had given
		// earlier in startSubActivity
		switch(requestCode){
			case REQUEST_CODE_CAMERA:
				//selectedPhoto.setText(ReportnService.fileName);
				if(ReportnService.fileName != ""){
					//Util.showToast(AddIncident.this, R.string.toast_photos);
					
					//imagePreview.setMaxWidth(50);
					//imagePreview.setMaxHeight(50);
					imagePreview.setImageURI(Uri.parse(ReportnService.savePath + ReportnService.fileName));				
				}
				break;
		}
	}
}

