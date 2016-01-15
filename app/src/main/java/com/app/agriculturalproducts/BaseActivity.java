package com.app.agriculturalproducts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    protected int getContentView(){
        return R.layout.activity_main;
    }

    protected void setToolBar(Toolbar toolbar,String title){
        if(title != null){
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);//toolbar支持
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );
    }
}
