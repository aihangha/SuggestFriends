package mekong89.suggestfriend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(arg0,CheckOnlineService.class);
        arg0.startService(intent);
        Log.d("Mekong89", "WECOM started");
	}

}
