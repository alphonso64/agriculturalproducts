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
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.OtherInfo;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.OtherInfoDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.model.UserInfoModel;
import com.app.agriculturalproducts.util.InputType;
import java.util.Calendar;
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
    @Bind(R.id.o_person_text)
    TextView person_text;

    @Bind(R.id.o_situation_text)
    EditText situation_text;
    @Bind(R.id.o_handle_text)
    EditText handle_text;

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
        if(!isEmpty() && textInput){
            OtherInfo otherInfo = new OtherInfo();
            otherInfo.setField(field_text.getText().toString());
            otherInfo.setSpecies(species_text.getText().toString());
            otherInfo.setRecorder(person_text.getText().toString());
            otherInfo.setDate(date_text.getText().toString());
            otherInfo.setHandle(handle_text.getText().toString());
            otherInfo.setSituation(situation_text.getText().toString());
            otherInfoDataHelper.insert_(otherInfo);
            flag = true;
            disableWidget();
            return InputType.INPUT_SAVE_OK;
        }
        return InputType.INPUT_EMPTY;
    }

    private void disableWidget(){
        situation_text.setFocusable(false);
        handle_text.setFocusable(false);
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
        if(TextUtils.isEmpty(handle_text.getText().toString().trim())){
            return true;
        }
        if(TextUtils.isEmpty(situation_text.getText().toString().trim())){
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
        otherInfoDataHelper = new OtherInfoDataHelper(getActivity().getApplicationContext());
        cursor = fieldDataHelper.getCursor();
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor, new String[]{"filed"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fieldImg.setOnClickListener(fieldClickListener);
        dateImg.setOnClickListener(dateClickListener);
        UserInfoModel userInfoModel = new UserInfoModel(getActivity().getApplicationContext());
        person_text.setText(userInfoModel.getUserInfo().getName());
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
        cursor.close();
        ButterKnife.unbind(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

