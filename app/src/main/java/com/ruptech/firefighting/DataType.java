package com.ruptech.firefighting;

import android.os.AsyncTask;

import com.ruptech.firefighting.utils.PrefUtils;

import java.util.HashMap;
import java.util.Map;

public class DataType {

    static Map<Integer, String> taskStatus = new HashMap<Integer, String>();

    static {
        taskStatus.put(5, "暂停");
        taskStatus.put(6, "启动");
    }

    private static Map<Integer, String> EMPTY_MAP = new HashMap<Integer, String>();

    private static Map<Integer, String> deviceTypes;
    private static Map<Integer, String> systemTypes;
    private static Map<Integer, String> errorTypes;

    public static void init() {
        new TypesBackgroundTask().execute();
    }

    public static String getItemDevice(int id) {
        return getItemDeviceMap().get(id);
    }

    public static String getItemError(int id) {
        return getItemErrorMap().get(id);
    }

    public static String getItemSystem(int id) {
        return getItemSystemMap().get(id);
    }

    public static String getTaskStatus(int id) {
        return getTaskStatusMap().get(id);
    }

    public static Map<Integer, String> getItemDeviceMap() {
        if (deviceTypes == null)
            deviceTypes = PrefUtils.readDeviceTypes();
        if (deviceTypes == null)
            deviceTypes = EMPTY_MAP;
        return deviceTypes;
    }

    public static Map<Integer, String> getItemErrorMap() {
        if (errorTypes == null)
            errorTypes = PrefUtils.readErrorTypes();
        if (errorTypes == null)
            errorTypes = EMPTY_MAP;
        return errorTypes;
    }

    public static Map<Integer, String> getItemSystemMap() {
        if (systemTypes == null)
            systemTypes = PrefUtils.readSystemTypes();
        if (systemTypes == null)
            systemTypes = EMPTY_MAP;
        return systemTypes;
    }

    public static Map<Integer, String> getTaskStatusMap() {
        return taskStatus;
    }

    private static class TypesBackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Map<Integer, String> systemTypes = App.getHttpServer().getTypes("system");
                PrefUtils.writeSystemTypes(systemTypes);

                Map<Integer, String> deviceTypes = App.getHttpServer().getTypes("device");
                PrefUtils.writeDeviceTypes(deviceTypes);

                Map<Integer, String> errorTypes = App.getHttpServer().getTypes("error");
                PrefUtils.writeErrorTypes(errorTypes);

            } catch (Exception e) {
            }
            return null;
        }
    }

}
