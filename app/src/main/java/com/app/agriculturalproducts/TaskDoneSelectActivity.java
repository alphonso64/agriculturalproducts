package com.app.agriculturalproducts;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.agriculturalproducts.adapter.BaseAbstractRecycleCursorAdapter;
import com.app.agriculturalproducts.adapter.FusageCursorAdapter;
import com.app.agriculturalproducts.adapter.OtherInfoCursorAdapter;
import com.app.agriculturalproducts.adapter.PickingCursorAdapter;
import com.app.agriculturalproducts.adapter.PlantCursorAdapter;
import com.app.agriculturalproducts.adapter.PusageCursorAdapter;
import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.db.BaseDataHelper;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.fragment.TaskUnDoneSelectFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskDoneSelectActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String taskType;
    TaskUnDoneSelectFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolBar(toolbar,"提交已完成任务");
        TaskRecord task = (TaskRecord)getIntent().getSerializableExtra("task");
        if(task!=null){
            taskType = task.getWorktask_type();
        }else {
            taskType = "null";
        }
        currentFragment = new TaskUnDoneSelectFragment();
        currentFragment.setTaskType(taskType);
        getSupportFragmentManager().beginTransaction().add(R.id.taskDone_frame_view,currentFragment).commit();
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_task_done_select;
    }

}
