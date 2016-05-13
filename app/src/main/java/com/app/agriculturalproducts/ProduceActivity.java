package com.app.agriculturalproducts;

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

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.adapter.TaskCursorAdapter;
import com.app.agriculturalproducts.adapter.TaskDetailCursorAdapter;
import com.app.agriculturalproducts.db.TaskDataHelper;
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

}
