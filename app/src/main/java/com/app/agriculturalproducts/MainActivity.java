package com.app.agriculturalproducts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.app.agriculturalproducts.fragment.DataFragment;
import com.app.agriculturalproducts.fragment.MineFragment;
import com.app.agriculturalproducts.fragment.WorkFragment;
import com.app.agriculturalproducts.view.NoSrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.id_viewpager)
    NoSrollViewPager mViewPager;
    @Bind(R.id.rg_tab)
    RadioGroup radiogroup;
    @Bind(R.id.work_RadioButton)
    RadioButton workButton;
    @Bind(R.id.data_RadioButton)
    RadioButton dataButton;
    @Bind(R.id.mine_RadioButton)
    RadioButton mineButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private RadioGroup.OnCheckedChangeListener listener;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        radiogroup.setOnCheckedChangeListener(listener);
        workButton.setChecked(true);
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
