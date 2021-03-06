package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import com.app.agriculturalproducts.StockDetailActivity;
import com.app.agriculturalproducts.bean.PersonalStockDetail;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class StockDetailDataHelper extends BaseDataHelper implements DBInterface<PersonalStockDetail> {

    public StockDetailDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "PersonalStockDetail";
    @Override
    protected Uri getContentUri() {
        return DataProvider.STOCK_DETAIL_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<PersonalStockDetail> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (PersonalStockDetail item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(PersonalStockDetail data) {
        ContentValues values = cupboard().withEntity(PersonalStockDetail.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where, selectionArgs);
    }

    public void insert_(PersonalStockDetail data){
        ContentValues values = cupboard().withEntity(PersonalStockDetail.class).toContentValues(data);
        insert(values);
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

    public Cursor getCursor() {
        return query(getContentUri(), null, null, null, null);
    }

    public CursorLoader getEnterCursorLoader(int cmd) {
//        return new CursorLoader(getContext(), getContentUri(), null, "type=? and (personalstockdetail_goods_type = ? or personalstockdetail_goods_type = ?)",new String[]{PersonalStockDetail.ENTER_TYPE,"农药","种子"}, null);
        if(cmd == StockDetailActivity.ENTER_CMD_ALL){
            return new CursorLoader(getContext(), getContentUri(), null, "type=? ",new String[]{PersonalStockDetail.ENTER_TYPE}, null);
        }else if(cmd == StockDetailActivity.ENTER_CMD_SEED){
            return new CursorLoader(getContext(), getContentUri(), null, "type=? and personalstockdetail_goods_type = ?",new String[]{PersonalStockDetail.ENTER_TYPE,"种子"}, null);
        }else if(cmd == StockDetailActivity.ENTER_CMD_F){
            return new CursorLoader(getContext(), getContentUri(), null, "type=? and personalstockdetail_goods_type = ?",new String[]{PersonalStockDetail.ENTER_TYPE,"化肥"}, null);
        }else if(cmd == StockDetailActivity.ENTER_CMD_P){
            return new CursorLoader(getContext(), getContentUri(), null, "type=? and (personalstockdetail_goods_type = ? or personalstockdetail_goods_type = ?)",new String[]{PersonalStockDetail.ENTER_TYPE,"农药","高毒高残"}, null);
        }
        return  null;
    }


    public CursorLoader getOutCursorLoader(int cmd) {
        if(cmd == StockDetailActivity.OUT_CMD_ALL){
            return new CursorLoader(getContext(), getContentUri(), null, "type=? ",new String[]{PersonalStockDetail.OUT_TYPE}, null);
        }else if(cmd == StockDetailActivity.OUT_CMD_SEED){
            return new CursorLoader(getContext(), getContentUri(), null, "type=? and personalstockdetail_goods_type = ?",new String[]{PersonalStockDetail.OUT_TYPE,"种子"}, null);
        }else if(cmd == StockDetailActivity.OUT_CMD_F){
            return new CursorLoader(getContext(), getContentUri(), null, "type=? and personalstockdetail_goods_type = ?",new String[]{PersonalStockDetail.OUT_TYPE,"化肥"}, null);
        }else if(cmd == StockDetailActivity.OUT_CMD_P){
            return new CursorLoader(getContext(), getContentUri(), null, "type=? and (personalstockdetail_goods_type = ? or personalstockdetail_goods_type = ?)",new String[]{PersonalStockDetail.OUT_TYPE,"农药","高毒高残"}, null);
        }
        return  null;
    }

//    public void updateByID(ContentValues values,String id){
//        int val = update(values, "personalstockdetail_id = ?", new String[]{id});
//        Log.e("testcc", "updateByID:" + val);
//        if(val==0){
//            insert(values);
//        }
//    }

    public void replace(List<PersonalStockDetail> listData){
        delete(null, null);
        bulkInsert(listData);
    }

}
