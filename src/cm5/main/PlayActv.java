package cm5.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import cm5.items.AI;
import cm5.listeners.MPOnCompletionListener;
import cm5.listeners.button.ButtonOnClickListener;
import cm5.listeners.button.ButtonOnLongClickListener;
import cm5.listeners.button.ButtonOnTouchListener;
import cm5.utils.CONS;
import cm5.utils.Methods;
import cm5.utils.Methods_dialog;
import cm5.utils.Tags;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
		 * 
		 * 4. TextView => Title
		 *********************************/
		/*********************************
		 * 1. Button => Play
		 *********************************/
		Button bt_play = (Button) findViewById(R.id.actv_play_bt_play);
		
		bt_play.setTag(Tags.ButtonTags.actv_play_bt_play);
		
		bt_play.setOnTouchListener(new ButtonOnTouchListener(this));
//		bt_play.setOnClickListener(new ButtonOnClickListener(this));
		bt_play.setOnClickListener(new ButtonOnClickListener(this, ai));

		/*********************************
		 * 2. Button => Stop
		 *********************************/
		Button bt_stop = (Button) findViewById(R.id.actv_play_bt_stop);
		
		bt_stop.setTag(Tags.ButtonTags.actv_play_bt_stop);
		
		bt_stop.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_stop.setOnClickListener(new ButtonOnClickListener(this));

		/*********************************
		 * 3. Button => Back
		 *********************************/
		Button bt_back = (Button) findViewById(R.id.actv_play_bt_back);
		
		bt_back.setTag(Tags.ButtonTags.actv_play_bt_back);
		
		bt_back.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_back.setOnClickListener(new ButtonOnClickListener(this));

		/*********************************
		 * 4. TextView => Title
		 *********************************/
		TextView tv_title = (TextView) findViewById(R.id.actv_play_tv_title);
		
		tv_title.setTag(Tags.ButtonTags.actv_play_tv_title);
		
//		tv_title.setOnClickListener(new ButtonOnLongClickListener(this));

		tv_title.setOnTouchListener(new ButtonOnTouchListener(this));
		tv_title.setOnLongClickListener(new ButtonOnLongClickListener(this, ai));
		
		/*********************************
		 * 5. TextView => Memo
		 *********************************/
		TextView tv_memo = (TextView) findViewById(R.id.actv_play_tv_memo);
		
		tv_memo.setTag(Tags.ButtonTags.actv_play_tv_memo);
		
