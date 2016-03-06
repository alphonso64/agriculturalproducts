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
import com.app.agriculturalproducts.adapter.FusageCursorAdapter;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.PickingCursorAdapter;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
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
public class PickingHistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @Bind(R.id.pu_recyclerview)
    RecyclerView mRecyclerView;
    private PickingDataHelper mDataHelper;
    private PickingCursorAdapter mAdapter;

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
        mDataHelper = new PickingDataHelper(getActivity());
        mAdapter = new PickingCursorAdapter(getActivity());
        mAdapter.setOnItemClickListener(itemClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    private boolean checkPlantSaved(PickRecord pickRecord){
        String id = pickRecord.getLocal_plant_id();
        if(id==null ||id.length() == 0){
            return false;
        }
        return true;
    }

    private String getPlantID(PickRecord pickRecord) {
        String id = pickRecord.getLocal_plant_table_index();
        if (id == null || id.length() == 0) {
            return null;
        }
        PlantSpeciesDataHelper plantSpeciesDataHelper = new PlantSpeciesDataHelper(getActivity());
        String plantID = plantSpeciesDataHelper.queryPlantID(id);
        if (plantID == null || plantID.length() == 0) {
            return null;
        }
        return plantID;
    }


    private OnAdpaterItemClickListener itemClickListener = new OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {
            final PickRecord pickRecord = (PickRecord) obj;
            String saved = pickRecord.getSaved();
            if (saved.equals("no")) {
                new MaterialDialog.Builder(getActivity())
                        .title("上传采摘信息?")
                        .positiveText("是")
                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        if(!checkPlantSaved(pickRecord)){
                            String val = getPlantID(pickRecord);
                            if(val == null){
                                new MaterialDialog.Builder(getActivity())
                                        .title("种植记录未上传：请先上传种植记录！")
                                        .positiveText("好的")
                                        .show();
                                return;
                            }
                            pickRecord.setLocal_plant_id(val);
                        }
                        HttpClient.getInstance().uploadPick(new HttpListener<String>() {
                            @Override
                            public void onSuccess(String s, Response<String> response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String val = jsonObject.getString("return_code");
                                    if (val.equals("success")) {
                                        pickRecord.setSaved("yes");
                                        ContentValues values = cupboard().withEntity(PickRecord.class).toContentValues(pickRecord);
                                        mDataHelper.updateByID(values, String.valueOf(pickRecord.get_id()));
                                        new MaterialDialog.Builder(getActivity())
                                                .title("上传成功！")
                                                .positiveText("好的")
                                                .show();
                                    } else {
                                        pickRecord.setSaved("err");
                                        ContentValues values = cupboard().withEntity(PickRecord.class).toContentValues(pickRecord);
                                        mDataHelper.updateByID(values, String.valueOf(pickRecord.get_id()));
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
                        }, pickRecord);
                    }
                }).show();
            }else if(saved.equals("err")){
                new MaterialDialog.Builder(getActivity())
                        .title("删除错误信息?")
                        .positiveText("是")
                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        mDataHelper.deleteByID(String.valueOf(pickRecord.get_id()));
                    }
                }).show();
            }
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

