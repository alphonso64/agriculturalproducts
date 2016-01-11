package com.app.agriculturalproducts.bean;

import android.database.Cursor;

import com.app.agriculturalproducts.db.TaskDataHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/6.
 */
public class Task {
    private String title;
    private String detail;
    private int iconID;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public static Task fromCursor(Cursor cursor) {
//        Task task = new Task();
//        task.iconID = cursor.getInt(cursor.getColumnIndex(TaskDataHelper.ItemsDBInfo.ID));
//        task.title = cursor.getString(cursor.getColumnIndex(TaskDataHelper.ItemsDBInfo.TITLE));
//        task.detail = cursor.getString(cursor.getColumnIndex(TaskDataHelper.ItemsDBInfo.DETAIL));

        Task task = cupboard().withCursor(cursor).get(Task.class);
        return task;
    }


}
