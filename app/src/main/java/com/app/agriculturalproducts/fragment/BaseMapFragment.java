package com.app.agriculturalproducts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.util.InputType;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class BaseMapFragment extends Fragment {

    // 定位相关
    MapView mMapView;
    LocationClient mLocClient;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    GeoCoder mSearch = null;
    BitmapDescriptor mCurrentMarker;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true; // 是否首次定位
    PersticidesUsageDataHelper mDataHelper;
    MyLocationListenner myListener ;
    String longtitude;
    String latitude;
    String location;

    private TextView latTextView;
    private TextView longTextView;
    private TextView locTextView;
    private LinearLayout mapLy;
    private ImageView map_imgview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_persticides,
                container, false);
        return contextView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mMapView!=null){
            isFirstLoc = true;
            // 地图初始化
            mBaiduMap = mMapView.getMap();
            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);
            // 定位初始化
            myListener = new MyLocationListenner();
            mLocClient = new LocationClient(getActivity());
            mLocClient.registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000);
            mLocClient.setLocOption(option);
            mLocClient.start();

            // 初始化搜索模块，注册事件监听
            mSearch = GeoCoder.newInstance();
            mSearch.setOnGetGeoCodeResultListener(new MyOnGetGeoCoderResultListener());
        }
        if(mapLy!=null){
            mapLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mMapView.getVisibility() == View.GONE){
                        mMapView.setVisibility(View.VISIBLE);
                        map_imgview.setImageResource(R.drawable.ic_expand_more_white_24dp);
                    }else if(mMapView.getVisibility() == View.VISIBLE){
                        mMapView.setVisibility(View.GONE);
                        map_imgview.setImageResource(R.drawable.ic_expand_less_white_24dp);
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMapView!=null){
            mMapView.onResume();
            mLocClient.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mMapView!=null){
            mMapView.onPause();
            mLocClient.stop();
        }
    }

    @Override
    public void onDestroyView() {
        if(mMapView!=null){
            // 退出时销毁定位
            mLocClient.stop();
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
            mSearch.destroy();
        }
        super.onDestroyView();
    }

    protected void setMpView(MapView mapView){
        mMapView = mapView;
    }
    protected void setLocTextView(TextView tx){
        locTextView = tx;
    }
    protected void setLatTextView(TextView tx){
        latTextView = tx;
    }
    protected void setLongTextView(TextView tx){
        longTextView = tx;
    }
    public void setMapLy(LinearLayout mapLy) {
        this.mapLy = mapLy;
    }

    public int upload(){
        return InputType.INPUT_SAVE_OK;
    }

    public void setMap_imgview(ImageView map_imgview) {
        this.map_imgview = map_imgview;
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            Log.e("testbb", "getLatitude:" + location.getLatitude() + " getLongitude():" + location.getLongitude());
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
            String latTmp = String.valueOf(location.getLatitude());
            String longTMp = String.valueOf(location.getLongitude());
            if(latTmp.equals(latitude) && longTMp.equals(longtitude) ){
                return;
            }
            latitude =latTmp;
            longtitude = longTMp;
            if(latTextView!=null){
                latTextView.setText(latitude);
            }
            if(longTextView!=null){
                longTextView.setText(longtitude);
            }
            LatLng ptCenter = new LatLng(location.getLatitude(),location.getLongitude());
            // 反Geo搜索
            mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(ptCenter));

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    class MyOnGetGeoCoderResultListener implements OnGetGeoCoderResultListener{

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
//            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
//                        .show();
//                return;
//            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            location = result.getAddress();
            if(locTextView!=null){
                locTextView.setText(location);
            }
        }
    }

}

