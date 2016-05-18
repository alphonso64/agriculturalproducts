package com.app.supervisorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SupervisorActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initToolBar(toolbar,"执法检查");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_supervisor;
    }
}
