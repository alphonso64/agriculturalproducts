package com.app.agriculturalproducts.db;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;

import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.FertilizerUsage;
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.OtherInfo;
import com.app.agriculturalproducts.bean.OtherRecord;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.Picking;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.util.InputType;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class DataProvider extends ContentProvider {
    public static final Object obj = new Object();
    public static final String AUTHORITY = "com.app.agriculturalproducts";
    public static final String SCHEME = "content://";

    private static final int TASK_TABLE = 0;
    private static final int TASK_Detail_TABLE = 100;
    private static final int TASK_Done_TABLE = 101;
    private static final int PUSAGE_TABLE  = 1;
    private static final int FUSAGE_TABLE  = 2;
    private static final int PLANT_TABLE = 3;
    private static final int PICK_TABLE = 4;
    private static final int OTHERINFO_TABLE = 5;
    private static final int FIELD_TABLE = 6;

    public static final String PATH_TASK_TABLE = "/task";
    public static final String PATH_TASK_DETAIL_TABLE = "/taskd";
    public static final String PATH_TASK_DONE_TABLE = "/taskud";
    public static final String PATH_PUSAGE_TABLE = "/preventionrecord";
    public static final String PATH_FUSAGE_TABLE = "/fertilizerrecord";
    public static final String PATH_PLANT_TABLE =  "/planterrecord";
    public static final String PATH_PICK_TABLE = "/pickrecord";
    public static final String PATH_OTHER_TABLE =  "/otherrecord";
    public static final String PATH_FIELD_TABLE =  "/field";

    public static final Uri TASK_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_TASK_TABLE);
    public static final Uri TASK_DETAIL_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_TASK_DETAIL_TABLE);
    public static final Uri TASK_DONE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_TASK_DONE_TABLE);
    public static final Uri PUSAGE_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_PUSAGE_TABLE);
    public static final Uri FUSAGE_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_FUSAGE_TABLE);
    public static final Uri PLANT_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_PLANT_TABLE);
    public static final Uri PICK_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_PICK_TABLE);
    public static final Uri OTHER_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_OTHER_TABLE);
    public static final Uri FILED_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_FIELD_TABLE);


    public static final String TASK_TABLE_CONTENT_TYPE = "com.task";
    public static final String TASK_DETAIL_TABLE_CONTENT_TYPE = "com.task.detail";
    public static final String TASK_DONE_TABLE_CONTENT_TYPE = "com.task.done";
    public static final String PUSAGE_TABLE_CONTENT_TYPE = "com.pusage";
    public static final String FUSAGE_TABLE_CONTENT_TYPE = "com.fusage";
    public static final String PLANT_TABLE_CONTENT_TYPE = "com.plant";
    public static final String PICK_TABLE_CONTENT_TYPE = "com.pick";
    public static final String OTHER_TABLE_CONTENT_TYPE = "com.other";
    public static final String FIELD_TABLE_CONTENT_TYPE = "com.field";
    private static DBHelper mDBHelper;

    private static final UriMatcher sUriMATCHER = new UriMatcher(UriMatcher.NO_MATCH) {{
        addURI(AUTHORITY, "task", TASK_TABLE);//Demo列表
        addURI(AUTHORITY,"preventionrecord",PUSAGE_TABLE);
        addURI(AUTHORITY,"fertilizerrecord",FUSAGE_TABLE);
        addURI(AUTHORITY,"planterrecord",PLANT_TABLE);
        addURI(AUTHORITY,"pickrecord",PICK_TABLE);
        addURI(AUTHORITY,"otherrecord",OTHERINFO_TABLE);
        addURI(AUTHORITY,"field",FIELD_TABLE);
        addURI(AUTHORITY,"taskd",TASK_Detail_TABLE);
        addURI(AUTHORITY,"taskud",TASK_Done_TABLE);
    }};

    public static DBHelper getDBHelper() {
        if (mDBHelper == null) {
            SharedPreferences sp = AppApplication.getContext().getSharedPreferences(InputType.loginInfoDB,
                    Activity.MODE_PRIVATE);
            mDBHelper = new DBHelper(AppApplication.getContext(),sp.getString("name","null"));
        }

        return mDBHelper;
    }

    public static void resetDBHelper(){
        SharedPreferences sp = AppApplication.getContext().getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        mDBHelper = new DBHelper(AppApplication.getContext(),sp.getString("name","null"));
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        synchronized (obj) {
            Cursor cursor;
            SQLiteDatabase db = getDBHelper().getReadableDatabase();
            Log.e("testbb","query + "+uri);
            switch (sUriMATCHER.match(uri)) {
                case TASK_TABLE://Demo列表
                    cursor =  cupboard().withDatabase(db).query(Task.class).
                            withProjection(projection).
                            withSelection("isDone = ?", "false").
                            orderBy("_id DESC").
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case TASK_Detail_TABLE://Demo列表
                    cursor =  cupboard().withDatabase(db).query(Task.class).
                            withProjection(projection).
                            withSelection(selection).
                            orderBy("_id DESC").
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case TASK_Done_TABLE://Demo列表
                    cursor =  cupboard().withDatabase(db).query(Task.class).
                            withProjection(projection).
                            withSelection("isDone = ?", "true").
                            orderBy("_id DESC").
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case PUSAGE_TABLE:
                    cursor=  cupboard().withDatabase(db).query(PreventionRecord.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case FUSAGE_TABLE:
                    cursor=  cupboard().withDatabase(db).query(FertilizerRecord.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case PLANT_TABLE:
                    cursor=  cupboard().withDatabase(db).query(PlanterRecord.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case PICK_TABLE:
                    cursor=  cupboard().withDatabase(db).query(PickRecord.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case OTHERINFO_TABLE:
                    cursor=  cupboard().withDatabase(db).query(OtherRecord.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case FIELD_TABLE:
                    cursor=  cupboard().withDatabase(db).query(Field.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy(sortOrder).
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown Uri" + uri);
            }
            return cursor;
        }
    }



    private String matchTable(Uri uri) {
        String table;
        switch (sUriMATCHER.match(uri)) {
            case TASK_TABLE://Demo列表
                table = TaskDataHelper.TABLE_NAME;
                break;
            case TASK_Detail_TABLE://Demo列表
                table = TaskDataHelper.TABLE_NAME;
                break;
            case TASK_Done_TABLE://Demo列表
                table = TaskDataHelper.TABLE_NAME;
                break;
            case PUSAGE_TABLE:
                table = PersticidesUsageDataHelper.TABLE_NAME;
                break;
            case FUSAGE_TABLE:
                table = FertilizerUsageDataHelper.TABLE_NAME;
                break;
            case PLANT_TABLE:
                table = PlantSpeciesDataHelper.TABLE_NAME;
                break;
            case PICK_TABLE:
                table = PickingDataHelper.TABLE_NAME;
                break;
            case OTHERINFO_TABLE:
                table = OtherInfoDataHelper.TABLE_NAME;
                break;
            case FIELD_TABLE:
                table = FieldDataHelper.TABLE_NAME;
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
            case PUSAGE_TABLE://Demo列表
                return PUSAGE_TABLE_CONTENT_TYPE;
            case FUSAGE_TABLE://Demo列表
                return FUSAGE_TABLE_CONTENT_TYPE;
            case PLANT_TABLE://Demo列表
                return PLANT_TABLE_CONTENT_TYPE;
            case PICK_TABLE://Demo列表
                return PICK_TABLE_CONTENT_TYPE;
            case OTHERINFO_TABLE://Demo列表
                return OTHER_TABLE_CONTENT_TYPE;
            case FIELD_TABLE://Demo列表
                return FIELD_TABLE_CONTENT_TYPE;
            case TASK_Detail_TABLE://Demo列表
                return TASK_DETAIL_TABLE_CONTENT_TYPE;
            case TASK_Done_TABLE://Demo列表
                return TASK_DONE_TABLE_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e("testbbb","insert1:"+uri.toString());
        synchronized (obj) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            long rowId = 0;
            db.beginTransaction();
            try {
                rowId = db.insert(matchTable(uri), null, values);
                db.setTransactionSuccessful();
                Log.e("testbbb", "insert2:" + uri.toString());
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
                    Log.e("testcc","bulkInsert:"+uri.toString());
                    Log.e("testcc","bulkInsert:"+matchTable(uri));
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
//            DBHelper mDBHelper = DataProvider.getDBHelper();
//            SQLiteDatabase db = mDBHelper.getWritableDatabase();
//            db.delete(TaskDataHelper.TABLE_NAME, null, null);
        }
    }
}
