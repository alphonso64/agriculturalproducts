package com.app.agriculturalproducts;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.fragment.DataFragment;
import com.app.agriculturalproducts.fragment.PerticidesFragment;
import com.app.agriculturalproducts.fragment.PerticidesHistoryFragment;
import com.app.agriculturalproducts.fragment.PlantFragment;
import com.app.agriculturalproducts.util.InputType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PesticidesActivity extends BaseUploadActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

//        if (savedInstanceState == null) {
//            perticidesFragment = new PerticidesFragment();
//            dataFragment = new PerticidesHistoryFragment();
//            currentFragment = perticidesFragment;
//            getSupportFragmentManager().beginTransaction().add(R.id.frame_view,perticidesFragment).commit();
//        }

        editFragment = new PlantFragment();
        dataFragment = new PerticidesHistoryFragment();
        currentFragment = editFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frame_view,editFragment).commit();

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_pesticides;
    }


}
