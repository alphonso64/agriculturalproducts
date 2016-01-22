package com.app.agriculturalproducts.fragment;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.FertilizerUsage;
import com.app.agriculturalproducts.bean.FieldInfo;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.db.FertilizerUsageDataHelper;
import com.app.agriculturalproducts.db.FieldDataHelper;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.db.PlantSpeciesDataHelper;
import com.app.agriculturalproducts.util.InputType;
import com.baidu.mapapi.map.MapView;

import java.lang.reflect.Field;
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
    @Bind(R.id.list_test)
    ListView list;

    @Override
    public int upload() {
        return super.upload();
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fieldDataHelper = new FieldDataHelper(getActivity().getApplicationContext());
        plantSpeciesDataHelper = new PlantSpeciesDataHelper(getActivity().getApplicationContext());
        final Cursor cursor = fieldDataHelper.getCursor();
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cursor, new String[]{"filed"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        final LinearLayout linearLayoutMain = new LinearLayout(getActivity());//自定义一个布局文件
        linearLayoutMain.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ListView listView = new ListView(getActivity());//this为获取当前的上下文
        listView.setFadingEdgeLength(0);
        listView.setAdapter(adapter);
        linearLayoutMain.addView(listView);//往这个布局中加入listview

        fieldImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).customView(linearLayoutMain,true)
                        .build();
                dialog.show();
            }
        });

//        mSpinner.setAdapter(adapter);
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int pos, long id) {
//                Cursor selectedCursor = (Cursor) parent.getSelectedItem();
//                FieldInfo fieldInfo = FieldInfo.fromCursor(selectedCursor);
//                species_text.setText(fieldInfo.getSpecies());
//                seed_text.setText(fieldInfo.getSeed());
//                source_text.setText(fieldInfo.getSource());
//                spec_text.setText(fieldInfo.getSpec());
//                plot_info_text.setText(fieldInfo.getInfo());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }
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
    }
}

