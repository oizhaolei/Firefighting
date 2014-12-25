package com.ruptech.firefighting;

import android.app.Application;
import android.util.Log;

import com.ruptech.firefighting.utils.AssetsPropertyReader;

import java.util.Properties;


/**
 * A login screen that offers login via email/password.
 */
public class App extends Application implements
        Thread.UncaughtExceptionHandler {
    public final static String TAG = App.class.getSimpleName();
    static public Properties properties;
    private AssetsPropertyReader assetsPropertyReader;

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

        assetsPropertyReader = new AssetsPropertyReader(this);
        properties = assetsPropertyReader.getProperties("env.properties");

    }

}



