package cm5.services;

import java.util.Timer;
import java.util.TimerTask;

import cm5.main.PlayActv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Service_ShowProgress extends Service {

	Timer timer;

	int counter;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onBind()");
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onCreate()");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onDestroy()");

		/***************************************
		 * Cancel counting
		 ***************************************/
		timer.cancel();
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "Timer => Cancelled");
		
	}//public void onDestroy()

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		// Log
		Log.d("Service_ShowProgress.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "onStart()");
		
		startCount();

	}//public void onStart(Intent intent, int startId)

	private void startCount() {
		
		final android.os.Handler handler = new android.os.Handler();
		
		counter = 0;
		
		//
		if (timer != null) {
			timer.cancel();
		}//if (timer != null)
		
		timer = new Timer();

		timer.schedule(
				new TimerTask(){

					@Override
					public void run() {
						
//						// Log
//						Log.d("Service_ShowProgress.java"
//								+ "["
//								+ Thread.currentThread().getStackTrace()[2]
//										.getLineNumber()
//								+ ":"
//								+ Thread.currentThread().getStackTrace()[2]
//										.getMethodName() + "]",
//								"counter=" + counter);
						
						if (PlayActv.mp != null) {
							
//							// Log
//							Log.d("Service_ShowProgress.java"
//									+ "["
//									+ Thread.currentThread().getStackTrace()[2]
//											.getLineNumber()
//									+ ":"
//									+ Thread.currentThread().getStackTrace()[2]
//											.getMethodName() + "]",
//									"PlayActv.mp.getCurrentPosition()="
//									+ PlayActv.mp.getCurrentPosition());

//							int currentPosition = PlayActv.mp.getCurrentPosition();
							
							handler.post(new Runnable(){

//								@Override
								public void run() {
									
//									PlayActv.updateProgressLabel(actv);
									new PlayActv().updateProgressLabel();
									
								}//public void run() // Runnable
								
							});//handler.post()
							
							
						}//if (CONS.P == condition)
						
						counter += 1;
						
					}//public void run()
					
				},//new TimerTask()
				0,
				1000
		);//timer.schedule
		
	}//private void startCount()
	

}//public class Service_ShowProgress extends Service
