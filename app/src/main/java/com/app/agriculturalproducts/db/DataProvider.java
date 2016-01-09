package com.app.agriculturalproducts.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;

import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.Task;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class DataProvider extends ContentProvider {
    public static final Object obj = new Object();
    public static final String AUTHORITY = "com.app.agriculturalproducts";
    public static final String SCHEME = "content://";

    private static final int TASK_TABLE = 0;//Demo列表
    private static final int PUSAGE_TABLE  = 1;//Demo列表

    public static final String PATH_TASK_TABLE = "/task";//Demo列表
    public static final String PATH_PUSAGE_TABLE = "/persticidesusage";//Demo列表

    public static final Uri TASK_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_TASK_TABLE);//Demo列表
    public static final Uri PUSAGE_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_PUSAGE_TABLE);//Demo列表

    public static final String TASK_TABLE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.frankzhu.all.items";//Demo列表
    public static final String PUSAGE_TABLE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.frankzhu.all.pusage";//Demo列表

    private static DBHelper mDBHelper;

    public static DBHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(AppApplication.getContext());
        }
        return mDBHelper;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        synchronized (obj) {
//            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
//            queryBuilder.setTables(matchTable(uri));
//
//            SQLiteDatabase db = getDBHelper().getReadableDatabase();
//            Cursor cursor = queryBuilder.query(db,
//                    projection,
//                    selection,
//                    selectionArgs,
//                    null,
//                    null,
//                    sortOrder);
            Cursor cursor;
            SQLiteDatabase db = getDBHelper().getReadableDatabase();
            Log.e("testbb","query");
            switch (sUriMATCHER.match(uri)) {
                case TASK_TABLE://Demo列表
                    cursor =  cupboard().withDatabase(db).query(Task.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case PUSAGE_TABLE:
                    cursor=  cupboard().withDatabase(db).query(PersticidesUsage.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy("time DESC").
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown Uri" + uri);
            }
            return cursor;
        }
    }

    private static final UriMatcher sUriMATCHER = new UriMatcher(UriMatcher.NO_MATCH) {{
        addURI(AUTHORITY, "task", TASK_TABLE);//Demo列表
        addURI(AUTHORITY,"persticidesusage",PUSAGE_TABLE);

    }};

    private String matchTable(Uri uri) {
        String table;
        switch (sUriMATCHER.match(uri)) {
            case TASK_TABLE://Demo列表
                table = TaskDataHelper.TABLE_NAME;
                break;
            case PUSAGE_TABLE:
                table = PersticidesUsageDataHelper.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri);
        }
        return table;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMATCHER.match(uri)) {
            case TASK_TABLE://Demo列表
                return TASK_TABLE_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        synchronized (obj) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            long rowId = 0;
            db.beginTransaction();
            try {
                rowId = db.insert(matchTable(uri), null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            if (rowId > 0) {
                Uri returnUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public int bulkInsert(Uri uri, @NonNull ContentValues[] values) {
        synchronized (obj) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            db.beginTransaction();
            try {
                for (ContentValues contentValues : values) {
                    db.insertWithOnConflict(matchTable(uri), BaseColumns._ID, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                }
                db.setTransactionSuccessful();
                getContext().getContentResolver().notifyChange(uri, null);
                return values.length;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        synchronized (obj) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            int count = 0;
            db.beginTransaction();
            try {
                count = db.delete(matchTable(uri), selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        synchronized (obj) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            int count;
            db.beginTransaction();
            try {
                count = db.update(matchTable(uri), values, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }

    public static void clearDBCache() {
        synchronized (DataProvider.obj) {
            DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            db.delete(TaskDataHelper.TABLE_NAME, null, null);
        }
    }
}
