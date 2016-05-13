package com.app.agriculturalproducts.bean;

import android.database.Cursor;
import android.util.Log;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class PickRecord {
    private String pickrecord_id;
    private String pickrecord_date;
    private String pickrecord_number;
    private String pickrecord_area;
    private String pickrecord_people;
    private String pickrecord_code;
    private String plantrecord_plant_date;
    private String field_name;
    private String plantrecord_breed;
    private String field_area;
    private String pickrecord_status;
    private String member_name;
    private String saved;
    private String local_plant_id;
    private Long _id;
    private String local_plant_table_index;
    private String task_id;

    public static PickRecord fromCursor(Cursor cursor) {
        PickRecord pickRecord= cupboard().withCursor(cursor).get(PickRecord.class);
        return pickRecord;
    }

    public void printfInfo(){
        Log.e("testcc", "pickrecord_id" + ":" + pickrecord_id
                + " " + "pickrecord_date" + ":" + pickrecord_date + " " + "pickrecord_number" + ":" + pickrecord_number
                + " " + "pickrecord_area" + ":" + pickrecord_area + " " + "pickrecord_people" + ":" + pickrecord_people
                + " " + "pickrecord_code" + ":" + pickrecord_code + " " + "plantrecord_plant_date" + ":" + plantrecord_plant_date
                + " " + "field_name" + ":" + field_name + " " + "plantrecord_breed" + ":" + plantrecord_breed
                + " " + "field_area" + ":" + field_area + " " + "pickrecord_status" + ":" + pickrecord_status
                + " " + "member_name" + ":" + member_name);
    }

    public String getPickrecord_id() {
        return pickrecord_id;
    }

    public void setPickrecord_id(String pickrecord_id) {
        this.pickrecord_id = pickrecord_id;
    }

    public String getPickrecord_date() {
        return pickrecord_date;
    }

    public void setPickrecord_date(String pickrecord_date) {
        this.pickrecord_date = pickrecord_date;
    }

    public String getPickrecord_number() {
        return pickrecord_number;
    }

    public void setPickrecord_number(String pickrecord_number) {
        this.pickrecord_number = pickrecord_number;
    }

    public String getPickrecord_area() {
        return pickrecord_area;
    }

    public void setPickrecord_area(String pickrecord_area) {
        this.pickrecord_area = pickrecord_area;
    }

    public String getPickrecord_people() {
        return pickrecord_people;
    }

    public void setPickrecord_people(String pickrecord_people) {
        this.pickrecord_people = pickrecord_people;
    }

    public String getPickrecord_code() {
        return pickrecord_code;
    }

    public void setPickrecord_code(String pickrecord_code) {
        this.pickrecord_code = pickrecord_code;
    }

    public String getPlantrecord_plant_date() {
        return plantrecord_plant_date;
    }

    public void setPlantrecord_plant_date(String plantrecord_plant_date) {
        this.plantrecord_plant_date = plantrecord_plant_date;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getPlantrecord_breed() {
        return plantrecord_breed;
    }

    public void setPlantrecord_breed(String plantrecord_breed) {
        this.plantrecord_breed = plantrecord_breed;
    }

    public String getField_area() {
        return field_area;
    }

    public void setField_area(String field_area) {
        this.field_area = field_area;
    }

    public String getPickrecord_status() {
        return pickrecord_status;
    }

    public void setPickrecord_status(String pickrecord_status) {
        this.pickrecord_status = pickrecord_status;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    public String getLocal_plant_id() {
        return local_plant_id;
    }

    public void setLocal_plant_id(String local_plant_id) {
        this.local_plant_id = local_plant_id;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getLocal_plant_table_index() {
        return local_plant_table_index;
    }

    public void setLocal_plant_table_index(String local_plant_table_index) {
        this.local_plant_table_index = local_plant_table_index;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
}
