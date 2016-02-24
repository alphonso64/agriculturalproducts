package com.app.agriculturalproducts.http;

import com.app.agriculturalproducts.util.Configure;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.param.HttpMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Created by aa on 2016/2/24.
 */
public class HttpClient {
    private LiteHttp liteHttp;
    private static HttpClient single=null;
    private HttpClient(){
        liteHttp = LiteHttp.newApacheHttpClient(null);
    };
    public static HttpClient getInstance() {
        if (single == null) {
            single = new HttpClient();
        }
        return single;
    }



    public void checkLogin(HttpListener<String> listener,String name ,String pwd){
        if(listener == null || name == null || pwd == null) return;

        JSONObject object = new JSONObject();
        try {
            object.put("userinfo_username",name);
            object.put("userinfo_password",pwd);
        } catch (JSONException e) {
            return;
        }

        LinkedHashMap<String,String> header = new LinkedHashMap<>();
        header.put("contentType", "utf-8");
        header.put("Content-type", "application/x-java-serialized-object");
        HttpClient.getInstance().liteHttp.executeAsync(new StringRequest(Configure.LOGIN_URL).setHeaders(header)
                .setMethod(HttpMethods.Post).setHttpBody(new StringBody(object.toString())).setHttpListener(listener));
    }

}