//		tv_title.setOnClickListener(new ButtonOnLongClickListener(this));

		tv_memo.setOnTouchListener(new ButtonOnTouchListener(this));
		tv_memo.setOnLongClickListener(new ButtonOnLongClickListener(this, ai));
		
		/***************************************
		 * Button: See bookmarks
		 ***************************************/
		Button btSeeBM = (Button) findViewById(R.id.actv_play_bt_see_bm);
		
		btSeeBM.setTag(Tags.ButtonTags.actv_play_bt_see_bm);
		
		btSeeBM.setOnTouchListener(new ButtonOnTouchListener(this));
		btSeeBM.setOnClickListener(new ButtonOnClickListener(this, ai));

		/***************************************
		 * Button: Add bookmarks
		 ***************************************/
		Button btAddBM = (Button) findViewById(R.id.actv_play_bt_add_bm);
		
		btAddBM.setTag(Tags.ButtonTags.actv_play_bt_add_bm);
		
		btAddBM.setOnTouchListener(new ButtonOnTouchListener(this));
		btAddBM.setOnClickListener(new ButtonOnClickListener(this, ai));

		
	}//private void setup_2_set_listeners()

	private void setup_1_setStrings2Views() {
		/*********************************
		 * 1. Get intent
		 * 
		 * 2. Get db_id
		 * 3. Get table name
		 * 
		 * 4. Get an AI object from DB
		 * 
		 * 5. Set file name to the view
		 * 
		 * 6. Set title to the view
		 * 
		 * 7. Set memo to the view
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
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "actv_play_bt_add_bm=" + ai.getTitle());
		
		/*********************************
		 * 5. Set file name to the view
		 *********************************/
		TextView tv_file_name = (TextView) findViewById(R.id.actv_play_tv_file_name);
		
		if (!ai.getFile_name().equals("")) {
			
			tv_file_name.setText(ai.getFile_name());
			
		} else {//if (!ai.getFile_name().equals(""))
			
			tv_file_name.setText(this.getString(R.string.generic_tv_no_data));
			
		}//if (!ai.getFile_name().equals(""))
		
		/*********************************
		 * 6. Set title to the view
		 *********************************/
		TextView tv_title = (TextView) findViewById(R.id.actv_play_tv_title);
		
		if (!ai.getTitle().equals("")) {
			
			tv_title.setText(ai.getTitle());
			
		} else {//if (!ai.getFile_name().equals(""))
			
//			tv_title.setText(this.getString(R.string.generic_tv_no_data));
			tv_title.setText("Title!");
			
		}//if (!ai.getFile_name().equals(""))
		
		/*********************************
		 * 7. Set memo to the view
		 *********************************/
		TextView tvMemo = (TextView) findViewById(R.id.actv_play_tv_memo);
		
		if (!ai.getMemo().equals("")) {
			
			tvMemo.setText(ai.getMemo());
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Memo set => " + ai.getMemo());
			
		} else {//if (!ai.getFile_name().equals(""))
			
//			tv_title.setText(this.getString(R.string.generic_tv_no_data));
			tvMemo.setText("No memo");
			
		}//if (!ai.getFile_name().equals(""))
		
		/***************************************
		 * Set: File length
		 ***************************************/
		TextView tvLength = (TextView) findViewById(R.id.actv_play_tv_length);
		
		long length = ai.getLength();
		
		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "length=" + length);
		
		if (length > 0) {
			
			tvLength.setText(Methods.convert_intSec2Digits_lessThanHour((int)length / 1000));
			
		} else {//if (length == condition)
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "length <= 0");
			
		}//if (length == condition)
		
	}//private void setup_1_setStrings2Views()
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_actv_play, menu);

		return super.onCreateOptionsMenu(menu);
		
	}//public boolean onCreateOptionsMenu(Menu menu)

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
		
		switch (item.getItemId()) {
			
		case R.id.menu_actv_play_create_folder://------------------------------------
			
//			Methods.dlg_register_patterns(this);
			
			Methods_dialog.dlg_patterns(this);
			
			break;// case R.id.menu_actv_play_create_folder
			
		}//switch (item.getItemId())

		
		return super.onOptionsItemSelected(item);
	}//public boolean onOptionsItemSelected(MenuItem item)

	@Override
	protected void onPause() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onResume();
	}

	@Override
	protected void onStart() {
		/*********************************
		 * memo
		 *********************************/
		
		setup_1_setStrings2Views();
		
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStop();
	}


	@Override
	protected void
	onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == CONS.Intent.REQUEST_CODE_SEE_BOOKMARKS) {
			
			if (resultCode == CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_OK) {
				
				onActivityResult_BM_OK(data);
				
//				long position = data.getLongExtra(CONS.Intent.bmactv_key_position, -1);
//				
//				// Log
//				Log.d("PlayActv.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber()
//						+ ":"
//						+ Thread.currentThread().getStackTrace()[2]
//								.getMethodName() + "]", "Returned position => " + position);
				
			} else if (resultCode == CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_CANCEL) {//if (resultCode == CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_OK)
				
				// Log
				Log.d("PlayActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", "Cancelled");
				
			}//if (resultCode == CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_OK)
			
			
		} else {//if (requestCode == CONS.Intent.REQUEST_CODE_SEE_BOOKMARKS)
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "request code => " + requestCode);
			
		}//if (requestCode == CONS.Intent.REQUEST_CODE_SEE_BOOKMARKS)
		
	}//onActivityResult(int requestCode, int resultCode, Intent data)

	private void
	onActivityResult_BM_OK(Intent data) {
		// TODO Auto-generated method stub
		long position = data.getLongExtra(CONS.Intent.bmactv_key_position, -1);
		long aiDbId = data.getLongExtra(CONS.Intent.bmactv_key_ai_id, -1);
		String aiTableName = data.getStringExtra(CONS.Intent.bmactv_key_table_name);
		
		/***************************************
		 * Set OnClickListener to the button "Stop"
		 ***************************************/
		/*********************************
		 * 2. Button => Stop
		 *********************************/
		Button bt_stop = (Button) findViewById(R.id.actv_play_bt_stop);
		
		bt_stop.setTag(Tags.ButtonTags.actv_play_bt_stop);
		
		bt_stop.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_stop.setOnClickListener(new ButtonOnClickListener(this));

		
		
		// Log
		Log.d("PlayActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2]
						.getMethodName() + "]", "Returned position => " + position);
		
//		// Log
//		Log.d("PlayActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "aiDbId=" + aiDbId);
//		
//		// Log
//		Log.d("PlayActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "ai.getTable_name()=" + ai.getTable_name());
//		
//		AI aiNew = Methods.get_data_ai(this, aiDbId, aiTableName);
//		
//		// Log
//		Log.d("PlayActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ ":"
//				+ Thread.currentThread().getStackTrace()[2].getMethodName()
//				+ "]", "aiNew.getTable_name()=" + aiNew.getTable_name());
		
		/***************************************
		 * Got a position?
		 ***************************************/
		if (position == -1) {
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "position => -1");
			
			return;
			
		}//if (position == -1)

		/***************************************
		 * Is an AI instance instantiated already?
		 ***************************************/
		if (ai == null) {
			
			ai = Methods.get_data_ai(this, aiDbId, aiTableName);
			
		}//if (this.ai == null)

		/*********************************
		 * 1. Media player is playing?
		 *********************************/
		if (PlayActv.mp != null && PlayActv.mp.isPlaying()) {

			PlayActv.mp.stop();
			
		}//if (mp.isPlaying())

		/*********************************
		 * 2. OnCompletionListener
		 *********************************/
