package com.app.agriculturalproducts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.db.DBHelper;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.fragment.DataFragment;
import com.app.agriculturalproducts.fragment.MineFragment;
import com.app.agriculturalproducts.fragment.WorkFragment;
import com.app.agriculturalproducts.presenter.UserInfoPresenter;
import com.app.agriculturalproducts.view.UserInfoSimpleView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainDrawlayoutActivity extends BaseActivity implements UserInfoSimpleView{

    private UserInfoPresenter mUserInfoPresenter;
    CircleImageView mImgView;
    TextView mNameText;
    TextView mCOOPText;
    private FieldDataHelper filedDataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("首页");
        setSupportActionBar(mToolbar);

        //设置抽屉DrawerLayout
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open, R.string.close);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new WorkFragment()).commit();

        //设置导航栏NavigationView的点击事件
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_one:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new WorkFragment()).commit();
                        mToolbar.setTitle(R.string.mainPage);
                        break;
                    case R.id.item_two:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new DataFragment()).commit();
                        mToolbar.setTitle("地块信息");
                        break;
                    case R.id.item_three:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new MineFragment()).commit();
                        mToolbar.setTitle(R.string.set);
                        break;
                }
                menuItem.setChecked(true);//点击了把它设为选中状态
                mDrawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });

        int width = getResources().getDisplayMetrics().widthPixels*4/5;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mNavigationView.getLayoutParams();
        params.width = width;
        mNavigationView.setLayoutParams(params);

        View header = LayoutInflater.from(this).inflate(R.layout.draw_header, null);
        mNavigationView.addHeaderView(header);
        mImgView = (CircleImageView)header.findViewById(R.id.headerImgView);
        mNameText = (TextView)header.findViewById(R.id.headNameText);
        mCOOPText = (TextView)header.findViewById(R.id.headCOOPText);
        mUserInfoPresenter = new UserInfoPresenter(getApplicationContext(),this);
        mUserInfoPresenter.loadUserInfo();

        filedDataHelper = new FieldDataHelper(getApplicationContext());
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_main_drawlayout;
    }

    @Override
    public void setName(String name) {
        mNameText.setText(name);
    }

    @Override
    public void setImg(String path) {
        if(path!=null){
            Bitmap picture = BitmapFactory.decodeFile(path);
            mImgView.setImageBitmap(picture);
        }
    }

    @Override
    public void setPhone(String phone) {

    }

    @Override
    public void setCoop(String coop) {
        mCOOPText.setText(coop);
    }

    @Override
    public void setId(String id) {

    }
}
