package com.app.agriculturalproducts.bean;

import android.database.Cursor;
import android.util.Log;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class PreventionRecord {
    private String preventionrecord_id;
    private String preventionrecord_medicine_name;
    private String preventionrecord_date;
    private String preventionrecord_range;
    private String preventionrecord_type;
    private String preventionrecord_spec;
    private String preventionrecord_method;
    private String preventionrecord_medicine_number;
    private String preventionrecord_plant_day;
    private String preventionrecord_symptom;
    private String preventionrecord_medicine_people;
    private String preventionrecord_use_people;
    private String field_name;
    private String field_area;
    private String plantrecord_breed;
    private String plantrecord_plant_date;
    private String member_name;


    public static PreventionRecord fromCursor(Cursor cursor) {
        PreventionRecord preventionRecord= cupboard().withCursor(cursor).get(PreventionRecord.class);
        return preventionRecord;
    }

    public void printfInfo(){
        Log.e("testcc", "preventionrecord_id" + ":" + preventionrecord_id
                + " " + "preventionrecord_medicine_name" + ":" + preventionrecord_medicine_name + " " + "preventionrecord_date" + ":" + preventionrecord_date
                + " " + "preventionrecord_range" + ":" + preventionrecord_range + " " + "preventionrecord_type" + ":" + preventionrecord_type
                + " " + "preventionrecord_spec" + ":" + preventionrecord_spec + " " + "preventionrecord_method" + ":" + preventionrecord_method
                + " " + "preventionrecord_medicine_number" + ":" + preventionrecord_medicine_number + " " + "preventionrecord_plant_day" + ":" + preventionrecord_plant_day
                + " " + "preventionrecord_symptom" + ":" + preventionrecord_symptom + " " + "preventionrecord_medicine_people" + ":" + preventionrecord_medicine_people
                + " " + "preventionrecord_use_people" + ":" + preventionrecord_use_people + " " + "field_name" + ":" + field_name
                + " " + "field_area" + ":" + field_area + " " + "plantrecord_breed" + ":" + plantrecord_breed
                + " " + "plantrecord_plant_date" + ":" + plantrecord_plant_date + " " + "member_name" + ":" + member_name);
    }

    public String getPreventionrecord_id() {
        return preventionrecord_id;
    }

    public void setPreventionrecord_id(String preventionrecord_id) {
        this.preventionrecord_id = preventionrecord_id;
    }

    public String getPreventionrecord_medicine_name() {
        return preventionrecord_medicine_name;
    }

    public void setPreventionrecord_medicine_name(String preventionrecord_medicine_name) {
        this.preventionrecord_medicine_name = preventionrecord_medicine_name;
    }

    public String getPreventionrecord_date() {
        return preventionrecord_date;
    }

    public void setPreventionrecord_date(String preventionrecord_date) {
        this.preventionrecord_date = preventionrecord_date;
    }

    public String getPreventionrecord_range() {
        return preventionrecord_range;
    }

    public void setPreventionrecord_range(String preventionrecord_range) {
        this.preventionrecord_range = preventionrecord_range;
    }

    public String getPreventionrecord_type() {
        return preventionrecord_type;
    }

    public void setPreventionrecord_type(String preventionrecord_type) {
        this.preventionrecord_type = preventionrecord_type;
    }

    public String getPreventionrecord_spec() {
        return preventionrecord_spec;
    }

    public void setPreventionrecord_spec(String preventionrecord_spec) {
        this.preventionrecord_spec = preventionrecord_spec;
    }

    public String getPreventionrecord_method() {
        return preventionrecord_method;
    }

    public void setPreventionrecord_method(String preventionrecord_method) {
        this.preventionrecord_method = preventionrecord_method;
    }

    public String getPreventionrecord_medicine_number() {
        return preventionrecord_medicine_number;
    }

    public void setPreventionrecord_medicine_number(String preventionrecord_medicine_number) {
        this.preventionrecord_medicine_number = preventionrecord_medicine_number;
    }

    public String getPreventionrecord_symptom() {
        return preventionrecord_symptom;
    }

    public void setPreventionrecord_symptom(String preventionrecord_symptom) {
        this.preventionrecord_symptom = preventionrecord_symptom;
    }

    public String getPreventionrecord_plant_day() {
        return preventionrecord_plant_day;
    }

    public void setPreventionrecord_plant_day(String preventionrecord_plant_day) {
        this.preventionrecord_plant_day = preventionrecord_plant_day;
    }

    public String getPreventionrecord_medicine_people() {
        return preventionrecord_medicine_people;
    }

    public void setPreventionrecord_medicine_people(String preventionrecord_medicine_people) {
        this.preventionrecord_medicine_people = preventionrecord_medicine_people;
    }

    public String getPreventionrecord_use_people() {
        return preventionrecord_use_people;
    }

    public void setPreventionrecord_use_people(String preventionrecord_use_people) {
        this.preventionrecord_use_people = preventionrecord_use_people;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_area() {
        return field_area;
    }

    public void setField_area(String field_area) {
        this.field_area = field_area;
    }

    public String getPlantrecord_breed() {
        return plantrecord_breed;
    }

    public void setPlantrecord_breed(String plantrecord_breed) {
        this.plantrecord_breed = plantrecord_breed;
    }

    public String getPlantrecord_plant_date() {
        return plantrecord_plant_date;
    }

    public void setPlantrecord_plant_date(String plantrecord_plant_date) {
        this.plantrecord_plant_date = plantrecord_plant_date;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

//    public void printfInfo(){
//        Log.e("testcc", "plantrecord_id" + ":" + plantrecord_id
//                + " " + "plantrecord_seed_name" + ":" + plantrecord_seed_name + " " + "plantrecord_breed" + ":" + plantrecord_breed
//                + " " + "plantrecord_seed_source" + ":" + plantrecord_seed_source + " " + "plantrecord_specifications" + ":" + plantrecord_specifications
//                + " " + "plantrecord_seed_number" + ":" + plantrecord_seed_number + " " + "plantrecord_plant_date" + ":" + plantrecord_plant_date
//                + " " + "field_name" + ":" + field_name + " " + "employee_name" + ":" + employee_name);
//    }
}
