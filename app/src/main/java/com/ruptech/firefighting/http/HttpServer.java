package com.ruptech.firefighting.http;


import android.util.Log;

import com.ruptech.firefighting.DataType;
import com.ruptech.firefighting.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServer extends HttpConnection {
    private static final String API_COMPANY = "Company.aspx";
    private static final String API_ITEM_DELETE = "ItemDelete.aspx";
    private static final String API_ITEM_DETAIL = "ItemDetail.aspx";
    private static final String API_ITEM_EDIT = "ItemEdit.aspx";
    private static final String API_ITEM_LIST = "ItemList.aspx";
    private static final String API_LOGIN = "user_login.php";
    private static final String API_STATUS = "Status.aspx";
    private static final String API_TASK_DETAIL = "TaskDetail.aspx";
    private static final String API_TASK_EDIT = "TaskEdit.aspx";
    private static final String API_TASK_LIST = "TaskList.aspx";
    private static final String API_OPTIONS = "Option.aspx";
    private static final String API_WORKER = "Worker.aspx";
    private static final String API_WORKLOG_DETAIL = "WorkLogDetail.aspx";
    private static final String API_WORKLOG_EDIT = "WorkLogEdit.aspx";
    private static final String API_BAIDU_PUSH_REGISTER = "Baidu/BaiduUserTokenSave.aspx";

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

    public Map<String, Object> genEmptyWorklog() throws JSONException {
        String s = "{" +
                "        \"ID\": \"\"," +
                "        \"标题\": \"\"," +
                "        \"详细描述\": \"\"," +
                "        \"是否提交\": \"\"" +
                "    }";
        return jsonToMap(new JSONObject(s));
    }

    public Map<String, Object> genEmptyWorkHour() throws JSONException {
        String s = "{" +
                "                \"维修人员ID\": \"\"," +
                "                \"开始时间\": \"\"," +
                "                \"结束时间\": \"\"," +
                "                \"工时\": \"\"," +
                "                \"维修人员姓名\": \"\"" +
                "            }";
        return jsonToMap(new JSONObject(s));
    }

    public Map<String, Object> genEmptyItem(String type) throws JSONException {
        //TODO type
        String s = "{" +
                "            \"ID\": \"\"," +
                "            \"部件报修来源\": \"\"," +
                "            \"受理编号\": \"\"," +
                "            \"中心ID\": \"\"," +
                "            \"单位ID\": \"\"," +
                "            \"故障内容\": \"\"," +
                "            \"报修时间\": \"\"," +
                "            \"系统类型ID\": \"\"," +
                "            \"部件ID\": \"\"," +
                "            \"派单时间\": \"\"," +
                "            \"结束时间\": \"\"," +
                "            \"维修状态\": \"\"," +
                "            \"检查编号\": \"\"," +
                "            \"建筑物编号\": \"\"," +
                "            \"建筑物类型\": \"\"," +
                "            \"设备单项\": \"\"," +
                "            \"故障单项\": \"\"," +
                "            \"实际系统类型ID\": \"\"," +
                "            \"实际设备单项\": \"\"," +
                "            \"实际故障单项\": \"\"," +
                "            \"维修措施\": \"\"," +
                "            \"报修类型\": \"\"," +
                "            \"维修联系人\": \"\"," +
                "            \"维修联系电话\": \"\"" +
                "        }";
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

    public boolean editWorklog(String id, String type, String[] columns, String[] values)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);
        params.put("column", strJoin(columns, ","));
        params.put("value", strJoin(values, ","));

        Response res = _get(API_WORKLOG_EDIT, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }
        return success;
    }

    public boolean editItem(String id, String type, String[] columns, String[] values)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);
        params.put("column", strJoin(columns, ","));
        params.put("value", strJoin(values, ","));

        Response res = _get(API_ITEM_EDIT, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }
        return success;
    }

    public boolean editTask(String id, String type, String[] columns, String[] values)
            throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("type", type);
        params.put("column", strJoin(columns, ","));
        params.put("value", strJoin(values, ","));

        Response res = _get(API_TASK_EDIT, params);
        JSONObject result = res.asJSONObject();

        boolean success = result.getBoolean("success");
        if (!success) {
            throw new RuntimeException(result.getString("message"));
        }
        return success;
    }


    public List<Map<String, Object>> getTaskList(String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", type);

        Response res = _get(API_TASK_LIST + type, params);
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

        Response res = _get(API_TASK_DETAIL + type, params);
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

        Response res = _get(API_ITEM_LIST + type, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            Map<String, Object> task = jsonToMap(data);

            return task;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<String, Object> getWorkLogDetail(String workLogId, String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", workLogId);
        params.put("type", type);

        Response res = _get(API_WORKLOG_DETAIL + type, params);
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

        Response res = _get(API_ITEM_DETAIL + type, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            Map<String, Object> task = jsonToMap(data);

            return task;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<Integer, String> getOptions(String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", type);

        Response res = _get(API_OPTIONS + type, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONArray data = result.getJSONArray("data");
            List<Map<String, Object>> list = toList(data);

            Map<Integer, String> types = new HashMap<Integer, String>(list.size());
            for (Map<String, Object> map : list) {
                Integer id = Integer.valueOf(map.get("id").toString());
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

        Response res = _get(API_STATUS + category, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONArray data = result.getJSONArray("data");
            List<Map<String, Object>> list = toList(data);

            Map<Integer, String> types = new HashMap<Integer, String>(list.size());
            for (Map<String, Object> map : list) {
                Integer id = Integer.valueOf(map.get("code").toString());
                String value = map.get("name").toString();

                types.put(id, value);
            }
            return types;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    protected Response _get(String ifPage, Map<String, String> params) throws InterruptedException {
        String url = genRequestURL(ifPage, params);
        Log.e(TAG, url);
        Thread.sleep(1000);
        Response response = null;
        if (API_LOGIN.equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "        \"编号\": \"1\"," +
                    "        \"用户名\": \"admin\"," +
                    "        \"密码\": \"111111\"," +
                    "        \"中心编号\": \"35020301\"," +
                    "        \"真实姓名\": \"admin\"," +
                    "        \"性别\": \"男\"," +
                    "        \"移动电话\": \"34\"," +
                    "        \"固定电话\": \"34\"," +
                    "        \"邮箱\": \"3434\"," +
                    "        \"角色编号\": \"1\"," +
                    "        \"人员类型\": \"1\"," +
                    "        \"人员状态\": \"1\"," +
                    "        \"IsDel\": \"False\"" +
                    "    }" +
                    "}";
            response = new Response(body);
        } else if ((API_OPTIONS + "system").equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"id\": \"1\"," +
                    "            \"data\": \"火灾自动报警系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"11\"," +
                    "            \"data\": \"室外消火栓系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"12\"," +
                    "            \"data\": \"自动喷水灭火系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"13\"," +
                    "            \"data\": \"气体灭火系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"16\"," +
                    "            \"data\": \"泡沫灭火系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"18\"," +
                    "            \"data\": \"防烟排烟系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"19\"," +
                    "            \"data\": \"防火门及卷帘系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"20\"," +
                    "            \"data\": \"消防电梯\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"21\"," +
                    "            \"data\": \"消防应急广播\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"22\"," +
                    "            \"data\": \"消防应急照明和疏散指示系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"23\"," +
                    "            \"data\": \"消防电源\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"24\"," +
                    "            \"data\": \"消防电话\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"128\"," +
                    "            \"data\": \"消防水源\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"129\"," +
                    "            \"data\": \"灭火器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"130\"," +
                    "            \"data\": \"室内消火栓系统\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"131\"," +
                    "            \"data\": \"前置机信息\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_OPTIONS + "error").equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"id\": \"0\"," +
                    "            \"data\": \"其他\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"1\"," +
                    "            \"data\": \"屏幕显示乱码\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"2\"," +
                    "            \"data\": \"无法屏蔽\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"3\"," +
                    "            \"data\": \"按钮无反应\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"4\"," +
                    "            \"data\": \"地址未定义\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"5\"," +
                    "            \"data\": \"无法屏蔽\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_OPTIONS + "device").equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"id\": \"1\"," +
                    "            \"data\": \"火灾报警控制器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"2\"," +
                    "            \"data\": \"未带电话插口手动报警报钮\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"3\"," +
                    "            \"data\": \"手动报警按钮\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"4\"," +
                    "            \"data\": \"报警警铃（讯响器）\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"5\"," +
                    "            \"data\": \"打印机\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"6\"," +
                    "            \"data\": \"插孔电话\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"7\"," +
                    "            \"data\": \"火焰（或感光）探测器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"8\"," +
                    "            \"data\": \"线性感温探测器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"9\"," +
                    "            \"data\": \"离子感温探测器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"10\"," +
                    "            \"data\": \"点型感温探测器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"11\"," +
                    "            \"data\": \"线性光束感烟探测器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"12\"," +
                    "            \"data\": \"点型感烟探测器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"13\"," +
                    "            \"data\": \"消防联动控制设备\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"14\"," +
                    "            \"data\": \"多线盘\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"15\"," +
                    "            \"data\": \"可燃气体控制器\"" +
                    "        }," +
                    "        {" +
                    "            \"id\": \"16\"," +
                    "            \"data\": \"点型可燃气体探测器\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_TASK_EDIT).equals(ifPage)) {
            String body = "{\"success\":true,\"message\":\"\"}";
            response = new Response(body);
        } else if ((API_WORKLOG_EDIT).equals(ifPage)) {
            String body = "{\"success\":true,\"message\":\"\"}";
            response = new Response(body);
        } else if ((API_BAIDU_PUSH_REGISTER).equals(ifPage)) {
            String body = "{\"success\":true,\"message\":\"\"}";
            response = new Response(body);
        } else if ((API_ITEM_EDIT).equals(ifPage)) {
            String body = "{\"success\":true,\"message\":\"\"}";
            response = new Response(body);
        } else if ((API_ITEM_DELETE).equals(ifPage)) {
            String body = "{\"success\":true,\"message\":\"\"}";
            response = new Response(body);
        } else if ((API_TASK_LIST + DataType.TAB_AUDIT).equals(ifPage)) {
            String body = "{\"success\":true,\"message\":\"\",\"data\":[]} ";
            response = new Response(body);
        } else if ((API_TASK_LIST + DataType.TAB_UNDO).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"ID\": \"25\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2014/12/22 14:09:08\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"15\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/8/29 13:53:59\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"14\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/8/29 13:52:09\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"13\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/8/29 13:52:03\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"11\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/8/26 14:32:31\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"10\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/8/26 14:32:17\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"9\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/8/26 14:29:58\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"8\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/8/26 14:25:55\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"11\"," +
                    "            \"任务名称\": \"沈阳消防研究所维修\"," +
                    "            \"任务状态\": \"等待派单\"," +
                    "            \"派单时间\": \"2011/8/26 11:25:56\"," +
                    "            \"任务类型\": \"maintain\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"7\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/8/26 10:34:18\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"24\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"检查中\"," +
                    "            \"派单时间\": \"2011/3/8 15:21:08\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"22\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未通过审核\"," +
                    "            \"派单时间\": \"2011/3/8 14:51:19\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"20\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/3/8 13:46:38\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"21\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/3/8 13:46:38\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"19\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/3/8 13:41:46\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"18\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/3/8 13:22:33\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"17\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/3/8 13:21:49\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"9\"," +
                    "            \"任务名称\": \"沈阳消防研究所\"," +
                    "            \"任务状态\": \"等待派单\"," +
                    "            \"派单时间\": \"2011/1/21 10:09:42\"," +
                    "            \"任务类型\": \"maintain\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"8\"," +
                    "            \"任务名称\": \"沈阳消防研究所\"," +
                    "            \"任务状态\": \"等待派单\"," +
                    "            \"派单时间\": \"2011/1/21 10:09:42\"," +
                    "            \"任务类型\": \"maintain\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"16\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2011/1/15 14:01:20\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"6\"," +
                    "            \"任务名称\": \"沈阳牡丹国际大酒店有限公司牡丹酒店\"," +
                    "            \"任务状态\": \"等待派单\"," +
                    "            \"派单时间\": \"2010/11/25 11:22:45\"," +
                    "            \"任务类型\": \"maintain\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"6\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2010/11/25 9:56:16\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"5\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"未开始检查\"," +
                    "            \"派单时间\": \"2010/11/25 9:43:45\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }," +
                    "        {" +
                    "            \"ID\": \"2\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"任务状态\": \"检查中\"," +
                    "            \"派单时间\": \"2010/11/24 16:37:49\"," +
                    "            \"任务类型\": \"check\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_WORKLOG_DETAIL + DataType.TYPE_MAINTAIN).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "        \"ID\": \"3\"," +
                    "        \"标题\": \"123\"," +
                    "        \"详细描述\": \"123333\"," +
                    "        \"是否提交\": \"1\"," +
                    "        \"workhours\": [" +
                    "            {" +
                    "                \"维修人员ID\": \"1\"," +
                    "                \"开始时间\": \"\"," +
                    "                \"结束时间\": \"\"," +
                    "                \"工时\": \"1\"," +
                    "                \"维修人员姓名\": \"admin\"" +
                    "            }," +
                    "            {" +
                    "                \"维修人员ID\": \"7\"," +
                    "                \"开始时间\": \"\"," +
                    "                \"结束时间\": \"\"," +
                    "                \"工时\": \"2.5\"," +
                    "                \"维修人员姓名\": \"lijm\"" +
                    "            }," +
                    "            {" +
                    "                \"维修人员ID\": \"10\"," +
                    "                \"开始时间\": \"\"," +
                    "                \"结束时间\": \"\"," +
                    "                \"工时\": \"2\"," +
                    "                \"维修人员姓名\": \"yandf\"" +
                    "            }" +
                    "        ]" +
                    "    }" +
                    "}";
            response = new Response(body);
        } else if ((API_WORKLOG_DETAIL + DataType.TYPE_CHECK).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "        \"ID\": \"7\"," +
                    "        \"标题\": \"开始检查\"," +
                    "        \"详细描述\": \"完成所有检查\"," +
                    "        \"是否提交\": \"0\"," +
                    "        \"workhours\": [" +
                    "            {" +
                    "                \"维修人员ID\": \"7\"," +
                    "                \"开始时间\": \"2011/1/9 9:15:53\"," +
                    "                \"结束时间\": \"2011/1/9 12:16:02\"," +
                    "                \"工时\": \"3\"," +
                    "                \"维修人员姓名\": \"lijm\"" +
                    "            }," +
                    "            {" +
                    "                \"维修人员ID\": \"1\"," +
                    "                \"开始时间\": \"2011/1/9 9:15:53\"," +
                    "                \"结束时间\": \"2011/1/9 12:16:02\"," +
                    "                \"工时\": \"3\"," +
                    "                \"维修人员姓名\": \"admin\"" +
                    "            }," +
                    "            {" +
                    "                \"维修人员ID\": \"6\"," +
                    "                \"开始时间\": \"2011/1/9 9:15:53\"," +
                    "                \"结束时间\": \"2011/1/9 12:16:02\"," +
                    "                \"工时\": \"3\"," +
                    "                \"维修人员姓名\": \"datatest\"" +
                    "            }" +
                    "        ]" +
                    "    }" +
                    "}";
            response = new Response(body);
        } else if ((API_ITEM_DETAIL + DataType.TYPE_MAINTAIN).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "            \"ID\": \"9204\"," +
                    "            \"部件报修来源\": \"3\"," +
                    "            \"受理编号\": \"283768213502030001\"," +
                    "            \"中心ID\": \"35020301\"," +
                    "            \"单位ID\": \"3502030001\"," +
                    "            \"故障内容\": \"建筑消防设施部件运行状态:000000001001001-故障\"," +
                    "            \"报修时间\": \"2010/12/6 16:35:10\"," +
                    "            \"系统类型ID\": \"0\"," +
                    "            \"部件ID\": \"0\"," +
                    "            \"派单时间\": \"2011/1/21 10:09:42\"," +
                    "            \"结束时间\": \"\"," +
                    "            \"维修状态\": \"2\"," +
                    "            \"检查编号\": \"0\"," +
                    "            \"建筑物编号\": \"0\"," +
                    "            \"建筑物类型\": \"0\"," +
                    "            \"设备单项\": \"12\"," +
                    "            \"故障单项\": \"0\"," +
                    "            \"实际系统类型ID\": \"0\"," +
                    "            \"实际设备单项\": \"\"," +
                    "            \"实际故障单项\": \"\"," +
                    "            \"维修措施\": \"\"," +
                    "            \"报修类型\": \"\"," +
                    "            \"维修联系人\": \"\"," +
                    "            \"维修联系电话\": \"\"" +
                    "        }" +
                    "}";
            response = new Response(body);
        } else if ((API_ITEM_DETAIL + DataType.TYPE_CHECK).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "            \"ID\": \"9204\"," +
                    "            \"部件报修来源\": \"3\"," +
                    "            \"受理编号\": \"283768213502030001\"," +
                    "            \"中心ID\": \"35020301\"," +
                    "            \"单位ID\": \"3502030001\"," +
                    "            \"故障内容\": \"建筑消防设施部件运行状态:000000001001001-故障\"," +
                    "            \"报修时间\": \"2010/12/6 16:35:10\"," +
                    "            \"系统类型ID\": \"0\"," +
                    "            \"部件ID\": \"0\"," +
                    "            \"派单时间\": \"2011/1/21 10:09:42\"," +
                    "            \"结束时间\": \"\"," +
                    "            \"维修状态\": \"2\"," +
                    "            \"检查编号\": \"0\"," +
                    "            \"建筑物编号\": \"0\"," +
                    "            \"建筑物类型\": \"0\"," +
                    "            \"设备单项\": \"12\"," +
                    "            \"故障单项\": \"0\"," +
                    "            \"实际系统类型ID\": \"0\"," +
                    "            \"实际设备单项\": \"\"," +
                    "            \"实际故障单项\": \"\"," +
                    "            \"维修措施\": \"\"," +
                    "            \"报修类型\": \"\"," +
                    "            \"维修联系人\": \"\"," +
                    "            \"维修联系电话\": \"\"" +
                    "        }" +
                    "}";
            response = new Response(body);
        } else if ((API_ITEM_LIST + DataType.TYPE_MAINTAIN).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "        \"sum\": [" +
                    "            {" +
                    "                \"option\": \"总数\"," +
                    "                \"count\": \"2\"," +
                    "                \"code\": \"-1\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"等待派单\"," +
                    "                \"count\": \"1\"," +
                    "                \"code\": \"1\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"正在维修\"," +
                    "                \"count\": \"1\"," +
                    "                \"code\": \"2\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"暂停\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"3\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"修好但未交工\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"4\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"等待审核\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"5\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"审核不合格\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"6\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"审核合格\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"7\"" +
                    "            }" +
                    "        ]," +
                    "        \"detail\": [" +
                    "            {" +
                    "                \"ID\": \"9212\"," +
                    "                \"序号\": \"\"," +
                    "                \"报修来源\": \"3\"," +
                    "                \"报修时间\": \"2010/12/8 8:42:18\"," +
                    "                \"结束时间\": \"\"," +
                    "                \"维修状态\": \"等待派单\"," +
                    "                \"系统名称\": \"气体灭火系统\"," +
                    "                \"单位\": \"公安部沈阳消防研究所\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"9214\"," +
                    "                \"序号\": \"0\"," +
                    "                \"报修来源\": \"3\"," +
                    "                \"报修时间\": \"2011/1/21 8:39:08\"," +
                    "                \"结束时间\": \"\"," +
                    "                \"维修状态\": \"正在维修\"," +
                    "                \"系统名称\": \"火灾自动报警系统\"," +
                    "                \"单位\": \"沈阳消防研究所\"" +
                    "            }" +
                    "        ]" +
                    "    }" +
                    "}";
            response = new Response(body);
        } else if ((API_ITEM_LIST + DataType.TYPE_CHECK).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "        \"sum\": [" +
                    "            {" +
                    "                \"option\": \"总数\"," +
                    "                \"count\": \"4\"," +
                    "                \"code\": \"-1\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"正常\"," +
                    "                \"count\": \"3\"," +
                    "                \"code\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"故障\"," +
                    "                \"count\": \"1\"," +
                    "                \"code\": \"1\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"待检\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"2\"" +
                    "            }" +
                    "        ]," +
                    "        \"detail\": [" +
                    "            {" +
                    "                \"ID\": \"36\"," +
                    "                \"编码\": \"b000000001001001\"," +
                    "                \"类型\": \"感烟火灾探测器\"," +
                    "                \"检查状态\": \"正常\"," +
                    "                \"系统名称\": \"\"," +
                    "                \"单位\": \"沈阳消防研究所\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"37\"," +
                    "                \"编码\": \"b000000001001002\"," +
                    "                \"类型\": \"手动火灾报警按钮\"," +
                    "                \"检查状态\": \"正常\"," +
                    "                \"系统名称\": \"\"," +
                    "                \"单位\": \"沈阳消防研究所\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"39\"," +
                    "                \"编码\": \"b000000001001004\"," +
                    "                \"类型\": \"感烟火灾探测器\"," +
                    "                \"检查状态\": \"正常\"," +
                    "                \"系统名称\": \"\"," +
                    "                \"单位\": \"沈阳消防研究所\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"38\"," +
                    "                \"编码\": \"b000000001001003\"," +
                    "                \"类型\": \"感烟火灾探测器\"," +
                    "                \"检查状态\": \"故障\"," +
                    "                \"系统名称\": \"火灾自动报警系统\"," +
                    "                \"单位\": \"沈阳消防研究所\"" +
                    "            }" +
                    "        ]" +
                    "    }" +
                    "}";
            response = new Response(body);
        } else if ((API_TASK_DETAIL + DataType.TYPE_CHECK).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "        \"task\": {" +
                    "            \"ID\": \"1\"," +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\"," +
                    "            \"单位名称\": \"沈阳消防研究所\"," +
                    "            \"单位联系人\": \"\"," +
                    "            \"单位联系人电话\": \"\"," +
                    "            \"派单时间\": \"2010/11/23 11:10:30\"," +
                    "            \"任务状态\": 3," +
                    "            \"派单人姓名\": \"admin\"" +
                    "        }," +
                    "        \"worklogs\": [" +
                    "            {" +
                    "                \"ID\": \"3\"," +
                    "                \"标题\": \"789\"," +
                    "                \"详细描述\": \"789\"," +
                    "                \"是否提交\": \"1\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"2\"," +
                    "                \"标题\": \"456\"," +
                    "                \"详细描述\": \"456\"," +
                    "                \"是否提交\": \"1\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"1\"," +
                    "                \"标题\": \"123\"," +
                    "                \"详细描述\": \"123\"," +
                    "                \"是否提交\": \"1\"" +
                    "            }" +
                    "        ]," +
                    "        \"plans\": [" +
                    "            {" +
                    "                \"SId\": \"2\"," +
                    "                \"单位名称\": \"沈阳消防研究所\"," +
                    "                \"系统名称\": \"火灾自动报警系统\"," +
                    "                \"检查类型\": \"点型可燃气体探测器\"" +
                    "            }," +
                    "            {" +
                    "                \"SId\": \"1\"," +
                    "                \"单位名称\": \"沈阳消防研究所\"," +
                    "                \"系统名称\": \"火灾自动报警系统\"," +
                    "                \"检查类型\": \"火灾报警控制器\"" +
                    "            }" +
                    "        ]," +
                    "        \"wokers\": [" +
                    "            {" +
                    "                \"维修人员ID\": \"8\"," +
                    "                \"维修人员真实姓名\": \"lianfd\"" +
                    "            }" +
                    "        ]," +
                    "        \"workhoursum\": [" +
                    "            {" +
                    "                \"姓名\": \"lianfd\"," +
                    "                \"工时\": \"13\"" +
                    "            }" +
                    "        ]" +
                    "    }" +
                    "}";
            response = new Response(body);
        } else if ((API_WORKER).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"编号\": \"1\"," +
                    "            \"用户名\": \"admin\"," +
                    "            \"密码\": \"111111\"," +
                    "            \"中心编号\": \"35020301\"," +
                    "            \"真实姓名\": \"admin\"," +
                    "            \"性别\": \"男\"," +
                    "            \"移动电话\": \"34\"," +
                    "            \"固定电话\": \"34\"," +
                    "            \"邮箱\": \"3434\"," +
                    "            \"角色编号\": \"1\"," +
                    "            \"人员类型\": \"1\"," +
                    "            \"人员状态\": \"1\"," +
                    "            \"IsDel\": \"False\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_STATUS + "maintaintask").equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"code\": \"1\"," +
                    "            \"name\": \"等待派单\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"2\"," +
                    "            \"name\": \"正在执行\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"3\"," +
                    "            \"name\": \"挂起\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"4\"," +
                    "            \"name\": \"修好但未交工\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"5\"," +
                    "            \"name\": \"等待审核\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"6\"," +
                    "            \"name\": \"审核不合格\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"7\"," +
                    "            \"name\": \"审核合格\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_STATUS + "maintainitem").equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"code\": \"1\"," +
                    "            \"name\": \"等待派单\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"2\"," +
                    "            \"name\": \"正在执行\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"3\"," +
                    "            \"name\": \"挂起\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"4\"," +
                    "            \"name\": \"修好但未交工\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"5\"," +
                    "            \"name\": \"等待审核\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"6\"," +
                    "            \"name\": \"审核不合格\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"7\"," +
                    "            \"name\": \"审核合格\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_STATUS + "checkitem").equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"code\": \"2\"," +
                    "            \"name\": \"待检\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"0\"," +
                    "            \"name\": \"正常\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"1\"," +
                    "            \"name\": \"故障\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_STATUS + "checktask").equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"code\": \"5\"," +
                    "            \"name\": \"未开始检查\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"0\"," +
                    "            \"name\": \"检查中\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"1\"," +
                    "            \"name\": \"审核中\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"2\"," +
                    "            \"name\": \"未通过审核\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"3\"," +
                    "            \"name\": \"已完成\"" +
                    "        }," +
                    "        {" +
                    "            \"code\": \"4\"," +
                    "            \"name\": \"回退\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_COMPANY).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": [" +
                    "        {" +
                    "            \"中心名称\": \"和平区监控中心\"," +
                    "            \"单位编号\": \"3502050001\"," +
                    "            \"单位名称\": \"沈阳金龙旅行有限公司\"," +
                    "            \"中心编号\": \"35020501\"," +
                    "            \"组织机构代码\": \"61201252-D\"," +
                    "            \"所属区域\": \"于洪\"," +
                    "            \"单位类别\": \"99\"," +
                    "            \"单位地址\": \"于洪新光路159号\"," +
                    "            \"联系电话\": \"5608888\"," +
                    "            \"邮政编码\": \"361000\"," +
                    "            \"监管等级\": \"1\"," +
                    "            \"主管消防中队编号\": \"35020101\"," +
                    "            \"消防控制室电话\": \"5608888\"," +
                    "            \"单位职工人数\": \"600\"," +
                    "            \"成立时间\": \"\"," +
                    "            \"上级主管单位名称\": \"\"," +
                    "            \"管辖单位\": \"\"," +
                    "            \"经济所有制\": \"\"," +
                    "            \"固定资产\": \"9000万人民币\"," +
                    "            \"占地面积\": \"140000\"," +
                    "            \"总建筑面积\": \"28000\"," +
                    "            \"单位法人代表姓名\": \"\"," +
                    "            \"单位法人代表身份证号码\": \"\"," +
                    "            \"单位法人代表电话\": \"\"," +
                    "            \"单位法人固定电话\": \"\"," +
                    "            \"消防安全责任人姓名\": \"叶宏廷\"," +
                    "            \"消防安全责任人身份证号码\": \"\"," +
                    "            \"消防安全责任人电话\": \"13950102801\"," +
                    "            \"消防安全责任人固定电话\": \"5908958\"," +
                    "            \"消防安全管理人姓名\": \"胡永正\"," +
                    "            \"消防安全管理人身份证号码\": \"\"," +
                    "            \"消防安全管理人电话\": \"13950102801\"," +
                    "            \"消防安全管理人固定电话\": \"\"," +
                    "            \"专职消防管理人姓名\": \"\"," +
                    "            \"专职消防管理人身份证号码\": \"\"," +
                    "            \"专职消防管理人电话\": \"\"," +
                    "            \"专职消防管理人固定电话\": \"\"," +
                    "            \"兼职消防管理人姓名\": \"\"," +
                    "            \"兼职消防管理人身份证号码\": \"\"," +
                    "            \"兼职消防管理人电话\": \"\"," +
                    "            \"兼职消防管理人固定电话\": \"\"," +
                    "            \"单位接收通知联系人姓名\": \"\"," +
                    "            \"单位接收通知联系人电话\": \"\"," +
                    "            \"入网日期\": \"2010/11/24 0:00:00\"," +
                    "            \"单位状态\": \"1\"," +
                    "            \"Gis_X\": \"\"," +
                    "            \"Gis_Y\": \"\"," +
                    "            \"备注\": \"\"," +
                    "            \"info_id\": \"1421\"," +
                    "            \"单位状态名称\": \"联网\"" +
                    "        }" +
                    "    ]" +
                    "}";
            response = new Response(body);
        } else if ((API_TASK_DETAIL + DataType.TYPE_MAINTAIN).equals(ifPage)) {
            String body = "{" +
                    "    \"success\": true," +
                    "    \"message\": \"\"," +
                    "    \"data\": {" +
                    "        \"task\": {" +
                    "            \"ID\": \"3\"," +
                    "            \"任务名称\": \"沈阳消防研究所维修\"," +
                    "            \"单位名称\": \"沈阳消防研究所\"," +
                    "            \"单位联系人\": \"123(专职消防管理人)\"," +
                    "            \"单位联系人电话\": \"\"," +
                    "            \"派单时间\": \"2010/11/25 10:00:38\"," +
                    "            \"任务状态\": 6," +
                    "            \"派单人姓名\": \"admin\"" +
                    "        }," +
                    "        \"worklogs\": [" +
                    "            {" +
                    "                \"ID\": \"35\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"34\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"33\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"32\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"31\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"30\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"29\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"28\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"27\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"26\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"25\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"24\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"23\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"21\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"12\"," +
                    "                \"标题\": \"1\"," +
                    "                \"详细描述\": \"2\"," +
                    "                \"是否提交\": \"0\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"5\"," +
                    "                \"标题\": \"1212\"," +
                    "                \"详细描述\": \"121\"," +
                    "                \"是否提交\": \"1\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"4\"," +
                    "                \"标题\": \"1212\"," +
                    "                \"详细描述\": \"12121212\"," +
                    "                \"是否提交\": \"1\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"3\"," +
                    "                \"标题\": \"123\"," +
                    "                \"详细描述\": \"123333\"," +
                    "                \"是否提交\": \"1\"" +
                    "            }" +
                    "        ]," +
                    "        \"items\": [" +
                    "            {" +
                    "                \"ID\": \"9214\"," +
                    "                \"序号\": \"0\"," +
                    "                \"报修来源\": \"3\"," +
                    "                \"报修时间\": \"2011/1/21 8:39:08\"," +
                    "                \"结束时间\": \"\"," +
                    "                \"维修状态\": \"正在执行\"," +
                    "                \"系统名称\": \"火灾自动报警系统\"," +
                    "                \"单位\": \"沈阳消防研究所\"" +
                    "            }," +
                    "            {" +
                    "                \"ID\": \"9212\"," +
                    "                \"序号\": \"\"," +
                    "                \"报修来源\": \"3\"," +
                    "                \"报修时间\": \"2010/12/8 8:42:18\"," +
                    "                \"结束时间\": \"\"," +
                    "                \"维修状态\": \"等待派单\"," +
                    "                \"系统名称\": \"气体灭火系统\"," +
                    "                \"单位\": \"公安部沈阳消防研究所\"" +
                    "            }" +
                    "        ]," +
                    "        \"wokers\": [" +
                    "            {" +
                    "                \"维修人员ID\": \"7\"," +
                    "                \"维修人员真实姓名\": \"lijm\"" +
                    "            }," +
                    "            {" +
                    "                \"维修人员ID\": \"10\"," +
                    "                \"维修人员真实姓名\": \"yandf\"" +
                    "            }" +
                    "        ]," +
                    "        \"workhoursum\": [" +
                    "            {" +
                    "                \"姓名\": \"admin\"," +
                    "                \"工时\": \"11\"" +
                    "            }," +
                    "            {" +
                    "                \"姓名\": \"handler1\"," +
                    "                \"工时\": \"7\"" +
                    "            }," +
                    "            {" +
                    "                \"姓名\": \"lijm\"," +
                    "                \"工时\": \"39\"" +
                    "            }," +
                    "            {" +
                    "                \"姓名\": \"yandf\"," +
                    "                \"工时\": \"3\"" +
                    "            }" +
                    "        ]," +
                    "        \"componentsum\": [" +
                    "            {" +
                    "                \"option\": \"总数\"," +
                    "                \"count\": \"2\"," +
                    "                \"code\": \"-1\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"等待派单\"," +
                    "                \"count\": \"1\"," +
                    "                \"code\": \"1\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"正在执行\"," +
                    "                \"count\": \"1\"," +
                    "                \"code\": \"2\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"挂起\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"3\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"修好但未交工\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"4\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"等待审核\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"5\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"审核不合格\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"6\"" +
                    "            }," +
                    "            {" +
                    "                \"option\": \"审核合格\"," +
                    "                \"count\": \"0\"," +
                    "                \"code\": \"7\"" +
                    "            }" +
                    "        ]" +
                    "    }" +
                    "}";
            response = new Response(body);
        }
        return response;
    }

}
