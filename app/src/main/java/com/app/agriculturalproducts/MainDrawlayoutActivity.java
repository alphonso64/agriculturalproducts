package com.app.agriculturalproducts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.db.DBHelper;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.StockDataHelper;
import com.app.agriculturalproducts.db.StockDetailDataHelper;
import com.app.agriculturalproducts.fragment.DataFragment;
import com.app.agriculturalproducts.fragment.MineFragment;
import com.app.agriculturalproducts.fragment.WorkFragment;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.model.EmployeeInfoModel;
import com.app.agriculturalproducts.presenter.UserInfoPresenter;
import com.app.agriculturalproducts.util.InputType;
import com.app.agriculturalproducts.view.UserInfoSimpleView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainDrawlayoutActivity extends BaseActivity {

    private UserInfoPresenter mUserInfoPresenter;
    CircleImageView mImgView;
    TextView mNameText;
    TextView mCOOPText;
    private FieldDataHelper filedDataHelper;
    private ProgressDialog progressDialog;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            SharedPreferences sp = AppApplication.getContext().getSharedPreferences(InputType.loginInfoDB,
                    Activity.MODE_PRIVATE);
            String name = sp.getString("name", null);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(name, "no");
            editor.commit();
            progressDialog.dismiss();
            setPersonINfo();
        }
    };
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

        SharedPreferences sp = AppApplication.getContext().getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        final String name = sp.getString("name", null);
        final String isFirstLogin = sp.getString(name,null);
        final EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(getApplicationContext());
        //Log.e("testcc", name + isFirstLogin);
        if(isFirstLogin==null)
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("初始化");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
            new Thread(){

                @Override
                public void run() {
                    HttpClient.getInstance().getAllInfo(name);
                    //保存地块信息至数据库
                    FieldDataHelper fieldDataHelper = new FieldDataHelper(MainDrawlayoutActivity.this);
                    fieldDataHelper.bulkInsert(HttpClient.getInstance().fieldList);

                    PlantSpeciesDataHelper plantSpeciesDataHelper = new PlantSpeciesDataHelper(MainDrawlayoutActivity.this);
                    plantSpeciesDataHelper.bulkInsert(HttpClient.getInstance().planterList);

                    FertilizerUsageDataHelper fertilizerUsageDataHelper = new FertilizerUsageDataHelper(MainDrawlayoutActivity.this);
                    fertilizerUsageDataHelper.bulkInsert(HttpClient.getInstance().fertiList);

                    PersticidesUsageDataHelper persticidesUsageDataHelper = new PersticidesUsageDataHelper(MainDrawlayoutActivity.this);
                    persticidesUsageDataHelper.bulkInsert(HttpClient.getInstance().preventionList);

                    PickingDataHelper pickingDataHelper = new PickingDataHelper(MainDrawlayoutActivity.this);
                    pickingDataHelper.bulkInsert(HttpClient.getInstance().pickList);

                    OtherInfoDataHelper otherInfoDataHelper = new OtherInfoDataHelper(MainDrawlayoutActivity.this);
                    otherInfoDataHelper.bulkInsert(HttpClient.getInstance().otherList);

                    StockDataHelper stockDataHelper = new StockDataHelper(MainDrawlayoutActivity.this);
                    stockDataHelper.bulkInsert(HttpClient.getInstance().seedStockList);
                    stockDataHelper.bulkInsert(HttpClient.getInstance().fStockList);
                    stockDataHelper.bulkInsert(HttpClient.getInstance().pStcokList);

                    StockDetailDataHelper stockDetailDataHelper = new StockDetailDataHelper(MainDrawlayoutActivity.this);
                    stockDetailDataHelper.bulkInsert(HttpClient.getInstance().enterstockList);
                    stockDetailDataHelper.bulkInsert(HttpClient.getInstance().outstockList);
                    //保存雇员信息sp中
                    EmployeeInfo employeeInfo = HttpClient.getInstance().employeeInfo;
                    employeeInfoModel.setEmployeeInfo(employeeInfo);
                    mHandler.sendEmptyMessage(1);
                }}.start();
        }

        View header = LayoutInflater.from(this).inflate(R.layout.draw_header, null);
        mNavigationView.addHeaderView(header);
        mImgView = (CircleImageView)header.findViewById(R.id.headerImgView);
        mNameText = (TextView)header.findViewById(R.id.headNameText);
        mCOOPText = (TextView)header.findViewById(R.id.headCOOPText);
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_main_drawlayout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPersonINfo();
    }

    void  setPersonINfo(){
        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(MainDrawlayoutActivity.this);
        EmployeeInfo employeeInfo = employeeInfoModel.getEmployeeInfo();
        mNameText.setText(employeeInfo.getEmployee_name());
        mCOOPText.setText(employeeInfo.getMember_name());
        String path = employeeInfo.getPath();
        if(path!=null){
            Bitmap picture = BitmapFactory.decodeFile(path);
            mImgView.setImageBitmap(picture);
        }
    }

}
