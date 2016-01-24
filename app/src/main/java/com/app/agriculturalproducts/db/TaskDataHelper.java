package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;

import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.database.Column;
import com.app.agriculturalproducts.db.database.SQLiteTable;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class TaskDataHelper extends BaseDataHelper implements DBInterface<Task> {

    public TaskDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "task";
    @Override
    protected Uri getContentUri() {
        return DataProvider.TASK_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<Task> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (Task item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(Task data) {
//        ContentValues values = new ContentValues();
//        values.put(ItemsDBInfo.ID, data.getIconID());
//        values.put(ItemsDBInfo.TITLE, data.getTitle());
//        values.put(ItemsDBInfo.DETAIL, data.getDetail());
        ContentValues values = cupboard().withEntity(Task.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where,selectionArgs);
    }

    public void insert_(Task data){
        ContentValues values = cupboard().withEntity(Task.class).toContentValues(data);
        insert(values);
    }

    public void updateTask(ContentValues values,String id){
        update(values, "_id = ?", new String[]{id});
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

    public CursorLoader getDetailCursorLoader() {
        return new CursorLoader(getContext(), DataProvider.TASK_DETAIL_CONTENT_URI, null, null, null, null);
    }

    public CursorLoader getDoneCursorLoader() {
        return new CursorLoader(getContext(), DataProvider.TASK_DONE_CONTENT_URI, null, null, null, null);
    }

}
