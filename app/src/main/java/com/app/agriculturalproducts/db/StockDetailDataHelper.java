package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;


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
        delete(where,selectionArgs);
    }

    public void insert_(PersonalStockDetail data){
        Log.e("tetstbb", "insert_");
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

    public void updateByID(ContentValues values,String id){
        update(values, "personalstockdetail_id = ?", new String[]{id});
    }

}
