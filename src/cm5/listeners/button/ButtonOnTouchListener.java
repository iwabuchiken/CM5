package cm5.listeners.button;

import cm5.utils.Methods;
import cm5.utils.Tags;
import cm5.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ButtonOnTouchListener implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	public ButtonOnTouchListener(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			switch (tag) {
			
			case ib_up://----------------------------------------------------
				
				ImageButton ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_up_disenabled);
				
//				v.setBackgroundColor(Color.GRAY);
				
				break;// case ib_up
				
			case thumb_activity_ib_bottom://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_bottom_50x50_disenabled);
						
				break;// case thumb_activity_ib_bottom

			case thumb_activity_ib_top://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_top_50x50_disenabled);
				
				break;// case thumb_activity_ib_top

			case image_activity_prev://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_back_disenabled);
				
				break;// case image_activity_prev

			case image_activity_next://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_forward_disenabled);
				
				break;// case image_activity_next

			case actv_play_bt_play:
			case actv_play_bt_stop:
			case actv_play_bt_back:

				v.setBackgroundColor(Color.GRAY);
				break;
				
			case actv_play_tv_title:
				
				v.setBackgroundColor(Color.BLACK);
				
//				// Log
//				Log.d("ButtonOnTouchListener.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]",
////						"v.toString()=" + v.toString());
//						"((TextView)v).getText()=" + ((TextView)v).getText());
				
//				TextView tv = (TextView) v;
//				
//				tv.setTextColor(Color.WHITE);
				
				((TextView)v).setTextColor(Color.WHITE);
				
				break;

			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:

			
		case MotionEvent.ACTION_UP:
			switch (tag) {
			case ib_up://----------------------------------------------------
				
				ImageButton ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_up);
				
//				v.setBackgroundColor(Color.WHITE);
				
				break;// case ib_up
				
			case thumb_activity_ib_bottom://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_bottom_50x50);

				break;// case thumb_activity_ib_bottom
				
			case thumb_activity_ib_top://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_thumb_top_50x50);
				
				break;// case thumb_activity_ib_top

			case image_activity_prev://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_back);
				
				break;// case image_activity_prev

			case image_activity_next://----------------------------------------------------
				
				ib = (ImageButton) v;
				ib.setImageResource(R.drawable.ifm8_forward);
				
				break;// case image_activity_next

			case actv_play_bt_play:
			case actv_play_bt_stop:
			case actv_play_bt_back:

				v.setBackgroundColor(Color.WHITE);
				break;

			case actv_play_tv_title:
				
				v.setBackgroundColor(Color.WHITE);
				
				TextView tv = (TextView) v;
				
				tv.setTextColor(Color.BLACK);
//				tv.setTextColor(Color.BLUE);
				
				break;

			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
		}//switch (event.getActionMasked())
		return false;
	}

}