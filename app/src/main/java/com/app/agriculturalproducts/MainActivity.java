package com.app.agriculturalproducts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.agriculturalproducts.fragment.DataFragment;
import com.app.agriculturalproducts.fragment.MineFragment;
import com.app.agriculturalproducts.fragment.WorkFragment;
import com.app.agriculturalproducts.view.NoSrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private NoSrollViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;
    private RadioGroup radiogroup;
    private RadioButton workButton;
    private RadioButton dataButton;
    private RadioButton mineButton;
    private RadioGroup.OnCheckedChangeListener listener;
    private  Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = (NoSrollViewPager)findViewById(R.id.id_viewpager);
        radiogroup = (RadioGroup)findViewById(R.id.rg_tab);
        workButton = (RadioButton)findViewById(R.id.work_RadioButton);
        dataButton = (RadioButton)findViewById(R.id.data_RadioButton);
        mineButton = (RadioButton)findViewById(R.id.mine_RadioButton);
        workButton.setChecked(true);
        initView();
        radiogroup.setOnCheckedChangeListener(listener);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.work_name);
        setSupportActionBar(toolbar);//toolbar支持

    }

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
