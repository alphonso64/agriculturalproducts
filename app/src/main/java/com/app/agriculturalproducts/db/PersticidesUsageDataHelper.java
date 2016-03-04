package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;


import com.app.agriculturalproducts.bean.PreventionRecord;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class PersticidesUsageDataHelper extends BaseDataHelper implements DBInterface<PreventionRecord> {

    public PersticidesUsageDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "PreventionRecord";
    @Override
    protected Uri getContentUri() {
        return DataProvider.PUSAGE_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<PreventionRecord> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (PreventionRecord item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(PreventionRecord data) {
        ContentValues values = cupboard().withEntity(PreventionRecord.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(PreventionRecord data){
        Log.e("tetstbb", "insert_");
        ContentValues values = cupboard().withEntity(PreventionRecord.class).toContentValues(data);
        insert(values);
    }
    public void updateByID(ContentValues values,String id){
        update(values, "_id = ?", new String[]{id});
    }

    public void deleteByID(String id){
        delete("_id = ?", new String[]{id});
    }


    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, "_id desc");
    }

}
