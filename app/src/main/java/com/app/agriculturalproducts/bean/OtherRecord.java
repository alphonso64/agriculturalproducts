package com.app.agriculturalproducts.bean;

import android.database.Cursor;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class OtherRecord {
    private String otherrecord_id;
    private String otherrecord_situation;
    private String otherrecord_date;
    private String otherrecord_method;
    private String otherrecord_place;
    private String otherrecord_people;
    private String plantrecord_breed;
    private String field_name;
    private String member_name;
    private String saved;
    private String local_plant_id;
    private Long _id;
    private String local_plant_table_index;

    public static OtherRecord fromCursor(Cursor cursor) {
        OtherRecord otherRecord= cupboard().withCursor(cursor).get(OtherRecord.class);
        return otherRecord;
    }

    public String getOtherrecord_id() {
        return otherrecord_id;
    }

    public void setOtherrecord_id(String otherrecord_id) {
        this.otherrecord_id = otherrecord_id;
    }

    public String getOtherrecord_situation() {
        return otherrecord_situation;
    }

    public void setOtherrecord_situation(String otherrecord_situation) {
        this.otherrecord_situation = otherrecord_situation;
    }

    public String getOtherrecord_date() {
        return otherrecord_date;
    }

    public void setOtherrecord_date(String otherrecord_date) {
        this.otherrecord_date = otherrecord_date;
    }

    public String getOtherrecord_method() {
        return otherrecord_method;
    }

    public void setOtherrecord_method(String otherrecord_method) {
        this.otherrecord_method = otherrecord_method;
    }

    public String getOtherrecord_place() {
        return otherrecord_place;
    }

    public void setOtherrecord_place(String otherrecord_place) {
        this.otherrecord_place = otherrecord_place;
    }

    public String getOtherrecord_people() {
        return otherrecord_people;
    }

    public void setOtherrecord_people(String otherrecord_people) {
        this.otherrecord_people = otherrecord_people;
    }

    public String getPlantrecord_breed() {
        return plantrecord_breed;
    }

    public void setPlantrecord_breed(String plantrecord_breed) {
        this.plantrecord_breed = plantrecord_breed;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
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
}
