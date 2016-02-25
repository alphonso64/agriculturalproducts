package com.app.agriculturalproducts.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.app.agriculturalproducts.db.DBHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.Configure;
import com.app.agriculturalproducts.util.FieldTest;
import com.app.agriculturalproducts.util.InputType;
import com.litesuits.http.request.StringRequest;

import java.util.LinkedHashMap;

/**
 * Created by ALPHONSO on 2016/1/7.
 */
public class AppApplication extends Application {
    private static Context sContext;
    private static String name;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
       // SDKInitializer.initialize(this);
//        if(isFirstLogin()){
//            DBHelper dbHelper =new DBHelper(getApplicationContext());
//            FieldTest fieldTest = new FieldTest(getApplicationContext());
//            fieldTest.test();
//        }
//        HttpClient.getInstance().test();
        Log.e("testcc", "onCreate");
    }

//    public void setUserName(){
//        name =
//    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.e("testcc", "onTerminate");
        super.onTerminate();
    }

//    private boolean isFirstLogin(){
//        SharedPreferences sp = getSharedPreferences("loginTest", Activity.MODE_PRIVATE);
//        if(sp.getString("login",null)==null){
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("login", "ok");
//            editor.commit();
//            return  true;
//        }
//        return  false;
//    }
}
