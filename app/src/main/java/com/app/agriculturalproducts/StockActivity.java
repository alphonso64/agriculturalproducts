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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.adapter.StockCursorAdapter;
import com.app.agriculturalproducts.adapter.StockDetailCursorAdapter;
import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.StockDataHelper;
import com.app.agriculturalproducts.db.StockDetailDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.InputType;

import butterknife.Bind;
import butterknife.ButterKnife;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class StockActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    @Bind(R.id.toolbar)
    Toolbar tooblbar;
    @Bind(R.id.ptask_recyclerview)
    RecyclerView mTaskRecyclerView;

    private StockDataHelper mDataHelper;
    private StockCursorAdapter mAdapter;
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
        setToolBar(tooblbar, "库存信息");

        mDataHelper = new StockDataHelper(this);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StockCursorAdapter(this);
        mTaskRecyclerView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, StockActivity.this);
        tooblbar.setOnMenuItemClickListener(itemClick);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_produce;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mDataHelper.getCursorLoader();
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
        getMenuInflater().inflate(R.menu.menu_update_, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener itemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            progressDialog = new ProgressDialog(StockActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("数据更新中");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
            SharedPreferences sp = getSharedPreferences(InputType.loginInfoDB,
                    Activity.MODE_PRIVATE);
            final String name = sp.getString("name", null);
            new Thread(){
                @Override
                public void run() {
                    HttpClient.getInstance().getStockInfo(name);
                    for(PersonalStock ps:HttpClient.getInstance().seedStockList){
                        ContentValues values = cupboard().withEntity(PersonalStock.class).toContentValues(ps);
                        mDataHelper.updateByID(values, ps.getPersonalstock_id());
                    }
                    for(PersonalStock ps:HttpClient.getInstance().fStockList){
                        ContentValues values = cupboard().withEntity(PersonalStock.class).toContentValues(ps);
                        mDataHelper.updateByID(values, ps.getPersonalstock_id());
                    }
                    for(PersonalStock ps:HttpClient.getInstance().pStcokList){
                        ContentValues values = cupboard().withEntity(PersonalStock.class).toContentValues(ps);
                        mDataHelper.updateByID(values, ps.getPersonalstock_id());
                    }
                    mHandler.sendEmptyMessage(1);
                }}.start();
            return true;
        }
    };

}
