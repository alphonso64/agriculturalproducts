package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.app.agriculturalproducts.bean.FieldInfo;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class FieldDataHelper extends BaseDataHelper implements DBInterface<FieldInfo> {

    public FieldDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "FieldInfo";
    @Override
    protected Uri getContentUri() {
        return DataProvider.FILED_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<FieldInfo> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (FieldInfo item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(FieldInfo data) {
        ContentValues values = cupboard().withEntity(FieldInfo.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(FieldInfo data){
        Log.e("tetstbb", "insert_");
        ContentValues values = cupboard().withEntity(FieldInfo.class).toContentValues(data);
        insert(values);
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

    public Cursor getCursor() {
        return query(getContentUri(), null, null, null, null);
    }

}
