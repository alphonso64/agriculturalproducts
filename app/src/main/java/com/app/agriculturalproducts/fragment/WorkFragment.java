package com.app.agriculturalproducts.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.FertilizerActivity;
import com.app.agriculturalproducts.PesticidesActivity;
import com.app.agriculturalproducts.PickingActivity;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.adapter.BasicIconRecyclerAdapter;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.TaskCursorAdapter;
import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.MyIcon;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.model.UnuploadTaskModel;
import com.app.agriculturalproducts.util.InputType;
import com.app.agriculturalproducts.view.NoScrollGridLayoutManager;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class WorkFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @Bind(R.id.mrecyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.task_recyclerview)
    RecyclerView mTaskRecyclerView;
    @Bind(R.id.task_update)
    ImageButton update_btn;
    @Bind(R.id.task_more)
    ImageButton more_btn;

    private ArrayList<MyIcon> mDatas;
    private TaskDataHelper mDataHelper;
    private TaskCursorAdapter mAdapter;
    private FieldDataHelper mFieldDataHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                mDataHelper.replace(HttpClient.getInstance().taskList);
            }else if(msg.what == -1){
                Toast toast = Toast.makeText(getActivity(),
                        "网络未连接", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_work,
                container, false);
        ButterKnife.bind(this, contextView);
        initData();
        return contextView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new NoScrollGridLayoutManager(getActivity(), 4));
        BasicIconRecyclerAdapter ba = new BasicIconRecyclerAdapter(mDatas,getActivity());
        ba.setOnItemClickListener(icon_adpaterItemClickListener);
        mRecyclerView.setAdapter(ba);

        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TaskCursorAdapter(getActivity());
        mAdapter.setOnItemClickListener(task_adpaterItemClickListener);
        mTaskRecyclerView.setAdapter(mAdapter);

        update_btn.setOnClickListener(update_onclickListner);
        more_btn.setVisibility(View.INVISIBLE);
        mDataHelper = new TaskDataHelper(getActivity().getApplicationContext());
        mFieldDataHelper = new FieldDataHelper(getActivity().getApplicationContext());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    private View.OnClickListener update_onclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sp = getActivity().getSharedPreferences(InputType.loginInfoDB,
                    Activity.MODE_PRIVATE);
            final String name = sp.getString("name", null);
            UnuploadTaskModel taskModel = new UnuploadTaskModel(getActivity());
            final HashMap<String,String> map = taskModel.getUnuploadTask();
            new Thread(){
                @Override
                public void run() {
                    int val = HttpClient.getInstance().getTaskInfo(name,map);
                    if(val == -1){
                        mHandler.sendEmptyMessage(-1);
                    }else if(val == 0) {
                        mHandler.sendEmptyMessage(1);
                    }
                }}.start();
        }
    };

    private OnAdpaterItemClickListener task_adpaterItemClickListener =new  OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {
            final TaskRecord taskRecord = (TaskRecord)obj;
            if(taskRecord.getWorktask_type().equals("农药使用")){
                taskRecord.setWorktasklist_status("已查看");
                HttpClient.getInstance().uploadTask(null, taskRecord);
            }

            UnuploadTaskModel taskModel = new UnuploadTaskModel(getActivity());
            taskModel.setUnuploadTask(taskRecord);

            new MaterialDialog.Builder(getActivity())
                    .title(taskRecord.getWorktask_name())
                    .content(taskRecord.getWorktask_content())
                    .positiveText("接受")
                    .negativeText("暂不").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog dialog, DialogAction which) {
                    if(taskRecord.getWorktask_type().equals("农药使用")){

                        UnuploadTaskModel taskModel = new UnuploadTaskModel(getActivity());
                        taskModel.removeUnuploadTask(taskRecord);

                        Intent intent = new Intent(getActivity(), PesticidesActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("task", taskRecord);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
//                    if(task.getTitle().equals("化肥任务")){
//                        Intent intent = new Intent(getActivity(), FertilizerActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("task", task);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                    if(task.getTitle().equals("采摘任务")){
//                        Intent intent = new Intent(getActivity(), PickingActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("task", task);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
                }
            }).show();

        }
    };

    private OnAdpaterItemClickListener icon_adpaterItemClickListener =new  OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {
            try {
                activityJump((String)obj);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    protected void initData()
    {
        String[] titles = getActivity().getResources().getStringArray(R.array.titles);
        String[] iconlist = getActivity().getResources().getStringArray(R.array.icons);
        String[] className = getActivity().getResources().getStringArray(R.array.class_name);
        mDatas = new ArrayList<>();
        for (int i = 0; i < titles.length; i++)
        {
            MyIcon icon = new MyIcon();
            icon.setTitle(titles[i]);
            int resId = getResources().getIdentifier(iconlist[i], "drawable" , getActivity().getPackageName());
            icon.setIconID(resId);
            icon.setClassName(className[i]);
            mDatas.add(i,icon);
        }
    }

    private void activityJump(String title) throws ClassNotFoundException {
        for(MyIcon ai:mDatas){
            if(title.equals(ai.getTitle())){
            Intent intent = new Intent(getActivity(), Class.forName(ai.getClassName()));
            startActivity(intent);
            }
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
