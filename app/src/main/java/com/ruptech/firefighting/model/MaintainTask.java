package com.ruptech.firefighting.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhaolei on 15/1/1.
 */
public class MaintainTask extends Task implements Serializable {

    private static final long serialVersionUID = 2394068598151253816L;
    private final String type;
    private String ID;
    private String 标题;
    private String 任务状态;
    private String 所属中心ID;
    private String 当前负责人ID;
    private String 单位ID;
    private String 单位联系人;
    private String 单位联系人电话;
    private String 派单时间;
    private String 提交审核时间;
    private String 故障现象;
    private String 故障原因;
    private String 维修措施;
    private String 维修总工时;
    private String 审核时间;
    private String 审核人ID;
    private String 审核结果;
    private String 不合格原因;
    private String 结束时间;
    private String 派单人ID;


    public MaintainTask(JSONObject json, String type) {
        this.type = type;

        ID = json.optString("ID");
        标题 = json.optString("标题");
        任务状态 = json.optString("任务状态");
        所属中心ID = json.optString("所属中心ID");
        当前负责人ID = json.optString("当前负责人ID");
        单位ID = json.optString("单位ID");
        单位联系人 = json.optString("单位联系人");
        单位联系人电话 = json.optString("单位联系人电话");
        派单时间 = json.optString("派单时间");
        提交审核时间 = json.optString("提交审核时间");
        故障现象 = json.optString("故障现象");
        故障原因 = json.optString("故障原因");
        维修措施 = json.optString("维修措施");
        维修总工时 = json.optString("维修总工时");
        审核时间 = json.optString("审核时间");
        审核人ID = json.optString("审核人ID");
        审核结果 = json.optString("审核结果");
        不合格原因 = json.optString("不合格原因");
        结束时间 = json.optString("结束时间");
        派单人ID = json.optString("派单人ID");
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String get标题() {
        return 标题;
    }

    public void set标题(String 标题) {
        this.标题 = 标题;
    }

    public String get任务状态() {
        return 任务状态;
    }

    public void set任务状态(String 任务状态) {
        this.任务状态 = 任务状态;
    }

    public String get所属中心ID() {
        return 所属中心ID;
    }

    public void set所属中心ID(String 所属中心ID) {
        this.所属中心ID = 所属中心ID;
    }

    public String get当前负责人ID() {
        return 当前负责人ID;
    }

    public void set当前负责人ID(String 当前负责人ID) {
        this.当前负责人ID = 当前负责人ID;
    }

    public String get单位ID() {
        return 单位ID;
    }

    public void set单位ID(String 单位ID) {
        this.单位ID = 单位ID;
    }

    public String get单位联系人() {
        return 单位联系人;
    }

    public void set单位联系人(String 单位联系人) {
        this.单位联系人 = 单位联系人;
    }

    public String get单位联系人电话() {
        return 单位联系人电话;
    }

    public void set单位联系人电话(String 单位联系人电话) {
        this.单位联系人电话 = 单位联系人电话;
    }

    public String get派单时间() {
        return 派单时间;
    }

    public void set派单时间(String 派单时间) {
        this.派单时间 = 派单时间;
    }

    public String get提交审核时间() {
        return 提交审核时间;
    }

    public void set提交审核时间(String 提交审核时间) {
        this.提交审核时间 = 提交审核时间;
    }

    public String get故障现象() {
        return 故障现象;
    }

    public void set故障现象(String 故障现象) {
        this.故障现象 = 故障现象;
    }

    public String get故障原因() {
        return 故障原因;
    }

    public void set故障原因(String 故障原因) {
        this.故障原因 = 故障原因;
    }

    public String get维修措施() {
        return 维修措施;
    }

    public void set维修措施(String 维修措施) {
        this.维修措施 = 维修措施;
    }

    public String get维修总工时() {
        return 维修总工时;
    }

    public void set维修总工时(String 维修总工时) {
        this.维修总工时 = 维修总工时;
    }

    public String get审核时间() {
        return 审核时间;
    }

    public void set审核时间(String 审核时间) {
        this.审核时间 = 审核时间;
    }

    public String get审核人ID() {
        return 审核人ID;
    }

    public void set审核人ID(String 审核人ID) {
        this.审核人ID = 审核人ID;
    }

    public String get审核结果() {
        return 审核结果;
    }

    public void set审核结果(String 审核结果) {
        this.审核结果 = 审核结果;
    }

    public String get不合格原因() {
        return 不合格原因;
    }

    public void set不合格原因(String 不合格原因) {
        this.不合格原因 = 不合格原因;
    }

    public String get结束时间() {
        return 结束时间;
    }

    public void set结束时间(String 结束时间) {
        this.结束时间 = 结束时间;
    }

    public String get派单人ID() {
        return 派单人ID;
    }

    public void set派单人ID(String 派单人ID) {
        this.派单人ID = 派单人ID;
    }
}
