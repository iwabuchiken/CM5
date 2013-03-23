package cm5.listeners;

import cm5.main.PlayActv;
import cm5.main.R;
import cm5.utils.CONS;
import cm5.utils.Methods;
import android.app.Activity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarChangeListener implements OnSeekBarChangeListener {

	Activity actv;
	SeekBar sb;
	
	public SeekBarChangeListener(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
	}

	public SeekBarChangeListener(Activity actv, SeekBar sb) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		this.sb = sb;
	}

	public void
	onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		
		// Log
		Log.d("SeekBarChangeListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "progress=" + progress);
		
		// Log
		Log.d("SeekBarChangeListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "fromUser=" + fromUser);
		
		// Log
		Log.d("SeekBarChangeListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]",
				"PlayActv.ai.getLength()="
				+ Methods.convert_intSec2Digits_lessThanHour((int)PlayActv.ai.getLength() / 1000));
		
		int seekedPosition = (int) ((float)progress / sb.getMax() * (float)PlayActv.ai.getLength());
		
		// Log
		Log.d("SeekBarChangeListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "(float)progress / sb.getMax() = " + ((float)progress / sb.getMax()));
		
		// Log
		Log.d("SeekBarChangeListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]",
				"seekedPosition=" + seekedPosition
				+ "("
				+ Methods.convert_intSec2Digits_lessThanHour(seekedPosition / 1000)
				+ ")");

		/***************************************
		 * Set: Current position to the view
		 ***************************************/
		if (CONS.PlayActv.tvCurrentPosition == null) {

			CONS.PlayActv.tvCurrentPosition = (TextView) actv.findViewById(R.id.actv_play_tv_current_position);
			
		}//if (CONS.PlayActv.tvCurrentPosition == null)
//		CONS.PlayActv.tvCurrentPosition = (TextView) this.findViewById(R.id.actv_play_tv_current_position);
		
		
		CONS.PlayActv.tvCurrentPosition.setText(Methods.convert_intSec2Digits_lessThanHour(seekedPosition / 1000));

		
	}//onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)

	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

}
