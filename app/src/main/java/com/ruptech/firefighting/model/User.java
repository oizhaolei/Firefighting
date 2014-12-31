package com.ruptech.firefighting.model;

import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 2394068598151253815L;

    private long id;
    private String 编号;
    private String 用户名;

    public User(JSONObject json) {
        编号 = json.optString("编号");
        用户名 = json.optString("用户名");

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String get编号() {
        return 编号;
    }

    public void set编号(String 编号) {
        this.编号 = 编号;
    }

    public String get用户名() {
        return 用户名;
    }

    public void set用户名(String 用户名) {
        this.用户名 = 用户名;
    }
}
