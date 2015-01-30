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
    private static final String PREF_PUSH_TOKEN = "PREF_PUSH_TOKEN";
    private static final String PREF_STATUS_CHECK_TASK = "PREF_STATUS_CHECK_TASK";
    private static final String PREF_STATUS_MAINTAIN_TASK = "PREF_STATUS_MAINTAIN_TASK";
    private static final String PREF_STATUS_MAINTAIN_ITEM = "PREF_STATUS_MAINTAIN_ITEM";
    private static final String PREF_STATUS_CHECK_ITEM = "PREF_STATUS_CHECK_ITEM";
    private static final String PREF_WORKER = "PREF_WORKER";
    private static SharedPreferences mPref;


    private static SharedPreferences getPref() {
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(App.mContext);
        }
        return mPref;
    }

    public static String readPushToken() {
        return getPref().getString(PREF_PUSH_TOKEN, null);
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

    public static Map<Integer, String> readMaintainTaskStatusMap() {
        return (Map<Integer, String>) readObject(PREF_STATUS_MAINTAIN_TASK);
    }

    public static Map<Integer, String> readCheckTaskStatusMap() {
        return (Map<Integer, String>) readObject(PREF_STATUS_CHECK_TASK);
    }

    public static Map<Integer, String> readMaintainItemStatusMap() {
        return (Map<Integer, String>) readObject(PREF_STATUS_MAINTAIN_ITEM);
    }

    public static Map<Integer, String> readCheckItemStatusMap() {
        return (Map<Integer, String>) readObject(PREF_STATUS_CHECK_ITEM);
    }

    public static Map<Integer, String> readWorkerMap() {
        return (Map<Integer, String>) readObject(PREF_WORKER);
    }

    public static void writeMaintainTaskStatus(Map<Integer, String> maintaintasks) {
        writeObject(PREF_STATUS_MAINTAIN_TASK, maintaintasks);
    }

    public static void writeCheckItemStatus(Map<Integer, String> checkItemStatus) {
        writeObject(PREF_STATUS_CHECK_ITEM, checkItemStatus);
    }

    public static void writeMaintainItemStatus(Map<Integer, String> maintainItemStatus) {
        writeObject(PREF_STATUS_MAINTAIN_ITEM, maintainItemStatus);
    }

    public static void writeCheckTaskStatus(Map<Integer, String> checkTaskStatus) {
        writeObject(PREF_STATUS_CHECK_TASK, checkTaskStatus);
    }

    public static void writeWorker(Map<Integer, String> worker) {
        writeObject(PREF_WORKER, worker);
    }

    public static void writeUser(User user) {
        writeObject(PREF_USER, user);
    }


    public static void writePushToken(String token) {
        if (token == null) {
            getPref().edit().remove(PREF_PUSH_TOKEN).commit();
        } else {
            getPref().edit().putString(PREF_PUSH_TOKEN, token).commit();
        }
    }

    public static void writeSystemTypes(Map<Integer, String> types) {
        writeObject(PREF_SYSTEM, types);
    }

    public static void writeErrorTypes(Map<Integer, String> types) {
        writeObject(PREF_ERROR, types);
    }

    public static void writeDeviceTypes(Map<Integer, String> types) {
        writeObject(PREF_DEVICE, types);
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

    private static void writeObject(String prefKey, Object obj) {
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
