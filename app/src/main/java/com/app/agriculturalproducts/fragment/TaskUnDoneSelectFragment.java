package com.app.agriculturalproducts.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.TaskDoneSelectActivity;
import com.app.agriculturalproducts.adapter.BaseAbstractRecycleCursorAdapter;
import com.app.agriculturalproducts.adapter.FusageCursorAdapter;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.OtherInfoCursorAdapter;
import com.app.agriculturalproducts.adapter.PickingCursorAdapter;
import com.app.agriculturalproducts.adapter.PlantCursorAdapter;
import com.app.agriculturalproducts.adapter.PusageCursorAdapter;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.TaskRecordUtil;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class TaskUnDoneSelectFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @Bind(R.id.pu_recyclerview)
    RecyclerView mRecyclerView;
    private Object mDataHelper;
    private Object mAdapter;
    private String taskType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_persticides_his,
                container, false);
        ButterKnife.bind(this, contextView);

        return contextView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerview();
    }

    private void initRecyclerview(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(taskType.equals("农药使用")){
            mDataHelper = new PersticidesUsageDataHelper(getActivity());
            mAdapter = new PusageCursorAdapter(getActivity());
            mRecyclerView.setAdapter((PusageCursorAdapter)mAdapter);
            ((PusageCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
        }else  if(taskType.equals("种植录入")){
            mDataHelper = new PlantSpeciesDataHelper(getActivity());
            mAdapter = new PlantCursorAdapter(getActivity());
            mRecyclerView.setAdapter((PlantCursorAdapter)mAdapter);
            ((PlantCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
        }else  if (taskType.equals("肥料施用")){
            mDataHelper = new FertilizerUsageDataHelper(getActivity());
            mAdapter = new FusageCursorAdapter(getActivity());
            mRecyclerView.setAdapter((FusageCursorAdapter)mAdapter);
            ((FusageCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
        }else  if(taskType.equals("采摘记录")){
            mDataHelper = new PickingDataHelper(getActivity());
            mAdapter = new PickingCursorAdapter(getActivity());
            mRecyclerView.setAdapter((PickingCursorAdapter)mAdapter);
            ((PickingCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
        }else  if(taskType.equals("其他记录")){
            mDataHelper = new OtherInfoDataHelper(getActivity());
            mAdapter = new OtherInfoCursorAdapter(getActivity());
            mRecyclerView.setAdapter((OtherInfoCursorAdapter)mAdapter);
            ((OtherInfoCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
        }
    }

    private OnAdpaterItemClickListener itemClickListener = new OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(taskType.equals("农药使用")){
            return ((PersticidesUsageDataHelper)mDataHelper).getCursorLoaderTask();
        }else  if(taskType.equals("种植录入")){
            return ((PlantSpeciesDataHelper)mDataHelper).getCursorLoaderTask();
        }else  if (taskType.equals("肥料施用")){
            return ((FertilizerUsageDataHelper)mDataHelper).getCursorLoaderTask();
        }else  if(taskType.equals("采摘记录")){
            return ((PickingDataHelper)mDataHelper).getCursorLoaderTask();
        }else  if(taskType.equals("其他记录")){
            return ((OtherInfoDataHelper)mDataHelper).getCursorLoaderTask();
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ((BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder>)mAdapter).changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder>)mAdapter).changeCursor(null);
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}

