package com.app.agriculturalproducts.bean;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class EmployeeInfo  {
    private String employee_id;
    private String member_id;
    private String employee_name;
    private String employee_sex;
    private String employee_birthday;
    private String employee_tel;
    private String employee_address;
    private String employee_passport;
    private String employee_type;
    private String member_name;
    private String path;

    public void printfInfo(){
        Log.e("testcc", "employee_id"+":" + employee_id
                + " "+"member_id"+":" + member_id+ " "+"employee_name"+":" + employee_name
                + " "+"employee_sex"+":" + employee_sex+ " "+"employee_birthday"+":" + employee_birthday
                + " "+"employee_tel"+":" + employee_tel+ " "+"employee_address"+":" + employee_address
                + " "+"employee_passport"+":" + employee_passport + " "+"employee_type"+":" + employee_type
                + " "+"member_name"+":" + member_name);
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_sex() {
        return employee_sex;
    }

    public void setEmployee_sex(String employee_sex) {
        this.employee_sex = employee_sex;
    }

    public String getEmployee_birthday() {
        return employee_birthday;
    }

    public void setEmployee_birthday(String employee_birthday) {
        this.employee_birthday = employee_birthday;
    }

    public String getEmployee_tel() {
        return employee_tel;
    }

    public void setEmployee_tel(String employee_tel) {
        this.employee_tel = employee_tel;
    }

    public String getEmployee_address() {
        return employee_address;
    }

    public void setEmployee_address(String employee_address) {
        this.employee_address = employee_address;
    }

    public String getEmployee_passport() {
        return employee_passport;
    }

    public void setEmployee_passport(String employee_passport) {
        this.employee_passport = employee_passport;
    }

    public String getEmployee_type() {
        return employee_type;
    }

    public void setEmployee_type(String employee_type) {
        this.employee_type = employee_type;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



}