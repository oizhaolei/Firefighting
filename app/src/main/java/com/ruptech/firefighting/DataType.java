package com.ruptech.firefighting;

import android.os.AsyncTask;
import android.util.Log;

import com.ruptech.firefighting.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataType {
    public static final String TAB_UNDO = "undo";
    public static final String TAB_AUDIT = "audit";
    public static final String TYPE_MAINTAIN = "maintain";
    public static final String TYPE_CHECK = "check";
    public static final List<Map<String, Object>> EMPTY_LIST = new ArrayList<Map<String, Object>>();
    private static final String TAG = DataType.class.getName();
    public static Map<Integer, String> EMPTY_MAP = new HashMap<Integer, String>();

    private static Map<Integer, String> deviceTypes;
    private static Map<Integer, String> systemTypes;
    private static Map<Integer, String> errorTypes;
    private static Map<Integer, String> maintainTaskStatus;
    private static Map<Integer, String> checkTaskStatus;
    private static Map<Integer, String> maintainItemStatus;
    private static Map<Integer, String> checkItemStatus;

    public static void init() {
        if ((getCheckItemStatusMap().isEmpty()) || (
                getCheckTaskStatusMap().isEmpty()) || (
                getItemDeviceMap().isEmpty()) || (
                getItemErrorMap().isEmpty()) || (
                getItemSystemMap().isEmpty()) || (
                getMaintainItemStatusMap().isEmpty()) || (
                getMaintainTaskStatusMap().isEmpty())) {
            new TypesBackgroundTask().execute();
        }
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

    public static String getMaintainTaskStatus(int id) {
        return getMaintainTaskStatusMap().get(id);
    }

    public static String getCheckTaskStatus(int id) {
        return getCheckTaskStatusMap().get(id);
    }

    public static String getMaintainItemStatus(int id) {
        return getMaintainItemStatusMap().get(id);
    }

    public static String getCheckItemStatus(int id) {
        return getCheckItemStatusMap().get(id);
    }


    public static Map<Integer, String> getMaintainTaskStatusMap() {
        if (maintainTaskStatus == null)
            maintainTaskStatus = PrefUtils.readMaintainTaskStatusMap();
        if (maintainTaskStatus == null)
            maintainTaskStatus = EMPTY_MAP;
        return maintainTaskStatus;
    }

    public static Map<Integer, String> getCheckTaskStatusMap() {
        if (checkTaskStatus == null)
            checkTaskStatus = PrefUtils.readCheckTaskStatusMap();
        if (checkTaskStatus == null)
            checkTaskStatus = EMPTY_MAP;
        return checkTaskStatus;
    }

    public static Map<Integer, String> getMaintainItemStatusMap() {
        if (maintainItemStatus == null)
            maintainItemStatus = PrefUtils.readMaintainItemStatusMap();
        if (maintainItemStatus == null)
            maintainItemStatus = EMPTY_MAP;
        return maintainItemStatus;
    }

    public static Map<Integer, String> getCheckItemStatusMap() {
        if (checkItemStatus == null)
            checkItemStatus = PrefUtils.readCheckItemStatusMap();
        if (checkItemStatus == null)
            checkItemStatus = EMPTY_MAP;
        return checkItemStatus;
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

    private static class TypesBackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Map<Integer, String> systemTypes = App.getHttpServer().getOptions("system");
                PrefUtils.writeSystemTypes(systemTypes);

                Map<Integer, String> deviceTypes = App.getHttpServer().getOptions("device");
                PrefUtils.writeDeviceTypes(deviceTypes);

                Map<Integer, String> errorTypes = App.getHttpServer().getOptions("error");
                PrefUtils.writeErrorTypes(errorTypes);

                Map<Integer, String> maintainTaskStatus
                        = App.getHttpServer().getStatus("maintaintask");
                PrefUtils.writeMaintainTaskStatus(maintainTaskStatus);

                Map<Integer, String> checkTaskStatus
                        = App.getHttpServer().getStatus("checktask");
                PrefUtils.writeCheckTaskStatus(checkTaskStatus);

                Map<Integer, String> maintainItemStatus
                        = App.getHttpServer().getStatus("maintainitem");
                PrefUtils.writeMaintainItemStatus(maintainItemStatus);

                Map<Integer, String> checkItemStatus
                        = App.getHttpServer().getStatus("checkitem");
                PrefUtils.writeCheckItemStatus(checkItemStatus);

            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
            return null;
        }
    }

}
