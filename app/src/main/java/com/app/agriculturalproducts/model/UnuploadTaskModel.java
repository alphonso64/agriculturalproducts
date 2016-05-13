package com.app.agriculturalproducts.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.util.InputType;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by aa on 2016/5/12.
 */
public class UnuploadTaskModel {
    private Context context;

    public UnuploadTaskModel(Context context) {
        this.context = context;
    }

    public HashMap<String,String> getUnuploadTask() {
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        HashMap<String,String> map = null;
        if(name != null){
            String path = name+"__"+InputType.unUploadDB;
            SharedPreferences prefs = context.getSharedPreferences(path, Context.MODE_PRIVATE);
            map = (HashMap<String, String>) prefs.getAll();
        }
        return map;
    }

    public void setUnuploadTask(TaskRecord record) {
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        if(name != null){
            String path = name+"__"+InputType.unUploadDB;
            SharedPreferences prefs = context.getSharedPreferences(path, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(record.getWorktasklist_id()," ");
            editor.commit();
        }
    }
    public void removeUnuploadTask(TaskRecord record) {
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        if(name != null){
            String path = name+"__"+InputType.unUploadDB;
            SharedPreferences prefs = context.getSharedPreferences(path, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(record.getWorktasklist_id());
            editor.commit();
        }
    }

    public void removeUnuploadTask(String  id) {
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        if(name != null){
            String path = name+"__"+InputType.unUploadDB;
            SharedPreferences prefs = context.getSharedPreferences(path, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(id);
            editor.commit();
        }
    }

    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(InputType.loginInfoDB,
                Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        if(name != null){
            String path = name+"__"+InputType.unUploadDB;
            SharedPreferences prefs = context.getSharedPreferences(path, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
        }
    }
}
