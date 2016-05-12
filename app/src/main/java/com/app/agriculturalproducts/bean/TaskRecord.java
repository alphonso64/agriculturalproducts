package com.app.agriculturalproducts.bean;

import android.database.Cursor;
import android.util.Log;

import java.io.Serializable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class TaskRecord implements Serializable {
    private String worktasklist_id;
    private String worktasklist_ischeck;
    private String worktasklist_status;
    private String worktask_name;
    private String worktask_type;
    private String worktask_content;
    private String worktask_publish_people;
    private String worktask_publish_date;
    private String worktask_finish_date;
    private String employee_name;
    private String employee_card;
    private String sync;
    private Long _id;

    public void printfInfo(){
        Log.e("testcc", "worktasklist_id" + ":" + worktasklist_id
                + " " + "worktasklist_ischeck" + ":" + worktasklist_ischeck + " " + "worktasklist_status" + ":" + worktasklist_status
                + " " + "worktask_name" + ":" + worktask_name + " " + "worktask_type" + ":" + worktask_type
                + " " + "worktask_content" + ":" + worktask_content + " " + "worktask_publish_people" + ":" + worktask_publish_people
                + " " + "worktask_publish_date" + ":" + worktask_publish_date + " " + "worktask_finish_date" + ":" + worktask_finish_date
                + " " + "employee_name" + ":" + employee_name + " " + "employee_card" + ":" + employee_card
                + " " + "sync" + ":" + sync);
    }

        public static TaskRecord fromCursor(Cursor cursor) {
        TaskRecord taskRecord= cupboard().withCursor(cursor).get(TaskRecord.class);
        return taskRecord;
    }

    public String getWorktasklist_ischeck() {
        return worktasklist_ischeck;
    }

    public void setWorktasklist_ischeck(String worktasklist_ischeck) {
        this.worktasklist_ischeck = worktasklist_ischeck;
    }

    public String getWorktasklist_status() {
        return worktasklist_status;
    }

    public void setWorktasklist_status(String worktasklist_status) {
        this.worktasklist_status = worktasklist_status;
    }

    public String getWorktask_name() {
        return worktask_name;
    }

    public void setWorktask_name(String worktask_name) {
        this.worktask_name = worktask_name;
    }

    public String getWorktask_type() {
        return worktask_type;
    }

    public void setWorktask_type(String worktask_type) {
        this.worktask_type = worktask_type;
    }

    public String getWorktask_content() {
        return worktask_content;
    }

    public void setWorktask_content(String worktask_content) {
        this.worktask_content = worktask_content;
    }

    public String getWorktask_publish_people() {
        return worktask_publish_people;
    }

    public void setWorktask_publish_people(String worktask_publish_people) {
        this.worktask_publish_people = worktask_publish_people;
    }

    public String getWorktask_publish_date() {
        return worktask_publish_date;
    }

    public void setWorktask_publish_date(String worktask_publish_date) {
        this.worktask_publish_date = worktask_publish_date;
    }

    public String getWorktask_finish_date() {
        return worktask_finish_date;
    }

    public void setWorktask_finish_date(String worktask_finish_date) {
        this.worktask_finish_date = worktask_finish_date;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_card() {
        return employee_card;
    }

    public void setEmployee_card(String employee_card) {
        this.employee_card = employee_card;
    }

    public String getWorktasklist_id() {
        return worktasklist_id;
    }

    public void setWorktasklist_id(String worktasklist_id) {
        this.worktasklist_id = worktasklist_id;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }
}
