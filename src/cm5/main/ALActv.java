/*********************************
 * ALACtv.java (Audio list activity)
 * 
 *********************************/
package cm5.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cm5.adapters.AILAdapter;
import cm5.adapters.TIListAdapter;
import cm5.items.AI;
import cm5.items.TI;
import cm5.listeners.CustomOnItemLongClickListener;
import cm5.listeners.button.ButtonOnClickListener;
import cm5.listeners.button.ButtonOnTouchListener;
import cm5.listeners.dialog.DialogListener;
import cm5.utils.CONS;
import cm5.utils.DBUtils;
import cm5.utils.Methods;
import cm5.utils.Methods_dialog;
import cm5.utils.Tags;

import cm5.main.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class ALActv extends ListActivity {

	public static Vibrator vib;

	public static List<TI> tiList;

//	public static TIListAdapter aAdapter;
//	public static TIListAdapter bAdapter;

	public static boolean move_mode = false;

	/*********************************
	 * Special intent data
	 *********************************/
	public static long[] long_searchedItems; //=> Used in initial_setup()
	
	public static long[] history_file_ids = null;
	
	public static String[] history_table_names = null;
	
	public static String[] string_searchedItems_table_names = null;
	
	/*********************************
	 * List-related
	 *********************************/
	public static ArrayList<Integer> checkedPositions;

	public static List<String> fileNameList;
	
	public static List<AI> ai_list;

	public static AILAdapter ail_adp;
	
	public static ArrayAdapter<String> dirListAdapter;
	
	/****************************
	 * Preference names
		****************************/
	public static String tnactv_selected_item = "tnactv_selected_item";

	/*********************************
	 * Views
	 *********************************/
	public static ListView lv_main;
	
	/****************************************
	 * Methods
	 ****************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/****************************
		 * Steps
		 * 1. Super
		 * 2. Set content
		 * 3. Basics
		 * 
		 * 4. Set up
		 * 5. Initialize vars
		****************************/
		super.onCreate(savedInstanceState);

		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onCreate()");
		
		//
//		setContentView(R.layout.thumb_activity);
		setContentView(R.layout.actv_al);
		
		/****************************
		 * 3. Basics
			****************************/
		this.setTitle(this.getClass().getName());
		
//		vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
//		
//		/****************************
//		 * 4. Set up
//			****************************/
////		//debug
////		Methods.update_prefs_currentPath(this, MainActv.dirName_base);
//		
//		setup_1_set_listeners();
//		
//		setup_2_set_list();

//		// Log
//		Log.d("ALActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Table name: " + Methods.convert_path_into_table_name(this));
		
		/****************************
		 * 5. Initialize vars
			****************************/
		checkedPositions = new ArrayList<Integer>();

		//debug
//		get_data_from_table_AAA();
		
//		get_tables_from_db();
		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "prefs_current_path: " + Methods.get_pref(this, MainActv.prefs_current_path, "NO DATA"));
		
	}//public void onCreate(Bundle savedInstanceState)


	private void get_tables_from_db() {
		
		DBUtils dbu = new DBUtils(this, CONS.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "rdb.getPath(): " + rdb.getPath());
		
		
		// REF=> http://stackoverflow.com/questions/82875/how-do-i-list-the-tables-in-a-sqlite-database-file
		String sql = "SELECT * FROM sqlite_master WHERE type='table'";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		startManagingCursor(c);
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Tables: c.getCount()" + c.getCount());
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "name: " + c.getString(1));
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		rdb.close();
		
	}//private void get_tables_from_db()

	private void get_data_from_table_AAA() {
		
		DBUtils dbu = new DBUtils(this, CONS.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		String sql = "SELECT * FROM AAA";
		
		Cursor c = rdb.rawQuery(sql, null);
		
		startManagingCursor(c);
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "AAA: c.getCount()" + c.getCount());
		
		rdb.close();
		
	}//private void get_data_from_table_AAA()


	private void setup_2_set_list() {
		/*********************************
		 * 1. Get table name
		 * 
		 * 2. Prep list
		 * 
		 * 3. Sort list
		 * 
		 * 4. Prep adapter
		 * 
		 * 5. Set adapter
		 *********************************/
		/*********************************
		 * 1. Get table name
		 *********************************/
		String table_name = Methods.convert_path_into_table_name(this);
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "table_name=" + table_name);

		/*********************************
		 * 2. Prep list
		 *********************************/
		ai_list = Methods.get_all_data_ai(this, table_name);
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "ai_list.size()=" + ai_list.size());
		
		/*********************************
		 * 3. Sort list
		 *********************************/
		Methods.sort_list_ai_created_at(ai_list, CONS.SORT_ORDER.DEC);
		
		/*********************************
		 * 4. Prep adapter
		 *********************************/
		ail_adp = new AILAdapter(
				this,
				R.layout.list_row_ai_list,
				ai_list
				);
		
		/*********************************
		 * 5. Set adapter
		 *********************************/
		this.setListAdapter(ail_adp);
		
	}//private void setup_2_set_list()


	private List<TI> prep_list() {
		/****************************
		 * Get ThumbnailItem list
		 * 1. Get intent data
		 * 2. Build tiList
			****************************/
		/****************************
		 * 1. Get intent data
			****************************/
		Intent i = this.getIntent();
		
//		long_searchedItems = i.getLongArrayExtra("long_searchedItems");
//		
//		history_file_ids = i.getLongArrayExtra(MainActv.intent_label_file_ids);
//		
//		history_table_names = i.getStringArrayExtra(MainActv.intent_label_table_names);
//		
//		string_searchedItems_table_names = 
//					i.getStringArrayExtra(MainActv.intent_label_searchedItems_table_names);
		
		/****************************
		 * 2. Build tiList
			****************************/
		String tableName = Methods.convert_path_into_table_name(this);
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tableName: " + tableName);
		
		
		if (long_searchedItems != null) {

			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "long_searchedItems.length: " + long_searchedItems.length);

			if (string_searchedItems_table_names != null) {
				
				// Log
				Log.d("ALActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Calling: Methods.convert_fileIdArray2tiList()");
				
	//			tiList = Methods.getAllData(this, tableName);
	//			tiList = Methods.convert_fileIdArray2tiList(this, MainActv.dirName_base, long_searchedItems);
				tiList = Methods.convert_fileIdArray2tiList_all_table(
										this,
										long_searchedItems,
										string_searchedItems_table_names);
				
			} else {//if (string_searchedItems_table_names != null)
				
				// Log
				Log.d("ALActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Calling: Methods.convert_fileIdArray2tiList()");
				
	//			tiList = Methods.getAllData(this, tableName);
	//			tiList = Methods.convert_fileIdArray2tiList(this, MainActv.dirName_base, long_searchedItems);
				tiList = Methods.convert_fileIdArray2tiList(this, tableName, long_searchedItems);
				
			}//if (string_searchedItems_table_names != null)
			
//				// Log
//				Log.d("ALActv.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Calling: Methods.convert_fileIdArray2tiList()");
//				
//	//			tiList = Methods.getAllData(this, tableName);
//	//			tiList = Methods.convert_fileIdArray2tiList(this, MainActv.dirName_base, long_searchedItems);
//				tiList = Methods.convert_fileIdArray2tiList(this, tableName, long_searchedItems);

			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "[prep_list()] tiList.size()=" + tiList.size());
			
			
		} else if (history_file_ids != null) {//if (long_searchedItems == null)

			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "history_file_ids != null");
			
			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"file_ids: length=" + history_file_ids.length + 
					"/" + "history_table_names: length=" + history_table_names.length);
			
			tiList = Methods.get_all_data_history(this, history_file_ids, history_table_names);
			
			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "tiList.size()=" + tiList.size());
			
