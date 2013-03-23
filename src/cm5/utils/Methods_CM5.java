package cm5.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import cm5.items.BM;
import cm5.main.ALActv;
import cm5.tasks.Task_UpdateFileLength;

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


	
}//public class Methods_CM5
