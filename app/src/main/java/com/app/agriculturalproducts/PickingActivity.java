package com.app.agriculturalproducts;

import android.os.Bundle;

import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.fragment.FertilizerFragment;
import com.app.agriculturalproducts.fragment.FertilizerHistoryFragment;
import com.app.agriculturalproducts.fragment.PickingFragment;
import com.app.agriculturalproducts.fragment.PickingHistoryFragment;
import com.app.agriculturalproducts.fragment.PlantFragment;

import butterknife.ButterKnife;

public class PickingActivity extends BaseUploadActivity {
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
        initToolBar("采摘记录");
        editFragment = new PickingFragment();
        dataFragment = new PickingHistoryFragment();
        currentFragment = editFragment;
        Task task = (Task)getIntent().getSerializableExtra("task");
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
