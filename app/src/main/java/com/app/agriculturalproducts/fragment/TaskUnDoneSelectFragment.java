package com.app.agriculturalproducts.fragment;

import android.content.ContentValues;
import android.content.Intent;
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
import com.app.agriculturalproducts.FertilizerActivity;
import com.app.agriculturalproducts.OtherInfoActivity;
import com.app.agriculturalproducts.PesticidesActivity;
import com.app.agriculturalproducts.PickingActivity;
import com.app.agriculturalproducts.PlantActivity;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.TaskDoneSelectActivity;
import com.app.agriculturalproducts.adapter.BaseAbstractRecycleCursorAdapter;
import com.app.agriculturalproducts.adapter.FusageCursorAdapter;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.OtherInfoCursorAdapter;
import com.app.agriculturalproducts.adapter.PickingCursorAdapter;
import com.app.agriculturalproducts.adapter.PlantCursorAdapter;
import com.app.agriculturalproducts.adapter.PusageCursorAdapter;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.OtherRecord;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.util.ResultCheck;
import com.app.agriculturalproducts.util.TaskRecordUtil;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

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
    private TaskRecord task;

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
        if(task!=null)
        {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if(task.getWorktask_type().equals("农药使用")){
                mDataHelper = new PersticidesUsageDataHelper(getActivity());
                mAdapter = new PusageCursorAdapter(getActivity());
                mRecyclerView.setAdapter((PusageCursorAdapter)mAdapter);
                ((PusageCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
            }else  if(task.getWorktask_type().equals("种植录入")){
                mDataHelper = new PlantSpeciesDataHelper(getActivity());
                mAdapter = new PlantCursorAdapter(getActivity());
                mRecyclerView.setAdapter((PlantCursorAdapter)mAdapter);
                ((PlantCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
            }else  if (task.getWorktask_type().equals("肥料施用")){
                mDataHelper = new FertilizerUsageDataHelper(getActivity());
                mAdapter = new FusageCursorAdapter(getActivity());
                mRecyclerView.setAdapter((FusageCursorAdapter)mAdapter);
                ((FusageCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
            }else  if(task.getWorktask_type().equals("采摘记录")){
                mDataHelper = new PickingDataHelper(getActivity());
                mAdapter = new PickingCursorAdapter(getActivity());
                mRecyclerView.setAdapter((PickingCursorAdapter)mAdapter);
                ((PickingCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
            }else  if(task.getWorktask_type().equals("其他记录")){
                mDataHelper = new OtherInfoDataHelper(getActivity());
                mAdapter = new OtherInfoCursorAdapter(getActivity());
                mRecyclerView.setAdapter((OtherInfoCursorAdapter)mAdapter);
                ((OtherInfoCursorAdapter)mAdapter).setOnItemClickListener(itemClickListener);
            }
        }
    }

    private OnAdpaterItemClickListener itemClickListener = new OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {
            final Object record = obj;
            new MaterialDialog.Builder(getActivity())
                    .title("确定提交任务")
                    .positiveText("确定").negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog dialog, DialogAction which) {
                    uploadTask(record);
                }
            }).show();
        }
    };

    private void uploadTask(Object obj){

        if(task.getWorktask_type().equals("农药使用")){
            preventionUpload((PreventionRecord) obj);
        }else  if(task.getWorktask_type().equals("种植录入")){
            plantUpload((PlanterRecord) obj);
        }else  if (task.getWorktask_type().equals("肥料施用")){
            fertilizerUpload((FertilizerRecord)obj);
        }else  if(task.getWorktask_type().equals("采摘记录")){
            pickUpload((PickRecord)obj);
        }else  if(task.getWorktask_type().equals("其他记录")){
            otherUpload((OtherRecord) obj);
        }
    }

    private int checkResult(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            String val = jsonObject.getString("return_code");
            if (val.equals("success")) {
                return 0;
            } else {
                String res = jsonObject.getString("return_msg");
                new MaterialDialog.Builder(getActivity())
                        .title(res)
                        .positiveText("好的")
                        .show();
                return -1;
            }
        } catch (JSONException e) {

        }
        return -2;
    }

    private void otherUpload(final OtherRecord record){
        if(!ResultCheck.checkSaved(record.getLocal_plant_id()))
        {
            new MaterialDialog.Builder(getActivity())
                    .title("任务递交失败")
                    .content("种植记录未上传：请先上传种植记录！")
                    .positiveText("好的")
                    .show();
            return;
        }
        HttpClient.getInstance().uploadOther(new HttpListener<String>() {
            @Override
            public void onSuccess(String s, Response<String> response) {
                int val = checkResult(s);
                if (val == 0) {
                    record.setSaved("yes");
                    ContentValues values = cupboard().withEntity(OtherRecord.class).toContentValues(record);
                    ((OtherInfoDataHelper) mDataHelper).updateByID(values, String.valueOf(record.get_id()));
                    TaskRecordUtil.removeLocalDoneTask(getActivity(), new TaskDataHelper(getActivity().getApplicationContext()), task.getWorktasklist_id());
                    new MaterialDialog.Builder(getActivity())
                            .title("任务递交成功")
                            .positiveText("好的")
                            .show();
                } else if (val == -1) {
                    record.setSaved("err");
                    ContentValues values = cupboard().withEntity(OtherRecord.class).toContentValues(record);
                    ((OtherInfoDataHelper) mDataHelper).updateByID(values, String.valueOf(record.get_id()));
                }
            }
        }, record);
    }

    private void pickUpload(final PickRecord record){
        if(!ResultCheck.checkSaved(record.getLocal_plant_id()))
        {
            new MaterialDialog.Builder(getActivity())
                    .title("任务递交失败")
                    .content("种植记录未上传：请先上传种植记录！")
                    .positiveText("好的")
                    .show();
            return;
        }
        HttpClient.getInstance().uploadPick(new HttpListener<String>() {
            @Override
            public void onSuccess(String s, Response<String> response) {
                int val = checkResult(s);
                if (val == 0) {
                    record.setSaved("yes");
                    ContentValues values = cupboard().withEntity(PickRecord.class).toContentValues(record);
                    ((PickingDataHelper) mDataHelper).updateByID(values, String.valueOf(record.get_id()));
                    TaskRecordUtil.removeLocalDoneTask(getActivity(), new TaskDataHelper(getActivity().getApplicationContext()), task.getWorktasklist_id());
                    new MaterialDialog.Builder(getActivity())
                            .title("任务递交成功")
                            .positiveText("好的")
                            .show();
                } else if (val == -1) {
                    record.setSaved("err");
                    ContentValues values = cupboard().withEntity(PickRecord.class).toContentValues(record);
                    ((PickingDataHelper) mDataHelper).updateByID(values, String.valueOf(record.get_id()));
                }
            }
        }, record);
    }

    private void preventionUpload(final PreventionRecord record){
        if(!ResultCheck.checkSaved(record.getLocal_plant_id()))
        {
            new MaterialDialog.Builder(getActivity())
                    .title("任务递交失败")
                    .content("种植记录未上传：请先上传种植记录！")
                    .positiveText("好的")
                    .show();
            return;
        }
        HttpClient.getInstance().uploadPrevention(new HttpListener<String>() {
            @Override
            public void onSuccess(String s, Response<String> response) {
                int val = checkResult(s);
                if (val == 0) {
                    record.setSaved("yes");
                    ContentValues values = cupboard().withEntity(PreventionRecord.class).toContentValues(record);
                    ((PersticidesUsageDataHelper) mDataHelper).updateByID(values, String.valueOf(record.get_id()));
                    TaskRecordUtil.removeLocalDoneTask(getActivity(), new TaskDataHelper(getActivity().getApplicationContext()), task.getWorktasklist_id());
                    new MaterialDialog.Builder(getActivity())
                            .title("任务递交成功")
                            .positiveText("好的")
                            .show();
                } else if (val == -1) {
                    record.setSaved("err");
                    ContentValues values = cupboard().withEntity(PreventionRecord.class).toContentValues(record);
                    ((PersticidesUsageDataHelper) mDataHelper).updateByID(values, String.valueOf(record.get_id()));
                }
            }
        }, record);
    }

    private void fertilizerUpload(final FertilizerRecord record){
        if(!ResultCheck.checkSaved(record.getLocal_plant_id()))
        {
            new MaterialDialog.Builder(getActivity())
                    .title("任务递交失败")
                    .content("种植记录未上传：请先上传种植记录！")
                    .positiveText("好的")
                    .show();
            return;
        }
        HttpClient.getInstance().uploadFertilizer(new HttpListener<String>() {
            @Override
            public void onSuccess(String s, Response<String> response) {
                int val = checkResult(s);
                if (val == 0) {
                    record.setSaved("yes");
                    ContentValues values = cupboard().withEntity(FertilizerRecord.class).toContentValues(record);
                    ((FertilizerUsageDataHelper)mDataHelper).updateByID(values, String.valueOf(record.get_id()));
                    TaskRecordUtil.removeLocalDoneTask(getActivity(), new TaskDataHelper(getActivity().getApplicationContext()), task.getWorktasklist_id());
                    new MaterialDialog.Builder(getActivity())
                            .title("任务递交成功")
                            .positiveText("好的")
                            .show();
                }else if(val == -1){
                    record.setSaved("err");
                    ContentValues values = cupboard().withEntity(FertilizerRecord.class).toContentValues(record);
                    ((FertilizerUsageDataHelper)mDataHelper).updateByID(values, String.valueOf(record.get_id()));
                }
            }
        }, record);
    }

    private boolean checkPlantResult(PlanterRecord planterRecord,String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            String val = jsonObject.getString("return_code");
            if(val.equals("success")){
                JSONArray array = jsonObject.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jobject = (JSONObject) array.get(i);
                    String id = jobject.getString("plantrecord_id");
                    planterRecord.setPlantrecord_id(id);
                }
                ResultCheck.savedPlantIDtoDB(planterRecord.getPlantrecord_id(), String.valueOf(planterRecord.get_id()), getActivity());
                planterRecord.setSaved("yes");
                ContentValues values = cupboard().withEntity(PlanterRecord.class).toContentValues(planterRecord);
                ((PlantSpeciesDataHelper)mDataHelper).updateByID(values, String.valueOf(planterRecord.get_id()));
                return true;
            }else{
                planterRecord.setSaved("err");
                ContentValues values = cupboard().withEntity(PlanterRecord.class).toContentValues(planterRecord);
                ((PlantSpeciesDataHelper)mDataHelper).updateByID(values, String.valueOf(planterRecord.get_id()));
                String res = jsonObject.getString("return_msg");
                new MaterialDialog.Builder(getActivity())
                        .title("任务递交失败")
                        .content(res)
                        .positiveText("好的")
                        .show();
                return  false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void plantUpload(final PlanterRecord planterRecord){
        HttpClient.getInstance().uploadPlant(new HttpListener<String>() {
            @Override
            public void onSuccess(String s, Response<String> response) {
                if(checkPlantResult(planterRecord, s) ){
                    TaskRecordUtil.removeLocalDoneTask(getActivity(),new TaskDataHelper(getActivity().getApplicationContext()),task.getWorktasklist_id());
                    new MaterialDialog.Builder(getActivity())
                            .title("任务递交成功")
                            .positiveText("好的")
                            .show();
                }
            }
        },planterRecord);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(task!=null){
            if(task.getWorktask_type().equals("农药使用")){
                return ((PersticidesUsageDataHelper)mDataHelper).getCursorLoaderTask();
            }else  if(task.getWorktask_type().equals("种植录入")){
                return ((PlantSpeciesDataHelper)mDataHelper).getCursorLoaderTask();
            }else  if (task.getWorktask_type().equals("肥料施用")){
                return ((FertilizerUsageDataHelper)mDataHelper).getCursorLoaderTask();
            }else  if(task.getWorktask_type().equals("采摘记录")){
                return ((PickingDataHelper)mDataHelper).getCursorLoaderTask();
            }else  if(task.getWorktask_type().equals("其他记录")){
                return ((OtherInfoDataHelper)mDataHelper).getCursorLoaderTask();
            }
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

    public void setTaskType(TaskRecord task) {
        this.task = task;
    }
}