//			tiList = Methods.convert_fileIdArray2tiList(this, "IFM8", long_searchedItems);
			
		} else {//if (long_searchedItems == null)
			
			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Calling => Methods.getAllData");
			
			tiList = Methods.getAllData(this, tableName);
			
		}//if (long_searchedItems == null)

		
		
		return tiList;
	}//private List<TI> prep_list()


	private void setup_1_set_listeners() {
		/****************************
		 * Steps
		 * 1. "Back" button
		 * 2. LongClick
		 * 3. "Bottom"
		 * 4. "Top"
			****************************/
		//
		ImageButton ib_back = (ImageButton) findViewById(R.id.actv_al_ib_back);
		
		ib_back.setEnabled(true);
		ib_back.setImageResource(R.drawable.ifm8_thumb_back_50x50);
		
		ib_back.setTag(Tags.ButtonTags.thumb_activity_ib_back);
		
		ib_back.setOnTouchListener(new ButtonOnTouchListener(this));
		ib_back.setOnClickListener(new ButtonOnClickListener(this));
		
		/****************************
		 * 2. LongClick
			****************************/
//		ListView lv = (ListView) findViewById(android.R.layout.activity_list_item);
		ListView lv = this.getListView();
		
		lv.setTag(Tags.ItemTags.dir_list_thumb_actv);
		
		lv.setOnItemLongClickListener(new CustomOnItemLongClickListener(this));
		
		/****************************
		 * 3. "Bottom"
		 * 		1. Set up
		 * 		2. Listeners
			****************************/
		ImageButton bt_bottom = (ImageButton) findViewById(R.id.actv_al_ib_toBottom);
		
		bt_bottom.setEnabled(true);
		bt_bottom.setImageResource(R.drawable.ifm8_thumb_bottom_50x50);
		
		// Tag
		bt_bottom.setTag(Tags.ButtonTags.thumb_activity_ib_bottom);
		
		bt_bottom.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_bottom.setOnClickListener(new ButtonOnClickListener(this, lv));
		
		/****************************
		 * 4. "Top"
		 * 		1. Set up
		 * 		2. Listeners
			****************************/
		ImageButton bt_top = (ImageButton) findViewById(R.id.actv_al_ib_toTop);
		
		bt_top.setEnabled(true);
		bt_top.setImageResource(R.drawable.ifm8_thumb_top_50x50);
		
		// Tag
		bt_top.setTag(Tags.ButtonTags.thumb_activity_ib_top);
		
		/****************************
		 * 4.2. Listeners
			****************************/
		bt_top.setOnTouchListener(new ButtonOnTouchListener(this));
		bt_top.setOnClickListener(new ButtonOnClickListener(this, lv));
		
		
	}//private void setup_1_set_listeners()
	
	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}

	@Override
	protected void onResume() {
		/*********************************
		 * 1. super
		 * 2. Notify adapter
		 * 
		 * 3. Set selection
		 *********************************/
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onResume()");

//		if (ALActv.aAdapter != null) {
//					
//			ALActv.aAdapter.notifyDataSetChanged();
//			
//		}
//		TNActv.aAdapter.notifyDataSetChanged();

//		/*********************************
//		 * 3. Set selection
//		 *********************************/
//		lv_main = this.getListView();
//		
//		SharedPreferences prefs = this.getSharedPreferences(
//				MainActv.prefName_tnActv,
//				MODE_PRIVATE);
//	
//
//		//Methods.PrefenceLabels.thumb_actv.name()
//		
//		//int savedPosition = prefs.getInt("chosen_list_item", -1);
//		int savedPosition = prefs.getInt(
//							MainActv.prefName_tnActv_current_image_position,
//							-1);
//		
//		int target_position = savedPosition - (lv_main.getChildCount() / 2);
//		
//		if (target_position < 0) {
//			
//			target_position = 0;
//			
//		}//if (target_position == 0)
//
//		// Log
//		Log.d("ALActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "target_position=" + target_position);
//		
//		
//		lv_main.setSelection(target_position);

		
//		// Log
//		Log.d("ALActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "lv_main.getCheckedItemPosition()=" + lv_main.getCheckedItemPosition());
		
	}//protected void onResume()

	@Override
	protected void onStart() {
		/*********************************
		 * 1. super
		 * 
		 * 2. Set up
		 * 
		 * 3. Debug: Store file length data
		 *********************************/
		super.onStart();
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStart()");
		
		vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
		
		/****************************
		 * 2. Set up
			****************************/
//		//debug
//		Methods.update_prefs_currentPath(this, MainActv.dirName_base);
		
		setup_1_set_listeners();
		
		setup_2_set_list();

		/*********************************
		 * 3. Debug: Store file length data
		 *********************************/
		debug_1_store_file_length();
		
//		/*********************************
//		 * 2. Set selection
//		 *********************************/
//		lv_main = this.getListView();
//		
//		SharedPreferences prefs = this.getSharedPreferences(
//				MainActv.prefName_tnActv,
//				MODE_PRIVATE);
//	
//
//		//Methods.PrefenceLabels.thumb_actv.name()
//		
//		//int savedPosition = prefs.getInt("chosen_list_item", -1);
//		int savedPosition = prefs.getInt(
//							MainActv.prefName_tnActv_current_image_position,
//							-1);
//		
//		int target_position = savedPosition - (lv_main.getChildCount() / 2);
//		
//		if (target_position < 0) {
//			
//			target_position = 0;
//			
//		}//if (target_position == 0)
//
//		// Log
//		Log.d("ALActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "target_position=" + target_position);
//		
//		
//		lv_main.setSelection(target_position);
		
	}//protected void onStart()

	private void debug_1_store_file_length() {
		/*********************************
		 * 1. DB setup
		 * 2. Table exists?
		 *********************************/
		/*********************************
		 * 1. DB setup
		 *********************************/
		DBUtils dbu = new DBUtils(this, CONS.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		String tname = CONS.tname_main;
		
		/****************************
		 * 2. Table exists?
			****************************/
		boolean res = dbu.tableExists(rdb, tname);
		
		if (res == false) {
			
			// Log
			Log.e("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]",
				"debug_1_store_file_length() => Table doesn't exist: " + tname);
			
			rdb.close();
			
			return;
			
		} else {//if (res == false)
			
			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tname);
			
		}
		
		/*********************************
		 * Query
		 *********************************/
		String sql = "SELECT * FROM " + tname;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
			startManagingCursor(c);
			
		} catch (Exception e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return;
		}
		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount());

		/****************************
		 * 
			****************************/
