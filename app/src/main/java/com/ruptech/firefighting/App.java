package com.ruptech.firefighting;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.ruptech.firefighting.http.HttpServer;
import com.ruptech.firefighting.main.MainActivity;
import com.ruptech.firefighting.model.User;
import com.ruptech.firefighting.utils.AssetsPropertyReader;
import com.ruptech.firefighting.utils.PrefUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * A login screen that offers login via email/password.
 */
public class App extends Application implements
        Thread.UncaughtExceptionHandler {
    public final static String TAG = App.class.getName();
    static public Properties properties;
    public static Context mContext;
    private static HttpServer httpServer;
    private static User user;

    public static int getAppVersionCode() {
        int verCode = 0;
        try {
            PackageInfo packageInfo = App.mContext.getPackageManager()
                    .getPackageInfo(App.mContext.getPackageName(), 0);
            verCode = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            if (BuildConfig.DEBUG)
                Log.e(TAG, e.getMessage(), e);
        }

        return verCode;
    }

    public static HttpServer getHttpServer() {
        if (httpServer == null) {
            httpServer = new HttpServer();
        }
        return httpServer;
    }

    public static User readUser() {
        if (user == null)
            user = PrefUtils.readUser();
        return user;
    }

    public static void saveUser(User user) {
        PrefUtils.writeUser(user);
        App.user = user;
    }

    public static void logout() {
        Log.v(TAG, "logout.");
        MainActivity.close();
        App.saveUser(null);
    }

    public static Map getItemDeviceMap() {
        Map choices = new HashMap<String, String>();
        choices.put(5, "暂停");
        choices.put(6, "启动");
        return choices;
    }

    public static Map getItemErrorMap() {
        Map choices = new HashMap<String, String>();
        choices.put(5, "暂停");
        choices.put(6, "启动");
        return choices;
    }

    public static Map getItemSystemMap() {
        Map choices = new HashMap<String, String>();
        choices.put(5, "暂停");
        choices.put(6, "启动");
        return choices;
    }

    public static Map getTaskStatusMap() {
        Map choices = new HashMap<String, String>();
        choices.put(5, "暂停");
        choices.put(6, "启动");
        return choices;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, thread.getName(), throwable);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG)
            Log.e(TAG, "App.onCreate");

        mContext = this.getApplicationContext();

        AssetsPropertyReader assetsPropertyReader = new AssetsPropertyReader(this);
        properties = assetsPropertyReader.getProperties("env.properties");

    }

}



