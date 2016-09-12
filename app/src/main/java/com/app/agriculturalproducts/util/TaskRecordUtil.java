package com.app.agriculturalproducts.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.model.UnuploadTaskModel;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/5/13.
 */
public class TaskRecordUtil {
    public static void recordLocalDoneTask(Context ctx,TaskDataHelper taskDataHelper,TaskRecord task){
        task.setSync("false");
        ContentValues values = cupboard().withEntity(TaskRecord.class).toContentValues(task);
        taskDataHelper.updateTask(values, String.valueOf(task.getWorktasklist_id()));
        UnuploadTaskModel taskmodel = new UnuploadTaskModel(ctx);
        taskmodel.setUnuploadTask(task);
    }

    public static void removeLocalDoneTask(final Context ctx, final TaskDataHelper taskDataHelper,String id){
        final TaskRecord task = taskDataHelper.queryTaskRecord(id);
        task.setWorktasklist_status("已完成");
        HttpClient.getInstance(ctx).uploadTask(new HttpListener<String>() {
            @Override
            public void onSuccess(String s, Response<String> response) {
                task.setSync("true");
                ContentValues values = cupboard().withEntity(TaskRecord.class).toContentValues(task);
                taskDataHelper.updateTask(values, String.valueOf(task.getWorktasklist_id()));
                UnuploadTaskModel taskmodel = new UnuploadTaskModel(ctx);
                taskmodel.removeUnuploadTask(task);
            }
        }, task);
    }

    public static void removeLocalUnDoneTask(final Context ctx, final TaskDataHelper taskDataHelper,String id){
        final TaskRecord task = taskDataHelper.queryTaskRecord(id);
        task.setSync("true");
        ContentValues values = cupboard().withEntity(TaskRecord.class).toContentValues(task);
        taskDataHelper.updateTask(values, String.valueOf(task.getWorktasklist_id()));

        UnuploadTaskModel taskmodel = new UnuploadTaskModel(ctx);
        taskmodel.removeUnuploadTask(task);
    }
    public static void cleatLocalList(final Context ctx){
        UnuploadTaskModel taskmodel = new UnuploadTaskModel(ctx);
        taskmodel.clear();
    }

}
