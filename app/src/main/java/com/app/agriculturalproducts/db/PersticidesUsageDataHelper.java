package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.BaseDataHelper;
import com.app.agriculturalproducts.db.DBInterface;
import com.app.agriculturalproducts.db.DataProvider;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class PersticidesUsageDataHelper extends BaseDataHelper implements DBInterface<PersticidesUsage> {

    public PersticidesUsageDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "PersticidesUsage";
    @Override
    protected Uri getContentUri() {
        return DataProvider.PUSAGE_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<PersticidesUsage> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (PersticidesUsage item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(PersticidesUsage data) {
        ContentValues values = cupboard().withEntity(PersticidesUsage.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(PersticidesUsage data){
        Log.e("tetstbb", "insert_");
        ContentValues values = cupboard().withEntity(PersticidesUsage.class).toContentValues(data);
        insert(values);
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

}
