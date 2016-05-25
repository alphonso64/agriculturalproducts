package com.app.agriculturalproducts.fragment;

import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.util.InputType;

import java.util.Objects;

/**
 * Created by ALPHONSO on 2016/1/22.
 */
public class BaseUploadFragment extends Fragment implements Upload {
    public Object object;
    @Override
    public int upload() {
        return InputType.INPUT_UPLOAD_OK;
    }

    @Override
    public int save() {
        return InputType.INPUT_SAVE_OK;
    }

    public void uploadClick(){
        int val = save();
        if(val == InputType.INPUT_EMPTY){
            new MaterialDialog.Builder(getActivity())
                    .title("内容不能为空！")
                    .positiveText("好的")
                    .show();
        }else if(val == InputType.INPUT_SAVE_OK){
            new MaterialDialog.Builder(getActivity())
                    .title("保存成功！")
                    .positiveText("好的")
                    .show();
        }else if(val == InputType.INPUT_SAVE_ALREADY){
            new MaterialDialog.Builder(getActivity())
                    .title("已经保存！")
                    .positiveText("好的")
                    .show();
        }
    }

    public void saveOK(){
        new MaterialDialog.Builder(getActivity())
                .title("保存成功！")
                .positiveText("好的")
                .show();
    }

}
