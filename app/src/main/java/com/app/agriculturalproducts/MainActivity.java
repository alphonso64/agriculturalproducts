package com.app.agriculturalproducts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.agriculturalproducts.fragment.DataFragment;
import com.app.agriculturalproducts.fragment.MineFragment;
import com.app.agriculturalproducts.fragment.WorkFragment;
import com.app.agriculturalproducts.view.NoSrollViewPager;
import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.id_viewpager)
    NoSrollViewPager mViewPager;
    @InjectView(R.id.rg_tab)
    RadioGroup radiogroup;
    @InjectView(R.id.work_RadioButton)
    RadioButton workButton;
    @InjectView(R.id.data_RadioButton)
    RadioButton dataButton;
    @InjectView(R.id.mine_RadioButton)
    RadioButton mineButton;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private RadioGroup.OnCheckedChangeListener listener;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    LiteHttp liteHttp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        initView();
        radiogroup.setOnCheckedChangeListener(listener);
        workButton.setChecked(true);
        toolbar.setTitle(R.string.work_name);
        setSupportActionBar(toolbar);//toolbar支持

        HttpConfig config = new HttpConfig(this) // configuration quickly
                .setDebugged(true)                   // log output when debugged
                .setDetectNetwork(true)              // detect network before connect
                .setDoStatistics(true)               // statistics of time and traffic
                .setUserAgent("Mozilla/5.0 (...)")   // set custom User-Agent
                .setTimeOut(10000, 10000);
        liteHttp = LiteHttp.newApacheHttpClient(config);
        // 主线程处理，注意HttpListener默认是在主线程回调
            // get data in listener,  handle result on UI thread
        if(liteHttp == null){
            Log.e("testbb", "hah");
        }
        Log.e("testbb", "hehe");

        liteHttp.executeAsync(new StringRequest("http://139.196.11.207/app/v1/login").setHttpListener(
                new HttpListener<String>() {
                    @Override
                    public void onSuccess(String data, Response<String> response) {
                        Log.e("testbb", data);
                    }
                }
        ));
       // new Thread(runnable).start();

    }

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            liteHttp.executeAsync(new StringRequest("http://139.196.11.207/app/v1/login").setHttpListener(
                    new HttpListener<String>() {
                        @Override
                        public void onSuccess(String data, Response<String> response) {
                            Log.e("testbb", data);
                        }
                    }
            ));
        }
    };

    private void  initView(){
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new WorkFragment());
        mFragments.add(new DataFragment());
        mFragments.add(new MineFragment());
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }
            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
        listener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == workButton.getId()) {
                    mViewPager.setCurrentItem(0, false);
                    toolbar.setTitle(R.string.work_name);
                } else if (checkedId == dataButton.getId()) {
                    mViewPager.setCurrentItem(1, false);
                    toolbar.setTitle(R.string.data_name);
                } else if (checkedId == mineButton.getId()) {
                    mViewPager.setCurrentItem(2, false);
                    toolbar.setTitle(R.string.mine_name);
                }
            }
        };
    }
}
