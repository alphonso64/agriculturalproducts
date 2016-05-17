package com.app.agriculturalproducts.util;

import android.content.ContentValues;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/5/16.
 */
public class ResultCheck {
    public static void  savedPlantIDtoDB(String pid,String id,Context ctx){
        FertilizerUsageDataHelper fertilizerUsageDataHelper = new FertilizerUsageDataHelper(ctx);
        fertilizerUsageDataHelper.updatePlantIDByID(pid,id);
        PersticidesUsageDataHelper persticidesUsageDataHelper = new PersticidesUsageDataHelper(ctx);
        persticidesUsageDataHelper.updatePlantIDByID(pid,id);
        PickingDataHelper pickingDataHelper = new PickingDataHelper(ctx);
        pickingDataHelper.updatePlantIDByID(pid,id);
        OtherInfoDataHelper otherInfoDataHelper = new OtherInfoDataHelper(ctx);
        otherInfoDataHelper.updatePlantIDByID(pid,id);
    }

    public static  boolean checkSaved(String id){
        if(id==null ||id.length() == 0){
            return false;
        }
        return true;
    }
}
