package com.app.agriculturalproducts.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.agriculturalproducts.db.DBHelper;
import com.app.agriculturalproducts.util.FieldTest;
import com.baidu.mapapi.SDKInitializer;
import com.litesuits.http.LiteHttp;

/**
 * Created by ALPHONSO on 2016/1/7.
 */
public class AppApplication extends Application {
    private static Context sContext;
    private static LiteHttp liteHttp;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        liteHttp = LiteHttp.newApacheHttpClient(null);
        SDKInitializer.initialize(this);
        if(isFirstLogin()){
            DBHelper dbHelper =new DBHelper(getApplicationContext());
            FieldTest fieldTest = new FieldTest(getApplicationContext());
            fieldTest.test();
        }
    }

    public static Context getContext() {
        return sContext;
    }
    public static LiteHttp getLiteHttp(){return liteHttp;}

    private boolean isFirstLogin(){
        SharedPreferences sp = getSharedPreferences("loginTest", Activity.MODE_PRIVATE);
        if(sp.getString("login",null)==null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("login", "ok");
            editor.commit();
            return  true;
        }
        return  false;
    }
}
