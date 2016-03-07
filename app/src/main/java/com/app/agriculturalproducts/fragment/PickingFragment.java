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
import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.Picking;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PickingDataHelper;
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

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PickingFragment extends BaseUploadFragment {
    private FieldDataHelper fieldDataHelper;
    private TaskDataHelper taskDataHelper;
    private PickingDataHelper pickingDataHelper;

    @Bind(R.id.pick_date_img)
    ImageView dateImg;
    @Bind(R.id.pick_field_img)
    ImageView fieldImg;

    @Bind(R.id.pick_field_text)
    TextView field_text;
    @Bind(R.id.pick_field_area_text)
    TextView field_area_text;
    @Bind(R.id.pick_plant_date_text)
    TextView plant_date_text;
    @Bind(R.id.pick_species_text)
    TextView species_text;
    @Bind(R.id.pick_date_text)
    TextView date_text;
    @Bind(R.id.pick_member_text)
    TextView member_text;
    @Bind(R.id.pick_person_text)
    TextView person_text;

    @Bind(R.id.pick_area_text)
    EditText area_text;
    @Bind(R.id.pick_num_text)
    EditText num_text;


    boolean flag = false;
    MaterialDialog dialog;
    MaterialDialog dialog_inner;
    Cursor cursor;
    Cursor cursor_inner;
    ListAdapter adapter;
    Field field;
    PlanterRecord planterRecord;
    Calendar calendar;

    @Override
    public int save() {
        if(flag){
            return InputType.INPUT_SAVE_ALREADY;
        }
        if(object == null){
            if(!isEmpty()){
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
        PickRecord picking = new PickRecord();
        picking.setPickrecord_date(date_text.getText().toString());
        picking.setPickrecord_number(num_text.getText().toString());
        picking.setPickrecord_area(area_text.getText().toString());
        picking.setPickrecord_people(person_text.getText().toString());

        picking.setPlantrecord_plant_date(plant_date_text.getText().toString());
        picking.setPlantrecord_breed(species_text.getText().toString());
        picking.setField_name(field_text.getText().toString());
        picking.setField_area(field_area_text.getText().toString());
        picking.setMember_name(member_text.getText().toString());
        picking.setSaved("no");
        picking.setLocal_plant_id(planterRecord.getPlantrecord_id());
        picking.setLocal_plant_table_index(String.valueOf(planterRecord.get_id()));
        pickingDataHelper.insert_(picking);
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
        View contextView = inflater.inflate(R.layout.fragment_picking,
                container, false);
        ButterKnife.bind(this, contextView);

        return contextView;

    }

    boolean isEmpty(){
        if(TextUtils.isEmpty(field_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(date_text.getText().toString().trim())){
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
        taskDataHelper = new TaskDataHelper(getActivity().getApplicationContext());
        pickingDataHelper = new PickingDataHelper(getActivity().getApplicationContext());
        cursor = fieldDataHelper.getCursor();
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor, new String[]{"field_name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fieldImg.setOnClickListener(fieldClickListener);
        dateImg.setOnClickListener(dateClickListener);
        checkInputType();
        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(getActivity());
        EmployeeInfo employeeInfo = employeeInfoModel.getEmployeeInfo();
        member_text.setText(employeeInfo.getMember_name());
        person_text.setText(employeeInfo.getEmployee_name());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date_text.setText(format.format(new Date()));
    }
    void checkInputType(){
//        if(object!=null){
//            fieldImg.setVisibility(View.INVISIBLE);
//            area_text.setBackgroundResource(R.drawable.text_backgroud);
//            area_text.setFocusable(false);
//            Task task = (Task)object;
//            field_text.setText(task.getField());
//            species_text.setText(task.getSpecies());
//            area_text.setText(task.getPick_area());
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
                    cursor_inner = new PlantSpeciesDataHelper(getActivity()).getCursor();
                    ListAdapter adapter_inner = new SimpleCursorAdapter(getActivity(),
                            android.R.layout.simple_list_item_2,
                            cursor_inner, new String[]{"plantrecord_breed","plantrecord_plant_date"},
                            new int[]{android.R.id.text1,android.R.id.text2}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    dialog_inner = new MaterialDialog.Builder(getActivity()).title("品种选择").adapter(adapter_inner, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            cursor_inner.moveToPosition(which);
                            planterRecord = PlanterRecord.fromCursor(cursor_inner);
                            species_text.setText(planterRecord.getPlantrecord_breed());
                            plant_date_text.setText(planterRecord.getPlantrecord_plant_date());
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
        cursor.close();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

