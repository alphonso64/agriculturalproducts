package com.app.supervisorapp.fragment;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.app.supervisorapp.R;
import com.app.supervisorapp.adapter.BasicIconRecyclerAdapter;
import com.app.supervisorapp.adapter.OnAdpaterItemClickListener;
import com.app.supervisorapp.adapter.TaskCursorAdapter;
import com.app.supervisorapp.bean.MyIcon;
import com.app.supervisorapp.bean.TaskRecord;
import com.app.supervisorapp.db.TaskDataHelper;
import com.app.supervisorapp.view.NoScrollGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ALPHONSO on 2016/5/17.
 */
public class MainFragment  extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    @Bind(R.id.mrecyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.task_recyclerview)
    RecyclerView mTaskRecyclerView;
    @Bind(R.id.task_update)
    ImageButton update_btn;
    @Bind(R.id.task_more)
    ImageButton more_btn;

    private ArrayList<MyIcon> mDatas;
    private TaskCursorAdapter mAdapter;
    private TaskDataHelper mDataHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_main,
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
//
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TaskCursorAdapter(getActivity());
        mTaskRecyclerView.setAdapter(mAdapter);

        update_btn.setOnClickListener(update_onclickListner);
        mDataHelper = new TaskDataHelper(getActivity().getApplicationContext());
    }

    private View.OnClickListener update_onclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TaskRecord task = new TaskRecord();
            task.setWorktask_type("农药使用");
            task.setWorktask_name("消息通知");
            task.setWorktask_content("xxxxxxxxxxxxxxxxxxxxxx");
            task.setWorktask_publish_date("yy-hh-mm-ss");
            mDataHelper.insert_(task);
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
                intent.putExtra("title",ai.getTitle());
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

}
