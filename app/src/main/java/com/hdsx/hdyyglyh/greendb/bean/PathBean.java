package com.hdsx.hdyyglyh.greendb.bean;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class PathBean {

    @Id
    private String userid;

    private String name;
    private String tracktime;
    private String polygon;//wgs84
    private String polygonbd;//bd09
    @Generated(hash = 957777609)
    public PathBean(String userid, String name, String tracktime, String polygon,
            String polygonbd) {
        this.userid = userid;
        this.name = name;
        this.tracktime = tracktime;
        this.polygon = polygon;
        this.polygonbd = polygonbd;
    }
    @Generated(hash = 277194800)
    public PathBean() {
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTracktime() {
        return this.tracktime;
    }
    public void setTracktime(String tracktime) {
        this.tracktime = tracktime;
    }
    public String getPolygon() {
        return this.polygon;
    }
    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }
    public String getPolygonbd() {
        return this.polygonbd;
    }
    public void setPolygonbd(String polygonbd) {
        this.polygonbd = polygonbd;
    }
}