//		if (PlayActv.mp == null) {
//
//			PlayActv.mp = new MediaPlayer();
//			
//			PlayActv.mp.setOnCompletionListener(new MPOnCompletionListener(this));
//
//		} else {//if (PlayActv.mp == null)
//		
//			// Log
//			Log.d("PlayActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "PlayActv.mp != null");
//			
//		}//if (PlayActv.mp == null)
		
		if (PlayActv.mp != null) {

			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "PlayActv.mp != null");
			
			PlayActv.mp.release();
			
			PlayActv.mp = null;
			

//			PlayActv.mp = new MediaPlayer();
//			
//			PlayActv.mp.setOnCompletionListener(new MPOnCompletionListener(this));
			
		} else {//if (PlayActv.mp != null)
			
			// Log
			Log.d("PlayActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "PlayActv.mp => null");
			
		}//if (PlayActv.mp != null)
		
		
		PlayActv.mp = new MediaPlayer();
		
		PlayActv.mp.setOnCompletionListener(new MPOnCompletionListener(this));

		/*********************************
		 * 3. Set data source
		 *********************************/
		String file_full_path = StringUtils.join(
				new String[]{ai.getFile_path(), ai.getFile_name()},
				File.separator);

		try {

			PlayActv.mp.setDataSource(file_full_path);
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Data source => Set");
			
		} catch (IllegalArgumentException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			
		} catch (IllegalStateException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		} catch (IOException e) {

			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		}//try

		/*********************************
		 * 4. Prepare mp
		 *********************************/
		try {

			PlayActv.mp.prepare();
			
		} catch (IllegalStateException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		}//try

//		/***************************************
//		 * 4.1 Set position
//		 ***************************************/
//		PlayActv.mp.seekTo((int) position);
//		
		/*********************************
		 * 5. Start
		 *********************************/
		PlayActv.mp.start();

		// Log
		Log.d("PlayActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "PlayActv.mp.isPlaying()=" + PlayActv.mp.isPlaying());
		
//		/***************************************
//		 * Start: Playing
//		 * 1. Is the player initialized?
//		 * 2. Player playing?
//		 ***************************************/
//		if (PlayActv.mp == null) {
//			
//			PlayActv.mp = new MediaPlayer();
//			
//			PlayActv.mp.setOnCompletionListener(new MPOnCompletionListener(this));
//			
//		}//if (PlayActv.mp == null)
//		
//		/***************************************
//		 * 2. Player playing?
//		 ***************************************/
//		if (PlayActv.mp.isPlaying() == true) {
//			
//			PlayActv.mp.stop();
//			
//		}//if (PlayActv.mp.isPlaying() == true)
		
	}//onActivityResult_BM_OK(Intent data)
	
}//public class PlayActv extends Activity
