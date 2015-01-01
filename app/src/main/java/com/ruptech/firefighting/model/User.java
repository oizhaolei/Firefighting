package com.ruptech.firefighting.model;

import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 2394068598151253815L;

    private String 编号;
    private String 用户名;
    private String 密码;
    private String 中心编号;
    private String 真实姓名;
    private String 性别;
    private String 移动电话;
    private String 固定电话;
    private String 邮箱;
    private String 角色编号;
    private String 人员类型;
    private String 人员状态;
    private String IsDel;


    public User(JSONObject json) {
        编号 = json.optString("编号");
        用户名 = json.optString("用户名");
        编号 = json.optString("编号");
        用户名 = json.optString("用户名");
        密码 = json.optString("密码");
        中心编号 = json.optString("中心编号");
        真实姓名 = json.optString("真实姓名");
        性别 = json.optString("性别");
        移动电话 = json.optString("移动电话");
        固定电话 = json.optString("固定电话");
        邮箱 = json.optString("邮箱");
        角色编号 = json.optString("角色编号");
        人员类型 = json.optString("人员类型");
        人员状态 = json.optString("人员状态");
        IsDel = json.optString("IsDel");
    }

    public String get密码() {
        return 密码;
    }

    public void set密码(String 密码) {
        this.密码 = 密码;
    }

    public String get中心编号() {
        return 中心编号;
    }

    public void set中心编号(String 中心编号) {
        this.中心编号 = 中心编号;
    }

    public String get真实姓名() {
        return 真实姓名;
    }

    public void set真实姓名(String 真实姓名) {
        this.真实姓名 = 真实姓名;
    }

    public String get性别() {
        return 性别;
    }

    public void set性别(String 性别) {
        this.性别 = 性别;
    }

    public String get移动电话() {
        return 移动电话;
    }

    public void set移动电话(String 移动电话) {
        this.移动电话 = 移动电话;
    }

    public String get固定电话() {
        return 固定电话;
    }

    public void set固定电话(String 固定电话) {
        this.固定电话 = 固定电话;
    }

    public String get邮箱() {
        return 邮箱;
    }

    public void set邮箱(String 邮箱) {
        this.邮箱 = 邮箱;
    }

    public String get角色编号() {
        return 角色编号;
    }

    public void set角色编号(String 角色编号) {
        this.角色编号 = 角色编号;
    }

    public String get人员类型() {
        return 人员类型;
    }

    public void set人员类型(String 人员类型) {
        this.人员类型 = 人员类型;
    }

    public String get人员状态() {
        return 人员状态;
    }

    public void set人员状态(String 人员状态) {
        this.人员状态 = 人员状态;
    }

    public String getIsDel() {
        return IsDel;
    }

    public void setIsDel(String isDel) {
        IsDel = isDel;
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
