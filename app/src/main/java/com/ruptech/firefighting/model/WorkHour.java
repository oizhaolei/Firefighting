package com.ruptech.firefighting.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ls_gao on 2015/1/27.
 */
public class WorkHour {
    private String 维修人员ID;
    private String 开始时间;
    private String 结束时间;
    private String 工时 = "0.0";
    private String 维修人员姓名;

    public String get维修人员ID() {
        return 维修人员ID;
    }

    public void set维修人员ID(String 维修人员ID) {
        this.维修人员ID = 维修人员ID;
    }

    public String get开始时间() {
        return 开始时间;
    }

    public void set开始时间(String 开始时间) {
        this.开始时间 = 开始时间;
    }

    public String get结束时间() {
        return 结束时间;
    }

    public void set结束时间(String 结束时间) {
        this.结束时间 = 结束时间;
    }

    public String get工时() {
        return 工时;
    }

    public void set工时(String 工时) {
        this.工时 = 工时;
    }

    public String get维修人员姓名() {
        return 维修人员姓名;
    }

    public void set维修人员姓名(String 维修人员姓名) {
        this.维修人员姓名 = 维修人员姓名;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("维修人员ID", 维修人员ID);
        map.put("开始时间", 开始时间);
        map.put("结束时间", 结束时间);
        map.put("工时", 工时);
        map.put("维修人员姓名", 维修人员姓名);

        return map;
    }
}
