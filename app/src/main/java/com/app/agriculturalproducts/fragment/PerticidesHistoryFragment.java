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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.adapter.BasicIconRecyclerAdapter;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.PusageCursorAdapter;
import com.app.agriculturalproducts.adapter.TaskCursorAdapter;
import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.TaskRecordUtil;
import com.app.agriculturalproducts.view.NoScrollGridLayoutManager;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PerticidesHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @Bind(R.id.pu_recyclerview)
    RecyclerView mRecyclerView;
    private PersticidesUsageDataHelper mDataHelper;
    private PusageCursorAdapter mAdapter;

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
        mDataHelper = new PersticidesUsageDataHelper(getActivity());
        mAdapter = new PusageCursorAdapter(getActivity());
        mAdapter.setOnItemClickListener(itemClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean checkPlantSaved(PreventionRecord preventionRecord){
        String id = preventionRecord.getLocal_plant_id();
        if(id==null ||id.length() == 0){
            return false;
        }
        return true;
    }

//    private String getPlantID(PreventionRecord preventionRecord) {
//        String id = preventionRecord.getLocal_plant_table_index();
//        if (id == null || id.length() == 0) {
//            return null;
//        }
//        PlantSpeciesDataHelper plantSpeciesDataHelper = new PlantSpeciesDataHelper(getActivity());
//        String plantID = plantSpeciesDataHelper.queryPlantID(id);
//        if (plantID == null || plantID.length() == 0) {
//            return null;
//        }
//        return plantID;
//    }

    private OnAdpaterItemClickListener itemClickListener = new OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {
            final PreventionRecord preventionRecord = (PreventionRecord) obj;
            String saved = preventionRecord.getSaved();
            if (saved.equals("no")) {
                new MaterialDialog.Builder(getActivity())
                        .title("上传防治信息?")
                        .positiveText("是")
                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        if(!checkPlantSaved(preventionRecord)){
//                            String val = getPlantID(preventionRecord);
//                            if(val == null){
//                                new MaterialDialog.Builder(getActivity())
//                                        .title("种植记录未上传：请先上传种植记录！")
//                                        .positiveText("好的")
//                                        .show();
////                                return;
//                            }
//                            preventionRecord.setLocal_plant_id(val);
                            new MaterialDialog.Builder(getActivity())
                                    .title("种植记录未上传：请先上传种植记录！")
                                    .positiveText("好的")
                                    .show();
                            return;
                        }
                        HttpClient.getInstance().uploadPrevention(new HttpListener<String>() {
                            @Override
                            public void onSuccess(String s, Response<String> response) {
                                Log.e("testcc",s);
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String val = jsonObject.getString("return_code");
                                    if (val.equals("success")) {
                                        preventionRecord.setSaved("yes");
                                        ContentValues values = cupboard().withEntity(PreventionRecord.class).toContentValues(preventionRecord);
                                        mDataHelper.updateByID(values, String.valueOf(preventionRecord.get_id()));

                                        String taskID = preventionRecord.getTask_id();
                                        if(!taskID.equals("null")){
                                            TaskRecordUtil.removeLocalDoneTask(getActivity(), new TaskDataHelper(getActivity().getApplicationContext()), taskID);
                                        }

                                        new MaterialDialog.Builder(getActivity())
                                                .title("上传成功！")
                                                .positiveText("好的")
                                                .show();
                                    } else {
                                        preventionRecord.setSaved("err");
                                        ContentValues values = cupboard().withEntity(PreventionRecord.class).toContentValues(preventionRecord);
                                        mDataHelper.updateByID(values, String.valueOf(preventionRecord.get_id()));

                                        String taskID = preventionRecord.getTask_id();
                                        if(!taskID.equals("null")){
                                            TaskRecordUtil.removeLocalUnDoneTask(getActivity(),new TaskDataHelper(getActivity().getApplicationContext()),taskID);
                                        }

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
                        }, preventionRecord);
                    }
                }).show();
            }else if(saved.equals("err")){
                new MaterialDialog.Builder(getActivity())
                        .title("删除错误信息?")
                        .positiveText("是")
                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        mDataHelper.deleteByID(String.valueOf(preventionRecord.get_id()));
                    }
                }).show();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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

