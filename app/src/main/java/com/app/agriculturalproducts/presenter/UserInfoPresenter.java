package com.app.agriculturalproducts.presenter;

import android.content.Context;

import com.app.agriculturalproducts.bean.UserInfo;
import com.app.agriculturalproducts.model.UserInfoModel;
import com.app.agriculturalproducts.view.UserInfoSimpleView;

/**
 * Created by ALPHONSO on 2016/1/20.
 */
public class UserInfoPresenter {
    private Context contex;
    private UserInfoModel umodel;
    private UserInfoSimpleView uView;

    public UserInfoPresenter(Context contex,UserInfoSimpleView view) {
        this.contex = contex;
        uView = view;
        umodel = new UserInfoModel(contex);
    }

    public void setUserInfo(UserInfo userInfo){
        umodel.setUserInfo(userInfo);
    }

    public void loadUserInfo(){
        UserInfo userInfo = umodel.getUserInfo();
        uView.setId(userInfo.getId());
        uView.setPhone(userInfo.getPhone());
        uView.setName(userInfo.getName());
        uView.setCoop(userInfo.getCoop());
        uView.setImg(userInfo.getPath());
    }

}
