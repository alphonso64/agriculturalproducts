package com.app.agriculturalproducts.fragment;

import android.app.DatePickerDialog;
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
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.util.InputType;
import java.util.Calendar;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PlantFragment extends BaseUploadFragment {
    private FieldDataHelper fieldDataHelper;
    private PlantSpeciesDataHelper plantSpeciesDataHelper;
    @Bind(R.id.date_img)
    ImageView dateImg;
    @Bind(R.id.field_img)
    ImageView fieldImg;
    @Bind(R.id.species_text)
    TextView species_text;
    @Bind(R.id.seed_text)
    TextView seed_text;
    @Bind(R.id.spec_text)
    TextView spec_text;
    @Bind(R.id.source_text)
    TextView source_text;
    @Bind(R.id.plot_info_text)
    TextView plot_info_text;
    @Bind(R.id.field_text)
    TextView field_text;
    @Bind(R.id.date_text)
    TextView date_text;
    @Bind(R.id.num_text)
    EditText num_text;

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
            PlantSpecies plantSpecies = new PlantSpecies();
            plantSpecies.setFiled(field_text.getText().toString());
            plantSpecies.setPlant_num(num_text.getText().toString());
            plantSpecies.setDate(date_text.getText().toString());
            plantSpeciesDataHelper.insert_(plantSpecies);
            flag = true;
            disableWidget();
            return InputType.INPUT_SAVE_OK;
        }
        return InputType.INPUT_EMPTY;
    }

    private void disableWidget(){
        num_text.setFocusable(false);
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

    boolean isEmpty(){
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
        plantSpeciesDataHelper = new PlantSpeciesDataHelper(getActivity().getApplicationContext());
        cursor = fieldDataHelper.getCursor();
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor, new String[]{"filed"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fieldImg.setOnClickListener(fieldClickListener);
        dateImg.setOnClickListener(dateClickListener);
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
                    seed_text.setText(fieldInfo.getSeed());
                    spec_text.setText(fieldInfo.getSpec());
                    source_text.setText(fieldInfo.getSource());
                    plot_info_text.setText(fieldInfo.getInfo());
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

