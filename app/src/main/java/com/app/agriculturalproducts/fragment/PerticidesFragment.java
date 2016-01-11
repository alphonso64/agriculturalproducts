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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PerticidesFragment extends BaseMapFragment {
    @Bind(R.id.persticides_name_text)
    EditText name;
    @Bind(R.id.usage_text)
    EditText usage;
    @Bind(R.id.remarks_text)
    EditText remark;
    @Bind(R.id.bmapView)
    MapView mMapView;
    @Bind(R.id.location__text)
    TextView locTextView;
    @Bind(R.id.lat_text)
    TextView latTextView;
    @Bind(R.id.long_text)
    TextView longTextView;
    boolean flag;
    PersticidesUsageDataHelper mDataHelper;
    List<EditText> ls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View contextView = inflater.inflate(R.layout.fragment_persticides,
                container, false);
        ButterKnife.bind(this, contextView);
        setMpView(mMapView);
        setLatTextView(latTextView);
        setLocTextView(locTextView);
        setLongTextView(longTextView);
        ls = new ArrayList<>();
        ls.add(name);
        ls.add(usage);
        ls.add(remark);
        return contextView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataHelper = new PersticidesUsageDataHelper(getActivity());
        if(flag){
            disableEditText(ls);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Log.e("testbb", "onDestroyView:");
    }

    public void setSavedFlag(boolean flag){
        this.flag = flag;
    }

    public boolean isEditEmpty(List<EditText> ls){
        for(EditText et:ls){
            if(TextUtils.isEmpty(et.getText().toString().trim())){
                return true;
            }
        }
        return false;
    }

     public void disableEditText(List<EditText> ls){
         for(EditText et:ls){
             et.setFocusable(false);
         }
    }

    public int upload(){
        if(flag){
            return InputType.INPUT_SAVE_ALREADY;
        }
        if(!isEditEmpty(ls)){
            PersticidesUsage pu = new PersticidesUsage();
            pu.setName(name.getText().toString());
            pu.setRemarks(remark.getText().toString());
            pu.setUsage(usage.getText().toString());
            pu.setTime(Calendar.getInstance().getTimeInMillis());
            pu.setLatitude(latitude);
            pu.setLongtitude(longtitude);
            pu.setLocation(location);
            mDataHelper.insert_(pu);
            disableEditText(ls);
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            flag = true;
            return InputType.INPUT_SAVE_OK;
        }
        return InputType.INPUT_EMPTY;
    }
}

