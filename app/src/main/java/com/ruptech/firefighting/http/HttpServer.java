package com.ruptech.firefighting.http;


import android.util.Log;

import com.ruptech.firefighting.BuildConfig;
import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.model.User;
import com.ruptech.firefighting.utils.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServer extends HttpConnection {
    private static final String API_COMPANY = "Query/Company.aspx";
    private static final String API_ITEM_DELETE = "Item/ItemDelete.aspx";
    private static final String API_ITEM_DETAIL = "Item/ItemDetail.aspx";
    private static final String API_ITEM_EDIT = "Item/ItemEdit.aspx";
    private static final String API_ITEM_LIST = "Item/ItemList.aspx";
    private static final String API_LOGIN = "Login/Login.aspx";
    private static final String API_STATUS = "Query/Status.aspx";
    private static final String API_TASK_DETAIL = "Task/TaskDetail.aspx";
    private static final String API_TASK_EDIT = "Task/TaskEdit.aspx";
    private static final String API_TASK_LIST = "Task/TaskList.aspx";
    private static final String API_OPTION = "Query/Option.aspx";
    private static final String API_WORKER = "Query/Worker.aspx";
    private static final String API_WORKHOUR_EDIT = "WorkHour/WorkHourEdit.aspx";
    private static final String API_WORKHOUR_SUM = "WorkHour/WorkHourSum.aspx";
    private static final String API_WORKLOG_DETAIL = "WorkLog/WorkLogDetail.aspx";
    private static final String API_WORKLOG_EDIT = "WorkLog/WorkLogEdit.aspx";
    private static final String API_BAIDU_PUSH_REGISTER = "ThirdParty/Baidu/BaiduUserTokenSave.aspx";

    private final String TAG = HttpServer.class.getSimpleName();

    // Low-level interface

    private static String strJoin(String[] aArr, String sSep) {
        StringBuilder sbStr = new StringBuilder();
        for (int i = 0, il = aArr.length; i < il; i++) {
            if (i > 0)
                sbStr.append(sSep);
            sbStr.append(aArr[i]);
        }
        return sbStr.toString();
    }

    public Map<String, Object> genEmptyWorklog(String taskId, String type) throws JSONException {
        String s = "{" +
                "        \"ID\": \"\"," +
                "        \"标题\": \"\"," +
                "        \"详细描述\": \"\"," +
                "        \"是否提交\": \"\"" +
                "    }";
        if(DataType.TYPE_MAINTAIN.equals(type)) {
            s = "{" +
                    "        \"ID\": \"\"," +
                    "        \"标题\": \"\"," +
                    "        \"详细描述\": \"\"," +
                    "        \"是否提交\": \"0\"," +
                    "        \"维修任务ID\": \"" + taskId +"\"" +
                    "    }";
        } else if(DataType.TYPE_CHECK.equals(type)) {
            s = "{" +
                    "        \"ID\": \"\"," +
                    "        \"标题\": \"\"," +
                    "        \"详细描述\": \"\"," +
                    "        \"是否提交\": \"0\"," +
                    "        \"CId\": \"" + taskId +"\"," +
                    "        \"FId\": \"\"," +
                    "        \"SId\": \"0\"" +
                    "    }";
        }
        return jsonToMap(new JSONObject(s));
    }

    public Map<String, Object> genEmptyWorkHour(String type, String CId, String FId, String SId) throws JSONException {
        String s = "";
        if(DataType.TYPE_MAINTAIN.equals(type)) {
            s = "{" +
                    "        \"ID\": \"\"," +
                    "        \"维修人员ID\": \"\"," +
                    "        \"开始时间\": \"\"," +
                    "        \"结束时间\": \"\"," +
                    "        \"工时\": \"\"," +
                    "        \"维修人员姓名\": \"\"" +
                    "    }";
        } else if(DataType.TYPE_CHECK.equals(type)) {
            s = "{" +
                    "        \"ID\": \"\"," +
                    "        \"维修人员ID\": \"\"," +
                    "        \"开始时间\": \"\"," +
                    "        \"结束时间\": \"\"," +
                    "        \"工时\": \"\"," +
                    "        \"维修人员姓名\": \"\"," +
                    "        \"CId\": \"" + CId + "\"," +
                    "        \"FId\": \"" + FId + "\"," +
                    "        \"SId\": \"" + SId + "\"" +
                    "    }";
        }
        return jsonToMap(new JSONObject(s));
    }

    public Map<String, Object> genEmptyItem(String type, String parentId) throws JSONException {
        String s = "{"
                + "\"ID\": \"\","
                + "\"受理编号\": \"\","
                + "\"部件报修来源\": \"\","
                + "\"报修时间\": \"\","
                + "\"结束时间\": \"\","
                + "\"维修状态\": \"\","
                + "\"单位ID\": \"\","
                + "\"中心ID\": \"\","
                + "\"维修状态名称\": \"\""
                + "}";
        if(DataType.TYPE_MAINTAIN.equals(type)) {
            s = "{"
                    + "\"ID\": \"\","
                    + "\"企业编码\": \"\","
                    + "\"故障内容\": \"\","
                    + "\"结束时间\": \"\","
                    + "\"系统类型ID\": \"\","
                    + "\"系统类型名称\": \"\","
                    + "\"设备单项\": \"\","
                    + "\"设备单项名称\": \"\","
                    + "\"故障单项\": \"\","
                    + "\"故障单项名称\": \"\","
                    + "\"维修措施\": \"\","
                    + "\"维修状态\": \"\","
                    + "\"维修状态名称\": \"\""
                + "}";
        } else if(DataType.TYPE_CHECK.equals(type)) {
            s = "{"
                    + "\"PartId\": \"\","
                    + "\"SId\":\"" + parentId + "\","
                    + "\"企业编码\": \"\","
                    + "\"检查状态\": \"\","
                    + "\"检查状态名称\": \"\","
                    + "\"实际系统类型ID\": \"\","
                    + "\"系统类型名称\": \"\","
                    + "\"实际设备单项\": \"\","
                    + "\"设备单项名称\": \"\","
                    + "\"实际故障单项\": \"\","
                    + "\"故障单项名称\": \"\","
                    + "\"故障内容\": \"\""
                + "}";
        }
        return jsonToMap(new JSONObject(s));
    }

    public User userLogin(String username, String password)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        Response res = _get(API_LOGIN, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");

            User user = new User(data);

            return user;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public boolean baiduPushRegist(String id, String token)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);

        Response res = _get(API_BAIDU_PUSH_REGISTER, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }
        return success;
    }

    public String editWorkHour(String id, String type, String[] columns, String[] values)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);
        params.put("column", strJoin(columns, ","));
        params.put("value", strJoin(values, ","));

        Response res = _get(API_WORKHOUR_EDIT, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }

        String newId = "";
        try {
            newId = result.getString("id");
        } catch (Exception e) {
            newId = "";
        }
        return newId;
    }

    public String editWorklog(String id, String type, String[] columns, String[] values, List<Map<String, Object>> workhours)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);
        params.put("column", strJoin(columns, ","));
        params.put("value", strJoin(values, ","));
        if(null != workhours && workhours.size() > 0) {
            params.put("data", "{\"workhours\":" + new JSONArray(workhours) + "}");
        }

        Response res = _get(API_WORKLOG_EDIT, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }

        String newId = "";
        try {
            newId = result.getString("id");
        } catch (Exception e) {
            newId = "";
        }
        return newId;
    }

    public String editItem(String id, String type, String[] columns, String[] values, String parentid)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);
        params.put("parentid", parentid);
        params.put("column", strJoin(columns, ","));
        params.put("value", strJoin(values, ","));

        Response res = _get(API_ITEM_EDIT, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }
        String newId = "";
        try {
            newId = result.getString("id");
        } catch (Exception e) {
            newId = "";
        }
        return newId;
    }

    public boolean deleteItem(String id, String type)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);

        Response res = _get(API_ITEM_DELETE, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }
        return success;
    }

    public boolean editTask(String id, String type, String[] column, String[] value)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);
        params.put("column", strJoin(column, ","));
        params.put("value", strJoin(value, ","));

        Response res = _get(API_TASK_EDIT, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }
        return success;
    }


    public List<Map<String, Object>> getTaskList(String status) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("status", status);
        params.put("loginid", PrefUtils.readUser().get编号());

        Response res = _get(API_TASK_LIST, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONArray data = result.getJSONArray("data");
            List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < data.length(); i++) {
                JSONObject jo = data.getJSONObject(i);
                tasks.add(jsonToMap(jo));
            }

            return tasks;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<String, Object> getTask(String taskId, String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", taskId);
        params.put("type", type);

        Response res = _get(API_TASK_DETAIL, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            Map<String, Object> task = jsonToMap(data);

            return task;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<String, Object> getItemList(String taskId, String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", taskId);
        params.put("type", type);

        Response res = _get(API_ITEM_LIST, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            Map<String, Object> plan = jsonToMap(data);

            return plan;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<String, Object> getWorkLogDetail(String workLogId, String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", workLogId);
        params.put("type", type);

        Response res = _get(API_WORKLOG_DETAIL, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            Map<String, Object> task = jsonToMap(data);

            return task;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }


    public Map<String, Object> getItemDetail(String taskId, String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", taskId);
        params.put("type", type);

        Response res = _get(API_ITEM_DETAIL, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            Map<String, Object> task = jsonToMap(data);

            return task;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<Integer, String> getOption(String type, String system, String device) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        params.put("system", system);
        params.put("device", device);

        Response res = _get(API_OPTION, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONArray data = result.getJSONArray("data");
            List<Map<String, Object>> list = toList(data);

            Map<Integer, String> types = new HashMap<Integer, String>(list.size());
            for (Map<String, Object> map : list) {
                Integer id = Integer.valueOf((String)map.get("id"));
                String value = map.get("data").toString();

                types.put(id, value);
            }
            return types;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<Integer, String> getStatus(String category) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("category", category);

        Response res = _get(API_STATUS, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONArray data = result.getJSONArray("data");
            List<Map<String, Object>> list = toList(data);

            Map<Integer, String> types = new HashMap<Integer, String>(list.size());
            for (Map<String, Object> map : list) {
                Integer id = Integer.valueOf((String)map.get("code"));
                String value = (String)map.get("name");

                types.put(id, value);
            }
            return types;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<Integer, String> getWorker() throws Exception {
        Map<String, String> params = new HashMap<String, String>();

        Response res = _get(API_WORKER, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONArray data = result.getJSONArray("data");
            List<Map<String, Object>> list = toList(data);

            Map<Integer, String> types = new HashMap<Integer, String>(list.size());
            for (Map<String, Object> map : list) {
                Integer id = Integer.valueOf((String)map.get("编号"));
                String value = (String)map.get("真实姓名");

                types.put(id, value);
            }
            return types;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public List<Map<String, Object>> getWorkHourSummary(String taskId, String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", taskId);
        params.put("type", type);

        Response res = _get(API_WORKHOUR_SUM, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONArray data = result.getJSONArray("data");
            List<Map<String, Object>> workhours = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < data.length(); i++) {
                JSONObject jo = data.getJSONObject(i);
                workhours.add(jsonToMap(jo));
            }

            return workhours;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    protected Response _get(String ifPage, Map<String, String> params) throws InterruptedException {
        String url = genRequestURL(ifPage, params);
        if (BuildConfig.DEBUG) {
            Log.i(TAG, url);
        }
//        Thread.sleep(1000);
        Response response = null;
        if (API_LOGIN.equals(ifPage)) {
            response = get(url);
        } else if ((API_TASK_LIST).equals(ifPage)) {
            response = get(url);
        } else if ((API_TASK_DETAIL).equals(ifPage)) {
            response = get(url);
        } else if ((API_TASK_EDIT).equals(ifPage)) {
            response = get(url);
        } else if ((API_WORKLOG_DETAIL).equals(ifPage)) {
            response = get(url);
        } else if ((API_WORKLOG_EDIT).equals(ifPage)) {
            response = get(url);
        } else if ((API_WORKER).equals(ifPage)) {
            response = get(url);
        } else if ((API_WORKHOUR_EDIT).equals(ifPage)) {
            response = get(url);
        } else if ((API_WORKHOUR_SUM).equals(ifPage)) {
            response = get(url);
        }else if ((API_BAIDU_PUSH_REGISTER).equals(ifPage)) {
            response = get(url);
        } else if ((API_OPTION).equals(ifPage)) {
            response = get(url);
        } else if ((API_ITEM_EDIT).equals(ifPage)) {
            response = get(url);
        } else if ((API_ITEM_DELETE).equals(ifPage)) {
            response = get(url);
        } else if ((API_ITEM_DETAIL).equals(ifPage)) {
            response = get(url);
        } else if ((API_STATUS).equals(ifPage)) {
            response = get(url);
        } else if ((API_ITEM_LIST).equals(ifPage)) {
            response = get(url);
        } else if ((API_COMPANY).equals(ifPage)) {
            response = get(url);
        } else {
            response = get(url);
        }
        return response;
    }

}
