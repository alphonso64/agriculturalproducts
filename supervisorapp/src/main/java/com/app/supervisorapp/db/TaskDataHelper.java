package com.app.supervisorapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;


import com.app.supervisorapp.bean.TaskRecord;

import java.util.ArrayList;
import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class TaskDataHelper extends BaseDataHelper implements DBInterface<TaskRecord> {

    public TaskDataHelper(Context context) {
        super(context);
    }

    public static final String TABLE_NAME = "TaskRecord";
    @Override
    protected Uri getContentUri() {
        return DataProvider.TASK_TABLE_CONTENT_URI;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void bulkInsert(List<TaskRecord> listData) {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (TaskRecord item : listData) {
            ContentValues values = getContentValues(item);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    @Override
    public ContentValues getContentValues(TaskRecord data) {
        ContentValues values = cupboard().withEntity(TaskRecord.class).toContentValues(data);
        return values;
    }

    public void delete_(String where, String[] selectionArgs ){
        delete(where, selectionArgs);
    }

    public void insert_(TaskRecord data){
        Log.e("testcc","insert_");
        ContentValues values = cupboard().withEntity(TaskRecord.class).toContentValues(data);
        insert(values);
    }

    public void updateTask(ContentValues values,String id){
        update(values, "worktasklist_id = ?", new String[]{id});
    }

    public TaskRecord queryTaskRecord(String id){
        Cursor cursor =  query(null,"worktasklist_id = ?", new String[]{id},null);
        if(cursor.moveToPosition(0)){
            TaskRecord task = TaskRecord.fromCursor(cursor);
            return  task;
        }
        return null;
    }

    public void updateSyncTask(String id){
        Cursor cursor =  query(null,"worktasklist_id = ?", new String[]{id},null);
        if(cursor.moveToPosition(0)){
            TaskRecord task = TaskRecord.fromCursor(cursor);
            task.setSync("true");
            task.setWorktasklist_status("已完成");
            ContentValues values = cupboard().withEntity(TaskRecord.class).toContentValues(task);
            update(values, "worktasklist_id = ?", new String[]{id});
        }
    }

    public void replace(List<TaskRecord> listData){
        delete(null, null);
        bulkInsert(listData);
    }

    @Override
    public CursorLoader getCursorLoader() {
//        return new CursorLoader(getContext(), getContentUri(), null, "worktasklist_status!=? and sync=?",new String[]{"已完成","true"}, null);
      return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

    public CursorLoader getDetailCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, null, null, null);
    }

    public CursorLoader getDoneCursorLoader() {
        return new CursorLoader(getContext(),getContentUri(), null, "worktasklist_status=? or sync=?", new String[]{"已完成","false"}, null);
    }

}
