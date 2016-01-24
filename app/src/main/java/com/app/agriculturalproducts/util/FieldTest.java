package com.app.agriculturalproducts.util;

import android.content.Context;

import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.db.FieldDataHelper;

/**
 * Created by aa on 2016/1/24.
 */
public class FieldTest {
    private Context context;
    private FieldDataHelper fieldDataHelper ;

    public FieldTest(Context context) {
        this.context = context;
        fieldDataHelper = new FieldDataHelper(context);
    }

    public void test(){
        for(int i=0;i<5;i++){
            insertFiled();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertFiled(){
        FieldInfo filed = new FieldInfo();
        long val = System.currentTimeMillis();
        filed.setFiled("地块"+val);
        filed.setInfo("地块详细信息"+val);
        filed.setSeed("种子信息"+val);
        filed.setSource("来源"+val);
        filed.setSpec("归资格信息"+val);
        filed.setSpecies("种植种类信息"+val);
        fieldDataHelper.insert_(filed);
    }
}
