package com.app.agriculturalproducts.bean;

import android.database.Cursor;
import android.util.Log;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class PersonalStockDetail {
    private String personalstockdetail_id;
    private String personalstockdetail_orderno;
    private String personalstockdetail_goods_id;
    private String personalstockdetail_num;
    private String personalstockdetail_price;
    private String personalstockdetail_total;
    private String personalstockdetail_goods_type;
    private String personalstockdetail_date;
    private String personalstockdetail_type;
    private String personalstockdetail_goods_name;
    private String spec;
    private String producer;
    private String employee_name;
    private String member_name;

    public void printfInfo(){
        Log.e("testcc", "personalstockdetail_id" + ":" + personalstockdetail_id
                + " " + "personalstockdetail_orderno" + ":" + personalstockdetail_orderno + " " + "personalstockdetail_goods_id" + ":" + personalstockdetail_goods_id
                + " " + "personalstockdetail_num" + ":" + personalstockdetail_num + " " + "personalstockdetail_price" + ":" + personalstockdetail_price
                +"personalstockdetail_total" + ":" + personalstockdetail_total
                + " " + "personalstockdetail_goods_type" + ":" + personalstockdetail_goods_type + " " + "personalstockdetail_date" + ":" + personalstockdetail_date
                + " " + "personalstockdetail_type" + ":" + personalstockdetail_type + " " + "personalstockdetail_goods_name" + ":" + personalstockdetail_goods_name
                + " " + "spec" + ":" + spec + " " + "producer" + ":" + producer
                + " " + "employee_name" + ":" + employee_name + " " + "member_name" + ":" + member_name);
    }

    public static PersonalStockDetail fromCursor(Cursor cursor) {
        PersonalStockDetail personalStockDetail= cupboard().withCursor(cursor).get(PersonalStockDetail.class);
        return personalStockDetail;
    }

    public String getPersonalstockdetail_id() {
        return personalstockdetail_id;
    }

    public void setPersonalstockdetail_id(String personalstockdetail_id) {
        this.personalstockdetail_id = personalstockdetail_id;
    }

    public String getPersonalstockdetail_orderno() {
        return personalstockdetail_orderno;
    }

    public void setPersonalstockdetail_orderno(String personalstockdetail_orderno) {
        this.personalstockdetail_orderno = personalstockdetail_orderno;
    }

    public String getPersonalstockdetail_goods_id() {
        return personalstockdetail_goods_id;
    }

    public void setPersonalstockdetail_goods_id(String personalstockdetail_goods_id) {
        this.personalstockdetail_goods_id = personalstockdetail_goods_id;
    }

    public String getPersonalstockdetail_num() {
        return personalstockdetail_num;
    }

    public void setPersonalstockdetail_num(String personalstockdetail_num) {
        this.personalstockdetail_num = personalstockdetail_num;
    }

    public String getPersonalstockdetail_price() {
        return personalstockdetail_price;
    }

    public void setPersonalstockdetail_price(String personalstockdetail_price) {
        this.personalstockdetail_price = personalstockdetail_price;
    }

    public String getPersonalstockdetail_total() {
        return personalstockdetail_total;
    }

    public void setPersonalstockdetail_total(String personalstockdetail_total) {
        this.personalstockdetail_total = personalstockdetail_total;
    }

    public String getPersonalstockdetail_goods_type() {
        return personalstockdetail_goods_type;
    }

    public void setPersonalstockdetail_goods_type(String personalstockdetail_goods_type) {
        this.personalstockdetail_goods_type = personalstockdetail_goods_type;
    }

    public String getPersonalstockdetail_date() {
        return personalstockdetail_date;
    }

    public void setPersonalstockdetail_date(String personalstockdetail_date) {
        this.personalstockdetail_date = personalstockdetail_date;
    }

    public String getPersonalstockdetail_type() {
        return personalstockdetail_type;
    }

    public void setPersonalstockdetail_type(String personalstockdetail_type) {
        this.personalstockdetail_type = personalstockdetail_type;
    }

    public String getPersonalstockdetail_goods_name() {
        return personalstockdetail_goods_name;
    }

    public void setPersonalstockdetail_goods_name(String personalstockdetail_goods_name) {
        this.personalstockdetail_goods_name = personalstockdetail_goods_name;
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
}
