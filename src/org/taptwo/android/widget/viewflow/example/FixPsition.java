package org.taptwo.android.widget.viewflow.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class FixPsition extends MapActivity implements OnClickListener{

	 private MapView mapView = null;
	 static GeoPoint pt;
	 private ImageButton place;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_position);
        
        mapView = (MapView) this.findViewById(R.id.map);
    	//mapView.setStreetView(true);
    	mapView.setBuiltInZoomControls(true);
    	//ActionBar bar=getActionBar();
    	//bar.setTitle("aymen");
       Drawable marker=getResources().getDrawable(R.drawable.bubble);
	    
	    marker.setBounds(0, 0, marker.getIntrinsicWidth(),
	                            marker.getIntrinsicHeight());
	    
	    mapView.getOverlays().add(new SitesOverlay(marker));
	    final ImageButton Ibutton2 = (ImageButton) findViewById(R.id.fd_pip);  
	    Ibutton2.setOnClickListener(this); 
    }

/*	 public void addListenerOnButton() {
		 
		place = (ImageButton) findViewById(R.id.fd_pip);
 
		place.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				Intent i = new Intent(FixPsition.this,Reporter.class);
				startActivity(i);
 
			}
 
		});

	}
	*/
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private GeoPoint getPoint(double lat, double lon) {
	    return(new GeoPoint((int)(lat*1000000.0),
	                          (int)(lon*1000000.0)));
	}
	 private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
		    private List<OverlayItem> items=new ArrayList<OverlayItem>();
		    private Drawable marker=null;
		    private OverlayItem inDrag=null;
		    private ImageView dragImage=null;
		    private int xDragImageOffset=0;
		    private int yDragImageOffset=0;
		    private int xDragTouchOffset=0;
		    private int yDragTouchOffset=0;
		    boolean first;
		    public SitesOverlay(Drawable marker) {
		      
		    	super(marker);
		      this.marker=marker;
		      first=true;
		      
		      dragImage=(ImageView)findViewById(R.id.drag);
		      xDragImageOffset=dragImage.getDrawable().getIntrinsicWidth()/2;
		      yDragImageOffset=dragImage.getDrawable().getIntrinsicHeight();
		      
		/*      items.add(new OverlayItem(getPoint(40.748963847316034,
		                                          -73.96807193756104),
		                                "UN", "United Nations"));
		      items.add(new OverlayItem(getPoint(40.76866299974387,
		                                          -73.98268461227417),
		                                "Lincoln Center",
		                                "Home of Jazz at Lincoln Center"));*/
		     

		      
		    

		     // populate();
		    }
		    
		    @Override
		    protected OverlayItem createItem(int i) {
		      return(items.get(i));
		    }
		    
		    @Override
		    public void draw(Canvas canvas, MapView mapView,
		                      boolean shadow) {
		      super.draw(canvas, mapView, shadow);
		      
		      boundCenterBottom(marker);
		    }
		    
		    @Override
		    public int size() {
		      return(items.size());
		    }
		    
		    @Override
		    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		      final int action=event.getAction();
		      final int x=(int)event.getX();
		      final int y=(int)event.getY();
		      boolean result=false;
		      
		      
		      if (action==MotionEvent.ACTION_DOWN) {
		    	  
		        for (OverlayItem item : items) {
		          Point p=new Point(0,0);
		          
		          mapView.getProjection().toPixels(item.getPoint(), p);
		          
		          if (hitTest(item, marker, x-p.x, y-p.y)) {
		            result=true;
		            inDrag=item;
		            items.remove(inDrag);
		            populate();

		            xDragTouchOffset=0;
		            yDragTouchOffset=0;
		            
		            setDragImagePosition(p.x, p.y);
		            dragImage.setVisibility(View.VISIBLE);

		            xDragTouchOffset=x-p.x;
		            yDragTouchOffset=y-p.y;
		            
		            break;
		          }
		        }
		      }
		      else if (action==MotionEvent.ACTION_MOVE && inDrag!=null) {
		        setDragImagePosition(x, y);
		        result=true;
		      }
		      else if (action==MotionEvent.ACTION_UP && inDrag!=null) {
		        dragImage.setVisibility(View.GONE);
		        
		        pt=mapView.getProjection().fromPixels(x-xDragTouchOffset,
		                                                   y-yDragTouchOffset);
		        OverlayItem toDrop=new OverlayItem(pt, inDrag.getTitle(),
		                                           inDrag.getSnippet());
		        
		        
		        Geocoder geoCoder = new Geocoder(
	                    getBaseContext(), Locale.getDefault());
	                try {
	                    List<Address> addresses = geoCoder.getFromLocation(
	                        pt.getLatitudeE6()  / 1E6, 
	                        pt.getLongitudeE6() / 1E6, 1);
	 
	                    String add = "";
	                    if (addresses.size() > 0) 
	                    {
	                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
	                             i++)
	                           add += addresses.get(0).getAddressLine(i) + "\n";
	                    }
	                    
	                    HomeActivity.setText(add);
	                    HomeActivity.lat=pt.getLatitudeE6();
	                    HomeActivity.lng=pt.getLongitudeE6();
	                
	                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
	                }
	                catch (IOException e) {                
	                    e.printStackTrace();
	                }   
		      
	                    /*Toast.makeText(getBaseContext(), 
	                        pt.getLatitudeE6() / 1E6 + "," + 
	                        pt.getLongitudeE6() /1E6 , 
	                        Toast.LENGTH_SHORT).show();*/
		        
		        items.add(toDrop);
		        populate();
		        
		        inDrag=null;
		        result=true;
		      }
		          		      
		      if(first==true){
				  items.add(new OverlayItem(getPoint(HomeActivity.lat,HomeActivity.lng),"laaaaaac", "emchii")); 
				  populate();
				  first=false;
				  
			    		      }
		          		      
		      return(result || super.onTouchEvent(event, mapView));
		    }
		    
		    
		    
		    
		    private void setDragImagePosition(int x, int y) {
		      RelativeLayout.LayoutParams lp=
		        (RelativeLayout.LayoutParams)dragImage.getLayoutParams();
		            
		      lp.setMargins(x-xDragImageOffset-xDragTouchOffset,
		                      y-yDragImageOffset-yDragTouchOffset, 0, 0);
		      dragImage.setLayoutParams(lp);
		    }
		  }
	@Override
	public void onClick(View v) {
		Intent i = new Intent (FixPsition.this,Reporter.class);
		startActivity(i);
		
	}
	

	

	 

}
