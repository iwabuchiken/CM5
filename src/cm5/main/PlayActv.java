package cm5.main;

import cm5.items.AI;
import cm5.listeners.button.ButtonOnClickListener;
import cm5.listeners.button.ButtonOnLongClickListener;
import cm5.listeners.button.ButtonOnTouchListener;
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
	
}//public class PlayActv extends Activity
