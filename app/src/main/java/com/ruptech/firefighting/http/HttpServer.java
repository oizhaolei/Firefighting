package com.ruptech.firefighting.http;


import com.ruptech.firefighting.model.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpServer extends HttpConnection {
    private final String TAG = HttpServer.class.getSimpleName();
    private String API_LOGIN = "user_login.php";

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

    protected Response _get(String ifPage, Map<String, String> params) {
        Response response = null;
        if (API_LOGIN.equals(ifPage)) {
            String body = "{ \"success\": true, \"message\": \"\", \"data\": { \"编号\": \"1\", \"用户名\": \"admin\", \"密码\": \"111111\", \"中心编号\": \"35020301\", \"真实姓名\": \"admin\", \"性别\": \"男\", \"移动电话\": \"34\", \"固定电话\": \"34\", \"邮箱\": \"3434\", \"角色编号\": \"1\", \"人员类型\": \"1\", \"人员状态\": \"1\", \"IsDel\": \"False\" } }";
            response = new Response(body);
        }
        return response;
    }

}
