package cm5.main;

import cm5.items.AI;
import cm5.listeners.ButtonOnClickListener;
import cm5.listeners.ButtonOnTouchListener;
import cm5.utils.Methods;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActv extends Activity {

	public static AI ai;
	
	public static MediaPlayer mp = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/********************************
		 * 
		 ********************************/

		super.onCreate(savedInstanceState);

		setContentView(R.layout.actv_play);

		this.setTitle(this.getClass().getName());

//		setup_1_set_file_name();
//		
//		setup_2_set_listeners();
		
	}//public void onCreate(Bundle savedInstanceState)

	private void setup_2_set_listeners() {
		/*********************************
		 * 1. Button => Play
		 * 2. Button => Stop
		 * 3. Button => Back
		 *********************************/
		/*********************************
		 * 1. Button => Play
		 *********************************/
		Button bt_play = (Button) findViewById(R.id.actv_play_bt_play);
		
		bt_play.setTag(Methods.ButtonTags.actv_play_bt_play);
		
		bt_play.setOnTouchListener(new ButtonOnTouchListener(this));
//		bt_play.setOnClickListener(new ButtonOnClickListener(this));
		bt_play.setOnClickListener(new ButtonOnClickListener(this, ai));

		/*********************************
		 * 2. Button => Stop
		 *********************************/
		Button bt_stop = (Button) findViewById(R.id.actv_play_bt_stop);
		
		bt_stop.setTag(Methods.ButtonTags.actv_play_bt_stop);
		
		bt_stop.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_stop.setOnClickListener(new ButtonOnClickListener(this));

		/*********************************
		 * 3. Button => Back
		 *********************************/
		Button bt_back = (Button) findViewById(R.id.actv_play_bt_back);
		
		bt_back.setTag(Methods.ButtonTags.actv_play_bt_back);
		
		bt_back.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_back.setOnClickListener(new ButtonOnClickListener(this));

		
	}//private void setup_2_set_listeners()

	private void setup_1_set_file_name() {
		/*********************************
		 * 1. Get intent
		 * 
		 * 2. Get db_id
		 * 3. Get table name
		 * 
		 * 4. Get an AI object from DB
		 * 
		 * 5. Set file name to the view
		 *********************************/
		Intent i = this.getIntent();
		
		if (i == null) {
			
			// debug
			Toast.makeText(this, "Can't get intent", Toast.LENGTH_SHORT).show();
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Can't get intent");
			
			return;
			
		}//if (i == null)
		
		long db_id = i.getLongExtra("db_id", -1);
		
		if (db_id == -1) {
			
			// debug
			Toast.makeText(this, "Can't get db_id", Toast.LENGTH_SHORT).show();
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Can't get db_id");
			
			return;
			
		}//if (db_id == -1)
		
		/*********************************
		 * 3. Get table name
		 *********************************/
		String table_name = i.getStringExtra("table_name");
		
		if (table_name == null) {
			
			// debug
			Toast.makeText(this, "Can't get table_name", Toast.LENGTH_SHORT).show();
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Can't get table_name");
			
			return;
			
		}//if (db_id == -1)
		
		/*********************************
		 * 4. Get an AI object from DB
		 *********************************/
		ai = Methods.get_data_ai(this, db_id, table_name);
		
//		// Log
//		Log.d("PlayActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "ai.getFile_name()=" + ai.getFile_name());
		
		/*********************************
		 * 5. Set file name to the view
		 *********************************/
		TextView tv_file_name = (TextView) findViewById(R.id.actv_play_tv_file_name);
		
		if (!ai.getFile_name().equals("")) {
			
			tv_file_name.setText(ai.getFile_name());
			
		} else {//if (!ai.getFile_name().equals(""))
			
			tv_file_name.setText(this.getString(R.string.generic_tv_no_data));
			
		}//if (!ai.getFile_name().equals(""))
		
		
	}//private void setup_1_set_file_name()
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onDestroy() {
		/*********************************
		 * memo
		 *********************************/
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		Methods.stop_player(this);
		
		
		super.onDestroy();
	}//protected void onDestroy()

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

	@Override
	protected void onStart() {
		/*********************************
		 * memo
		 *********************************/
		
		setup_1_set_file_name();
		
		setup_2_set_listeners();
		
		setup_3_initialize_vars();

		super.onStart();
	}//protected void onStart()

	private void setup_3_initialize_vars() {
		/*********************************
		 * memo
		 *********************************/
		mp = new MediaPlayer();
		
	}//private void setup_3_initialize_vars()
	

	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
	}
	
}//public class PlayActv extends Activity