//		int data_num = c.getCount();
//		
//		// Log
//		Log.d("ALActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "data_num=" + data_num);
		
		c.moveToFirst();
		
//		String file_full_path = StringUtils.join(
//				(new String[]{
//					c.getString(4),
//					c.getString(3)}),
//				File.separator);

		String[] col_names = 
				Methods.get_column_list(this, CONS.dbName, tname);
		
		for (String col_name : col_names) {
			
			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "col_name=" + col_name);
			
		}
		
		
		/*********************************
		 * Add column
		 *********************************/
		res = 
				Methods.add_column_to_table(
						this, CONS.dbName, tname, "length", "INTEGER");
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res=" + res);
		
		rdb.close();
		
	}//private void debug_1_store_file_length()
	


	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onStop()");

	}

	@Override
	protected void onDestroy() {
		/*********************************
		 * 1. super
		 * 2. move_mode => falsify
		 * 
		 * 3. History mode => Off
		 *********************************/
		
		super.onDestroy();
		
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		/****************************
		 * 2. move_mode => falsify
			****************************/
		if (move_mode == true) {
			
			move_mode = false;
			
			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "move_mode => Now false");
			
		}//if (move_mode == true)

		SharedPreferences prefs = 
				this.getSharedPreferences(
//						MainActv.prefName_tnActv,
						CONS.pname_tnActv,
						MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();

		editor.clear();
		editor.commit();
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Prefs cleared: " + MainActv.prefName_tnActv);
				+ "]", "Prefs cleared: " + CONS.pname_tnActv);

		/*********************************
		 * 3. History mode => Off
		 *********************************/
		int current_move_mode = Methods.get_pref(
							this, 
//							MainActv.prefName_mainActv, 
//							MainActv.prefName_mainActv_history_mode,
							CONS.pname_mainActv, 
							CONS.pname_mainActv_history_mode,

							-1);
		
		if (current_move_mode == CONS.HISTORY_MODE_ON) {
			
			boolean result = Methods.set_pref(
					this, 
//					MainActv.prefName_mainActv, 
//					MainActv.prefName_mainActv_history_mode,
					CONS.pname_mainActv, 
					CONS.pname_mainActv_history_mode,

					CONS.HISTORY_MODE_OFF);

			if (result == true) {
				// Log
				Log.d("ALActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Pref set: " + CONS.HISTORY_MODE_OFF);
			} else {//if (result == true)
				// Log
				Log.d("ALActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Set pref => Failed");
				
			}//if (result == true)
			
		}//if (current_move_mode == 1)
		
	}//protected void onDestroy()

	@Override
	public void onBackPressed() {
		/****************************
		 * memo
			****************************/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()

	/****************************************
	 * method_name(param_type)
	 * 
	 * <Caller> 1. TNActv.set_list()
	 * 
	 * <Desc> 
	 * 1. Click "OK" button, then TNActv will get finished.
	 * 
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public void show_message_no_data() {
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		
        dialog.setTitle("情報");
        dialog.setMessage("このフォルダには、データはありません。他のフォルダから、オプション・メニューの「移動」を使って、もってこれます");
        
        dialog.setPositiveButton("OK",new DialogListener(this, dialog, 0));
        
        dialog.create();
        dialog.show();
		
	}//public void show_message_no_data()

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		/*********************************
		 * 0. Vibrate
		 * 
		 * 1. Get item
		 * 2. Intent
		 * 		2.1. Set data
		 * 
		 * 9. Start intent
		 *********************************/
		/****************************
		 * 0. Vibrate
			****************************/
		vib.vibrate(Methods.vibLength_click);

		/****************************
		 * 1. Get item
			****************************/
		AI ai = (AI) lv.getItemAtPosition(position);
		
		/****************************
		 * 2. Intent
		 * 		2.1. Set data
			****************************/
		Intent i = new Intent();
		
		i.setClass(this, PlayActv.class);
		
		i.putExtra("db_id", ai.getDb_id());
		
		i.putExtra("table_name", ai.getTable_name());
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		
		/*********************************
		 * 9. Start intent
		 *********************************/
		startActivity(i);
		
//		if (ai != null) {
//			
//			// debug
//			Toast.makeText(this, ai.getFile_name(), Toast.LENGTH_SHORT).show();
//			
//		} else {//if (ai != null)
//
//			// debug
//			Toast.makeText(this, "ai == null", Toast.LENGTH_SHORT).show();
//			
//		}//if (ai != null)
		

//		/****************************
//		 * 0. Vibrate
//			****************************/
//		vib.vibrate(Methods.vibLength_click);
//		
//		if (MainActv.move_mode == false) {
//			/****************************
//			 * 1. Get item
//				****************************/
//			TI ti = (TI) lv.getItemAtPosition(position);
//			
//			/****************************
//			 * 2. Intent
//			 * 		2.1. Set data
//				****************************/
//			Intent i = new Intent();
//			
//			i.setClass(this, ImageActv.class);
//			
//			i.putExtra("file_id", ti.getFileId());
//			i.putExtra("file_path", ti.getFile_path());
//			i.putExtra("file_name", ti.getFile_name());
//			
//			i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//			
//			/*********************************
//			 * 2-2. Record history
//			 *********************************/
////			// Log
////			Log.d("ALActv.java" + "["
////					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////					+ "]", "Table name=" + Methods.convert_path_into_table_name(this));
//			int current_history_mode = Methods.get_pref(
//					this, 
//					MainActv.prefName_mainActv, 
//					MainActv.prefName_mainActv_history_mode,
//					-1);
//
//			if (current_history_mode == MainActv.HISTORY_MODE_OFF) {
//				
//				Methods.save_history(
//						this,
//						ti.getFileId(),
//						Methods.convert_path_into_table_name(this));
//				
//				/*********************************
//				 * 2-2-a. Update data
//				 *********************************/
////				// Log
////				Log.d("ALActv.java"
////						+ "["
////						+ Thread.currentThread().getStackTrace()[2]
////								.getLineNumber() + "]",
////						"[onListItemClick] Table name=" + Methods.convert_path_into_table_name(this));
//				
//				DBUtils dbu = new DBUtils(this, MainActv.dbName);
//				
//				//
//				SQLiteDatabase wdb = dbu.getWritableDatabase();
//
//				
//				boolean res = DBUtils.updateData_TI_last_viewed_at(
//									this,
//									wdb,
//									Methods.convert_path_into_table_name(this),
//									ti);
//				
//				if (res == true) {
//					// Log
//					Log.d("ALActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "Data updated: " + ti.getFile_name());
//				} else {//if (res == true)
//					// Log
//					Log.d("ALActv.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]",
//							"Update data => Failed: " + ti.getFile_name());
//				}//if (res == true)
//				
//				
//				wdb.close();
//				
//			} else {//if (current_move_mode == MainActv.HISTORY_MODE_OFF)
//				
//				// Log
//				Log.d("ALActv.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "History not saved");
//				
//			}//if (current_move_mode == MainActv.HISTORY_MODE_OFF)
////			Methods.save_history(this, ti.getFileId(), Methods.convert_path_into_table_name(this));
//			
//			
//			/*********************************
//			 * 2-3. Save preferences
//			 *********************************/
//			SharedPreferences preference = 
//					getSharedPreferences(
//							MainActv.prefName_tnActv,
//							MODE_PRIVATE);
//
//			SharedPreferences.Editor editor = preference.edit();
//			
//			editor.putInt(MainActv.prefName_tnActv_current_image_position, position);
//			editor.commit();
//
//			
//			// Log
//			Log.d("ALActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Prefs set");
//			
////			aAdapter.notifyDataSetChanged();
//			
//			// Log
//			Log.d("ALActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "aAdapter notified");
//			
//			this.startActivity(i);
//			
//		} else if (MainActv.move_mode == true) {//if (move_mode == false)
//			
//			/****************************
//			 * CheckBox on, then click on the item, then nothing happens (20120717_221403)
//				****************************/
//			
//			TNActv.checkedPositions.add(position);
//			
//			if (bAdapter != null) {
//				
//				bAdapter.notifyDataSetChanged();
//				
//			}//if (bAdapter != null)
//			
//			// Log
//			Log.d("ALActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "New position => " + position +
//					" / " + "(length=" + TNActv.checkedPositions.size() + ")");
//			
//			
//		}//if (move_mode == false)
//		
//		super.onListItemClick(lv, v, position, id);
		
	}//protected void onListItemClick(ListView lv, View v, int position, long id)

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.thumb_actv_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/****************************
		 * Steps
		 * 1. R.id.thumb_actv_menu_move_mode
		 * 2. R.id.thumb_actv_menu_move_files
			****************************/
		
		
		case R.id.thumb_actv_menu_move_mode://---------------------------------------
			if (move_mode == true) {
				
				move_mode_true(item);
				
			} else {// move_mode => false
				
				move_mode_false(item);
				
			}//if (move_mode == true)
			
			break;// case R.id.thumb_actv_menu_move_files
		
		case R.id.thumb_actv_menu_move_files:	//------------------------------------------
			
			if (move_mode == false) {
				
				// debug
				Toast.makeText(this, "Move mode is not on", 2000)
						.show();
				
				return false;
				
			} else if (move_mode == true) {
				/****************************
				 * Steps
				 * 1. checkedPositions => Has contents?
				 * 2. If yes, show dialog
					****************************/
				if (checkedPositions.size() < 1) {
					
					// debug
					Toast.makeText(ALActv.this, "No item selected", 2000).show();
					
					return false;
					
				}//if (checkedPositions.size() < 1)
				
				
				/****************************
				 * 2. If yes, show dialog
					****************************/
				Methods_dialog.dlg_moveFiles(this);
				
			}//if (move_mode == false)
			
			break;// case R.id.thumb_actv_menu_move_files
			
		}//switch (item.getItemId())
		
		
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)


	private void move_mode_false(MenuItem item) {
		
		/****************************
		 * Steps: Current mode => false
		 * 1. Set icon => On
		 * 2. move_mode => true
		 * 
		 * 2-1. Set position to preference
		 * 
		 * 3. Update aAdapter
		 * 4. Re-set tiList
			****************************/
		
		item.setIcon(R.drawable.ifm8_thumb_actv_opt_menu_move_mode_on);
		
		move_mode = true;
		
		// Log
		Log.d("ALActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "move_mode => Now true");
		
		/****************************
		 * 2-1. Set position to preference
			****************************/
		// Log
		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "this.getSelectedItemPosition(): " + this.getSelectedItemPosition());

		Log.d("ALActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "this.getSelectedItemId(): " + this.getSelectedItemId());

		/****************************
		 * 4. Re-set tiList
			****************************/
//		String tableName = Methods.convertPathIntoTableName(this);

		String currentPath = Methods.get_currentPath_from_prefs(this);
		
		String tableName = Methods.convert_filePath_into_table_name(this, currentPath);
		
		// Log
		Log.d("ALActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "tableName: " + tableName);
		
		
		//
		tiList.clear();

		// Log
		Log.d("ALActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "tiList => Cleared");

//		Log.d("ALActv.java"
//				+ "["
//				+ Thread.currentThread().getStackTrace()[2]
//						.getLineNumber() + "]", "checkedPositions.size() => " + checkedPositions.size());

		if (long_searchedItems == null) {

			tiList = Methods.getAllData(this, tableName);
			
		} else {//if (long_searchedItems == null)

			// Log
			Log.d("ALActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "long_searchedItems != null");
			
			tiList = Methods.convert_fileIdArray2tiList(this, tableName, long_searchedItems);
			
		}//if (long_searchedItems == null)


		// Log
		Log.d("ALActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "tiList.size() => " + tiList.size());
		
		/****************************
		 * 3. Update aAdapter
			****************************/
		Methods.sort_tiList(tiList);
		
//		bAdapter =
//				new TIListAdapter(
//						this, 
//						R.layout.thumb_activity, 
//						tiList,
//						Methods.MoveMode.ON);
//
//		setListAdapter(bAdapter);

	}//private void move_mode_false(MenuItem item)


	private void move_mode_true(MenuItem item) {
		/****************************
		 * Steps: Current mode => false
		 * 1. Set icon => On
		 * 2. move_mode => false
		 * 2-2. TNActv.checkedPositions => clear()
		 * 
		 * 2-3. Get position from preference
		 * 
		 * 3. Re-set tiList
		 * 4. Update aAdapter
			****************************/
		
		item.setIcon(R.drawable.ifm8_thumb_actv_opt_menu_move_mode_off);
		
		move_mode = false;

		// Log
		Log.d("ALActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "move_mode => Now false");
		/****************************
		 * 2-2. TNActv.checkedPositions => clear()
			****************************/
		ALActv.checkedPositions.clear();
		
		/****************************
		 * 2-3. Get position from preference
			****************************/
		int selected_position = Methods.get_pref(this, tnactv_selected_item, 0);
		
		/****************************
		 * 3. Re-set tiList
			****************************/
//		String tableName = Methods.convertPathIntoTableName(this);
		String currentPath = Methods.get_currentPath_from_prefs(this);
		
		String tableName = Methods.convert_filePath_into_table_name(this, currentPath);


		tiList.clear();

		// Log
		Log.d("ALActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "tiList => Cleared");

		Log.d("ALActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "checkedPositions.size() => " + checkedPositions.size());
		
		if (long_searchedItems == null) {

			tiList.addAll(Methods.getAllData(this, tableName));
			
		} else {//if (long_searchedItems == null)

//			tiList = Methods.getAllData(this, tableName);
//			tiList = Methods.convert_fileIdArray2tiList(this, "IFM8", long_searchedItems);
			
		}//if (long_searchedItems == null)

		// Log
		Log.d("ALActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "tiList.size() => " + tiList.size());
		
		/****************************
		 * 4. Update aAdapter
			****************************/
		Methods.sort_tiList(tiList);
		
//		aAdapter = 
//				new TIListAdapter(
//						this, 
//						R.layout.thumb_activity, 
//						tiList,
//						Methods.MoveMode.OFF);
//		
//		setListAdapter(aAdapter);
		
		this.setSelection(selected_position);
		
	}//private void move_mode_true()

}//public class TNActv
