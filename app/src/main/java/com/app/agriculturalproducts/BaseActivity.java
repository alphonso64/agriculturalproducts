package com.app.agriculturalproducts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    //for testaa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    protected int getContentView(){
        return R.layout.activity_main;
    }

}
