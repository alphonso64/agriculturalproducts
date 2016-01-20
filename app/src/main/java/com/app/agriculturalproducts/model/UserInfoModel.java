package com.app.agriculturalproducts.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.agriculturalproducts.bean.UserInfo;
import com.app.agriculturalproducts.util.InputType;

/**
 * Created by ALPHONSO on 2016/1/20.
 */
public class UserInfoModel {
    private Context context;

    public UserInfoModel(Context context) {
        this.context = context;
    }

    public UserInfo getUserInfo(){
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setCoop(sp.getString("coop",null));
        userInfo.setName(sp.getString("name", null));
        userInfo.setPhone(sp.getString("phone", null));
        userInfo.setId(sp.getString("id", null));
        userInfo.setPwd(sp.getString("pwd", null));
        userInfo.setPath(sp.getString("path", null));
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo){
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",userInfo.getName());
        editor.putString("phone",userInfo.getPhone());
        editor.putString("id",userInfo.getId());
        editor.putString("coop",userInfo.getCoop());
        editor.putString("pwd",userInfo.getPwd());
        editor.putString("path",userInfo.getPath());
        editor.commit();
    }
}
