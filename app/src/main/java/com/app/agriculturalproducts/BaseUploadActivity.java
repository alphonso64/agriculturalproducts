package com.app.agriculturalproducts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseUploadActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            toolbar.setTitle(bundle.getString("title"));
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
