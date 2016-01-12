package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.app.agriculturalproducts.bean.FertilizerUsage;
import com.app.agriculturalproducts.bean.Picking;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class PickingDataHelper extends BaseDataHelper implements DBInterface<Picking> {

    public PickingDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "Picking";
    @Override
    protected Uri getContentUri() {
        return DataProvider.PICK_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<Picking> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (Picking item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(Picking data) {
        ContentValues values = cupboard().withEntity(Picking.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(Picking data){
        ContentValues values = cupboard().withEntity(Picking.class).toContentValues(data);
        insert(values);
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

}
