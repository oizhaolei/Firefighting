package com.ruptech.firefighting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ruptech.firefighting.utils.ApkUpgrade;

/**
 * @author Administrator
 */
public class VersionCheckReceiver extends BroadcastReceiver {

    private final ApkUpgrade.ServerVersionListener serverInfoCheckTaskListener = new ApkUpgrade.ServerVersionListener() {
        @Override
        public void onServerVersion(int ver) {
            if (ver > App.getAppVersionCode()) {
                apkUpgrade.doApkUpdate();
            }
        }
    };
    private ApkUpgrade apkUpgrade;

    @Override
    public void onReceive(Context context, Intent intent) {
        getApkUpgrade(context).doRetrieveServerVersion(serverInfoCheckTaskListener);
    }

    private ApkUpgrade getApkUpgrade(Context context) {
        if (apkUpgrade == null) {
            apkUpgrade = new ApkUpgrade(context);
        }
        return apkUpgrade;
    }
}