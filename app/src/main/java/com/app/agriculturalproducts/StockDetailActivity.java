package com.app.agriculturalproducts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.agriculturalproducts.adapter.StockCursorAdapter;
import com.app.agriculturalproducts.adapter.StockDetailCursorAdapter;
import com.app.agriculturalproducts.adapter.TaskDetailCursorAdapter;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PersonalStockDetail;
import com.app.agriculturalproducts.db.StockDataHelper;
import com.app.agriculturalproducts.db.StockDetailDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.InputType;

import butterknife.Bind;
import butterknife.ButterKnife;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class StockDetailActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    @Bind(R.id.toolbar)
    Toolbar tooblbar;
    @Bind(R.id.ptask_recyclerview)
    RecyclerView mTaskRecyclerView;

    private final int ENTER_CMD = 0;
    private final int OUT_CMD = 1;

    //    private StockDataHelper mDataHelper;
//    private StockCursorAdapter mAdapter;
    private StockDetailDataHelper mDataHelper;
    private StockDetailCursorAdapter mAdapter;
    private ProgressDialog progressDialog;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolBar(tooblbar, "库存流水");

        mDataHelper = new StockDetailDataHelper(this);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StockDetailCursorAdapter(this);
        mTaskRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, StockDetailActivity.this);
        tooblbar.setOnMenuItemClickListener(itemClick);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_produce;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == ENTER_CMD) {
            return mDataHelper.getEnterCursorLoader();
        } else if (id == OUT_CMD) {
            return mDataHelper.getOutCursorLoader();
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
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

//    private Toolbar.OnMenuItemClickListener itemClick = new Toolbar.OnMenuItemClickListener() {
//        @Override
//        public boolean onMenuItemClick(MenuItem item) {
////            if (item.getItemId() == R.id.action_update)
////            {
////                progressDialog = new ProgressDialog(StockDetailActivity.this);
////                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////                progressDialog.setMessage("数据更新中");
////                progressDialog.setIndeterminate(false);
////                progressDialog.setCancelable(true);
////                progressDialog.show();
////                SharedPreferences sp = getSharedPreferences(InputType.loginInfoDB,
////                        Activity.MODE_PRIVATE);
////                final String name = sp.getString("name", null);
////                new Thread() {
////                    @Override
////                    public void run() {
////                        HttpClient.getInstance().getStockDetailInfo(name);
////                        for (PersonalStockDetail ps : HttpClient.getInstance().stockList) {
////                            ContentValues values = cupboard().withEntity(PersonalStockDetail.class).toContentValues(ps);
////                            mDataHelper.updateByID(values, ps.getPersonalstockdetail_id());
////                        }
////                        mHandler.sendEmptyMessage(1);
////                    }
////                }.start();
////            }else
//            if(item.getItemId() == R.id.action_enter){
//                setToolBar(tooblbar, "库存流水—入库信息");
//                item.setChecked(true);
//            }else if(item.getItemId() == R.id.action_out){
//                setToolBar(tooblbar, "库存流水—出库信息");
//                item.setChecked(true);
//            }
//            return true;
//        }
//    };

    private Toolbar.OnMenuItemClickListener itemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_enter) {
                getSupportLoaderManager().restartLoader(ENTER_CMD, null, StockDetailActivity.this);
                item.setChecked(true);
            } else if (item.getItemId() == R.id.action_out) {
                getSupportLoaderManager().restartLoader(OUT_CMD, null, StockDetailActivity.this);
                item.setChecked(true);
            } else if (item.getItemId() == R.id.action_update) {
                progressDialog = new ProgressDialog(StockDetailActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("数据更新中");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
                SharedPreferences sp = getSharedPreferences(InputType.loginInfoDB,
                        Activity.MODE_PRIVATE);
                final String name = sp.getString("name", null);
                new Thread() {
                    @Override
                    public void run() {
                        HttpClient.getInstance().getStockDetailInfo(name);
                        for (PersonalStockDetail ps : HttpClient.getInstance().enterstockList) {
                            ContentValues values = cupboard().withEntity(PersonalStockDetail.class).toContentValues(ps);
                            mDataHelper.updateByID(values, ps.getPersonalstockdetail_id());
                        }
                        for (PersonalStockDetail ps : HttpClient.getInstance().outstockList) {
                            ContentValues values = cupboard().withEntity(PersonalStockDetail.class).toContentValues(ps);
                            mDataHelper.updateByID(values, ps.getPersonalstockdetail_id());
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }.start();
            }
            return true;
        }
    };

}