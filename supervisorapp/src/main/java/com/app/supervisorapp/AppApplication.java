package com.app.supervisorapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by ALPHONSO on 2016/1/7.
 */
public class AppApplication extends Application {
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
