package com.app.agriculturalproducts.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.PersonInfoActivity;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.model.EmployeeInfoModel;
import com.app.agriculturalproducts.util.InputType;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class MineFragment extends Fragment {
    @Bind(R.id.personImgView)
    CircleImageView imgView;
    @Bind(R.id.person_name)
    TextView nameText;
    @Bind(R.id.coop_name)
    TextView coopText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_mine,
                container, false);
        ButterKnife.bind(this, contextView);

        Log.e("testbbb", "Mine onCreateView");
        return contextView;
    }

    @Override
    public void onResume() {
        Log.e("testbbb", "Mine resume");
        setPersonINfo();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.quit_layout)
    void quitClick(){
        new MaterialDialog.Builder(getActivity())
                .title("确认退出当前账号吗")
                .positiveText("确定").negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                SharedPreferences sp = getActivity().getSharedPreferences(InputType.loginInfoDB,
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("isLogin",InputType.INPUT_CHECK_ERR);
                ed.commit();
                getActivity().finish();
            }
        }).show();
    }

    @OnClick(R.id.person_info_layout)
    void infoClick(){
        Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
        startActivity(intent);
    }

    void  setPersonINfo(){
        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(getActivity());
        EmployeeInfo employeeInfo = employeeInfoModel.getEmployeeInfo();
        nameText.setText(employeeInfo.getEmployee_name());
        coopText.setText(employeeInfo.getMember_name());
        String path = employeeInfo.getPath();
        if(path!=null){
            Bitmap picture = BitmapFactory.decodeFile(path);
            imgView.setImageBitmap(picture);
        }
    }

}
