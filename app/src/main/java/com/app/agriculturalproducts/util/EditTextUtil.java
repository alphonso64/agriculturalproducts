package com.app.agriculturalproducts.util;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.List;

/**
 * Created by ALPHONSO on 2016/1/11.
 */
public class EditTextUtil {
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
}
