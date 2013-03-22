package cm5.utils;

import java.util.List;

import android.app.Activity;
import cm5.items.BM;
import cm5.main.ALActv;
import cm5.tasks.Task_UpdateFileLength;

public class Methods_CM5 {

	public static void updateFileLength(Activity actv) {
		
		Task_UpdateFileLength task = new Task_UpdateFileLength(actv);
		
		task.execute("Start");
		
	}//public static void updateFileLength(Activity actv)

	
}
