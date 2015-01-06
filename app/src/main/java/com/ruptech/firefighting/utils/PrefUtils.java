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
import java.util.Map;

public class PrefUtils {

    final public static String PREF_USER = "USER_INFO";
    private static final String TAG = PrefUtils.class.getSimpleName();
    private static final String PREF_DEVICE = "PREF_DEVICE";
    private static final String PREF_SYSTEM = "PREF_SYSTEM";
    private static final String PREF_ERROR = "PREF_ERROR";
    private static SharedPreferences mPref;


    private static SharedPreferences getPref() {
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(App.mContext);
        }
        return mPref;
    }

    public static void removePref(String prefKey) {
        getPref().edit().remove(prefKey).commit();
    }

    public static User readUser() {
        return (User) readObject(PREF_USER);
    }

    public static Map<Integer, String> readSystemTypes() {
        return (Map<Integer, String>) readObject(PREF_SYSTEM);
    }

    public static Map<Integer, String> readErrorTypes() {
        return (Map<Integer, String>) readObject(PREF_ERROR);
    }

    public static Map<Integer, String> readDeviceTypes() {
        return (Map<Integer, String>) readObject(PREF_DEVICE);
    }

    public static void writeUser(User user) {
        writeObject(user, PREF_USER);
    }

    public static void writeSystemTypes(Map<Integer, String> types) {
        writeObject(types, PREF_SYSTEM);
    }

    public static void writeErrorTypes(Map<Integer, String> types) {
        writeObject(types, PREF_ERROR);
    }

    public static void writeDeviceTypes(Map<Integer, String> types) {
        writeObject(types, PREF_DEVICE);
    }

    private static Object readObject(String prefKey) {
        String str = getPref().getString(prefKey, "");
        byte[] bytes = str.getBytes();
        if (bytes.length == 0) {
            return null;
        }
        try {
            ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
            Base64InputStream base64InputStream = new Base64InputStream(
                    byteArray, Base64.DEFAULT);
            ObjectInputStream in = new ObjectInputStream(base64InputStream);
            Object obj = in.readObject();
            in.close();
            return obj;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    private static void writeObject(Object obj, String prefKey) {
        if (obj == null) {
            removePref(prefKey);
        } else {
            try {
                ByteArrayOutputStream out;
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

                ObjectOutputStream objectOutput;
                objectOutput = new ObjectOutputStream(arrayOutputStream);
                objectOutput.writeObject(obj);
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
                getPref().edit().putString(prefKey, str).commit();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);

            }
        }
    }
}
