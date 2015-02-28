package com.ruptech.firefighting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ls_gao on 2015/2/27.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = BootBroadcastReceiver.class.getName();

    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "system boot completed");
    }
}
