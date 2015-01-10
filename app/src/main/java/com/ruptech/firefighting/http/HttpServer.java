package com.ruptech.firefighting.http;


import com.ruptech.firefighting.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServer extends HttpConnection {
    private static final String API_ITEM_LIST = "ItemList.aspx";
    private static final String API_TASK = "TaskDetail.aspx";
    private static final String API_TODO_TASK_LIST = "TaskList.aspx?type=todo";
    private static final String API_AUDIT_TASK_LIST = "TaskList.aspx?type=audit";
    private static final String API_TYPES = "Option.aspx";
    private static final String API_LOGIN = "user_login.php";

    private final String TAG = HttpServer.class.getSimpleName();

    // Low-level interface

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

    public List<Map<String, Object>> getTodoList() throws Exception {

        Response res = _get(API_TODO_TASK_LIST, null);
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

    public List<Map<String, Object>> getAuditTaskList() throws Exception {

        Response res = _get(API_AUDIT_TASK_LIST, null);
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

        Response res = _get(API_TASK + type, params);
        JSONObject result = res.asJSONObject();

        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            Map<String, Object> task = jsonToMap(data);

            return task;
        } else {
            throw new RuntimeException(result.getString("message"));
        }
    }

    public Map<String, Object> getItems(String taskId, String type) throws Exception {
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


    public Map<Integer, String> getTypes(String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", type);

        Response res = _get(API_TYPES + type, params);
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

    protected Response _get(String ifPage, Map<String, String> params) {
        Response response = null;
        if (API_LOGIN.equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": {\n" +
                    "        \"编号\": \"1\",\n" +
                    "        \"用户名\": \"admin\",\n" +
                    "        \"密码\": \"111111\",\n" +
                    "        \"中心编号\": \"35020301\",\n" +
                    "        \"真实姓名\": \"admin\",\n" +
                    "        \"性别\": \"男\",\n" +
                    "        \"移动电话\": \"34\",\n" +
                    "        \"固定电话\": \"34\",\n" +
                    "        \"邮箱\": \"3434\",\n" +
                    "        \"角色编号\": \"1\",\n" +
                    "        \"人员类型\": \"1\",\n" +
                    "        \"人员状态\": \"1\",\n" +
                    "        \"IsDel\": \"False\"\n" +
                    "    }\n" +
                    "}\n";
            response = new Response(body);
        } else if ((API_TYPES + "system").equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"id\": \"1\",\n" +
                    "            \"data\": \"火灾自动报警系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"11\",\n" +
                    "            \"data\": \"室外消火栓系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"12\",\n" +
                    "            \"data\": \"自动喷水灭火系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"13\",\n" +
                    "            \"data\": \"气体灭火系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"16\",\n" +
                    "            \"data\": \"泡沫灭火系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"18\",\n" +
                    "            \"data\": \"防烟排烟系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"19\",\n" +
                    "            \"data\": \"防火门及卷帘系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"20\",\n" +
                    "            \"data\": \"消防电梯\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"21\",\n" +
                    "            \"data\": \"消防应急广播\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"22\",\n" +
                    "            \"data\": \"消防应急照明和疏散指示系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"23\",\n" +
                    "            \"data\": \"消防电源\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"24\",\n" +
                    "            \"data\": \"消防电话\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"128\",\n" +
                    "            \"data\": \"消防水源\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"129\",\n" +
                    "            \"data\": \"灭火器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"130\",\n" +
                    "            \"data\": \"室内消火栓系统\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"131\",\n" +
                    "            \"data\": \"前置机信息\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            response = new Response(body);
        } else if ((API_TYPES + "error").equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"id\": \"0\",\n" +
                    "            \"data\": \"其他\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"1\",\n" +
                    "            \"data\": \"屏幕显示乱码\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"2\",\n" +
                    "            \"data\": \"无法屏蔽\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"3\",\n" +
                    "            \"data\": \"按钮无反应\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"4\",\n" +
                    "            \"data\": \"地址未定义\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"5\",\n" +
                    "            \"data\": \"无法屏蔽\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            response = new Response(body);
        } else if ((API_TYPES + "device").equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"id\": \"1\",\n" +
                    "            \"data\": \"火灾报警控制器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"2\",\n" +
                    "            \"data\": \"未带电话插口手动报警报钮\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"3\",\n" +
                    "            \"data\": \"手动报警按钮\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"4\",\n" +
                    "            \"data\": \"报警警铃（讯响器）\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"5\",\n" +
                    "            \"data\": \"打印机\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"6\",\n" +
                    "            \"data\": \"插孔电话\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"7\",\n" +
                    "            \"data\": \"火焰（或感光）探测器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"8\",\n" +
                    "            \"data\": \"线性感温探测器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"9\",\n" +
                    "            \"data\": \"离子感温探测器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"10\",\n" +
                    "            \"data\": \"点型感温探测器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"11\",\n" +
                    "            \"data\": \"线性光束感烟探测器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"12\",\n" +
                    "            \"data\": \"点型感烟探测器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"13\",\n" +
                    "            \"data\": \"消防联动控制设备\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"14\",\n" +
                    "            \"data\": \"多线盘\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"15\",\n" +
                    "            \"data\": \"可燃气体控制器\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"16\",\n" +
                    "            \"data\": \"点型可燃气体探测器\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            response = new Response(body);
        } else if (API_AUDIT_TASK_LIST.equals(ifPage)) {
            String body = "{\"success\":true,\"message\":\"\",\"data\":[]} \n";
            response = new Response(body);
        } else if (API_TODO_TASK_LIST.equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": [\n" +
                    "        {\n" +
                    "            \"ID\": \"2\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"0\",\n" +
                    "            \"派单时间\": \"2010/11/24 16:37:49\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"5\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2010/11/25 9:43:45\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"6\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2010/11/25 9:56:16\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"6\",\n" +
                    "            \"任务名称\": \"沈阳牡丹国际大酒店有限公司牡丹酒店\",\n" +
                    "            \"任务状态\": \"1\",\n" +
                    "            \"派单时间\": \"2010/11/25 11:22:45\",\n" +
                    "            \"任务类型\": \"maintain\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"16\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/1/15 14:01:20\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"8\",\n" +
                    "            \"任务名称\": \"沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"1\",\n" +
                    "            \"派单时间\": \"2011/1/21 10:09:42\",\n" +
                    "            \"任务类型\": \"maintain\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"9\",\n" +
                    "            \"任务名称\": \"沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"1\",\n" +
                    "            \"派单时间\": \"2011/1/21 10:09:42\",\n" +
                    "            \"任务类型\": \"maintain\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"17\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/3/8 13:21:49\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"18\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/3/8 13:22:33\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"19\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/3/8 13:41:46\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"20\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/3/8 13:46:38\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"21\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/3/8 13:46:38\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"22\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"2\",\n" +
                    "            \"派单时间\": \"2011/3/8 14:51:19\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"24\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"0\",\n" +
                    "            \"派单时间\": \"2011/3/8 15:21:08\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"7\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/8/26 10:34:18\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"11\",\n" +
                    "            \"任务名称\": \"沈阳消防研究所维修\",\n" +
                    "            \"任务状态\": \"1\",\n" +
                    "            \"派单时间\": \"2011/8/26 11:25:56\",\n" +
                    "            \"任务类型\": \"maintain\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"8\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/8/26 14:25:55\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"9\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/8/26 14:29:58\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"10\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/8/26 14:32:17\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"11\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/8/26 14:32:31\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"13\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/8/29 13:52:03\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"14\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/8/29 13:52:09\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"15\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2011/8/29 13:53:59\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"ID\": \"25\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"任务状态\": \"5\",\n" +
                    "            \"派单时间\": \"2014/12/22 14:09:08\",\n" +
                    "            \"任务类型\": \"check\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            response = new Response(body);
        } else if ((API_ITEM_LIST + "maintain").equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": {\n" +
                    "        \"sum\": [\n" +
                    "            {\n" +
                    "                \"option\": \"总数\",\n" +
                    "                \"count\": \"2\",\n" +
                    "                \"code\": \"-1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"等待派单\",\n" +
                    "                \"count\": \"1\",\n" +
                    "                \"code\": \"1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"正在维修\",\n" +
                    "                \"count\": \"1\",\n" +
                    "                \"code\": \"2\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"暂停\",\n" +
                    "                \"count\": \"0\",\n" +
                    "                \"code\": \"3\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"修好但未交工\",\n" +
                    "                \"count\": \"0\",\n" +
                    "                \"code\": \"4\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"等待审核\",\n" +
                    "                \"count\": \"0\",\n" +
                    "                \"code\": \"5\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"审核不合格\",\n" +
                    "                \"count\": \"0\",\n" +
                    "                \"code\": \"6\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"审核合格\",\n" +
                    "                \"count\": \"0\",\n" +
                    "                \"code\": \"7\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"detail\": [\n" +
                    "            {\n" +
                    "                \"ID\": \"9212\",\n" +
                    "                \"序号\": \"\",\n" +
                    "                \"报修来源\": \"3\",\n" +
                    "                \"报修时间\": \"2010/12/8 8:42:18\",\n" +
                    "                \"结束时间\": \"\",\n" +
                    "                \"维修状态\": \"等待派单\",\n" +
                    "                \"系统名称\": \"气体灭火系统\",\n" +
                    "                \"单位\": \"公安部沈阳消防研究所\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"9214\",\n" +
                    "                \"序号\": \"0\",\n" +
                    "                \"报修来源\": \"3\",\n" +
                    "                \"报修时间\": \"2011/1/21 8:39:08\",\n" +
                    "                \"结束时间\": \"\",\n" +
                    "                \"维修状态\": \"正在维修\",\n" +
                    "                \"系统名称\": \"火灾自动报警系统\",\n" +
                    "                \"单位\": \"沈阳消防研究所\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}\n";
            response = new Response(body);
        } else if ((API_ITEM_LIST + "check").equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": {\n" +
                    "        \"sum\": [\n" +
                    "            {\n" +
                    "                \"option\": \"总数\",\n" +
                    "                \"count\": \"4\",\n" +
                    "                \"code\": \"-1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"正常\",\n" +
                    "                \"count\": \"3\",\n" +
                    "                \"code\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"故障\",\n" +
                    "                \"count\": \"1\",\n" +
                    "                \"code\": \"1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"option\": \"待检\",\n" +
                    "                \"count\": \"0\",\n" +
                    "                \"code\": \"2\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"detail\": [\n" +
                    "            {\n" +
                    "                \"ID\": \"36\",\n" +
                    "                \"编码\": \"b000000001001001\",\n" +
                    "                \"类型\": \"感烟火灾探测器\",\n" +
                    "                \"检查状态\": \"正常\",\n" +
                    "                \"系统名称\": \"\",\n" +
                    "                \"单位\": \"沈阳消防研究所\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"37\",\n" +
                    "                \"编码\": \"b000000001001002\",\n" +
                    "                \"类型\": \"手动火灾报警按钮\",\n" +
                    "                \"检查状态\": \"正常\",\n" +
                    "                \"系统名称\": \"\",\n" +
                    "                \"单位\": \"沈阳消防研究所\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"39\",\n" +
                    "                \"编码\": \"b000000001001004\",\n" +
                    "                \"类型\": \"感烟火灾探测器\",\n" +
                    "                \"检查状态\": \"正常\",\n" +
                    "                \"系统名称\": \"\",\n" +
                    "                \"单位\": \"沈阳消防研究所\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"38\",\n" +
                    "                \"编码\": \"b000000001001003\",\n" +
                    "                \"类型\": \"感烟火灾探测器\",\n" +
                    "                \"检查状态\": \"故障\",\n" +
                    "                \"系统名称\": \"火灾自动报警系统\",\n" +
                    "                \"单位\": \"沈阳消防研究所\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
            response = new Response(body);
        } else if ((API_TASK + "check").equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": {\n" +
                    "        \"task\": {\n" +
                    "            \"ID\": \"1\",\n" +
                    "            \"任务名称\": \"沈北新区监控中心 沈阳消防研究所\",\n" +
                    "            \"单位名称\": \"沈阳消防研究所\",\n" +
                    "            \"单位联系人\": \"\",\n" +
                    "            \"单位联系人电话\": \"\",\n" +
                    "            \"派单时间\": \"2010/11/23 11:10:30\",\n" +
                    "            \"任务状态\": \"3\",\n" +
                    "            \"派单人姓名\": \"admin\"\n" +
                    "        },\n" +
                    "        \"worklogs\": [\n" +
                    "            {\n" +
                    "                \"ID\": \"3\",\n" +
                    "                \"标题\": \"789\",\n" +
                    "                \"详细描述\": \"789\",\n" +
                    "                \"是否提交\": \"1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"2\",\n" +
                    "                \"标题\": \"456\",\n" +
                    "                \"详细描述\": \"456\",\n" +
                    "                \"是否提交\": \"1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"1\",\n" +
                    "                \"标题\": \"123\",\n" +
                    "                \"详细描述\": \"123\",\n" +
                    "                \"是否提交\": \"1\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"plans\": [\n" +
                    "            {\n" +
                    "                \"SId\": \"1\",\n" +
                    "                \"单位名称\": \"沈阳消防研究所\",\n" +
                    "                \"系统名称\": \"火灾自动报警系统\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"SId\": \"2\",\n" +
                    "                \"单位名称\": \"沈阳消防研究所\",\n" +
                    "                \"系统名称\": \"火灾自动报警系统\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"wokers\": [\n" +
                    "            {\n" +
                    "                \"维修人员ID\": \"8\",\n" +
                    "                \"维修人员真实姓名\": \"lianfd\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"workhoursum\": [\n" +
                    "            {\n" +
                    "                \"姓名\": \"lianfd\",\n" +
                    "                \"工时\": \"13\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
            response = new Response(body);
        } else if ((API_TASK + "maintain").equals(ifPage)) {
            String body = "{\n" +
                    "    \"success\": true,\n" +
                    "    \"message\": \"\",\n" +
                    "    \"data\": {\n" +
                    "        \"task\": {\n" +
                    "            \"ID\": \"3\",\n" +
                    "            \"任务名称\": \"沈阳消防研究所维修\",\n" +
                    "            \"单位名称\": \"沈阳消防研究所\",\n" +
                    "            \"单位联系人\": \"123(专职消防管理人)\",\n" +
                    "            \"单位联系人电话\": \"\",\n" +
                    "            \"派单时间\": \"2010/11/25 10:00:38\",\n" +
                    "            \"任务状态\": \"6\",\n" +
                    "            \"派单人姓名\": \"admin\"\n" +
                    "        },\n" +
                    "        \"worklogs\": [\n" +
                    "            {\n" +
                    "                \"ID\": \"35\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"34\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"33\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"32\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"31\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"30\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"29\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"28\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"27\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"26\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"25\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"24\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"23\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"21\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"12\",\n" +
                    "                \"标题\": \"1\",\n" +
                    "                \"详细描述\": \"2\",\n" +
                    "                \"是否提交\": \"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"5\",\n" +
                    "                \"标题\": \"1212\",\n" +
                    "                \"详细描述\": \"121\",\n" +
                    "                \"是否提交\": \"1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"4\",\n" +
                    "                \"标题\": \"1212\",\n" +
                    "                \"详细描述\": \"12121212\",\n" +
                    "                \"是否提交\": \"1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"3\",\n" +
                    "                \"标题\": \"123\",\n" +
                    "                \"详细描述\": \"123333\",\n" +
                    "                \"是否提交\": \"1\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"items\": [\n" +
                    "            {\n" +
                    "                \"ID\": \"9212\",\n" +
                    "                \"序号\": \"\",\n" +
                    "                \"报修来源\": \"3\",\n" +
                    "                \"报修时间\": \"2010/12/8 8:42:18\",\n" +
                    "                \"结束时间\": \"\",\n" +
                    "                \"维修状态\": \"1\",\n" +
                    "                \"系统名称\": \"气体灭火系统\",\n" +
                    "                \"单位\": \"公安部沈阳消防研究所\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"ID\": \"9214\",\n" +
                    "                \"序号\": \"0\",\n" +
                    "                \"报修来源\": \"3\",\n" +
                    "                \"报修时间\": \"2011/1/21 8:39:08\",\n" +
                    "                \"结束时间\": \"\",\n" +
                    "                \"维修状态\": \"2\",\n" +
                    "                \"系统名称\": \"火灾自动报警系统\",\n" +
                    "                \"单位\": \"沈阳消防研究所\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"wokers\": [\n" +
                    "            {\n" +
                    "                \"维修人员ID\": \"7\",\n" +
                    "                \"维修人员真实姓名\": \"lijm\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"维修人员ID\": \"10\",\n" +
                    "                \"维修人员真实姓名\": \"yandf\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"workhoursum\": [\n" +
                    "            {\n" +
                    "                \"姓名\": \"admin\",\n" +
                    "                \"工时\": \"11\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"姓名\": \"handler1\",\n" +
                    "                \"工时\": \"7\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"姓名\": \"lijm\",\n" +
                    "                \"工时\": \"39\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"姓名\": \"yandf\",\n" +
                    "                \"工时\": \"3\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
            response = new Response(body);
        }
        return response;
    }
}
