package com.app.agriculturalproducts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.adapter.BasicIconRecyclerAdapter;
import com.app.agriculturalproducts.adapter.MyIcon;

import java.util.ArrayList;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class WorkFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<MyIcon> mDatas;
    private int [] iconlist = {R.drawable.icon_a,R.drawable.icon_b,R.drawable.icon_c,R.drawable.icon_d,R.drawable.icon_e,R.drawable.icon_f,R.drawable.icon_g,R.drawable.icon_h};
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
        mRecyclerView.setLayoutManager(new myGridLayoutManager(getActivity(), 4));
        initData();
        BasicIconRecyclerAdapter ba = new BasicIconRecyclerAdapter(mDatas,getActivity());
        ba.setOnItemClickListener(new BasicIconRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int p) {
                Log.e("testbb", "pos:" + p);
            }
        });
        mRecyclerView.setAdapter(ba);

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
    }

    class myGridLayoutManager extends GridLayoutManager {
        public myGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }
}
