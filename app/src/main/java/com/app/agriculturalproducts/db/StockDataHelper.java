package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;


import com.app.agriculturalproducts.bean.PersonalStock;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class StockDataHelper extends BaseDataHelper implements DBInterface<PersonalStock> {

    public StockDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "PersonalStock";
    @Override
    protected Uri getContentUri() {
        return DataProvider.STOCK_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<PersonalStock> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (PersonalStock item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(PersonalStock data) {
        ContentValues values = cupboard().withEntity(PersonalStock.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(PersonalStock data){
        Log.e("tetstbb", "insert_");
        ContentValues values = cupboard().withEntity(PersonalStock.class).toContentValues(data);
        insert(values);
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

    public Cursor getCursor() {
        return query(getContentUri(), null, null, null, null);
    }

    public Cursor getCursorSeed() {
        return query(getContentUri(),null, "type=?",new String[]{PersonalStock.SEED_TYPE}, null);
    }

    public Cursor getCursorFertilizer() {
        return query(getContentUri(),null, "type=?",new String[]{PersonalStock.F_TYPE}, null);
    }

    public Cursor getCursorPrevention() {
        return query(getContentUri(),null, "type=?",new String[]{PersonalStock.P_TYPE}, null);
    }

    public void updateByID(ContentValues values,String id){
        update(values, "personalstock_id = ?", new String[]{id});
    }

}
