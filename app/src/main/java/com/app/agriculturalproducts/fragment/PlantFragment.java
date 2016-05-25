package com.app.agriculturalproducts.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.bean.TaskRecord;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.StockDataHelper;
import com.app.agriculturalproducts.db.TaskDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.model.EmployeeInfoModel;
import com.app.agriculturalproducts.util.EditTextUtil;
import com.app.agriculturalproducts.util.InputType;
import com.app.agriculturalproducts.util.Lengthfilter;
import com.app.agriculturalproducts.util.TaskRecordUtil;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PlantFragment extends BaseUploadFragment {
    private FieldDataHelper fieldDataHelper;
    private TaskDataHelper taskDataHelper;
    private PlantSpeciesDataHelper plantSpeciesDataHelper;

    @Bind(R.id.date_img)
    ImageView dateImg;
    @Bind(R.id.field_img)
    ImageView fieldImg;
    @Bind(R.id.type_img)
    ImageView typeImg;

    @Bind(R.id.field_area_text)
    TextView plot_area_text;
    @Bind(R.id.plot_info_text)
    TextView plot_info_text;
    @Bind(R.id.field_text)
    TextView field_text;
    @Bind(R.id.date_text)
    TextView date_text;
    @Bind(R.id.seed_text)
    TextView seed_text;
    @Bind(R.id.spec_text)
    TextView spec_text;
    @Bind(R.id.source_text)
    TextView source_text;
    @Bind(R.id.species_text)
    TextView species_text;
    @Bind(R.id.recored_type_text)
    TextView recored_type_text;
    @Bind(R.id.circle_text)
    TextView circle_text;

    @Bind(R.id.num_text)
    EditText num_text;
    @Bind(R.id.plant_area_text)
    EditText plant_area;

    boolean flag = false;
    boolean flag_upload = false;
    MaterialDialog dialog;
    MaterialDialog dialog_inner;
    Cursor cursor;
    Cursor cursor_inner;
    ListAdapter adapter;
    SimpleAdapter typeAdapter;
    Field field;
    PersonalStock personalStock;
    Calendar calendar;

    private String[] type_name = { "普通农产品","无公害农产品","绿色农产品","有机农产品" };

    @Override
    public int save() {
        if (flag) {
            return InputType.INPUT_SAVE_ALREADY;
        }
        if (!isEmpty()) {
            String taskID = "null";
            if(object != null) {
                TaskRecord task = (TaskRecord)object;
                TaskRecordUtil.recordLocalDoneTask(getActivity(), taskDataHelper, task);
                taskID = task.getWorktasklist_id();
            }
            saveInfo(taskID);
            flag = true;
            disableWidget();
            return InputType.INPUT_SAVE_OK;
        }
        return InputType.INPUT_EMPTY;
    }

    private void saveInfo(String taskID){
        PlanterRecord planterRecord = new PlanterRecord();
        planterRecord.setEmployee_name(plot_info_text.getText().toString());
        planterRecord.setField_name(field_text.getText().toString());
        planterRecord.setPlantrecord_specifications(spec_text.getText().toString());
        planterRecord.setPlantrecord_breed(species_text.getText().toString());
        planterRecord.setPlantrecord_plant_date(date_text.getText().toString());
        planterRecord.setPlantrecord_seed_name(seed_text.getText().toString());
        planterRecord.setPlantrecord_seed_number(num_text.getText().toString());
        planterRecord.setPlantrecord_seed_source(source_text.getText().toString());
        planterRecord.setPlantrecord_type(recored_type_text.getText().toString());
        planterRecord.setPlantrecord_growth_cycle(circle_text.getText().toString());
        planterRecord.setSaved("no");
        planterRecord.setField_plant_area(plant_area.getText().toString());
        planterRecord.setLocal_field_id(field.getField_id());
        planterRecord.setLocal_stock_id(personalStock.getPersonalstock_id());
        planterRecord.setTask_id(taskID);
        plantSpeciesDataHelper.insert_(planterRecord);
    }

    private void disableWidget() {
        List<EditText> ls = new ArrayList();
        ls.add(num_text);
        EditTextUtil.disableEditText(ls);
        fieldImg.setVisibility(View.INVISIBLE);
        dateImg.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contextView = inflater.inflate(R.layout.fragment_plantspecies,
                container, false);
        ButterKnife.bind(this, contextView);
        return contextView;

    }

    boolean isEmpty() {
        List<EditText> ls = new ArrayList();
        ls.add(num_text);
        ls.add(plant_area);
        if (TextUtils.isEmpty(field_text.getText().toString().trim())) {
            return true;
        }
        if (TextUtils.isEmpty(date_text.getText().toString().trim())) {
            return true;
        }
        if (TextUtils.isEmpty(recored_type_text.getText().toString().trim())) {
            return true;
        }
        return EditTextUtil.isEditEmpty(ls);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fieldDataHelper = new FieldDataHelper(getActivity().getApplicationContext());
        taskDataHelper = new TaskDataHelper(getActivity().getApplicationContext());
        plantSpeciesDataHelper = new PlantSpeciesDataHelper(getActivity().getApplicationContext());
        cursor = fieldDataHelper.getCursor();
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor, new String[]{"field_name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fieldImg.setOnClickListener(fieldClickListener);
        dateImg.setOnClickListener(dateClickListener);
        typeImg.setOnClickListener(plantTypeClickListener);

        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < type_name.length; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("type", type_name[i]);
            listems.add(listem);
        }
        typeAdapter = new SimpleAdapter(getActivity(), listems,
                android.R.layout.simple_list_item_1, new String[] { "type" },
                new int[]{android.R.id.text1});

        plant_area.setFilters(new InputFilter[]{new Lengthfilter()});
        num_text.setFilters(new InputFilter[]{new Lengthfilter()});

        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(getActivity());
        plot_info_text.setText(employeeInfoModel.getEmployeeInfo().getEmployee_name());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date_text.setText(format.format(new Date()));
    }

    View.OnClickListener plantTypeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog = new MaterialDialog.Builder(getActivity()).title("种植类型选择").adapter(typeAdapter, new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    recored_type_text.setText(type_name[which]);
                    dialog.cancel();
                }
            }).alwaysCallSingleChoiceCallback().build();
            dialog.show();
        }
    };

    View.OnClickListener fieldClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog = new MaterialDialog.Builder(getActivity()).title("地块选择").adapter(adapter, new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    cursor.moveToPosition(which);
                    field = Field.fromCursor(cursor);
                    field_text.setText(field.getField_name());
                    plot_area_text.setText(field.getField_area());
                    cursor_inner = new StockDataHelper(getActivity()).getCursorSeed();
                    ListAdapter adapter_inner = new SimpleCursorAdapter(getActivity(),
                            android.R.layout.simple_list_item_1,
                            cursor_inner, new String[]{"personalstock_goods_name"},
                            new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    dialog_inner = new MaterialDialog.Builder(getActivity()).title("种子选择").adapter(adapter_inner, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            cursor_inner.moveToPosition(which);
                            personalStock = PersonalStock.fromCursor(cursor_inner);
                            seed_text.setText(personalStock.getPersonalstock_goods_name());
                            source_text.setText(personalStock.getProducer());
                            spec_text.setText(personalStock.getSpec());
                            species_text.setText(personalStock.getBreed());
                            circle_text.setText(personalStock.getPlantrecord_growth_cycle());
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
        if(cursor!=null){
            cursor.close();
        }
        if(cursor_inner!=null){
            cursor_inner.close();
        }
        ButterKnife.unbind(this);
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

