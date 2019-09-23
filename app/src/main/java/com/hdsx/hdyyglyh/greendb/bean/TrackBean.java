package com.hdsx.hdyyglyh.greendb.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jkYishon on 2018/1/1.
 */

@Entity
public class TrackBean implements Serializable {

    static final long serialVersionUID = 100L;

    @Id(autoincrement = true)
    private Long id;

    private String userid;
    private String tracktime;
    private double latitude;
    private double lontitude;

    @Generated(hash = 1898556796)
    public TrackBean(Long id, String userid, String tracktime, double latitude,
            double lontitude) {
        this.id = id;
        this.userid = userid;
        this.tracktime = tracktime;
        this.latitude = latitude;
        this.lontitude = lontitude;
    }

    @Generated(hash = 2100714331)
    public TrackBean() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTracktime() {
        return tracktime;
    }

    public void setTracktime(String tracktime) {
        this.tracktime = tracktime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLontitude() {
        return lontitude;
    }

    public void setLontitude(double lontitude) {
        this.lontitude = lontitude;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
