package com.ruptech.firefighting.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.util.Log;

import com.ruptech.firefighting.App;
import com.ruptech.firefighting.model.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PrefUtils {

    final public static String PREF_USER = "USER_INFO";
    private static final String TAG = PrefUtils.class.getSimpleName();
    private static SharedPreferences mPref;


    private static SharedPreferences getPref() {
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(App.mContext);
        }
        return mPref;
    }

    public static void removePrefUser() {
        getPref().edit().remove(PREF_USER).commit();
    }

    private static String getUserStr() {
        return getPref().getString(PREF_USER, "");
    }

    public static User readUser() {
        String str = getUserStr();
        byte[] bytes = str.getBytes();
        if (bytes.length == 0) {
            return null;
        }
        User user;
        try {
            ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
            Base64InputStream base64InputStream = new Base64InputStream(
                    byteArray, Base64.DEFAULT);
            ObjectInputStream in = new ObjectInputStream(base64InputStream);
            user = (User) in.readObject();
            in.close();
            return user;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public static void writeUser(User user) {
        if (user == null) {
            removePrefUser();
        } else {
            try {
                ByteArrayOutputStream out;
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

                ObjectOutputStream objectOutput;
                objectOutput = new ObjectOutputStream(arrayOutputStream);
                objectOutput.writeObject(user);
                byte[] data = arrayOutputStream.toByteArray();
                objectOutput.close();
                arrayOutputStream.close();

                out = new ByteArrayOutputStream();
                Base64OutputStream b64 = new Base64OutputStream(out,
                        Base64.DEFAULT);
                b64.write(data);
                b64.close();
                out.close();
                String str = new String(out.toByteArray());
                getPref().edit().putString(PREF_USER, str).commit();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);

            }
        }
    }
}
