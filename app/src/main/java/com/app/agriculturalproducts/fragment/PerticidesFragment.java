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

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.util.InputType;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PerticidesFragment extends Fragment {

    @Bind(R.id.persticides_name_text)
    EditText name;
    @Bind(R.id.usage_text)
    EditText usage;
    @Bind(R.id.remarks_text)
    EditText remark;
    boolean flag;
    private PersticidesUsageDataHelper mDataHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_persticides,
                container, false);
        ButterKnife.bind(this, contextView);
        return contextView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataHelper = new PersticidesUsageDataHelper(getActivity());
        if(flag){
            disableEditText();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setSavedFlag(boolean flag){
        this.flag = flag;
    }

    private boolean isEditEmpty(){
        if(TextUtils.isEmpty(name.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(usage.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(remark.getText().toString().trim())){
            return true;
        }
        return false;
    }

     public void disableEditText(){
        name.setFocusable(false);
        usage.setFocusable(false);
        remark.setFocusable(false);
    }

    public int upload(){
        if(flag){
            return InputType.INPUT_SAVE_ALREADY;
        }
        if(!isEditEmpty()){
            PersticidesUsage pu = new PersticidesUsage();
            pu.setName(name.getText().toString());
            pu.setRemarks(remark.getText().toString());
            pu.setUsage(usage.getText().toString());
            pu.setTime(Calendar.getInstance().getTimeInMillis());
            mDataHelper.insert_(pu);
            disableEditText();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            flag = true;
            return InputType.INPUT_SAVE_OK;
        }
        return InputType.INPUT_EMPTY;
    }

}

