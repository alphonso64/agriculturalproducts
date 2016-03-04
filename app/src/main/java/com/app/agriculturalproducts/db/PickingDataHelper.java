package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.app.agriculturalproducts.bean.FertilizerUsage;
import com.app.agriculturalproducts.bean.PickRecord;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class PickingDataHelper extends BaseDataHelper implements DBInterface<PickRecord> {

    public PickingDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "PickRecord";
    @Override
    protected Uri getContentUri() {
        return DataProvider.PICK_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<PickRecord> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (PickRecord item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(PickRecord data) {
        ContentValues values = cupboard().withEntity(PickRecord.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(PickRecord data){
        ContentValues values = cupboard().withEntity(PickRecord.class).toContentValues(data);
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
