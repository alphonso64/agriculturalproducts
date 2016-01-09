package com.app.agriculturalproducts.bean;

import android.database.Cursor;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/6.
 */
public class PersticidesUsage {
    private String name;
    private String usage;
    private String remarks;
    private long time;

    public static PersticidesUsage fromCursor(Cursor cursor) {

        PersticidesUsage persticidesUsage = cupboard().withCursor(cursor).get(PersticidesUsage.class);
        return persticidesUsage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
