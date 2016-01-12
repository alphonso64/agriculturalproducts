package com.app.agriculturalproducts.bean;

import android.database.Cursor;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/6.
 */
public class Picking {
    private String pick;
    private long time;
    private String longtitude;
    private String latitude;
    private String location;


    public static Picking fromCursor(Cursor cursor) {

        Picking persticidesUsage = cupboard().withCursor(cursor).get(Picking.class);
        return persticidesUsage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }
}
