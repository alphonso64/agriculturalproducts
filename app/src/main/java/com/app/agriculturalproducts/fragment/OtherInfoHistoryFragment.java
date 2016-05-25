package com.app.agriculturalproducts.fragment;

import android.app.ProgressDialog;
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
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.OtherInfoCursorAdapter;
import com.app.agriculturalproducts.adapter.PickingCursorAdapter;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.OtherRecord;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.TaskRecordUtil;
import com.litesuits.http.exception.HttpException;
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
public class OtherInfoHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @Bind(R.id.pu_recyclerview)
    RecyclerView mRecyclerView;
    private OtherInfoDataHelper mDataHelper;
    private OtherInfoCursorAdapter mAdapter;
    private ProgressDialog progressDialog;

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
        mDataHelper = new OtherInfoDataHelper(getActivity());
        mAdapter = new OtherInfoCursorAdapter(getActivity());
        mAdapter.setOnItemClickListener(itemClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean checkPlantSaved(OtherRecord otherRecord){
        String id = otherRecord.getLocal_plant_id();
        if(id==null ||id.length() == 0){
            return false;
        }
        return true;
    }

//    private String getPlantID(OtherRecord otherRecord) {
//        String id = otherRecord.getLocal_plant_table_index();
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
            final OtherRecord otherRecord = (OtherRecord) obj;
            String saved = otherRecord.getSaved();
            if (saved.equals("no")) {
                new MaterialDialog.Builder(getActivity())
                        .title("上传其他信息?")
                        .positiveText("是")
                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                        if(!checkPlantSaved(otherRecord)){
//                            String val = getPlantID(otherRecord);
//                            if(val == null){
//                                new MaterialDialog.Builder(getActivity())
//                                        .title("种植记录未上传：请先上传种植记录！")
//                                        .positiveText("好的")
//                                        .show();
//                                return;
//                            }
//                            otherRecord.setLocal_plant_id(val);
                            new MaterialDialog.Builder(getActivity())
                                    .title("种植记录未上传：请先上传种植记录！")
                                    .positiveText("好的")
                                    .show();
                            return;
                        }
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage("数据上传中");
                        progressDialog.setIndeterminate(false);
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                        HttpClient.getInstance().uploadOther(new HttpListener<String>() {
                            @Override
                            public void onSuccess(String s, Response<String> response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String val = jsonObject.getString("return_code");
                                    if (val.equals("success")) {
                                        otherRecord.setSaved("yes");
                                        ContentValues values = cupboard().withEntity(OtherRecord.class).toContentValues(otherRecord);
                                        mDataHelper.updateByID(values, String.valueOf(otherRecord.get_id()));

                                        String taskID = otherRecord.getTask_id();
                                        if(!taskID.equals("null")){
                                            TaskRecordUtil.removeLocalDoneTask(getActivity(), new TaskDataHelper(getActivity().getApplicationContext()), taskID);
                                        }

                                        new MaterialDialog.Builder(getActivity())
                                                .title("上传成功！")
                                                .positiveText("好的")
                                                .show();
                                    } else {
                                        String taskID = otherRecord.getTask_id();
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
                            @Override
                            public void onFailure(HttpException e, Response<String> response) {
                                progressDialog.dismiss();
                                new MaterialDialog.Builder(getActivity())
                                        .title("无法连接网络!")
                                        .positiveText("好的").show();
                            }
                        }, otherRecord);
                    }
                }).show();
            }else if(saved.equals("err")){
                new MaterialDialog.Builder(getActivity())
                        .title("删除错误信息?")
                        .positiveText("是")
                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        mDataHelper.deleteByID(String.valueOf(otherRecord.get_id()));
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

