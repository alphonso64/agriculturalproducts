package com.app.agriculturalproducts;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.InputType;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.serverIP_text)
    EditText serverIP_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolBar(toolbar,"设置");
        setDefaultData();
        Log.e("testcc","onCreate:");
    }

    private void setDefaultData() {
        SharedPreferences sp = getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        String val = sp.getString("serverIP","cdjytgs.kmdns.net:8081");
        serverIP_text.setText(val);

    }

    @Override
    public void onBackPressed() {
        SharedPreferences sp = getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("serverIP",serverIP_text.getText().toString());
        ed.commit();
        HttpClient.getInstance(SettingActivity.this).resetIP(serverIP_text.getText().toString());
        super.onBackPressed();
//        Log.e("testcc","onBackPressed:"+serverIP_text.getText().toString().length());
    }

    private boolean isTextChange() {
        String val = serverIP_text.getText().toString();
        return !InputType.DEFAULT_IP.equals(val);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }


}
