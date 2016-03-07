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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.PlantCursorAdapter;
import com.app.agriculturalproducts.adapter.PusageCursorAdapter;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PlantHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @Bind(R.id.pu_recyclerview)
    RecyclerView mRecyclerView;
    private PlantSpeciesDataHelper mDataHelper;
    private PlantCursorAdapter mAdapter;

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
        mDataHelper = new PlantSpeciesDataHelper(getActivity());
        mAdapter = new PlantCursorAdapter(getActivity());
        mAdapter.setOnItemClickListener(itemClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

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

    private void savedPlantIDtoDB(String pid,String id){
        FertilizerUsageDataHelper fertilizerUsageDataHelper = new FertilizerUsageDataHelper(getActivity());
        fertilizerUsageDataHelper.updatePlantIDByID(pid,id);
        PersticidesUsageDataHelper persticidesUsageDataHelper = new PersticidesUsageDataHelper(getActivity());
        persticidesUsageDataHelper.updatePlantIDByID(pid,id);
        PickingDataHelper pickingDataHelper = new PickingDataHelper(getActivity());
        pickingDataHelper.updatePlantIDByID(pid,id);
        OtherInfoDataHelper otherInfoDataHelper = new OtherInfoDataHelper(getActivity());
        otherInfoDataHelper.updatePlantIDByID(pid,id);
    }

    private void checkResult(PlanterRecord planterRecord,String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            String val = jsonObject.getString("return_code");
            Log.e("testcc", "upload:"+s);
            if(val.equals("success")){
                JSONArray array = jsonObject.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);
                    String id = jobject.getString("plantrecord_id");
                    planterRecord.setPlantrecord_id(id);
                }
                savedPlantIDtoDB(planterRecord.getPlantrecord_id(),String.valueOf(planterRecord.get_id()));
                planterRecord.setSaved("yes");
                ContentValues values = cupboard().withEntity(PlanterRecord.class).toContentValues(planterRecord);
                mDataHelper.updateByID(values, String.valueOf(planterRecord.get_id()));
                new MaterialDialog.Builder(getActivity())
                        .title("上传成功！")
                        .positiveText("好的")
                        .show();
            }else{
                planterRecord.setSaved("err");
                ContentValues values = cupboard().withEntity(PlanterRecord.class).toContentValues(planterRecord);
                mDataHelper.updateByID(values, String.valueOf(planterRecord.get_id()));
                String res = jsonObject.getString("return_msg");
                new MaterialDialog.Builder(getActivity())
                        .title(res)
                        .positiveText("好的")
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private OnAdpaterItemClickListener itemClickListener =new  OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {
            final PlanterRecord planterRecord =  (PlanterRecord) obj;
            String saved = planterRecord.getSaved();
            if(saved.equals("no")){
                new MaterialDialog.Builder(getActivity())
                        .title("上传种植信息?")
                        .positiveText("是")
                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        HttpClient.getInstance().uploadPlant(new HttpListener<String>() {
                            @Override
                            public void onSuccess(String s, Response<String> response) {
                                checkResult(planterRecord,s);
                            }
                        },planterRecord);
                    }
                }).show();
            }else if(saved.equals("err")){
                new MaterialDialog.Builder(getActivity())
                        .title("删除错误信息?")
                        .positiveText("是")
                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        mDataHelper.deleteByID(String.valueOf(planterRecord.get_id()));
                    }
                }).show();
            }
        }
    };

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

