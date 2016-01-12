package com.app.agriculturalproducts.bean;

import android.database.Cursor;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/12.
 */
public class PlantSpecies {
    private String plantingPlots;
    private String plant;
    private String seed;
    private String producer;
    private long time;
    private String longtitude;
    private String latitude;
    private String location;


    public static PlantSpecies fromCursor(Cursor cursor) {

        PlantSpecies fertilizerUsage = cupboard().withCursor(cursor).get(PlantSpecies.class);
        return fertilizerUsage;
    }

    public String getPlantingPlots() {
        return plantingPlots;
    }

    public void setPlantingPlots(String plantingPlots) {
        this.plantingPlots = plantingPlots;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
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
}
