package com.ruptech.firefighting;

import android.graphics.Color;
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
    private static final String TAG = DataType.class.getName();
    public static Map<Integer, String> EMPTY_MAP = new HashMap<Integer, String>();

    private static Map<Integer, String> deviceTypes;
    private static Map<Integer, String> systemTypes;
    private static Map<Integer, String> errorTypes;
    private static Map<Integer, String> maintainTaskStatus;
    private static Map<Integer, String> checkTaskStatus;
    private static Map<Integer, String> maintainItemStatus;
    private static Map<Integer, String> checkItemStatus;
    private static Map<Integer, String> worker;

    public static int readOnlyBackgroundColor = Color.parseColor("#f0f0f0");
    public static final String DATETIMEFORMAT = "yyyy/MM/dd HH:mm:ss";

    public static List<Map<String, Object>> getEmptyList() {
        return new ArrayList<Map<String, Object>>();
    }

    public static void init() {
        if ((getCheckItemStatusMap().isEmpty()) || (
                getCheckTaskStatusMap().isEmpty()) || (
                getItemDeviceMap().isEmpty()) || (
                getItemErrorMap().isEmpty()) || (
                getItemSystemMap().isEmpty()) || (
                getMaintainItemStatusMap().isEmpty()) || (
                getMaintainTaskStatusMap().isEmpty()) || (
                getWorkerMap().isEmpty())) {
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

    public static String getWorker(int id) {
        return getWorkerMap().get(id);
    }

    public static Map<Integer, String> getMaintainTaskStatusMap() {
        if (maintainTaskStatus == null)
            maintainTaskStatus = PrefUtils.readMaintainTaskStatusMap();
        if (maintainTaskStatus == null)
            maintainTaskStatus = EMPTY_MAP;
        return maintainTaskStatus;
    }

    public static Map<Integer, String> getMaintainTaskStatusMap(int currentStatusCode) {
        if (maintainTaskStatus == null)
            maintainTaskStatus = PrefUtils.readMaintainTaskStatusMap();
        if (maintainTaskStatus == null)
            maintainTaskStatus = EMPTY_MAP;

        Map<Integer, String> map = new HashMap<Integer, String>();
        switch (currentStatusCode) {
            case 1:
                // 等待派单
                map.put(new Integer(1), (String)maintainTaskStatus.get(new Integer(1)));
                map.put(new Integer(2), (String)maintainTaskStatus.get(new Integer(2)));
                break;
            case 2:
                // 正在执行
                map.put(new Integer(2), (String)maintainTaskStatus.get(new Integer(2)));
                map.put(new Integer(3), (String)maintainTaskStatus.get(new Integer(3)));
                map.put(new Integer(5), (String)maintainTaskStatus.get(new Integer(5)));
                break;
            case 3:
                // 挂起
                map.put(new Integer(1), (String)maintainTaskStatus.get(new Integer(1)));
                map.put(new Integer(2), (String)maintainTaskStatus.get(new Integer(2)));
                map.put(new Integer(3), (String)maintainTaskStatus.get(new Integer(3)));
                break;
            case 4:
                // 修好但未交工
                map.put(new Integer(4), (String)maintainTaskStatus.get(new Integer(4)));
                break;
            case 5:
                // 等待审核
                map.put(new Integer(5), (String)maintainTaskStatus.get(new Integer(5)));
                break;
            case 6:
                // 审核不合格
                map.put(new Integer(6), (String)maintainTaskStatus.get(new Integer(6)));
                break;
            case 7:
                // 审核合格
                map.put(new Integer(7), (String)maintainTaskStatus.get(new Integer(7)));
                break;
            case 8:
                // 略过维修
                map.put(new Integer(8), (String)maintainTaskStatus.get(new Integer(8)));
                break;
            default:
                break;
        }
        return map;
    }

    public static Map<Integer, String> getCheckTaskStatusMap() {
        if (checkTaskStatus == null)
            checkTaskStatus = PrefUtils.readCheckTaskStatusMap();
        if (checkTaskStatus == null)
            checkTaskStatus = EMPTY_MAP;
        return checkTaskStatus;
    }

    public static Map<Integer, String> getCheckTaskStatusMap(int currentStatusCode) {
        if (checkTaskStatus == null)
            checkTaskStatus = PrefUtils.readCheckTaskStatusMap();
        if (checkTaskStatus == null)
            checkTaskStatus = EMPTY_MAP;

        Map<Integer, String> map = new HashMap<Integer, String>();
        switch (currentStatusCode) {
            case 0:
                // 检查中
                map.put(new Integer(0), (String)checkTaskStatus.get(new Integer(0)));
                map.put(new Integer(1), (String)checkTaskStatus.get(new Integer(1)));
                break;
            case 1:
                // 审核中
                map.put(new Integer(1), (String)checkTaskStatus.get(new Integer(1)));
                break;
            case 2:
                // 未通过审核
                map.put(new Integer(2), (String)checkTaskStatus.get(new Integer(2)));
                break;
            case 3:
                // 已完成
                map.put(new Integer(3), (String)checkTaskStatus.get(new Integer(3)));
                break;
            case 4:
                // 回退
                map.put(new Integer(4), (String)checkTaskStatus.get(new Integer(4)));
                map.put(new Integer(5), (String)checkTaskStatus.get(new Integer(5)));
                break;
            case 5:
                // 未开始检查
                map.put(new Integer(0), (String)checkTaskStatus.get(new Integer(0)));
                map.put(new Integer(4), (String)checkTaskStatus.get(new Integer(4)));
                map.put(new Integer(5), (String)checkTaskStatus.get(new Integer(5)));
                break;
            default:
                break;
        }
        return map;
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

    public static Map<Integer, String> getItemDeviceMap(String system) {
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

    public static Map<Integer, String> getWorkerMap() {
        if (worker == null)
            worker = PrefUtils.readWorkerMap();
        if (worker == null)
            worker = EMPTY_MAP;
        return worker;
    }

    private static class TypesBackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Map<Integer, String> systemTypes = App.getHttpServer().getOption("system", "", "");
                PrefUtils.writeSystemTypes(systemTypes);

//                Map<Integer, String> deviceTypes = App.getHttpServer().getOption("device", "", "");
//                PrefUtils.writeDeviceTypes(deviceTypes);
//
//                Map<Integer, String> errorTypes = App.getHttpServer().getOption("error", "", "");
//                PrefUtils.writeErrorTypes(errorTypes);

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

                Map<Integer, String> worker
                        = App.getHttpServer().getWorker();
                PrefUtils.writeWorker(worker);

            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
            return null;
        }
    }

    public static Map<Integer, String> getWorkerMap(List<Map<String, Object>> workers) {
        Map<Integer, String> map = new HashMap<Integer,String>();
        for(Map<String, Object> worker : workers) {
            map.put(new Integer((String)worker.get("维修人员ID")), (String)worker.get("维修人员真实姓名"));
        }
        return map;
    }

}
