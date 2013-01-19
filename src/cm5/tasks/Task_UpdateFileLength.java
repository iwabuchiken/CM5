package cm5.tasks;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cm5.items.AI;
import cm5.utils.CONS;
import cm5.utils.DBUtils;
import cm5.utils.Methods;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Task_UpdateFileLength extends AsyncTask<String, Integer, String> {

	Activity actv;
	
	List<AI> ai_list;
	
	public Task_UpdateFileLength(Activity actv) {
		
		this.actv = actv;
		
	}//public RefreshDBTask(Activity actv)
	
	@Override
	protected String doInBackground(String... arg0) {
		/*********************************
		 * 01
		 *********************************/
		ai_list = Methods.get_all_data_ai(actv, CONS.tname_main);
		
		Methods.sort_list_ai_created_at(ai_list, CONS.SORT_ORDER.DEC);
		
		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		MediaPlayer mp = new MediaPlayer();

		/*********************************
		 * 02
		 *********************************/
		for (AI ai: ai_list) {
			
			String file_full_path = StringUtils.join(
					new String[]{ai.getFile_path(), ai.getFile_name()},
					File.separator);

			try {
				
				mp.setDataSource(file_full_path);
				
				mp.prepare();
				
				int length = mp.getDuration();
				
				dbu.updateData_aiLength(
						actv,
						ai.getTable_name(),
						ai.getDb_id(),
						length);
				
//				mp.release();
				
				mp.reset();

			} catch (Exception e) {
				
				// Log
				Log.d("Task_UpdateFileLength.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
					"Exc: " + e.toString()
					+ "(" + file_full_path + ")");
				
				continue;
				
			}//try
			
			
		}//for (AI item : ai_list)
		
		
		
		return "Update -> Done";
	}//protected String doInBackground(String... arg0)EE

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		// debug
		Toast.makeText(actv, "Length => Updated", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	
	
}//public class Task_UpdateFileLength extends AsyncTask<String, Integer, String>
