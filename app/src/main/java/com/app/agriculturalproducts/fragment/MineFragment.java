package com.app.agriculturalproducts.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
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
        setPersonINfo();
        return contextView;
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

    void  setPersonINfo(){
        SharedPreferences sp = getActivity().getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        nameText.setText(sp.getString("name","无"));
        coopText.setText(sp.getString("coop","无"));
        String path = sp.getString("path", null);
        if(path!=null){
            Bitmap picture = BitmapFactory.decodeFile(path);
            imgView.setImageBitmap(picture);
        }
    }

}
