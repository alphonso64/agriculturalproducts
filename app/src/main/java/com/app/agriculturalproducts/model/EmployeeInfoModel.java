package com.app.agriculturalproducts.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.bean.UserInfo;
import com.app.agriculturalproducts.util.InputType;

/**
 * Created by ALPHONSO on 2016/1/20.
 */
public class EmployeeInfoModel {
    private Context context;

    public EmployeeInfoModel(Context context) {
        this.context = context;
    }

    public EmployeeInfo getEmployeeInfo(){
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        String name = sp.getString("name",null);
        if(name !=null){
            sp = context.getSharedPreferences(name,
                    Activity.MODE_PRIVATE);
            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setEmployee_id(sp.getString("employee_id", null));
            employeeInfo.setMember_id(sp.getString("member_id", null));
            employeeInfo.setEmployee_name(sp.getString("employee_name", null));
            employeeInfo.setEmployee_sex(sp.getString("employee_sex", null));
            employeeInfo.setEmployee_birthday(sp.getString("employee_birthday", null));
            employeeInfo.setEmployee_tel(sp.getString("employee_tel", null));
            employeeInfo.setEmployee_address(sp.getString("employee_address", null));
            employeeInfo.setEmployee_passport(sp.getString("employee_passport", null));
            employeeInfo.setEmployee_type(sp.getString("employee_type", null));
            employeeInfo.setMember_name(sp.getString("member_name", null));
            employeeInfo.setPath(sp.getString("path",null));
            return employeeInfo;
        }
        return null;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo){
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        String name = sp.getString("name", null);

        if(name!=null){
            sp = context.getSharedPreferences(name,
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("employee_id",employeeInfo.getEmployee_id());
            editor.putString("member_id",employeeInfo.getMember_id());
            editor.putString("employee_name",employeeInfo.getEmployee_name());
            editor.putString("employee_sex",employeeInfo.getEmployee_sex());
            editor.putString("employee_birthday",employeeInfo.getEmployee_birthday());
            editor.putString("employee_tel",employeeInfo.getEmployee_tel());
            editor.putString("employee_address",employeeInfo.getEmployee_address());
            editor.putString("employee_passport",employeeInfo.getEmployee_passport());
            editor.putString("employee_type",employeeInfo.getEmployee_type());
            editor.putString("member_name",employeeInfo.getMember_name());
            editor.putString("path",employeeInfo.getPath());
            editor.commit();
        }


    }
}
