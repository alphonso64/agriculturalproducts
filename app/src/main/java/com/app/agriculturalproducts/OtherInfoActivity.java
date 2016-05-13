package com.app.agriculturalproducts;

import android.os.Bundle;

import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.fragment.OtherInfoFragment;
import com.app.agriculturalproducts.fragment.OtherInfoHistoryFragment;
import com.app.agriculturalproducts.fragment.PickingFragment;
import com.app.agriculturalproducts.fragment.PickingHistoryFragment;
import com.app.agriculturalproducts.fragment.PlantFragment;

import butterknife.ButterKnife;

public class OtherInfoActivity extends BaseUploadActivity {
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
        initToolBar("其他记录");
        editFragment = new OtherInfoFragment();
        dataFragment = new OtherInfoHistoryFragment();
        currentFragment = editFragment;
        TaskRecord task = (TaskRecord)getIntent().getSerializableExtra("task");
        if(task!=null){
            editFragment.object = task;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.frame_view,editFragment).commit();

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_pesticides;
    }
}
