package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.app.agriculturalproducts.bean.OtherRecord;
import com.app.agriculturalproducts.bean.PreventionRecord;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class OtherInfoDataHelper extends BaseDataHelper implements DBInterface<OtherRecord> {

    public OtherInfoDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "OtherRecord";
    @Override
    protected Uri getContentUri() {
        return DataProvider.OTHER_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<OtherRecord> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (OtherRecord item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(OtherRecord data) {
        ContentValues values = cupboard().withEntity(OtherRecord.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(OtherRecord data){
        ContentValues values = cupboard().withEntity(OtherRecord.class).toContentValues(data);
        insert(values);
    }
    public void updateByID(ContentValues values,String id){
        update(values, "_id = ?", new String[]{id});
    }

    public void deleteByID(String id){
        delete("_id = ?", new String[]{id});
    }

    public void repalceInfo(List<OtherRecord> listData){
        delete_("saved = ?", new String[]{"yes"});
       // delete(null,null);
        bulkInsert(listData);
    }

    public void updatePlantIDByID(String plantID,String id){
        ContentValues values = new ContentValues();
        values.put("local_plant_id", plantID);
        update(values, "local_plant_table_index = ?", new String[]{id});
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, "_id desc");
    }

}
