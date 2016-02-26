package com.app.agriculturalproducts.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.adapter.OnAdpaterItemClickListener;
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.db.StockDataHelper;
import com.app.agriculturalproducts.http.HttpClient;
import com.app.agriculturalproducts.model.EmployeeInfoModel;
import com.app.agriculturalproducts.util.EditTextUtil;
import com.app.agriculturalproducts.util.InputType;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class PlantFragment extends BaseUploadFragment {
    private FieldDataHelper fieldDataHelper;
    private PlantSpeciesDataHelper plantSpeciesDataHelper;

    private Field field;
    private PersonalStock personalStock;

    @Bind(R.id.date_img)
    ImageView dateImg;
    @Bind(R.id.field_img)
    ImageView fieldImg;

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
    EditText species_text;
    @Bind(R.id.num_text)
    EditText num_text;

    boolean flag = false;
    boolean flag_upload = false;
    MaterialDialog dialog;
    MaterialDialog dialog_inner;
    Cursor cursor;
    Cursor cursor_inner;
    ListAdapter adapter;

    @Override
    public int save() {
        if (flag) {
            return InputType.INPUT_SAVE_ALREADY;
        }
        if (!isEmpty()) {
            PlanterRecord planterRecord = new PlanterRecord();
            planterRecord.setEmployee_name(plot_info_text.getText().toString());
            planterRecord.setField_name(field_text.getText().toString());
            planterRecord.setPlantrecord_specifications(spec_text.getText().toString());
            planterRecord.setPlantrecord_breed(species_text.getText().toString());
            planterRecord.setPlantrecord_plant_date(date_text.getText().toString());
            planterRecord.setPlantrecord_seed_name(seed_text.getText().toString());
            planterRecord.setPlantrecord_seed_number(num_text.getText().toString());
            planterRecord.setPlantrecord_seed_source(source_text.getText().toString());
            planterRecord.setSaved("no");
            planterRecord.setLocal_field_id(field.getField_id());
            planterRecord.setLocal_stock_id(personalStock.getPersonalstock_id());
            plantSpeciesDataHelper.insert_(planterRecord);
            flag = true;
            disableWidget();
            return InputType.INPUT_SAVE_OK;
        }
        return InputType.INPUT_EMPTY;
    }

    private void disableWidget() {
        List<EditText> ls = new ArrayList();
        ls.add(species_text);
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
        ls.add(species_text);
        ls.add(num_text);

        if (TextUtils.isEmpty(field_text.getText().toString().trim())) {
            return true;
        }
        if (TextUtils.isEmpty(date_text.getText().toString().trim())) {
            return true;
        }

        return EditTextUtil.isEditEmpty(ls);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fieldDataHelper = new FieldDataHelper(getActivity().getApplicationContext());
        plantSpeciesDataHelper = new PlantSpeciesDataHelper(getActivity().getApplicationContext());
        cursor = fieldDataHelper.getCursor();
        adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor, new String[]{"field_name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        fieldImg.setOnClickListener(fieldClickListener);
        dateImg.setOnClickListener(dateClickListener);

        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(getActivity());
        plot_info_text.setText(employeeInfoModel.getEmployeeInfo().getEmployee_name());
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
            monthOfYear++;
            date_text.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
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

