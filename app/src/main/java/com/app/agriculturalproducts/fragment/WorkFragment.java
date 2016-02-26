package com.app.agriculturalproducts.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;

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
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.MyIcon;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.view.NoScrollGridLayoutManager;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
//        more_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataHelper.delete_("title=?", new String[]{"任务"});
//            }
//        });

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
            Task icon = new Task();
            Cursor cursor = mFieldDataHelper.getCursor();
            if (cursor.moveToFirst()) {
                Field fieldInfo = Field.fromCursor(cursor);
                icon.setField(fieldInfo.getField_name());
                icon.setSpecies(fieldInfo.getField_type());
            }
            cursor.close();
            icon.setTitle("化肥任务");
            icon.setImgPath("t_a");
            icon.setDetail("这是一个化肥任务");
            icon.setIsDone("false");
            icon.setF_area("化肥面积");
            icon.setF_method("化肥方法");
            icon.setF_spec("化肥规格");
            icon.setF_name("化肥名称");
            icon.setF_type("化肥类型");

            SimpleDateFormat formatter = new SimpleDateFormat("yy:MM:dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            icon.setDate(str);
            mDataHelper.insert_(icon);

            icon.setTitle("农药任务");
            icon.setImgPath("tb");
            icon.setDetail("这是一个农药任务");
            icon.setP_area("农药面积");
            icon.setP_spec("农药规格");
            icon.setP_name("农药名称");
            icon.setP_type("农药类型");
            mDataHelper.insert_(icon);

            icon.setTitle("采摘任务");
            icon.setPick_area("采摘面积");
            icon.setImgPath("tc");
            icon.setDetail("这是一个采摘任务");
            mDataHelper.insert_(icon);
        }
    };

    private OnAdpaterItemClickListener task_adpaterItemClickListener =new  OnAdpaterItemClickListener() {
        @Override
        public void onItemClick(Object obj, int p) {
            final Task task =  (Task) obj;
            new MaterialDialog.Builder(getActivity())
                    .title(task.getTitle())
                    .content(task.getDetail())
                    .positiveText("接受")
                    .negativeText("暂不").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog dialog, DialogAction which) {
//                    if(task.getTitle().equals("农药任务")){
//                        Intent intent = new Intent(getActivity(), PesticidesActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("task", task);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
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
