package com.app.agriculturalproducts.bean;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.app.agriculturalproducts.db.TaskDataHelper;

import java.io.Serializable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/6.
 */
public class Task implements Serializable{
    private Long _id;
    private String title;
    private String detail;
    private String ImgPath;
    private String isDone;

    private String field;
    private String species;

    private String p_area;
    private String p_type;
    private String p_spec;
    private String p_name;

    private String f_area;
    private String f_type;
    private String f_spec;
    private String f_method;
    private String f_name;

    private String pick_area;

    private String date;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public static Task fromCursor(Cursor cursor) {
        Task task = cupboard().withCursor(cursor).get(Task.class);
        return task;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public String isDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getP_area() {
        return p_area;
    }

    public void setP_area(String p_area) {
        this.p_area = p_area;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public String getP_spec() {
        return p_spec;
    }

    public void setP_spec(String p_spec) {
        this.p_spec = p_spec;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getF_area() {
        return f_area;
    }

    public void setF_area(String f_area) {
        this.f_area = f_area;
    }

    public String getF_type() {
        return f_type;
    }

    public void setF_type(String f_type) {
        this.f_type = f_type;
    }

    public String getF_spec() {
        return f_spec;
    }

    public void setF_spec(String f_spec) {
        this.f_spec = f_spec;
    }

    public String getF_method() {
        return f_method;
    }

    public void setF_method(String f_method) {
        this.f_method = f_method;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getPick_area() {
        return pick_area;
    }

    public void setPick_area(String pick_area) {
        this.pick_area = pick_area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

}
