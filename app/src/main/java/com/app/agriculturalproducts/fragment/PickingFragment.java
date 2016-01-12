package com.app.agriculturalproducts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.Picking;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
import com.app.agriculturalproducts.util.InputType;
import com.baidu.mapapi.map.MapView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PickingFragment extends BaseMapFragment {
    @Bind(R.id.pick_text)
    EditText pick;


    @Bind(R.id.bmapView)
    MapView mMapView;
    @Bind(R.id.location__text)
    TextView locTextView;
    @Bind(R.id.lat_text)
    TextView latTextView;
    @Bind(R.id.long_text)
    TextView longTextView;
    @Bind(R.id.map_ly)
    LinearLayout mapLy;
    @Bind(R.id.map_imgview)
    ImageView mapbtn;

    boolean flag;
    PickingDataHelper mDataHelper;
    List<EditText> ls;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contextView = inflater.inflate(R.layout.fragment_picking,
                container, false);
        ButterKnife.bind(this, contextView);
        setMpView(mMapView);
        setLatTextView(latTextView);
        setLocTextView(locTextView);
        setLongTextView(longTextView);
        setMapLy(mapLy);
        setMap_imgview(mapbtn);
        ls = new ArrayList<>();
        ls.add(pick);
        return contextView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataHelper = new PickingDataHelper(getActivity());
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
            Picking pc = new Picking();
            pc.setPick(pick.getText().toString());
            pc.setTime(Calendar.getInstance().getTimeInMillis());
            pc.setLatitude(latitude);
            pc.setLongtitude(longtitude);
            pc.setLocation(location);
            mDataHelper.insert_(pc);
            disableEditText(ls);
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            flag = true;
            return InputType.INPUT_SAVE_OK;
        }
        return InputType.INPUT_EMPTY;
    }
}

