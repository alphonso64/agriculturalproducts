package com.app.agriculturalproducts.db;

import android.content.ContentValues;
import android.support.v4.content.CursorLoader;

import java.util.List;


public interface DBInterface<T> {

    public void bulkInsert(List<T> listData);

    public ContentValues getContentValues(T data);

    public CursorLoader getCursorLoader();
}
