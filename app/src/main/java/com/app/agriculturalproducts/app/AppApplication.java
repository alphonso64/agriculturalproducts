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
        Log.e("testcc", "onCreate");
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
