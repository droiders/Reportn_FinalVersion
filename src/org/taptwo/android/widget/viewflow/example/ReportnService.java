package org.taptwo.android.widget.viewflow.example;

/** 
 ** Copyright (c) 2010 Reportn
 ** All rights reserved
 ** Contact: developer@Reportn.org
 ** Developers: Joey van der Bie, Maarten van der Mark and Vincent Vijn
 ** Website: http://www.Reportn.org
 ** 
 ** GNU Lesser General Public License Usage
 ** This file may be used under the terms of the GNU Lesser
 ** General Public License version 3 as published by the Free Software
 ** Foundation and appearing in the file LICENSE.LGPL included in the
 ** packaging of this file. Please review the following information to
 ** ensure the GNU Lesser General Public License version 3 requirements
 ** will be met: http://www.gnu.org/licenses/lgpl.html.	
 **	
 **
 ** If you have questions regarding the use of this file, please contact
 ** Reportn developers at developer@Reportn.org.
 ** 
 **/


import java.util.Timer;
import java.util.TimerTask;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;



public class ReportnService extends Service {
	public static String savePath = "/data/data/com.android.orange/files/";
	public static GoogleAnalyticsTracker tracker;
	public static String fileName = "";
	private Timer timer ; 
	 
	public IBinder onBind(Intent arg0) {
        return null;
  }
  @Override
  public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Service created ...", Toast.LENGTH_LONG).show();
  }
  
  @Override
  public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service destroyed ...", Toast.LENGTH_LONG).show();
  }
  public static void trackPageView(Context context, String page) {
		if (tracker == null) {
			startTracker(context);
		}

		try {
			tracker.trackPageView(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void startTracker(Context context) {
		// Start the tracker in manual dispatch mode...
		// tracker.start("UA-18696504-1", this);
		// ...alternatively, the tracker can be started with a dispatch interval
		// (in seconds).
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.start("UA-18696504-1", 300, context);
	}
}


