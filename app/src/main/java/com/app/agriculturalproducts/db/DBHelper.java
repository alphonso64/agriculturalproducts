package com.app.agriculturalproducts.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/7.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "data";
    private static final int DB_NEW_VERSION = 2;
    private static final int DB_BASE_VERSION = 1;
    private String name;

    public DBHelper(Context context,String name) {
        super(context, DATABASE_NAME+name+".db", null, DB_BASE_VERSION);
        Log.e("testcc", DATABASE_NAME + name + ".db");
        this.name = name;
    }

    static {
        cupboard().register(Task.class);
        cupboard().register(Field.class);
        cupboard().register(PlanterRecord.class);
        cupboard().register(FertilizerRecord.class);
        cupboard().register(PreventionRecord.class);
        cupboard().register(PickRecord.class);
        cupboard().register(OtherRecord.class);
//        cupboard().register(PersticidesUsage.class);
//        cupboard().register(FertilizerUsage.class);
//        cupboard().register(PlantSpecies.class);
//        cupboard().register(Picking.class);
//        cupboard().register(OtherInfo.class);
//        cupboard().register(FieldInfo.class);
    }

        @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }



//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        for (int i = DB_BASE_VERSION; i <= DB_NEW_VERSION; i++) {
//            updateDb(db, i);
//        }
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        for (int i = oldVersion + 1; i <= newVersion; i++) {
//            updateDb(db, i);
//        }
//    }
//
//    private void updateDb(SQLiteDatabase db, int version) {
//        switch (version) {
//            case 1:
//                TaskDataHelper.ItemsDBInfo.TABLE.create(db);//Demo列表
//                Log.e("testbb", TaskDataHelper.ItemsDBInfo.TABLE.toString());
//                break;
//        }
//    }

}
