package cm5.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cm5.items.AI;
import cm5.items.BM;
import cm5.items.HI;
import cm5.main.ALActv;
import cm5.tasks.Task_UpdateFileLength;
import cm5.utils.CONS.SORT_ORDER;

public class Methods_CM5 {

	public static void updateFileLength(Activity actv) {
		
		Task_UpdateFileLength task = new Task_UpdateFileLength(actv);
		
		task.execute("Start");
		
	}//public static void updateFileLength(Activity actv)

	
	public static
	List<BM> getBMList_FromCursor(Activity actv, Cursor c) {
		
		List<BM> bmList = new ArrayList<BM>();
		
		while(c.moveToNext()) {
			
			BM bm = new BM.Builder()
				.setDbId(c.getLong(c.getColumnIndex(android.provider.BaseColumns._ID)))
				.setPosition(c.getLong(c.getColumnIndex("position")))
				.setTitle(c.getString(c.getColumnIndex("title")))
				.setMemo(c.getString(c.getColumnIndex("memo")))
				.setAiId(c.getLong(c.getColumnIndex("ai_id")))
				.setAiTableName(c.getString(c.getColumnIndex("aiTableName")))
				.build();
					
			if (bm != null) {
				
				bmList.add(bm);
				
			} else {//if (bm != null)
				
				// Log
				Log.d("Methods_CM5.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]", "bm != null");
				
				continue;
				
			}//if (bm != null)
			
		}//while(c.moveToNext())
		
		return bmList;
		
	}//List<BM> getBMList_FromCursor(Activity actv, Cursor c)

	/***************************************
	 * @return null ... Table doesn't exist and can't create a table<br>
	 * 
	 ***************************************/
	public static List<HI> getAllData_HI(Activity actv) {
		// TODO Auto-generated method stub
		/*********************************
		 * 0. Table exists?
		 * 1. DB setup
		 * 2. Get data
		 * 		2.1. Get cursor
		 * 		2.2. Add to list
		 * 
		 * 9. Close db
		 * 10. Return value
		 *********************************/
		/*********************************
		 * 1. DB setup
		 *********************************/
		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		/****************************
		 * 0. Table exists?
			****************************/
		boolean res = dbu.tableExists(wdb, CONS.History.tname_history);
		
		if (res == false) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]",
					"getAllData() => Table doesn't exist: "
					+ CONS.History.tname_history);
			
			res = dbu.createTable(
							CONS.History.tname_history,
							CONS.History.cols_history,
							CONS.History.col_types_history);
			if (res == true) {
				
				// Log
				Log.d("Methods_CM5.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]",
						"Table created: " + CONS.History.tname_history);
				
				wdb.close();
				
//				return null;
				
			} else {//if (res == true)
				
				// Log
				Log.d("Methods_CM5.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
						+ ":"
						+ Thread.currentThread().getStackTrace()[2]
								.getMethodName() + "]",
						"Create table => Failed: " + CONS.History.tname_history);

				wdb.close();
				
				return null;
				
			}//if (res == true)
			
		} else {//if (res == false)
			
			// Log
			Log.d("Methods_CM5.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Table exists: " + CONS.History.tname_history);
			
			wdb.close();
			
		}//if (res == false)
		
		/****************************
		 * 2. Get data
		 * 		2.1. Get cursor
		 * 		2.2. Add to list
			****************************/
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		String sql = "SELECT * FROM " + CONS.History.tname_history;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
		} catch (Exception e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return null;
		}

		/****************************
		 * 2.2. Add to list
			****************************/
		c.moveToFirst();
		
		List<HI> hiList = new ArrayList<HI>();
		
		for (int i = 0; i < c.getCount(); i++) {
			
//			"aiId", 	"aiTableName"
			HI hi = new HI.Builder()
					.setDbId(0)
					.setCreatedAt(c.getLong(1))
					.setModifiedAt(2)
					.setAiId(c.getLong(c.getColumnIndex("aiId")))
					.setAiTableName(c.getString(c.getColumnIndex("aiTableName")))
					.build();
			
			// Add to the list
			hiList.add(hi);
			
			//
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		/****************************
		 * 9. Close db
			****************************/
		rdb.close();
		
		/****************************
		 * 10. Return value
			****************************/
		
		return hiList;
		
	}//public static List<HI> getAllData_HI(Activity actv)


	public static void
	sortList_HI_createdAt(List<HI> hiList, final SORT_ORDER sortOrder) {
		// TODO Auto-generated method stub
		Collections.sort(hiList, new Comparator<HI>(){

//			@Override
			public int compare(HI h1, HI h2) {
				
				int res;
				
				switch (sortOrder) {
				
				case CREATED_AT://-----------------------------
					
//					res = (int) (h1.getCreated_at() - h2.getCreated_at());
					res = (int) (h1.getCreatedAt() - h2.getCreatedAt());
					
					break;// case CREATED_AT
					
				default:
					
					res = 1;
					
					break;
					
				}
				
				return res;
				
//				return (int) (h1.getCreated_at() - h2.getCreated_at());
			}
			
		});//Collections.sort()

	}//sortList_HI_createdAt(List<HI> hiList, SORT_ORDER sortOrder)
	
}//public class Methods_CM5
