package com.app.agriculturalproducts.fragment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.OtherInfo;
import com.app.agriculturalproducts.bean.OtherRecord;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.model.EmployeeInfoModel;
import com.app.agriculturalproducts.model.UserInfoModel;
import com.app.agriculturalproducts.util.EditTextUtil;
import com.app.agriculturalproducts.util.InputType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class OtherInfoFragment extends BaseUploadFragment {
    private FieldDataHelper fieldDataHelper;
    private OtherInfoDataHelper otherInfoDataHelper;
    @Bind(R.id.o_date_img)
    ImageView dateImg;
    @Bind(R.id.o_field_img)
    ImageView fieldImg;

    @Bind(R.id.o_species_text)
    TextView species_text;
    @Bind(R.id.o_field_text)
    TextView field_text;
    @Bind(R.id.o_date_text)
    TextView date_text;
    @Bind(R.id.o_member_text)
    TextView member_text;

    @Bind(R.id.o_place_text)
    EditText place_text;
    @Bind(R.id.o_person_text)
    EditText person_text;
    @Bind(R.id.o_situation_text)
    EditText situation_text;
    @Bind(R.id.o_handle_text)
    EditText handle_text;

    boolean flag = false;
    MaterialDialog dialog;
    MaterialDialog dialog_inner;
    Cursor cursor;
    Cursor cursor_inner;
    ListAdapter adapter;
    Field field;
    PlanterRecord planterRecord;
    @Override
    public int save() {
        if(flag){
            return InputType.INPUT_SAVE_ALREADY;
        }
        if(!isEmpty() ){
            saveInfo();
            flag = true;
            disableWidget();
            return InputType.INPUT_SAVE_OK;
        }
        return InputType.INPUT_EMPTY;
    }

    private void saveInfo(){
        OtherRecord otherRecord = new OtherRecord();
        otherRecord.setOtherrecord_situation(situation_text.getText().toString());
        otherRecord.setOtherrecord_date(date_text.getText().toString());
        otherRecord.setOtherrecord_method(handle_text.getText().toString());
        otherRecord.setOtherrecord_place(place_text.getText().toString());

        otherRecord.setOtherrecord_people(person_text.getText().toString());
        otherRecord.setPlantrecord_breed(species_text.getText().toString());
        otherRecord.setField_name(field_text.getText().toString());
        otherRecord.setMember_name(member_text.getText().toString());

        otherRecord.setSaved("no");
        otherRecord.setLocal_plant_id(planterRecord.getPlantrecord_id());
        otherInfoDataHelper.insert_(otherRecord);
    }

    private void disableWidget(){
        List<EditText> ls = new ArrayList();
        ls.add(place_text);
        ls.add(situation_text);
        ls.add(person_text);
        ls.add(handle_text);
        EditTextUtil.disableEditText(ls);
        fieldImg.setVisibility(View.INVISIBLE);
        dateImg.setVisibility(View.INVISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View contextView = inflater.inflate(R.layout.fragment_otherinfo,
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
        ls.add(place_text);
        ls.add(situation_text);
        ls.add(person_text);
        ls.add(handle_text);
        return  EditTextUtil.isEditEmpty(ls);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fieldDataHelper = new FieldDataHelper(getActivity().getApplicationContext());
        otherInfoDataHelper = new OtherInfoDataHelper(getActivity().getApplicationContext());
        cursor = fieldDataHelper.getCursor();
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor, new String[]{"field_name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fieldImg.setOnClickListener(fieldClickListener);
        dateImg.setOnClickListener(dateClickListener);
        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(getActivity());
        member_text.setText(employeeInfoModel.getEmployeeInfo().getMember_name());
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
                    cursor_inner = new PlantSpeciesDataHelper(getActivity()).getCursor();
                    ListAdapter adapter_inner = new SimpleCursorAdapter(getActivity(),
                            android.R.layout.simple_list_item_1,
                            cursor_inner, new String[]{"plantrecord_breed"},
                            new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    dialog_inner = new MaterialDialog.Builder(getActivity()).title("品种选择").adapter(adapter_inner, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            cursor_inner.moveToPosition(which);
                            planterRecord = PlanterRecord.fromCursor(cursor_inner);
                            species_text.setText(planterRecord.getPlantrecord_breed());
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
        cursor.close();
        ButterKnife.unbind(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

