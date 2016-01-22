package com.app.agriculturalproducts.bean;

import android.database.Cursor;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/12.
 */
public class PlantSpecies {
    private String filed;
    private String plant_num;
    private String date;
    private long time;
    private String longtitude;
    private String latitude;
    private String location;

    public PlantSpecies(String filed, String plant_num) {
        this.filed = filed;
        this.plant_num = plant_num;
    }


    public static PlantSpecies fromCursor(Cursor cursor) {

        PlantSpecies fertilizerUsage = cupboard().withCursor(cursor).get(PlantSpecies.class);
        return fertilizerUsage;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlant_num() {
        return plant_num;
    }

    public void setPlant_num(String plant_num) {
        this.plant_num = plant_num;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }
}
