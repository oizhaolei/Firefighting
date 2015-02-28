package com.ruptech.firefighting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

import com.ruptech.firefighting.utils.NetUtil;

public class StartupBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = StartupBroadcastReceiver.class.getName();
    private PendingIntent versionCheckPendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "action = " + action);

        cancelVersionCheck(context);
        if (TextUtils.equals(action, ConnectivityManager.CONNECTIVITY_ACTION)) {
            int connectivity = NetUtil.getNetworkState(context);

            if (connectivity != NetUtil.NETWORN_NONE) {
                //version check
                startVersionCheck(context);
            }
        } else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
            Log.d(TAG, "System shutdown, stopping service.");

        } else {
            startVersionCheck(context);
        }
    }

    private void startVersionCheck(Context context) {
        //version check
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent versionCheckIntent = new Intent(context, VersionCheckReceiver.class);
        versionCheckPendingIntent = PendingIntent.getBroadcast(context, 0, versionCheckIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, 0, 60 * 60 * 1000, versionCheckPendingIntent);
    }

    private void cancelVersionCheck(Context context) {
        if (versionCheckPendingIntent != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(versionCheckPendingIntent);
        }
    }

}
