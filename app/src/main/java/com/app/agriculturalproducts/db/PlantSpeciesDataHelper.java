package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.PlanterRecord;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class PlantSpeciesDataHelper extends BaseDataHelper implements DBInterface<PlanterRecord> {

    public PlantSpeciesDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "PlanterRecord";
    @Override
    protected Uri getContentUri() {
        return DataProvider.PLANT_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<PlanterRecord> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (PlanterRecord item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(PlanterRecord data) {
        ContentValues values = cupboard().withEntity(PlanterRecord.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(PlanterRecord data){
        ContentValues values = cupboard().withEntity(PlanterRecord.class).toContentValues(data);
        insert(values);
    }

    public void updateByID(ContentValues values,String id){
        update(values, "_id = ?", new String[]{id});
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

    public Cursor getCursor() {
        return query(getContentUri(), null, null, null, null);
    }

}
