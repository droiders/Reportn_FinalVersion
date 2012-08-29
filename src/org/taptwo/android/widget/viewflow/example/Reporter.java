package org.taptwo.android.widget.viewflow.example;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;




public class Reporter extends SherlockActivity implements OnClickListener{

	private HomeActivity ha;
	private WebView webView;
	//private ProgressDialog dialogWait;
	public EditText mIncidentTitle;

	public EditText mIncidentLocation;

	public EditText mIncidentDesc;

	public ImageView mSelectedPhoto;

	public EditText mLatitude;

	public EditText mLongitude;

	public EditText mNews;

	public Button mBtnSend;

	public Button mBtnAddCategory;

	public Button mPickTime;

	public Button mPickDate;

	public Button mBtnPicture;
	
	public Button mDeleteReport;

	public Gallery gallery;
	
	private String mDateToSubmit = "";
	
	private static final int REQUEST_CODE_CAMERA = 0;
	
	private static final int REQUEST_CODE_IMAGE = 1;

	private static final int DIALOG_CHOOSE_IMAGE_METHOD = 2;

	private static final int DIALOG_MULTIPLE_CATEGORY = 3;

	private static final int TIME_DIALOG_ID = 4;

	private static final int DATE_DIALOG_ID = 5;
	
	private static final int DIALOG_SEND = 6;

	private String photoName;
	
	private Calendar mCalendar;
	
	private String filename = "";
	 
	private static final String PENDING = "/pending";
	
	protected String[] _options = { "choix 1", "choix 2", "choix 3", "choix 3", "choix 4", "choix 5", "choix 6", "choix 7" };
	
	protected boolean[] _selections =  new boolean[ _options.length ];
	
	private String selection;
	
	private TextView tvDisplayTime;
	private TimePicker timePicker1;
	private int hour;
	private int minute;
	private int year;
	private int month;
	private int day;
	private DatePicker dpResult;
	TextView tvDisplayDate;
	private ImageView imagePreview;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ha = new HomeActivity();
        int lat0=ha.getLat0();
        int lng0=ha.getLng0();
		setContentView(R.layout.add_report);
		
