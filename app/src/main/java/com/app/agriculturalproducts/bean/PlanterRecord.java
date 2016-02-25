package com.app.agriculturalproducts.bean;

import android.util.Log;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class PlanterRecord {
    private String plantrecord_id;
    private String plantrecord_seed_name;
    private String plantrecord_breed;
    private String plantrecord_seed_source;
    private String plantrecord_specifications;
    private String plantrecord_seed_number;
    private String plantrecord_plant_date;
    private String field_name;
    private String employee_name;

    public void printfInfo(){
        Log.e("testcc", "plantrecord_id" + ":" + plantrecord_id
                + " " + "plantrecord_seed_name" + ":" + plantrecord_seed_name + " " + "plantrecord_breed" + ":" + plantrecord_breed
                + " " + "plantrecord_seed_source" + ":" + plantrecord_seed_source + " " + "plantrecord_specifications" + ":" + plantrecord_specifications
                + " " + "plantrecord_seed_number" + ":" + plantrecord_seed_number + " " + "plantrecord_plant_date" + ":" + plantrecord_plant_date
                + " " + "field_name" + ":" + field_name + " " + "employee_name" + ":" + employee_name);
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getPlantrecord_plant_date() {
        return plantrecord_plant_date;
    }

    public void setPlantrecord_plant_date(String plantrecord_plant_date) {
        this.plantrecord_plant_date = plantrecord_plant_date;
    }

    public String getPlantrecord_seed_number() {
        return plantrecord_seed_number;
    }

    public void setPlantrecord_seed_number(String plantrecord_seed_number) {
        this.plantrecord_seed_number = plantrecord_seed_number;
    }

    public String getPlantrecord_specifications() {
        return plantrecord_specifications;
    }

    public void setPlantrecord_specifications(String plantrecord_specifications) {
        this.plantrecord_specifications = plantrecord_specifications;
    }

    public String getPlantrecord_seed_source() {
        return plantrecord_seed_source;
    }

    public void setPlantrecord_seed_source(String plantrecord_seed_source) {
        this.plantrecord_seed_source = plantrecord_seed_source;
    }

    public String getPlantrecord_breed() {
        return plantrecord_breed;
    }

    public void setPlantrecord_breed(String plantrecord_breed) {
        this.plantrecord_breed = plantrecord_breed;
    }

    public String getPlantrecord_seed_name() {
        return plantrecord_seed_name;
    }

    public void setPlantrecord_seed_name(String plantrecord_seed_name) {
        this.plantrecord_seed_name = plantrecord_seed_name;
    }

    public String getPlantrecord_id() {
        return plantrecord_id;
    }

    public void setPlantrecord_id(String plantrecord_id) {
        this.plantrecord_id = plantrecord_id;
    }
}
