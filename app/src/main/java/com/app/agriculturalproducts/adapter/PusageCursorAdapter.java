package com.app.agriculturalproducts.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.PreventionRecord;
import com.app.agriculturalproducts.bean.Task;

import java.text.SimpleDateFormat;

import butterknife.OnClick;


public class PusageCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private Context context;

    public PusageCursorAdapter(Context context) {
        super(context, null);
        this.context = context;
    }

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        PreventionRecord pu = PreventionRecord.fromCursor(cursor);
        ((PusageViewHolder) holder).title_1.setText("名称:"+pu.getPreventionrecord_medicine_name());
        ((PusageViewHolder) holder).title_2.setText("用量:"+pu.getPreventionrecord_medicine_number());

        ((PusageViewHolder) holder).title_3.setText("田地:"+pu.getField_name());
        ((PusageViewHolder) holder).title_4.setText("品种:"+pu.getPlantrecord_breed());

        ((PusageViewHolder) holder).title_5.setText("类型:"+pu.getPreventionrecord_type());
        ((PusageViewHolder) holder).title_6.setText("规格:" + pu.getPreventionrecord_spec());

        ((PusageViewHolder) holder).title_7.setText("施药面积:" + pu.getPreventionrecord_range());
        ((PusageViewHolder) holder).title_8.setText("方法:" + pu.getPreventionrecord_method());

        ((PusageViewHolder) holder).title_9.setText("防治日期:" + pu.getPreventionrecord_date());
        ((PusageViewHolder) holder).title_10.setText("xx日期:" + pu.getPreventionrecord_plant_day());

        ((PusageViewHolder) holder).title_11.setText("症状:"+pu.getPreventionrecord_symptom());
        ((PusageViewHolder) holder).title_12.setText("处方人:"+pu.getPreventionrecord_medicine_people());

        ((PusageViewHolder) holder).title_13.setText("使用人:" + pu.getPreventionrecord_use_people());
        ((PusageViewHolder) holder).title_15.setVisibility(View.GONE);
        ((PusageViewHolder) holder).title_16.setVisibility(View.GONE);

        String uploadState = pu.getSaved();
        if(uploadState.equals("no")){
            ((PusageViewHolder) holder).title_14.setText("未上传");
            ((PusageViewHolder) holder).title_14.setTextColor(context.getResources().getColor(R.color.text_red));
        }else {
            ((PusageViewHolder) holder).title_14.setText("已上传");
            ((PusageViewHolder) holder).title_14.setTextColor(context.getResources().getColor(R.color.text_dark));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.pusage_item_type_c,parent,false);
        PusageViewHolder bvh=new PusageViewHolder(v,this);
        return bvh;
    }

    public static class PusageViewHolder extends RecyclerView.ViewHolder {
        TextView title_1;
        TextView title_2;
        TextView title_3;
        TextView title_4;
        TextView title_5;
        TextView title_6;
        TextView title_7;
        TextView title_8;
        TextView title_9;
        TextView title_10;
        TextView title_11;
        TextView title_12;
        TextView title_13;
        TextView title_14;
        TextView title_15;
        TextView title_16;
        PusageCursorAdapter mAdapter;


        public PusageViewHolder(View itemView,PusageCursorAdapter adapter) {
            super(itemView);
            title_1 = (TextView) itemView.findViewById(R.id.pu_title_a_1);
            title_2 = (TextView) itemView.findViewById(R.id.pu_title_a_2);
            title_3 = (TextView) itemView.findViewById(R.id.pu_title_b_1);
            title_4 = (TextView) itemView.findViewById(R.id.pu_title_b_2);
            title_5 = (TextView) itemView.findViewById(R.id.pu_title_c_1);
            title_6 = (TextView) itemView.findViewById(R.id.pu_title_c_2);
            title_7 = (TextView) itemView.findViewById(R.id.pu_title_d_1);
            title_8 = (TextView) itemView.findViewById(R.id.pu_title_d_2);
            title_9 = (TextView) itemView.findViewById(R.id.pu_title_e_1);
            title_10 = (TextView) itemView.findViewById(R.id.pu_title_e_2);
            title_11 = (TextView) itemView.findViewById(R.id.pu_title_f_1);
            title_12 = (TextView) itemView.findViewById(R.id.pu_title_f_2);
            title_13 = (TextView) itemView.findViewById(R.id.pu_title_g_1);
            title_14 = (TextView) itemView.findViewById(R.id.pu_title_g_2);
            title_15 = (TextView) itemView.findViewById(R.id.pu_title_h_1);
            title_16 = (TextView) itemView.findViewById(R.id.pu_title_h_2);
            mAdapter = adapter;
            CardView cv = (CardView) itemView.findViewById(R.id.cv_pu_type_c);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = (Cursor) mAdapter.getItem(getAdapterPosition());
                    PreventionRecord preventionRecord = PreventionRecord.fromCursor(cursor);
                    if(mAdapter.onItemClickListener!=null){
                        mAdapter.onItemClickListener.onItemClick(preventionRecord,getAdapterPosition());
                    }
                }
            });
        }

    }
}