		ActionBar bar = getSupportActionBar();
		getSupportActionBar().setTitle("reportn !");
		getSupportActionBar().setIcon(R.drawable.logo);
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://maps.googleapis.com/maps/api/staticmap?center&markers=color:blue|label:A|"+HomeActivity.lat+","+HomeActivity.lng+"&zoom=20S&size=400x100&maptype=hybrid&sensor=false ");
		 webView.setOnTouchListener(new View.OnTouchListener() {
		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            
		              Intent i = new Intent(getBaseContext(),FixPsition.class);
		              startActivity(i);
		            
		            return false;
		        }
		    });
		
		//afficherListePlages(lat,lng);
		//dialogWait.dismiss();
		mBtnPicture = (Button)findViewById(R.id.btnPicture);
		mBtnAddCategory = (Button)findViewById(R.id.add_category);
		mCalendar = Calendar.getInstance();
		mPickDate = (Button)findViewById(R.id.pick_date);
		mPickTime = (Button)findViewById(R.id.pick_time);
		mDeleteReport = (Button)findViewById(R.id.send);
		mLatitude = (EditText)findViewById(R.id.incident_latitude);
		mLatitude.setText(String.valueOf(HomeActivity.getLat0()/1E6));
		mLongitude = (EditText)findViewById(R.id.incident_longitude);
	    mLongitude.setText(String.valueOf(HomeActivity.getLng0()/1E6));
		gallery = (Gallery)findViewById(R.id.gallery);
		mIncidentTitle = (EditText)findViewById(R.id.incident_title);
		mIncidentLocation = (EditText)findViewById(R.id.incident_location);
		mIncidentLocation.setText(HomeActivity.getText());
		mIncidentDesc = (EditText)findViewById(R.id.incident_desc);
		imagePreview=(ImageView)findViewById(R.id.pictosend);
		mBtnPicture.setOnClickListener(this);
		mBtnAddCategory.setOnClickListener(this);
		mPickDate.setOnClickListener(this);
		mPickTime.setOnClickListener(this);
		mDeleteReport.setOnClickListener(this);
		
	}
	 public boolean onOptionsItemSelected(MenuItem item) {

	        // app icon in action bar clicked; go home

	        Intent intentHome = new Intent(this, HomeActivity.class);

	        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	        startActivity(intentHome);

	        return true;

	        }
	 
	public void onClick(View button) {
		if (button.getId() == R.id.btnPicture) {
			// get a file name for the photo to be uploaded
			photoName =getDateTime() + ".jpg";
			showDialog(DIALOG_CHOOSE_IMAGE_METHOD);

		} else if (button.getId() == R.id.add_category) {
			showDialog(DIALOG_MULTIPLE_CATEGORY);
		} else if (button.getId() == R.id.pick_date) {
			showDialog(DATE_DIALOG_ID);
		} else if (button.getId() == R.id.pick_time) {
			showDialog(TIME_DIALOG_ID);
		} else if (button.getId()== R.id.send) {
			postFileAttachement();
		} 
	}
	
	public static String getDateTime() {
		DateFormat df = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.format(new Date());
	}
	
	public  Uri getPhotoUri(String filename) {
		File path = new File(Environment.getExternalStorageDirectory(),getPackageName() + PENDING);
		if (!path.exists() && path.mkdir()) {
			return Uri.fromFile(new File(path, filename));
		}
		return Uri.fromFile(new File(path, filename));
	}
	
	protected Dialog onCreateDialog(int id) {
		switch (id) {


		case DIALOG_CHOOSE_IMAGE_METHOD: {

			AlertDialog dialog = (new AlertDialog.Builder(this)).create();
			dialog.setTitle("choisir methode");
			dialog.setMessage("");
			dialog.setButton("gallerie",
					new Dialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent i = new Intent(getApplicationContext(), galery.class);
   	            			
							startActivityForResult(i, REQUEST_CODE_IMAGE);
							
						}
					});
			dialog.setButton2("cancel",
					new Dialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			dialog.setButton3("camera",
					new Dialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
							dialog.dismiss();
							Intent launchPreferencesIntent = new Intent().setClass(Reporter.this, CameraView.class);
							// Make it a subactivity so we know when it returns
							startActivityForResult(launchPreferencesIntent, REQUEST_CODE_CAMERA);
						}
					});

			dialog.setCancelable(false);
			return dialog;
		}

		case DIALOG_MULTIPLE_CATEGORY: {
			
				return new AlertDialog.Builder(this).setTitle("choisir une catégorie").setMultiChoiceItems(
								_options,
								_selections,
								new DialogInterface.OnMultiChoiceClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton, boolean isChecked) {
										// see if categories have previously


										setSelectedCategories(selection);
									}
								})
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {

										/* User clicked Yes so do some stuff */
									}
								}).create();
			}
		

		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener,
					mCalendar.get(Calendar.HOUR),
					mCalendar.get(Calendar.MINUTE), false);


		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener,
					mCalendar.get(Calendar.YEAR),
					mCalendar.get(Calendar.MONTH),
					mCalendar.get(Calendar.DAY_OF_MONTH));
			
		
			
			
		}
		return null;
	}

	protected void setSelectedCategories(String s)
	{
	        for( int i = 0; i < _options.length; i++ ){
		          Log.i( "ME", _options[ i ] + " selected: " + _selections[i] );
		          if(_selections[i]==true)
		        	  s=_options[i];
		}
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mCalendar.set(year, monthOfYear, dayOfMonth);
			updateDisplay();
		}
	};

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mCalendar.set(Calendar.MINUTE, minute);
			updateDisplay();
		}
	};
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case TIME_DIALOG_ID:
			((TimePickerDialog) dialog).updateTime(
					mCalendar.get(Calendar.HOUR_OF_DAY),
					mCalendar.get(Calendar.MINUTE));
			break;
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(
					mCalendar.get(Calendar.YEAR),
					mCalendar.get(Calendar.MONTH),
					mCalendar.get(Calendar.DAY_OF_MONTH));
			break;

		}
	}
	private void updateDisplay() {
		Date date = mCalendar.getTime();
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
			mPickDate.setText(dateFormat.format(date));

			SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
			mPickTime.setText(timeFormat.format(date));

			// Because the API doesn't support dates in diff Locale mode, force
			// it to show time in US
			SimpleDateFormat submitFormat = new SimpleDateFormat(
					"yyy-MM-dd kk:mm:ss", Locale.US);
			mDateToSubmit = submitFormat.format(date);
		} else {
			mPickDate.setText(R.string.change_date);
			mPickTime.setText(R.string.change_time);
			mDateToSubmit = null;
		}
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		if (requestCode==REQUEST_CODE_CAMERA)
			//selectedPhoto.setText(BoskoiService.fileName);
			if(ReportnService.fileName != ""){
				//Util.showToast(AddIncident.this, R.string.toast_photos);
				String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
				imagePreview.setImageURI(Uri.parse(extStorageDirectory+"/" + ReportnService.fileName));	
				//uri.parse(ReportnService.savePath + ReportnService.fileName);
				
			}
		

	if(requestCode==REQUEST_CODE_IMAGE){
		 int j=galery.getposition();
		 ImageAdapter imageAdapter = new ImageAdapter(this);
	        Bitmap myBitmap = BitmapFactory.decodeFile(imageAdapter.mlistFiles[j].getAbsolutePath());
			imagePreview.setImageBitmap(myBitmap);
	}
	}
	public void postFileAttachement(){
		 Log.i("RESPONSE","0");
		
		String url = "http://ville.terralego.com/open311/v2/requests.json?api_key=makinacorpus&api_pass=test_mobile_api_key";
		File file = new File(Environment.getExternalStorageDirectory(),
				 ReportnService.fileName);
		try {
			Log.i("RESPONSE","2");
		    HttpClient httpclient = new DefaultHttpClient();

		    HttpPost httppost = new HttpPost(url);
		    httppost.addHeader("Authorization", "Basic "+ "Zml4bXlzdHJlZXQ6NUZ0OUMyQlVZbjRhM3pi");
		    InputStreamEntity reqEntity = new InputStreamEntity(
		            new FileInputStream(file), -1);
		    List<NameValuePair> params = new ArrayList<NameValuePair>();
           params.add(new BasicNameValuePair("title", "alain"));
           
           params.add(new BasicNameValuePair("lat", "100"));
           params.add(new BasicNameValuePair("email", "test@test.tn"));
           params.add(new BasicNameValuePair("lon", "5"));
           params.add(new BasicNameValuePair("service_code", "20"));
           params.add(new BasicNameValuePair("first_name", "alain"));
           params.add(new BasicNameValuePair("last_name", "alain"));
           params.add(new BasicNameValuePair("phone", "0000235"));
           params.add(new BasicNameValuePair("description", "Testing 1 2 3 .."));
           params.add(new BasicNameValuePair("photo", "test"));
		    reqEntity.setContentType("binary/octet-stream");
		  
		    reqEntity.setChunked(true); // Send in multiple parts if needed
		    Log.i("RESPONSE","3");
		 //   httppost.setEntity(reqEntity);
		//    HttpResponse response = httpclient.execute(httppost);
		    //Do something with response...
		    UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
		    httppost.setEntity(ent);
           HttpResponse responsePOST = httpclient.execute(httppost);  
           HttpEntity resEntity = responsePOST.getEntity();  
           if (resEntity != null) {    
               Log.i("RESPONSE",EntityUtils.toString(resEntity));
               Log.i("RESPONSE","5");
           }

		} catch (Exception e) {
		    // show error
			 e.printStackTrace();
		}
		
	}

}


