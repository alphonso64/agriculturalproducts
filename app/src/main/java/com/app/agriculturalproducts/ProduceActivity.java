package com.app.agriculturalproducts;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.TaskCursorAdapter;
import com.app.agriculturalproducts.adapter.TaskDetailCursorAdapter;
import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.InputType;
import com.app.agriculturalproducts.util.TaskRecordUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProduceActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    @Bind(R.id.toolbar)
    Toolbar tooblbar;
    @Bind(R.id.ptask_recyclerview)
    RecyclerView mTaskRecyclerView;

    private final int ALL_CMD = 0;
    private final int DONE_CMD = 1;
    private final int UNDONE_CMD = 2;

    private TaskDataHelper mDataHelper;
    private TaskDetailCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolBar(tooblbar, "生产信息");

        mDataHelper = new TaskDataHelper(getApplicationContext());
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TaskDetailCursorAdapter(this);
        mAdapter.setOnItemClickListener(task_adpaterItemClickListener);
        mTaskRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, ProduceActivity.this);
        tooblbar.setOnMenuItemClickListener(itemClick);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_produce;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id == ALL_CMD ){
            return mDataHelper.getDetailCursorLoader();
        }else if(id == DONE_CMD){
            return mDataHelper.getDoneCursorLoader();
        }else if(id == UNDONE_CMD){
            return mDataHelper.getCursorLoader();
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener itemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_all) {
                getSupportLoaderManager().restartLoader(ALL_CMD,null,ProduceActivity.this);
                item.setChecked(true);
            }else if(item.getItemId()==R.id.action_Done){
                getSupportLoaderManager().restartLoader(DONE_CMD,null,ProduceActivity.this);
                item.setChecked(true);
            }else if(item.getItemId()==R.id.action_unDone){
                getSupportLoaderManager().restartLoader(UNDONE_CMD,null,ProduceActivity.this);
                item.setChecked(true);
            }
            return true;
        }
    };

    private OnAdpaterItemClickListener task_adpaterItemClickListener =new  OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {
            final TaskRecord taskRecord = (TaskRecord)obj;
            if(taskRecord.getWorktasklist_status().equals("未查看")){
                taskRecord.setWorktasklist_status("已查看");
                HttpClient.getInstance(ProduceActivity.this).uploadTask(null, taskRecord);
            }else if(taskRecord.getWorktasklist_status().equals("已查看")){
                if(taskRecord.getSync().equals("true")){
                    new MaterialDialog.Builder(ProduceActivity.this)
                            .title("确定接受任务")
                            .content(taskRecord.getWorktask_content())
                            .positiveText("确定")
                            .negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            new MaterialDialog.Builder(ProduceActivity.this)
                                    .title("提交任务方式")
                                    .positiveText("提交新记录").onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {

                                    Intent intent = new Intent(ProduceActivity.this, TaskDoneSelectActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("task", taskRecord);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }) .negativeText("提交已完成记录").onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                    if(taskRecord.getWorktask_type().equals("农药使用")){
                                        Intent intent = new Intent(ProduceActivity.this, PesticidesActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("task", taskRecord);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }else  if(taskRecord.getWorktask_type().equals("种植录入")){
                                        Intent intent = new Intent(ProduceActivity.this, PlantActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("task", taskRecord);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }else  if(taskRecord.getWorktask_type().equals("肥料施用")){
                                        Intent intent = new Intent(ProduceActivity.this, FertilizerActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("task", taskRecord);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }else  if(taskRecord.getWorktask_type().equals("采摘记录")){
                                        Intent intent = new Intent(ProduceActivity.this, PickingActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("task", taskRecord);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }else  if(taskRecord.getWorktask_type().equals("其他记录")){
                                        Intent intent = new Intent(ProduceActivity.this, OtherInfoActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("task", taskRecord);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                }
                            }).show();
                        }
                    }).show();
                }
            }else if(taskRecord.getWorktasklist_status().equals("已完成")){
//                new MaterialDialog.Builder(ProduceActivity.this)
//                        .title("查看任务详情")
//                        .content(taskRecord.getWorktask_content())
//                        .positiveText("确定")
//                        .negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(MaterialDialog dialog, DialogAction which) {
//                    }
//                }).show();
            }
        }
    };

}
