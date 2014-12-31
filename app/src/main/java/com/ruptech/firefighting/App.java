package com.ruptech.firefighting;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.ruptech.firefighting.http.HttpServer;
import com.ruptech.firefighting.model.User;
import com.ruptech.firefighting.utils.AssetsPropertyReader;
import com.ruptech.firefighting.utils.PrefUtils;

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
    private AssetsPropertyReader assetsPropertyReader;

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
        if (user == null)
            return;
        PrefUtils.writeUser(user);
        App.user = user;
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

        assetsPropertyReader = new AssetsPropertyReader(this);
        properties = assetsPropertyReader.getProperties("env.properties");

    }
}



