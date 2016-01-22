package com.app.agriculturalproducts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.agriculturalproducts.fragment.DataFragment;
import com.app.agriculturalproducts.fragment.FertilizerFragment;
import com.app.agriculturalproducts.fragment.FertilizerHistoryFragment;
import com.app.agriculturalproducts.fragment.PlantFragment;
import com.app.agriculturalproducts.fragment.PlantHistoryFragment;

import butterknife.ButterKnife;

public class PlantActivity extends BaseUploadActivity {
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
        dataFragment = new PlantHistoryFragment();
        currentFragment = editFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frame_view,editFragment).commit();

    }
    @Override
    protected int getContentView() {
        return R.layout.activity_pesticides;
    }
}