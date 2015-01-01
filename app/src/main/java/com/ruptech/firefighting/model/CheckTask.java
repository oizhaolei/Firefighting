package com.ruptech.firefighting.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhaolei on 15/1/1.
 */
public class CheckTask extends Task implements Serializable {

    private static final long serialVersionUID = 2394068598151253817L;
    private final String type;
    private String CId;
    private String 单位计划编号;
    private String 合同编号;
    private String 中心编号;
    private String 单位编号;
    private String 派单时间;
    private String 预定时间;
    private String 开始时间;
    private String 意见;
    private String 单位名称;
    private String 中心名称;
    private String 检查单状态;
    private String 状态编号;
    private String 结束时间;
    private String 派单人姓名;
    private String 派单人ID;
    private String 人员编号;
    private String 负责人;
    private String 负责人姓名;
    private String 审核人ID;
    private String 审核人姓名;
    private String 提交审核时间;
    private String 审核时间;
    private String 总工时;

    public CheckTask(JSONObject json, String type) {
        this.type = type;

        CId = json.optString("CId");
        单位计划编号 = json.optString("单位计划编号");
        合同编号 = json.optString("合同编号");
        中心编号 = json.optString("中心编号");
        单位编号 = json.optString("单位编号");
        派单时间 = json.optString("派单时间");
        预定时间 = json.optString("预定时间");
        开始时间 = json.optString("开始时间");
        意见 = json.optString("意见");
        单位名称 = json.optString("单位名称");
        中心名称 = json.optString("中心名称");
        检查单状态 = json.optString("检查单状态");
        状态编号 = json.optString("状态编号");
        结束时间 = json.optString("结束时间");
        派单人姓名 = json.optString("派单人姓名");
        派单人ID = json.optString("派单人ID");
        人员编号 = json.optString("人员编号");
        负责人 = json.optString("负责人");
        负责人姓名 = json.optString("负责人姓名");
        审核人ID = json.optString("审核人ID");
        审核人姓名 = json.optString("审核人姓名");
        提交审核时间 = json.optString("提交审核时间");
        审核时间 = json.optString("审核时间");
        总工时 = json.optString("总工时");
    }

    public String getType() {
        return type;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String get单位计划编号() {
        return 单位计划编号;
    }

    public void set单位计划编号(String 单位计划编号) {
        this.单位计划编号 = 单位计划编号;
    }

    public String get合同编号() {
        return 合同编号;
    }

    public void set合同编号(String 合同编号) {
        this.合同编号 = 合同编号;
    }

    public String get中心编号() {
        return 中心编号;
    }

    public void set中心编号(String 中心编号) {
        this.中心编号 = 中心编号;
    }

    public String get单位编号() {
        return 单位编号;
    }

    public void set单位编号(String 单位编号) {
        this.单位编号 = 单位编号;
    }

    public String get派单时间() {
        return 派单时间;
    }

    public void set派单时间(String 派单时间) {
        this.派单时间 = 派单时间;
    }

    public String get预定时间() {
        return 预定时间;
    }

    public void set预定时间(String 预定时间) {
        this.预定时间 = 预定时间;
    }

    public String get开始时间() {
        return 开始时间;
    }

    public void set开始时间(String 开始时间) {
        this.开始时间 = 开始时间;
    }

    public String get意见() {
        return 意见;
    }

    public void set意见(String 意见) {
        this.意见 = 意见;
    }

    public String get单位名称() {
        return 单位名称;
    }

    public void set单位名称(String 单位名称) {
        this.单位名称 = 单位名称;
    }

    public String get中心名称() {
        return 中心名称;
    }

    public void set中心名称(String 中心名称) {
        this.中心名称 = 中心名称;
    }

    public String get检查单状态() {
        return 检查单状态;
    }

    public void set检查单状态(String 检查单状态) {
        this.检查单状态 = 检查单状态;
    }

    public String get状态编号() {
        return 状态编号;
    }

    public void set状态编号(String 状态编号) {
        this.状态编号 = 状态编号;
    }

    public String get结束时间() {
        return 结束时间;
    }

    public void set结束时间(String 结束时间) {
        this.结束时间 = 结束时间;
    }

    public String get派单人姓名() {
        return 派单人姓名;
    }

    public void set派单人姓名(String 派单人姓名) {
        this.派单人姓名 = 派单人姓名;
    }

    public String get派单人ID() {
        return 派单人ID;
    }

    public void set派单人ID(String 派单人ID) {
        this.派单人ID = 派单人ID;
    }

    public String get人员编号() {
        return 人员编号;
    }

    public void set人员编号(String 人员编号) {
        this.人员编号 = 人员编号;
    }

    public String get负责人() {
        return 负责人;
    }

    public void set负责人(String 负责人) {
        this.负责人 = 负责人;
    }

    public String get负责人姓名() {
        return 负责人姓名;
    }

    public void set负责人姓名(String 负责人姓名) {
        this.负责人姓名 = 负责人姓名;
    }

    public String get审核人ID() {
        return 审核人ID;
    }

    public void set审核人ID(String 审核人ID) {
        this.审核人ID = 审核人ID;
    }

    public String get审核人姓名() {
        return 审核人姓名;
    }

    public void set审核人姓名(String 审核人姓名) {
        this.审核人姓名 = 审核人姓名;
    }

    public String get提交审核时间() {
        return 提交审核时间;
    }

    public void set提交审核时间(String 提交审核时间) {
        this.提交审核时间 = 提交审核时间;
    }

    public String get审核时间() {
        return 审核时间;
    }

    public void set审核时间(String 审核时间) {
        this.审核时间 = 审核时间;
    }

    public String get总工时() {
        return 总工时;
    }

    public void set总工时(String 总工时) {
        this.总工时 = 总工时;
    }
}
