package com.app.agriculturalproducts.app;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext() {
        return sContext;
    }
    public static LiteHttp getLiteHttp(){return liteHttp;}
}
