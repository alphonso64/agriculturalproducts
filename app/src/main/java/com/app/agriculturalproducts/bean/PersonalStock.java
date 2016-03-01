package com.app.agriculturalproducts.bean;

import android.database.Cursor;
import android.util.Log;

import nl.qbusict.cupboard.annotation.Index;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class PersonalStock {

    public static String SEED_TYPE = "seed";
    public static String F_TYPE = "f";
    public static String P_TYPE = "p";

    private String personalstock_id;
    private String personalstock_num;
    private String personalstock_goods_id;
    private String personalstock_goods_type;
    private String personalstock_goods_name;
    private String spec;
    private String producer;
    private String employee_name;
    private String member_name;
    private String type;
    private String breed;
    private String method;
    private String safe_spacing;

    public void printfInfo(){
        Log.e("testcc", "personalstock_id" + ":" + personalstock_id
                + " " + "personalstock_num" + ":" + personalstock_num + " " + "personalstock_goods_id" + ":" + personalstock_goods_id
                + " " + "personalstock_goods_type" + ":" + personalstock_goods_type + " " + "personalstock_goods_name" + ":" + personalstock_goods_name
                + " " + "spec" + ":" + spec + " " + "producer" + ":" + producer
                + " " + "employee_name" + ":" + employee_name + " " + "member_name" + ":" + member_name
                + " " + "type" + ":" + type);
    }

    public static PersonalStock fromCursor(Cursor cursor) {
        PersonalStock personalStock= cupboard().withCursor(cursor).get(PersonalStock.class);
        return personalStock;
    }


    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getPersonalstock_id() {
        return personalstock_id;
    }

    public void setPersonalstock_id(String personalstock_id) {
        this.personalstock_id = personalstock_id;
    }

    public String getPersonalstock_num() {
        return personalstock_num;
    }

    public void setPersonalstock_num(String personalstock_num) {
        this.personalstock_num = personalstock_num;
    }

    public String getPersonalstock_goods_id() {
        return personalstock_goods_id;
    }

    public void setPersonalstock_goods_id(String personalstock_goods_id) {
        this.personalstock_goods_id = personalstock_goods_id;
    }

    public String getPersonalstock_goods_type() {
        return personalstock_goods_type;
    }

    public void setPersonalstock_goods_type(String personalstock_goods_type) {
        this.personalstock_goods_type = personalstock_goods_type;
    }

    public String getPersonalstock_goods_name() {
        return personalstock_goods_name;
    }

    public void setPersonalstock_goods_name(String personalstock_goods_name) {
        this.personalstock_goods_name = personalstock_goods_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSafe_spacing() {
        return safe_spacing;
    }

    public void setSafe_spacing(String safe_spacing) {
        this.safe_spacing = safe_spacing;
    }
}
