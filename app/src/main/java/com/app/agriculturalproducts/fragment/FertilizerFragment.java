package com.app.agriculturalproducts.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.FertilizerUsage;
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.StockDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.model.EmployeeInfoModel;
import com.app.agriculturalproducts.model.UserInfoModel;
import com.app.agriculturalproducts.util.EditTextUtil;
import com.app.agriculturalproducts.util.InputType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.f_field_area_text)
    TextView field_area_text;
    @Bind(R.id.f_species_text)
    TextView species_text;
    @Bind(R.id.f_spec_text)
    TextView spec_text;
    @Bind(R.id.f_type_text)
    TextView type_text;
    @Bind(R.id.f_perticides_text)
    TextView name_text;
    @Bind(R.id.f_date_text)
    TextView date_text;
    @Bind(R.id.f_member_text)
    TextView member_text;
    @Bind(R.id.f_employee_text)
    TextView employee_text;
    @Bind(R.id.f_method_text)
    TextView method_text;
    @Bind(R.id.f_plant_area_text)
    TextView plant_area_text;

    @Bind(R.id.f_num_text)
    EditText num_text;
    @Bind(R.id.f_area_text)
    EditText area_text;

    boolean flag = false;
    MaterialDialog dialog;
    MaterialDialog dialog_inner;
    Cursor cursor;
    Cursor cursor_inner_a;
    Cursor cursor_inner_b;
    ListAdapter adapter;
    Field field;
    PersonalStock personalStock;
    PlanterRecord planterRecord;
    Calendar calendar;
    @Override
    public int save() {
        if(flag){
            return InputType.INPUT_SAVE_ALREADY;
        }
        if(object == null){
            if(!isEmpty() ){
                saveInfo();
                flag = true;
                disableWidget();
                return InputType.INPUT_SAVE_OK;
            }
        }else{
//            if(!isEmpty_()){
//                saveInfo();
//                flag = true;
//                disableWidget();
//                Task task = (Task)object;
//                task.setIsDone("true");
//                ContentValues values = cupboard().withEntity(Task.class).toContentValues(task);
//                taskDataHelper.updateTask(values, String.valueOf(task.get_id()));
//                return InputType.INPUT_SAVE_OK;
//            }
        }
        return InputType.INPUT_EMPTY;
    }

    private void saveInfo(){
        FertilizerRecord fertilizerRecord = new FertilizerRecord();
        fertilizerRecord.setFertilizerecord_date(date_text.getText().toString());
        fertilizerRecord.setFertilizerecord_name(name_text.getText().toString());
        fertilizerRecord.setFertilizerecord_number(num_text.getText().toString());
        fertilizerRecord.setFertilizerecord_range(area_text.getText().toString());
        fertilizerRecord.setFertilizerecord_type(type_text.getText().toString());
        fertilizerRecord.setFertilizerecord_spec(spec_text.getText().toString());
        fertilizerRecord.setFertilizerecord_method(method_text.getText().toString());
        fertilizerRecord.setField_name(field_text.getText().toString());
        fertilizerRecord.setField_area(field_area_text.getText().toString());
        fertilizerRecord.setMember_name(member_text.getText().toString());
        fertilizerRecord.setPlantrecord_breed(species_text.getText().toString());
        fertilizerRecord.setEmployee_name(employee_text.getText().toString());
        fertilizerRecord.setFertilizerecord_people(employee_text.getText().toString());
        fertilizerRecord.setLocal_plant_id(planterRecord.getPlantrecord_id());
        fertilizerRecord.setLocal_plant_table_index(String.valueOf(planterRecord.get_id()));
        fertilizerRecord.setLocal_stock_id(personalStock.getPersonalstock_id());
        fertilizerRecord.setSaved("no");
        Log.e("testcc", "save:" + planterRecord.getPlantrecord_id());
        fertilizerUsageDataHelper.insert_(fertilizerRecord);
    }

    private void disableWidget(){
        List<EditText> ls = new ArrayList();
        ls.add(num_text);
        ls.add(area_text);

        EditTextUtil.disableEditText(ls);
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
        if(TextUtils.isEmpty(field_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(species_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(name_text.getText().toString().trim())){
            return true;
        }
        List<EditText> ls = new ArrayList();
        ls.add(num_text);
        ls.add(area_text);
        return  EditTextUtil.isEditEmpty(ls);
    }

    boolean isEmpty_(){
//        if(TextUtils.isEmpty(num_text.getText().toString().trim())){
//            return true;
//        }
//        if(TextUtils.isEmpty(date_text.getText().toString().trim())){
//            return true;
//        }
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
                cursor, new String[]{"field_name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fieldImg.setOnClickListener(fieldClickListener);
        dateImg.setOnClickListener(dateClickListener);
        checkInputType();

        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(getActivity());
        employee_text.setText(employeeInfoModel.getEmployeeInfo().getEmployee_name());
        member_text.setText(employeeInfoModel.getEmployeeInfo().getMember_name());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date_text.setText(format.format(new Date()));
    }

    void checkInputType(){
//        if(object!=null){
//            fieldImg.setVisibility(View.INVISIBLE);
//            perticides_text.setBackgroundResource(R.drawable.text_backgroud);
//            perticides_text.setFocusable(false);
//            type_text.setBackgroundResource(R.drawable.text_backgroud);
//            type_text.setFocusable(false);
//            spec_text.setBackgroundResource(R.drawable.text_backgroud);
//            spec_text.setFocusable(false);
//            area_text.setBackgroundResource(R.drawable.text_backgroud);
//            area_text.setFocusable(false);
//            method_text.setBackgroundResource(R.drawable.text_backgroud);
//            method_text.setFocusable(false);
//            Task task = (Task)object;
//
//            field_text.setText(task.getField());
//            species_text.setText(task.getSpecies());
//            perticides_text.setText(task.getF_name());
//            spec_text.setText(task.getF_spec());
//            type_text.setText(task.getF_type());
//            area_text.setText(task.getF_area());
//            method_text.setText(task.getF_method());
//        }
    }

    View.OnClickListener fieldClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog = new MaterialDialog.Builder(getActivity()).title("地块选择").adapter(adapter, new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    cursor.moveToPosition(which);
                    field = Field.fromCursor(cursor);
                    field_text.setText(field.getField_name());
                    field_area_text.setText(field.getField_area());
                    cursor_inner_a = new PlantSpeciesDataHelper(getActivity()).getCursorByFiledID(field.getField_id());
                    ListAdapter adapter_inner = new SimpleCursorAdapter(getActivity(),
                            android.R.layout.simple_list_item_2,
                            cursor_inner_a, new String[]{"plantrecord_breed","plantrecord_plant_date"},
                            new int[]{android.R.id.text1,android.R.id.text2}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    dialog_inner = new MaterialDialog.Builder(getActivity()).title("品种选择").adapter(adapter_inner, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            cursor_inner_a.moveToPosition(which);
                            planterRecord = PlanterRecord.fromCursor(cursor_inner_a);
                            species_text.setText(planterRecord.getPlantrecord_breed());
                            plant_area_text.setText(planterRecord.getField_plant_area());
                            cursor_inner_b = new StockDataHelper(getActivity()).getCursorFertilizer();

                            ListAdapter adapter_inner = new SimpleCursorAdapter(getActivity(),
                                    android.R.layout.simple_list_item_1,
                                    cursor_inner_b, new String[]{"personalstock_goods_name"},
                                    new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                            dialog = new MaterialDialog.Builder(getActivity()).title("化肥选择").adapter(adapter_inner, new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    cursor_inner_b.moveToPosition(which);
                                    personalStock = PersonalStock.fromCursor(cursor_inner_b);
                                    name_text.setText(personalStock.getPersonalstock_goods_name());
                                    type_text.setText(personalStock.getPersonalstock_goods_type());
                                    spec_text.setText(personalStock.getSpec());
                                    method_text.setText(personalStock.getMethod());
                                    dialog.cancel();
                                }
                            }).alwaysCallSingleChoiceCallback().build();
                            dialog.show();
                            dialog_inner.cancel();
                        }
                    }).alwaysCallSingleChoiceCallback().build();
                    dialog_inner.show();
                    dialog.cancel();
                }
            }).alwaysCallSingleChoiceCallback().build();
            dialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener DatePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar = Calendar.getInstance();
            calendar.set(year,monthOfYear,dayOfMonth);
            new TimePickerDialog(getActivity(),TimePickerListener,0,0,true).show();
        }
    };
    private TimePickerDialog.OnTimeSetListener TimePickerListener = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(hourOfDay>=12){
                hourOfDay-=12;
            }else{
                hourOfDay+=12;
            }
            calendar.set(Calendar.HOUR,hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            Log.e("testcc", "calendar.set(Calendar.HOUR,hourOfDay);"+hourOfDay);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date=calendar.getTime();
            date_text.setText(format.format(date));
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
        if(cursor!=null){
            cursor.close();
        }
        if(cursor_inner_a!=null){
            cursor_inner_a.close();
        }
        if(cursor_inner_b!=null){
            cursor_inner_b.close();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @OnClick(R.id.upload_button)
    void click(){
        uploadClick();
    }

}

