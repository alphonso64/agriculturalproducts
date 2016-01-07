package com.app.agriculturalproducts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.TaskActivity;
import com.app.agriculturalproducts.adapter.BasicIconRecyclerAdapter;
import com.app.agriculturalproducts.adapter.MyIcon;
import com.app.agriculturalproducts.adapter.Task;
import com.app.agriculturalproducts.adapter.TaskRecyclerAdapter;
import com.app.agriculturalproducts.view.NoScrollGridLayoutManager;

import java.util.ArrayList;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class WorkFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView mTaskRecyclerView;
    private ArrayList<MyIcon> mDatas;
    private ArrayList<Task> mTaskDatas;
    private int [] iconlist = {R.drawable.icon_a,R.drawable.icon_b,R.drawable.icon_c,R.drawable.icon_d,R.drawable.icon_e,R.drawable.icon_f,R.drawable.icon_g,R.drawable.icon_h};
    private int [] tasklist = {R.drawable.t_a,R.drawable.tb,R.drawable.tc,R.drawable.t_a,R.drawable.tc,R.drawable.tb,R.drawable.t_a,R.drawable.tb};
    private String [] titlelist  = {"农药","化肥","土壤","种植物","生产","其他","采摘","..."};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_work,
                container, false);
        initData();
        mRecyclerView = (RecyclerView) contextView.findViewById(R.id.mrecyclerview);
        mRecyclerView.setLayoutManager(new NoScrollGridLayoutManager(getActivity(), 4));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
//                StaggeredGridLayoutManager.VERTICAL));
        BasicIconRecyclerAdapter ba = new BasicIconRecyclerAdapter(mDatas,getActivity());
        ba.setOnItemClickListener(new BasicIconRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int p) {
                Bundle bl = new Bundle();
                bl.putString("title", titlelist[p]);
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                intent.putExtras(bl);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(ba);

        mTaskRecyclerView = (RecyclerView) contextView.findViewById(R.id.task_recyclerview);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TaskRecyclerAdapter ta =  new TaskRecyclerAdapter(mTaskDatas,getActivity());
        ta.setOnItemClickListener(new TaskRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int p) {
                Bundle bl = new Bundle();
                bl.putString("title", "task "+titlelist[p]+" "+p);
                Intent intent = new Intent(getActivity(), TaskActivity.class);
                intent.putExtras(bl);
                startActivity(intent);
            }
        });
        mTaskRecyclerView.setAdapter(ta);
        return contextView;
    }

    protected void initData()
    {
        mDatas = new ArrayList<MyIcon>();
        for (int i = 0; i < 8; i++)
        {
            MyIcon icon = new MyIcon();
            icon.setTitle(titlelist[i]);
            icon.setIconID(iconlist[i]);
            mDatas.add(i,icon);
        }

        mTaskDatas = new ArrayList<Task>();
        for (int i = 0; i < 8; i++)
        {
            Task icon = new Task();
            icon.setTitle(titlelist[i]);
            icon.setIconID(tasklist[i]);
            icon.setDetail("zhe sshi yige hhhh 我我我我我我我");
            mTaskDatas.add(i, icon);
        }
    }

}
