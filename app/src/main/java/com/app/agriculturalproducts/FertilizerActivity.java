package com.app.agriculturalproducts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.fragment.FertilizerFragment;
import com.app.agriculturalproducts.fragment.FertilizerHistoryFragment;
import com.app.agriculturalproducts.fragment.PerticidesFragment;
import com.app.agriculturalproducts.fragment.PerticidesHistoryFragment;
import com.app.agriculturalproducts.fragment.PlantFragment;

import butterknife.ButterKnife;

public class FertilizerActivity extends BaseUploadActivity {
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
        initToolBar("化肥使用");
        editFragment = new FertilizerFragment();
        dataFragment = new FertilizerHistoryFragment();
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
