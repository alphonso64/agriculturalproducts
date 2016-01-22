package com.app.agriculturalproducts.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.util.Log;

import com.app.agriculturalproducts.app.AppApplication;
import com.app.agriculturalproducts.bean.FertilizerUsage;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.OtherInfo;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.Picking;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.Task;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class DataProvider extends ContentProvider {
    public static final Object obj = new Object();
    public static final String AUTHORITY = "com.app.agriculturalproducts";
    public static final String SCHEME = "content://";

    private static final int TASK_TABLE = 0;
    private static final int PUSAGE_TABLE  = 1;
    private static final int FUSAGE_TABLE  = 2;
    private static final int PLANT_TABLE = 3;
    private static final int PICK_TABLE = 4;
    private static final int OTHERINFO_TABLE = 5;
    private static final int FIELD_TABLE = 6;

    public static final String PATH_TASK_TABLE = "/task";
    public static final String PATH_PUSAGE_TABLE = "/persticidesusage";
    public static final String PATH_FUSAGE_TABLE = "/fertilizerusage";
    public static final String PATH_PLANT_TABLE =  "/plantspecies";
    public static final String PATH_PICK_TABLE = "/picking";
    public static final String PATH_OTHER_TABLE =  "/otherinfo";
    public static final String PATH_FIELD_TABLE =  "/fieldinfo";

    public static final Uri TASK_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_TASK_TABLE);
    public static final Uri PUSAGE_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_PUSAGE_TABLE);
    public static final Uri FUSAGE_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_FUSAGE_TABLE);
    public static final Uri PLANT_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_PLANT_TABLE);
    public static final Uri PICK_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_PICK_TABLE);
    public static final Uri OTHER_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_OTHER_TABLE);
    public static final Uri FILED_TABLE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_FIELD_TABLE);

    public static final String TASK_TABLE_CONTENT_TYPE = "com.task";
    public static final String PUSAGE_TABLE_CONTENT_TYPE = "com.pusage";
    public static final String FUSAGE_TABLE_CONTENT_TYPE = "com.fusage";
    public static final String PLANT_TABLE_CONTENT_TYPE = "com.plant";
    public static final String PICK_TABLE_CONTENT_TYPE = "com.pick";
    public static final String OTHER_TABLE_CONTENT_TYPE = "com.other";
    public static final String FIELD_TABLE_CONTENT_TYPE = "com.fieldinfo";

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
            Cursor cursor;
            SQLiteDatabase db = getDBHelper().getReadableDatabase();
            Log.e("testbb","query + "+uri);
            switch (sUriMATCHER.match(uri)) {
                case TASK_TABLE://Demo列表
                    cursor =  cupboard().withDatabase(db).query(Task.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy("time DESC").
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
                case FUSAGE_TABLE:
                    cursor=  cupboard().withDatabase(db).query(FertilizerUsage.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy("time DESC").
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case PLANT_TABLE:
                    cursor=  cupboard().withDatabase(db).query(PlantSpecies.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy("time DESC").
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case PICK_TABLE:
                    cursor=  cupboard().withDatabase(db).query(Picking.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy("time DESC").
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case OTHERINFO_TABLE:
                    cursor=  cupboard().withDatabase(db).query(OtherInfo.class).
                            withProjection(projection).
                            withSelection(selection, selectionArgs).
                            orderBy("time DESC").
                            getCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case FIELD_TABLE:
                    cursor=  cupboard().withDatabase(db).query(FieldInfo.class).
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

    private static final UriMatcher sUriMATCHER = new UriMatcher(UriMatcher.NO_MATCH) {{
        addURI(AUTHORITY, "task", TASK_TABLE);//Demo列表
        addURI(AUTHORITY,"persticidesusage",PUSAGE_TABLE);
        addURI(AUTHORITY,"fertilizerusage",FUSAGE_TABLE);
        addURI(AUTHORITY,"plantspecies",PLANT_TABLE);
        addURI(AUTHORITY,"picking",PICK_TABLE);
        addURI(AUTHORITY,"otherinfo",OTHERINFO_TABLE);
        addURI(AUTHORITY,"fieldinfo",FIELD_TABLE);
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
