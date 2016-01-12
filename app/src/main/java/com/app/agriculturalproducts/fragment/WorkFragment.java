package com.app.agriculturalproducts.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.media.IMediaBrowserServiceCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.FertilizerActivity;
import com.app.agriculturalproducts.OtherInfoActivity;
import com.app.agriculturalproducts.PesticidesActivity;
import com.app.agriculturalproducts.PickingActivity;
import com.app.agriculturalproducts.PlantActivity;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.TaskActivity;
import com.app.agriculturalproducts.adapter.BasicIconRecyclerAdapter;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.adapter.TaskCursorAdapter;
import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.MyIcon;
import com.app.agriculturalproducts.bean.OtherInfo;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.view.NoScrollGridLayoutManager;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    private List<String> title_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataHelper = new TaskDataHelper(getActivity().getApplicationContext());
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
        ba.setOnItemClickListener(new OnAdpaterItemClickListener() {
            @Override
            public void onItemClick(Object obj, int p) {
                activityJump((String)obj);
            }
        });
        mRecyclerView.setAdapter(ba);

        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TaskCursorAdapter(getActivity());
        mAdapter.setOnItemClickListener(new OnAdpaterItemClickListener() {
            @Override
            public void onItemClick(Object obj, int p) {
                new MaterialDialog.Builder(getActivity())
                        .title(((Task) obj).getTitle())
                        .content(((Task) obj).getDetail())
                        .positiveText("接受")
                        .negativeText("暂不")
                        .positiveColorRes(R.color.colorPrimary)
                        .negativeColorRes(R.color.colorPrimary)
                        .show();
            }
        });
        mTaskRecyclerView.setAdapter(mAdapter);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppApplication.getLiteHttp().executeAsync(new StringRequest("http://139.196.11.207/app/v1/login").setHttpListener(
                        new HttpListener<String>() {
                            @Override
                            public void onSuccess(String data, Response<String> response) {
                                Log.e("testbb", data);
                                Task icon = new Task();
                                icon.setTitle("任务");
                                icon.setIconID(R.drawable.t_a);
                                icon.setDetail(data);
                                mDataHelper.insert_(icon);
                            }
                        }
                ));
            }
        });
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataHelper.delete_("title=?", new String[]{"任务"});
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    protected void initData()
    {
        String[] titles = getActivity().getResources().getStringArray(R.array.titles);
        title_list = Arrays.asList(titles);
        String[] iconlist = getActivity().getResources().getStringArray(R.array.icons);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            MyIcon icon = new MyIcon();
            icon.setTitle(titles[i]);
            int resId = getResources().getIdentifier(iconlist[i], "drawable" , getActivity().getPackageName());
            icon.setIconID(resId);
            mDatas.add(i,icon);
        }
    }

    private void activityJump(String title){
        if(title.equals("农药")){
            Intent intent = new Intent(getActivity(), PesticidesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title",title);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(title.equals("化肥")){
            Intent intent = new Intent(getActivity(), FertilizerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title",title);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(title.equals("种植")){
            Intent intent = new Intent(getActivity(), PlantActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title",title);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(title.equals("采摘")){
            Intent intent = new Intent(getActivity(), PickingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title",title);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(title.equals("其他")){
            Intent intent = new Intent(getActivity(), OtherInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title",title);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(getActivity(), TaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title",title);
            intent.putExtras(bundle);
            startActivity(intent);
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
