package com.app.agriculturalproducts.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.OtherInfo;
import com.app.agriculturalproducts.bean.OtherRecord;
import com.app.agriculturalproducts.bean.PickRecord;
import com.app.agriculturalproducts.bean.PlantSpecies;
import com.app.agriculturalproducts.bean.Task;

import java.text.SimpleDateFormat;

import butterknife.OnClick;


public class OtherInfoCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private Context context;

    public OtherInfoCursorAdapter(Context context) {
        super(context, null);
        this.context = context;
    }

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        OtherRecord oi = OtherRecord.fromCursor(cursor);
        ((PusageViewHolder) holder).title_1.setText("田地:"+oi.getField_name());
        ((PusageViewHolder) holder).title_2.setText("品种:"+oi.getPlantrecord_breed());

        ((PusageViewHolder) holder).title_3.setText("日期:"+oi.getOtherrecord_date());
        ((PusageViewHolder) holder).title_4.setText("记录人:"+oi.getOtherrecord_people());

        ((PusageViewHolder) holder).title_5.setText("情况:"+oi.getOtherrecord_situation());
        ((PusageViewHolder) holder).title_6.setText("处理:"+oi.getOtherrecord_method());

        ((PusageViewHolder) holder).title_7.setText("地点:"+oi.getOtherrecord_place());
        String uploadState = oi.getSaved();
        if(uploadState.equals("no")){
            ((PusageViewHolder) holder).title_8.setText("未上传");
            ((PusageViewHolder) holder).title_8.setTextColor(context.getResources().getColor(R.color.text_red));
        }else {
            ((PusageViewHolder) holder).title_8.setText("已上传");
            ((PusageViewHolder) holder).title_8.setTextColor(context.getResources().getColor(R.color.text_dark));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.pusage_item_type_a,parent,false);
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
        OtherInfoCursorAdapter mAdapter;


        public PusageViewHolder(View itemView,OtherInfoCursorAdapter adapter) {
            super(itemView);
            title_1 = (TextView) itemView.findViewById(R.id.pu_title_a_1);
            title_2 = (TextView) itemView.findViewById(R.id.pu_title_a_2);
            title_3 = (TextView) itemView.findViewById(R.id.pu_title_b_1);
            title_4 = (TextView) itemView.findViewById(R.id.pu_title_b_2);
            title_5 = (TextView) itemView.findViewById(R.id.pu_title_c_1);
            title_6 = (TextView) itemView.findViewById(R.id.pu_title_c_2);
            title_7 = (TextView) itemView.findViewById(R.id.pu_title_d_1);
            title_8 = (TextView) itemView.findViewById(R.id.pu_title_d_2);
            mAdapter = adapter;
            CardView cv = (CardView) itemView.findViewById(R.id.cv_pu_type_a);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = (Cursor) mAdapter.getItem(getAdapterPosition());
                    OtherRecord otherRecord = OtherRecord.fromCursor(cursor);
                    if(mAdapter.onItemClickListener!=null){
                        mAdapter.onItemClickListener.onItemClick(otherRecord,getAdapterPosition());
                    }
                }
            });
        }
    }
}
