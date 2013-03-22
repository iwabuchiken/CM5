package cm5.main;

import java.util.List;

import cm5.adapters.AILAdapter;
import cm5.adapters.BMLAdapter;
import cm5.items.AI;
import cm5.items.BM;
import cm5.utils.CONS;
import cm5.utils.DBUtils;
import cm5.utils.Methods;
import cm5.utils.Methods_CM5;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class BMActv extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.actv_bm);

		this.setTitle(this.getClass().getName());
		
		/***************************************
		 * Get: AI db id
		 ***************************************/
		AI ai = setup__getAI();

		/***************************************
		 * Set: File name and others to the text views
		 ***************************************/
		if (ai != null) {
			
			setup__2_setData2TextViews(ai);
			
		} else {//if (ai != null)
			
			// Log
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "ai == null");
			
		}//if (ai != null)
		
		
		
//		if (ai != null) {
//
//			// Log
//			Log.d("BMActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "ai.getFile_name()=" + ai.getFile_name());
//
//		} else {//if (ai != null)
//			
//			// Log
//			Log.d("BMActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ ":"
//					+ Thread.currentThread().getStackTrace()[2].getMethodName()
//					+ "]", "ai == null");
//			
//		}//if (ai != null)
		
		/***************************************
		 * Set: BM list
		 * 1. Build a BM list
		 * 2. Set the list to adapter
		 ***************************************/
		/***************************************
		 * 1. Build a BM list
		 ***************************************/
		DBUtils dbu = new DBUtils(this, CONS.dbName);
		
		List<BM> bmList = dbu.getBMList(this, ai.getDb_id());
		
		// Log
		Log.d("BMActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "bmList=" + bmList);
		
		/***************************************
		 * 2. Set the list to adapter
		 ***************************************/
		CONS.BMActv.adpBML = new BMLAdapter(
				this,
				R.layout.listrow_actv_bm,
//				R.layout.actv_al,
				bmList
				);

		setListAdapter(CONS.BMActv.adpBML);
		
	}//protected void onCreate(Bundle savedInstanceState)

	private void setup__2_setData2TextViews(AI ai) {
		// TODO Auto-generated method stub
		/***************************************
		 * Set: File name
		 ***************************************/
		TextView tvFileName = (TextView) findViewById(R.id.actv_bm_tv_file_name);
		
		tvFileName.setText(ai.getFile_name());
		
		/***************************************
		 * Set: Memo
		 ***************************************/
		TextView tvTitle = (TextView) findViewById(R.id.actv_bm_tv_title);
		
		tvTitle.setText(ai.getTitle());
		
	}//private void setup__2_setData2TextViews(AI ai)

	private AI setup__getAI() {
		/***************************************
		 * Get: db id
		 ***************************************/
		Intent i = this.getIntent();
		
		long aiDbId = i.getLongExtra(CONS.Intent.bmactv_key_ai_id, -1);
		
		if (aiDbId == -1) {
			
			// Log
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "aiDbId == -1");
			
			return null;
			
		}//if (aiDbId == -1)
		
		/***************************************
		 * Get: Table name
		 ***************************************/
		String tableName = i.getStringExtra(CONS.Intent.bmactv_key_table_name);
		
		// Log
		Log.d("BMActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "aiDbId=" + aiDbId);
		
		// Log
		Log.d("BMActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "tableName=" + tableName);
		
		/***************************************
		 * Build an AI instance
		 ***************************************/
		AI ai = Methods.get_data_ai(this, aiDbId, tableName);
		
		return ai;
		
	}//private AI setup__getAI()

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	
	
}
