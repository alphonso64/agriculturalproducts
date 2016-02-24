package com.app.agriculturalproducts.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.agriculturalproducts.db.DBHelper;
import com.app.agriculturalproducts.util.FieldTest;

/**
 * Created by ALPHONSO on 2016/1/7.
 */
public class AppApplication extends Application {
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
       // SDKInitializer.initialize(this);
        if(isFirstLogin()){
            DBHelper dbHelper =new DBHelper(getApplicationContext());
            FieldTest fieldTest = new FieldTest(getApplicationContext());
            fieldTest.test();
        }
        httpTest();
    }

    private void httpTest(){
        String str_1 = "{\"userinfo_username\":\"admin\",\"userinfo_password\":\"admin\"}";
//        String str_2 = "{\"userinfo_username\":\"zhangsan\"}";
//        LinkedHashMap<String,String> header = new LinkedHashMap<>();
//        header.put("contentType", "utf-8");
//        header.put("Content-type", "application/x-java-serialized-object");
//
//
//        HttpClient.getInstance().liteHttp.executeAsync(new StringRequest(Configure.LOGIN_URL).setHeaders(header)
//                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(str_1)).setHttpListener(
//                        new HttpListener<String>() {
//                            @Override
//                            public void onSuccess(String s, Response<String> response) {
//                                Log.e("testcc", s);
//                            }
//
//                            @Override
//                            public void onFailure(HttpException e, Response<String> response) {
//
//                            }
//                        }
//                ));
//
//        HttpClient.getInstance().liteHttp.executeAsync(new StringRequest(Configure. GET_EMPLOYEE_BY_USERNAME_URL).setHeaders(header)
//                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(str_2)).setHttpListener(
//                        new HttpListener<String>() {
//                            @Override
//                            public void onSuccess(String s, Response<String> response) {
//                                Log.e("testcc", s);
//                            }
//
//                            @Override
//                            public void onFailure(HttpException e, Response<String> response) {
//
//                            }
//                        }
//                ));
//        HttpListener listener =  new HttpListener<String>() {
//            @Override
//            public void onSuccess(String s, Response<String> response) {
//                Log.e("testcc", "ssss  "+s);
//            }
//
//            @Override
//            public void onFailure(HttpException e, Response<String> response) {
//
//            }
//        };
//        HttpClient.getInstance().checkLogin(listener,"admi))","admin");
    }

    public static Context getContext() {
        return sContext;
    }

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
