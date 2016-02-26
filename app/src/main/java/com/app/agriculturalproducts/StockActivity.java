package com.app.agriculturalproducts;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.agriculturalproducts.adapter.StockCursorAdapter;
import com.app.agriculturalproducts.adapter.StockDetailCursorAdapter;
import com.app.agriculturalproducts.db.StockDataHelper;
import com.app.agriculturalproducts.db.StockDetailDataHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StockActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    @Bind(R.id.toolbar)
    Toolbar tooblbar;
    @Bind(R.id.ptask_recyclerview)
    RecyclerView mTaskRecyclerView;

        private StockDataHelper mDataHelper;
    private StockCursorAdapter mAdapter;
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

}
