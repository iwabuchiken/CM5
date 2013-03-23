package cm5.listeners.dialog;

import cm5.tasks.RefreshDBTask;
import cm5.utils.CONS;
import cm5.utils.DBUtils;
import cm5.utils.Methods;
import cm5.utils.Methods_dialog;
import cm5.utils.Tags;
import cm5.items.BM;
import cm5.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogOnItemClickListener implements OnItemClickListener {

	//
	Activity actv;
	Dialog dlg;
	Dialog dlg2;
	
	BM bm;
	
	//
	Vibrator vib;
	
	//
//	Methods.DialogTags dlgTag = null;

	public DialogOnItemClickListener(Activity actv, Dialog dlg) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DialogOnItemClickListener(Activity actv, Dialog dlg, Dialog dlg2) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		this.dlg2 = dlg2;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DialogOnItemClickListener(Activity actv, Dialog dlg, BM bm) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		this.bm = bm;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	//	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Tags.DialogItemTags tag = (Tags.DialogItemTags) parent.getTag();
//		
		vib.vibrate(Methods.vibLength_click);
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
		case dlg_move_files://----------------------------------------------

			case_dlg_move_files(parent, position);
//			String folderPath = (String) parent.getItemAtPosition(position);
//			
//			Methods_dialog.dlg_confirm_moveFiles(actv, dlg, folderPath);

//			// debug
//			Toast.makeText(actv, "Move files to: " + folderPath, 2000)
//					.show();
			
			break;// case dlg_move_files

		case dlg_move_files_search://----------------------------------------------

			case_dlg_move_files_search(parent, position);
//			String folderPath = (String) parent.getItemAtPosition(position);
//			
//			Methods_dialog.dlg_confirm_moveFiles(actv, dlg, folderPath);

//			// debug
//			Toast.makeText(actv, "Move files to: " + folderPath, 2000)
//					.show();
			
			break;// case dlg_move_files

		case dlg_add_memos_gv://----------------------------------------------
			
			String word = (String) parent.getItemAtPosition(position);
			
			Methods.add_pattern_to_text(dlg, position, word);
			
//			String word = (String) parent.getItemAtPosition(position);
//			
//			EditText et = (EditText) dlg.findViewById(R.id.dlg_add_memos_et_content);
//			
//			String content = et.getText().toString();
//			
//			content += word + " ";
//			
//			et.setText(content);
//			
//			et.setSelection(et.getText().toString().length());
			
//			// debug
//			Toast.makeText(actv, word, 2000).show();
			
			break;
			
		case dlg_db_admin_lv://----------------------------------------------
			/*----------------------------
			 * 1. Get chosen item name
			 * 2. Switching
				----------------------------*/
			
			String item = (String) parent.getItemAtPosition(position);
			
//			// debug
//			Toast.makeText(actv, item, 2000).show();
			
			/*----------------------------
			 * 2. Switching
				----------------------------*/
			if (item.equals(actv.getString(R.string.dlg_db_admin_item_backup_db))) {
				
				Methods.db_backup(actv, dlg);
				
			} else if (item.equals(actv.getString(R.string.dlg_db_admin_item_refresh_db))){
				
				RefreshDBTask task_ = new RefreshDBTask(actv, dlg);
				
				// debug
				Toast.makeText(actv, "Starting a task...", 2000)
						.show();
				
				task_.execute("Start");

				dlg.dismiss();
				
//				// Log
//				Log.d("DialogOnItemClickListener.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "DB refresh");
				
				
//				Methods.refreshMainDB(actv, dlg);
				
			}
			
			break;// case dlg_add_memos_gv

		case dlg_admin_patterns_lv://----------------------------------------------
			/*----------------------------
			 * 1. Get chosen item name
			 * 2. Switching
				----------------------------*/
			
			item = (String) parent.getItemAtPosition(position);
			
//			// debug
//			Toast.makeText(actv, item, 2000).show();
			
			/*----------------------------
			 * 2. Switching
				----------------------------*/
			if (item.equals(actv.getString(R.string.generic_tv_register))) {
				
				Methods_dialog.dlg_register_patterns(actv, dlg);
				
//				// Log
//				Log.d("DialogOnItemClickListener.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]",
//						"item=" + item);
				
			} else if (item.equals(actv.getString(R.string.generic_tv_delete))) {

				Methods_dialog.dlg_delete_patterns(actv, dlg);
				
//				// Log
//				Log.d("DialogOnItemClickListener.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]",
//						"item=" + item);
				
			} else if (item.equals(actv.getString(R.string.generic_tv_edit))) {

				// Log
				Log.d("DialogOnItemClickListener.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
						"item=" + item);

			} else {//if (item.equals(actv.getString(R.string.generic_tv_register)))
				
				// Log
				Log.d("DialogOnItemClickListener.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
						"item=" + item);
				
			}//if (item.equals(actv.getString(R.string.generic_tv_register)))
			
			break;// case dlg_admin_patterns_lv

		case dlg_delete_patterns_gv://----------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
//			// debug
//			Toast.makeText(actv, item, 2000).show();
			
			Methods_dialog.dlg_confirm_delete_patterns(actv, dlg, dlg2, item);
			
			break;// case dlg_delete_patterns_gv
	
		case main_opt_menu_admin://----------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			case_main_opt_menu_admin(item);
			
			break;// case main_opt_menu_admin

		case dlg_bmactv_list_long_click://----------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			case_dlg_bmactv_list_long_click(item);
			
			break;// case dlg_bmactv_list_long_click

		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)

	private void
	case_dlg_bmactv_list_long_click(String item) {
		// TODO Auto-generated method stub
		if (item.equals(actv.getString(R.string.generic_tv_edit))) {	// Edit
			
			// debug
			Toast.makeText(actv, "Edit", Toast.LENGTH_LONG).show();
			
		} else if (item.equals(actv.getString(R.string.generic_tv_delete))) {
	
			bmactv_deleteItem(bm);
//			CONS.BMActv.bmList.remove(bm);
//			
//			CONS.BMActv.adpBML.notifyDataSetChanged();
			
		}//if (item.equals(actv.getString(R.string.generic_tv_edit)))
		
		
	}//case_dlg_bmactv_list_long_click(String item)

	private void bmactv_deleteItem(BM bm) {
		/***************************************
		 * Delete from: bmList
		 ***************************************/
		// TODO Auto-generated method stub
		CONS.BMActv.bmList.remove(bm);
		
		CONS.BMActv.adpBML.notifyDataSetChanged();

		/***************************************
		 * Delete from: Database
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		boolean res = dbu.deleteData_bm(actv, bm.getDbId());

		/***************************************
		 * Close dialog
		 ***************************************/
		dlg.dismiss();

	}//private void bmactv_deleteItem(BM bm)

	private void case_main_opt_menu_admin(String item) {
		
		if (item.equals(actv.getString(R.string.admin_reset_table_bm))) {
			
			case_main_opt_menu_admin_resetTable_bm();
			
//		} else if (item.equals(actv.getString(R.string.dlg_db_admin_item_refresh_db))){
			
		}//if (item.equals(actv.getString(R.string.admin_reset_table_bm)))

	}//private void case_main_opt_menu_admin(String item)

	private void case_main_opt_menu_admin_resetTable_bm() {
		// TODO Auto-generated method stub
		/***************************************
		 * Drop table
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		boolean res = dbu.dropTable(actv, CONS.DB.tname_BM);
		
		if (res == false) {
			
			// Log
			Log.d("DialogOnItemClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "res => null");
			
			return;
			
		}//if (res == false)
		
		/***************************************
		 * Create table
		 ***************************************/
		res = dbu.createTable(CONS.DB.tname_BM, CONS.DB.cols_bm, CONS.DB.col_types_bm);
		
	}//private void case_main_opt_menu_admin_resetTable_bm()

	private void case_dlg_move_files(AdapterView<?> parent, int position) {
		// TODO Auto-generated method stub
		String folderPath = (String) parent.getItemAtPosition(position);
		
		Methods_dialog.dlg_confirm_moveFiles(actv, dlg, folderPath);

	}//private void case_dlg_move_files(AdapterView<?> parent, int position)

	private void case_dlg_move_files_search(AdapterView<?> parent, int position) {
		// TODO Auto-generated method stub
		String folderPath = (String) parent.getItemAtPosition(position);
		
		Methods_dialog.dlg_confirm_moveFiles_search(actv, dlg, folderPath);

	}//private void case_dlg_move_files(AdapterView<?> parent, int position)

}//public class DialogOnItemClickListener implements OnItemClickListener
