package com.app.agriculturalproducts.bean;

import android.database.Cursor;
import android.util.Log;


import nl.qbusict.cupboard.annotation.Index;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class Field  {
    private String field_id;
    private String employee_id;
    private String field_name;
    private String field_type;
    private String field_area;
    private String field_status;

    public void printfInfo(){
        Log.e("testcc", "field_id" + ":" + field_id
                + " " + "employee_id" + ":" + employee_id + " " + "field_name" + ":" + field_name
                + " " + "field_type" + ":" + field_type + " " + "field_area" + ":" + field_area
                + " " + "field_status" + ":" + field_status);
    }

    public static Field fromCursor(Cursor cursor) {

        Field field= cupboard().withCursor(cursor).get(Field.class);
        return field;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getField_id() {
        return field_id;
    }

    public void setField_id(String field_id) {
        this.field_id = field_id;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getField_area() {
        return field_area;
    }

    public void setField_area(String field_area) {
        this.field_area = field_area;
    }

    public String getField_status() {
        return field_status;
    }

    public void setField_status(String field_status) {
        this.field_status = field_status;
    }
}
