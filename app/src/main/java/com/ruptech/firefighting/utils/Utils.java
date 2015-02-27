package com.ruptech.firefighting.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.widget.Toast;

import com.ruptech.firefighting.R;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by ls_gao on 2015/2/10.
 */
public class Utils {

    public static boolean isMobileNetworkAvailible(Context content) {
        ConnectivityManager conMan = (ConnectivityManager) content
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            // mobile 3G Data Network
            State mobile = conMan.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).getState();

            if (mobile == State.CONNECTED || mobile == State.CONNECTING)
                return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isWifiAvailible(Context content) {
        if (content == null) {
            return true;
        }

        ConnectivityManager conMan = (ConnectivityManager) content
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // wifi
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (wifi == State.CONNECTED || wifi == State.CONNECTING)
            return true;
        return false;
    }

    public static boolean checkNetwork(Context content) {
        boolean network = Utils.isMobileNetworkAvailible(content)
                || Utils.isWifiAvailible(content);
        return network;
    }

    /*
     * 工时的计算方法：
     * 可根据开始时间和结束时间计算工时。1小时=1工时，最小分辨率为0.5工时（不足15分钟不记工时，超过15分钟为0.5小时）。
     */
    public static String getWorkHour(long seconds) {
        long minutes = seconds / 60;
        int hours = (int)(minutes / 60);
        int rest_minutes = (int)(minutes % 60);
        String workhour = "0.0";
        if(rest_minutes >= 45) {
            workhour = new Integer(hours + 1).toString();
        } else if(rest_minutes < 45 && rest_minutes >= 15) {
            workhour = new Integer(hours).toString() + ".5";
        } else {
            workhour = new Integer(hours).toString();
        }


        return workhour;
    }

}
