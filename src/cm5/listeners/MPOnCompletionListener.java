package cm5.listeners;

import cm5.main.PlayActv;
import cm5.utils.Methods;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.widget.Toast;

public class MPOnCompletionListener implements OnCompletionListener {

	//
	Activity actv;
	
	public MPOnCompletionListener(Activity actv) {
		
		this.actv = actv;
		
	}
	
	//@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO 自動生成されたメソッド・スタブ
//		Methods.stopPlayer(actv);
		PlayActv.mp.stop();
		
		// debug
		Toast.makeText(actv, "Complete", 2000).show();
		
	}

}
