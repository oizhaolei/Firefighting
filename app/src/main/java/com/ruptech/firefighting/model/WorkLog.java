package com.ruptech.firefighting.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ls_gao on 2015/1/27.
 */
public class WorkLog {
    private String ID;
    private String 标题;
    private String 详细描述;
    private String 是否提交;
    private String CId;
    private String FId;
    private String SId;
    private List<WorkHour> workhours = null;

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

    public String get详细描述() {
        return 详细描述;
    }

    public void set详细描述(String 详细描述) {
        this.详细描述 = 详细描述;
    }

    public String get是否提交() {
        return 是否提交;
    }

    public void set是否提交(String 是否提交) {
        this.是否提交 = 是否提交;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getFId() {
        return FId;
    }

    public void setFId(String FId) {
        this.FId = FId;
    }

    public String getSId() {
        return SId;
    }

    public void setSId(String SId) {
        this.SId = SId;
    }

    public List<WorkHour> getWorkhours() {
        return workhours;
    }

    public void setWorkhours(List<WorkHour> workhours) {
        this.workhours = workhours;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ID", ID);
        map.put("标题", 标题);
        map.put("详细描述", 详细描述);
        map.put("是否提交", 是否提交);
        map.put("CId", CId);
        map.put("FId", FId);
        map.put("SId", SId);

        List<Map<String, Object>> workhourList = new ArrayList<Map<String, Object>>();
        for(WorkHour workhour : workhours) {
            Map<String, Object> workhourMap = workhour.toMap();
            workhourList.add(workhourMap);
        }

        map.put("workhours", workhourList);

        return map;
    }
}
