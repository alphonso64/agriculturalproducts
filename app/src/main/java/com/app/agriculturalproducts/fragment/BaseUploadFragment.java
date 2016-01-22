package com.app.agriculturalproducts.fragment;

import android.support.v4.app.Fragment;

import com.app.agriculturalproducts.util.InputType;

/**
 * Created by ALPHONSO on 2016/1/22.
 */
public class BaseUploadFragment extends Fragment implements Upload {
    @Override
    public int upload() {
        return InputType.INPUT_SAVE_OK;
    }
}
