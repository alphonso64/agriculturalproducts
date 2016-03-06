package com.app.agriculturalproducts.bean;

import android.database.Cursor;
import android.util.Log;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class FertilizerRecord {
    private String fertilizerecord_id;
    private String fertilizerecord_date;
    private String fertilizerecord_name;
    private String fertilizerecord_number;
    private String fertilizerecord_range;
    private String fertilizerecord_type;
    private String fertilizerecord_spec;
    private String fertilizerecord_method;
    private String field_name;
    private String field_area;
    private String member_name;
    private String employee_name;
    private String plantrecord_breed;
    private String fertilizerecord_people;
    private String saved;
    private String local_stock_id;
    private String local_plant_id;
    private String local_plant_table_index;
    private Long _id;



    public static FertilizerRecord fromCursor(Cursor cursor) {
        FertilizerRecord fertilizerRecord= cupboard().withCursor(cursor).get(FertilizerRecord.class);
        return fertilizerRecord;
    }

    public String getFertilizerecord_id() {
        return fertilizerecord_id;
    }

    public void setFertilizerecord_id(String fertilizerecord_id) {
        this.fertilizerecord_id = fertilizerecord_id;
    }

    public String getFertilizerecord_date() {
        return fertilizerecord_date;
    }

    public void setFertilizerecord_date(String fertilizerecord_date) {
        this.fertilizerecord_date = fertilizerecord_date;
    }

    public String getFertilizerecord_name() {
        return fertilizerecord_name;
    }

    public void setFertilizerecord_name(String fertilizerecord_name) {
        this.fertilizerecord_name = fertilizerecord_name;
    }

    public String getFertilizerecord_number() {
        return fertilizerecord_number;
    }

    public void setFertilizerecord_number(String fertilizerecord_number) {
        this.fertilizerecord_number = fertilizerecord_number;
    }

    public String getFertilizerecord_range() {
        return fertilizerecord_range;
    }

    public void setFertilizerecord_range(String fertilizerecord_range) {
        this.fertilizerecord_range = fertilizerecord_range;
    }

    public String getFertilizerecord_type() {
        return fertilizerecord_type;
    }

    public void setFertilizerecord_type(String fertilizerecord_type) {
        this.fertilizerecord_type = fertilizerecord_type;
    }

    public String getFertilizerecord_spec() {
        return fertilizerecord_spec;
    }

    public void setFertilizerecord_spec(String fertilizerecord_spec) {
        this.fertilizerecord_spec = fertilizerecord_spec;
    }

    public String getFertilizerecord_method() {
        return fertilizerecord_method;
    }

    public void setFertilizerecord_method(String fertilizerecord_method) {
        this.fertilizerecord_method = fertilizerecord_method;
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

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getPlantrecord_breed() {
        return plantrecord_breed;
    }

    public void setPlantrecord_breed(String plantrecord_breed) {
        this.plantrecord_breed = plantrecord_breed;
    }

    public String getFertilizerecord_people() {
        return fertilizerecord_people;
    }

    public void setFertilizerecord_people(String fertilizerecord_people) {
        this.fertilizerecord_people = fertilizerecord_people;
    }

    public void printfInfo(){
        Log.e("testcc", "fertilizerecord_id" + ":" + fertilizerecord_id
                + " " + "fertilizerecord_date" + ":" + fertilizerecord_date + " " + "fertilizerecord_name" + ":" + fertilizerecord_name
                + " " + "fertilizerecord_number" + ":" + fertilizerecord_number + " " + "fertilizerecord_range" + ":" + fertilizerecord_range
                + " " + "fertilizerecord_type" + ":" + fertilizerecord_type + " " + "fertilizerecord_spec" + ":" + fertilizerecord_spec
                + " " + "fertilizerecord_method" + ":" + fertilizerecord_method + " " + "field_name" + ":" + field_name
                + " " + "field_area" + ":" + field_area + " " + "member_name" + ":" + member_name
                + " " + "employee_name" + ":" + employee_name+ " " + "plantrecord_breed" + ":" + plantrecord_breed
                        + " " + "fertilizerecord_people" + ":" + fertilizerecord_people
        );
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    public String getLocal_stock_id() {
        return local_stock_id;
    }

    public void setLocal_stock_id(String local_stock_id) {
        this.local_stock_id = local_stock_id;
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
