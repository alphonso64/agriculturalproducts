package com.app.supervisorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        initToolBar(title);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_message;
    }

    private void initToolBar(String title){
        toolbar.setTitle(title);
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
