package com.app.agriculturalproducts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.PreventionRecord;
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
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainDrawlayoutActivity extends BaseActivity {

    CircleImageView mImgView;
    TextView mNameText;
    TextView mCOOPText;
    private ProgressDialog progressDialog;
    private DataFragment dataFragment;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                SharedPreferences sp = AppApplication.getContext().getSharedPreferences(InputType.loginInfoDB,
                        Activity.MODE_PRIVATE);
                String name = sp.getString("name", null);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(name, "no");
                editor.commit();
                progressDialog.dismiss();
                setPersonINfo();
            }else if(msg.what == -1){
                progressDialog.dismiss();
                Toast toast = Toast.makeText(MainDrawlayoutActivity.this,
                        "网络未连接", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else{
                progressDialog.dismiss();
                dataFragment.updateData();
            }

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
                        mToolbar.getMenu().clear();
                        mToolbar.inflateMenu(R.menu.menu_update_single);
                        break;
                    case R.id.item_two:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new DataFragment(),"data").commit();
                        mToolbar.setTitle("地块信息");
                        mToolbar.getMenu().clear();
                        mToolbar.inflateMenu(R.menu.menu_update_single_icon);
                        break;
                    case R.id.item_three:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new MineFragment()).commit();
                        mToolbar.setTitle(R.string.set);
                        mToolbar.getMenu().clear();
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
        //Log.e("testcc", name + isFirstLogin);
        if(isFirstLogin==null)
        {
            updateALLDB();
        }

        View header = LayoutInflater.from(this).inflate(R.layout.draw_header, null);
        mNavigationView.addHeaderView(header);
        mImgView = (CircleImageView)header.findViewById(R.id.headerImgView);
        mNameText = (TextView)header.findViewById(R.id.headNameText);
        mCOOPText = (TextView)header.findViewById(R.id.headCOOPText);
        mToolbar.setOnMenuItemClickListener(itemClick);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_single, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener itemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId()==R.id.action_update_icon){
                progressDialog = new ProgressDialog(MainDrawlayoutActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("数据更新中");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
                dataFragment = (DataFragment)getSupportFragmentManager().findFragmentByTag("data");
                SharedPreferences sp = getSharedPreferences(InputType.loginInfoDB,
                        Activity.MODE_PRIVATE);
                final String name = sp.getString("name", null);
                new Thread(){
                    @Override
                    public void run() {
                        if(HttpClient.getInstance().getFieldInfo(name)){
                            mHandler.sendEmptyMessage(2);
                        }else{
                            mHandler.sendEmptyMessage(-1);
                        }
                    }}.start();
            }if(item.getItemId()==R.id.action_update_single){
                updateALLDB();
//                new MaterialDialog.Builder(MainDrawlayoutActivity.this)
//                        .title("更新数据会删除未上传数据！")
//                        .positiveText("是")
//                        .negativeText("否").onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(MaterialDialog dialog, DialogAction which) {
//                        updateALLDB();
//                    }
//                }).show();
            }
            return true;
        }
    };


    private void updateALLDB(){
        SharedPreferences sp = AppApplication.getContext().getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        final String name = sp.getString("name", null);
        final EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(getApplicationContext());

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("更新数据中");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
        new Thread(){

            @Override
            public void run() {
                if(HttpClient.getInstance().getAllInfo(name))
                {
                    //保存地块信息至数据库
                    FieldDataHelper fieldDataHelper = new FieldDataHelper(MainDrawlayoutActivity.this);
                    if(HttpClient.getInstance().fieldList!=null)
                        fieldDataHelper.replace(HttpClient.getInstance().fieldList);

                    PlantSpeciesDataHelper plantSpeciesDataHelper = new PlantSpeciesDataHelper(MainDrawlayoutActivity.this);
                    if(HttpClient.getInstance().planterList!=null)
                        plantSpeciesDataHelper.repalceInfo(HttpClient.getInstance().planterList);

                    FertilizerUsageDataHelper fertilizerUsageDataHelper = new FertilizerUsageDataHelper(MainDrawlayoutActivity.this);
                    if(HttpClient.getInstance().fertiList!=null)
                        fertilizerUsageDataHelper.repalceInfo(HttpClient.getInstance().fertiList);

                    PersticidesUsageDataHelper persticidesUsageDataHelper = new PersticidesUsageDataHelper(MainDrawlayoutActivity.this);
                    if(HttpClient.getInstance().preventionList!=null)
                        persticidesUsageDataHelper.repalceInfo(HttpClient.getInstance().preventionList);

                    PickingDataHelper pickingDataHelper = new PickingDataHelper(MainDrawlayoutActivity.this);
                    if(HttpClient.getInstance().pickList!=null)
                        pickingDataHelper.repalceInfo(HttpClient.getInstance().pickList);

                    OtherInfoDataHelper otherInfoDataHelper = new OtherInfoDataHelper(MainDrawlayoutActivity.this);
                    if(HttpClient.getInstance().otherList!=null)
                        otherInfoDataHelper.repalceInfo(HttpClient.getInstance().otherList);

                    StockDataHelper stockDataHelper = new StockDataHelper(MainDrawlayoutActivity.this);
                    if(HttpClient.getInstance().seedStockList!=null)
                        stockDataHelper.replace(HttpClient.getInstance().seedStockList);
                    if(HttpClient.getInstance().fStockList!=null)
                        stockDataHelper.bulkInsert(HttpClient.getInstance().fStockList);
                    if(HttpClient.getInstance().pStcokList!=null)
                        stockDataHelper.bulkInsert(HttpClient.getInstance().pStcokList);

                    StockDetailDataHelper stockDetailDataHelper = new StockDetailDataHelper(MainDrawlayoutActivity.this);
                    if(HttpClient.getInstance().enterstockList!=null)
                        stockDetailDataHelper.replace(HttpClient.getInstance().enterstockList);
                    if(HttpClient.getInstance().outstockList!=null)
                        stockDetailDataHelper.bulkInsert(HttpClient.getInstance().outstockList);
                    //保存雇员信息sp中
                    EmployeeInfo employeeInfo = HttpClient.getInstance().employeeInfo;
                    if(employeeInfo!=null)
                        employeeInfoModel.setEmployeeInfo(employeeInfo);
                    mHandler.sendEmptyMessage(1);
                }else {
                    mHandler.sendEmptyMessage(-1);
                }

            }}.start();
    }

}
