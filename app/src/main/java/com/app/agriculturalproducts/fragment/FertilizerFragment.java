package com.app.agriculturalproducts.fragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.FertilizerUsage;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.model.UserInfoModel;
import com.app.agriculturalproducts.util.InputType;

import java.util.ArrayList;
import java.util.Calendar;
import butterknife.Bind;
import butterknife.ButterKnife;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class FertilizerFragment extends BaseUploadFragment {
    private FieldDataHelper fieldDataHelper;
    private TaskDataHelper taskDataHelper;
    private FertilizerUsageDataHelper fertilizerUsageDataHelper;

    @Bind(R.id.f_date_img)
    ImageView dateImg;
    @Bind(R.id.f_field_img)
    ImageView fieldImg;

    @Bind(R.id.f_field_text)
    TextView field_text;
    @Bind(R.id.f_species_text)
    TextView species_text;

    @Bind(R.id.f_spec_text)
    EditText spec_text;
    @Bind(R.id.f_area_text)
    EditText area_text;
    @Bind(R.id.f_type_text)
    EditText type_text;
    @Bind(R.id.f_perticides_text)
    EditText perticides_text;
    @Bind(R.id.f_num_text)
    EditText num_text;
    @Bind(R.id.f_method_text)
    EditText method_text;
    @Bind(R.id.f_date_text)
    TextView date_text;
    @Bind(R.id.f_person_text)
    TextView person_text;

    boolean flag = false;
    boolean textInput = false;

    MaterialDialog dialog;
    Cursor cursor;
    ListAdapter adapter;
    @Override
    public int upload() {
        if(flag){
            return InputType.INPUT_SAVE_ALREADY;
        }
        if(object == null){
            if(!isEmpty() && textInput){
                saveInfo();
                flag = true;
                disableWidget();
                return InputType.INPUT_SAVE_OK;
            }
        }else{
            if(!isEmpty_()){
                saveInfo();
                flag = true;
                disableWidget();
                Task task = (Task)object;
                task.setIsDone("true");
                ContentValues values = cupboard().withEntity(Task.class).toContentValues(task);
                taskDataHelper.updateTask(values, String.valueOf(task.get_id()));
                return InputType.INPUT_SAVE_OK;
            }
        }
        return InputType.INPUT_EMPTY;
    }

    private void saveInfo(){
        FertilizerUsage fertilizerUsage = new FertilizerUsage();
        fertilizerUsage.setArea(area_text.getText().toString());
        fertilizerUsage.setField(field_text.getText().toString());
        fertilizerUsage.setDate(date_text.getText().toString());
        fertilizerUsage.setUsage(num_text.getText().toString());
        fertilizerUsage.setType(type_text.getText().toString());
        fertilizerUsage.setSpec(spec_text.getText().toString());
        fertilizerUsage.setSpecies(species_text.getText().toString());
        fertilizerUsage.setName(perticides_text.getText().toString());
        fertilizerUsage.setPerson(person_text.getText().toString());
        fertilizerUsage.setMethod(method_text.getText().toString());
        fertilizerUsageDataHelper.insert_(fertilizerUsage);
    }

    private void disableWidget(){
        num_text.setFocusable(false);
        perticides_text.setFocusable(false);
        type_text.setFocusable(false);
        spec_text.setFocusable(false);
        area_text.setFocusable(false);
        method_text.setFocusable(false);
        fieldImg.setVisibility(View.INVISIBLE);
        dateImg.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contextView = inflater.inflate(R.layout.fragment_fertilizer,
                container, false);
        ButterKnife.bind(this, contextView);
        return contextView;
    }

    boolean isEmpty(){
        if(TextUtils.isEmpty(num_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(date_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(perticides_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(type_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(spec_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(area_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(method_text.getText().toString().trim())){
            return true;
        }
        return  false;
    }

    boolean isEmpty_(){
        if(TextUtils.isEmpty(num_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(date_text.getText().toString().trim())){
            return true;
        }
        return  false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fieldDataHelper = new FieldDataHelper(getActivity().getApplicationContext());
        fertilizerUsageDataHelper = new FertilizerUsageDataHelper(getActivity().getApplicationContext());
        taskDataHelper = new TaskDataHelper(getActivity().getApplicationContext());
        cursor = fieldDataHelper.getCursor();
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor, new String[]{"filed"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fieldImg.setOnClickListener(fieldClickListener);
        dateImg.setOnClickListener(dateClickListener);
        UserInfoModel userInfoModel = new UserInfoModel(getActivity().getApplicationContext());
        person_text.setText(userInfoModel.getUserInfo().getName());
        checkInputType();
    }

    void checkInputType(){
        if(object!=null){
            fieldImg.setVisibility(View.INVISIBLE);
            perticides_text.setBackgroundResource(R.drawable.text_backgroud);
            perticides_text.setFocusable(false);
            type_text.setBackgroundResource(R.drawable.text_backgroud);
            type_text.setFocusable(false);
            spec_text.setBackgroundResource(R.drawable.text_backgroud);
            spec_text.setFocusable(false);
            area_text.setBackgroundResource(R.drawable.text_backgroud);
            area_text.setFocusable(false);
            method_text.setBackgroundResource(R.drawable.text_backgroud);
            method_text.setFocusable(false);
            Task task = (Task)object;

            field_text.setText(task.getField());
            species_text.setText(task.getSpecies());
            perticides_text.setText(task.getF_name());
            spec_text.setText(task.getF_spec());
            type_text.setText(task.getF_type());
            area_text.setText(task.getF_area());
            method_text.setText(task.getF_method());
        }
    }

    View.OnClickListener fieldClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog = new MaterialDialog.Builder(getActivity()).title("地块选择").adapter(adapter, new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    cursor.moveToPosition(which);
                    FieldInfo fieldInfo = FieldInfo.fromCursor(cursor);
                    species_text.setText(fieldInfo.getSpecies());
                    field_text.setText(fieldInfo.getFiled());
                    textInput = true;
                    dialog.cancel();
                }
            }).alwaysCallSingleChoiceCallback().build();
            dialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener DatePickerListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            monthOfYear++;
            date_text.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };

    View.OnClickListener dateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Calendar objTime = Calendar.getInstance();
            int iYear = objTime.get(Calendar.YEAR);
            int iMonth = objTime.get(Calendar.MONTH);
            int iDay = objTime.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(getActivity(), DatePickerListener, iYear, iMonth, iDay).show();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        cursor.close();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

