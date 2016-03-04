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
import com.app.agriculturalproducts.bean.FertilizerRecord;
import com.app.agriculturalproducts.bean.FertilizerUsage;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.Task;

import java.text.SimpleDateFormat;

import butterknife.OnClick;


public class FusageCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private Context context;

    public FusageCursorAdapter(Context context) {
        super(context, null);
        this.context = context;
    }

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        FertilizerRecord fu = FertilizerRecord.fromCursor(cursor);
        ((PusageViewHolder) holder).title_1.setText("名称:"+fu.getFertilizerecord_name());
        ((PusageViewHolder) holder).title_2.setText("用量:"+fu.getFertilizerecord_number());
        ((PusageViewHolder) holder).title_3.setText("田地:"+fu.getField_name());
        ((PusageViewHolder) holder).title_4.setText("品种:"+fu.getPlantrecord_breed());

        ((PusageViewHolder) holder).title_5.setText("类型:"+fu.getFertilizerecord_type());
        ((PusageViewHolder) holder).title_6.setText("规格:"+fu.getFertilizerecord_spec());

        ((PusageViewHolder) holder).title_7.setText("施肥面积:"+fu.getFertilizerecord_range());
        ((PusageViewHolder) holder).title_8.setText("方法:" + fu.getFertilizerecord_method());
        ((PusageViewHolder) holder).title_9.setText("施肥日期:" + fu.getFertilizerecord_date());
        ((PusageViewHolder) holder).title_10.setText("施肥人员:"+fu.getFertilizerecord_people());
        ((PusageViewHolder) holder).title_11.setText("企业:"+fu.getMember_name());
        String uploadState = fu.getSaved();
        if(uploadState.equals("no")){
            ((PusageViewHolder) holder).title_12.setText("未上传");
            ((PusageViewHolder) holder).title_12.setTextColor(context.getResources().getColor(R.color.text_red));
        }else if(uploadState.equals("yes")){
            ((PusageViewHolder) holder).title_12.setText("已上传");
            ((PusageViewHolder) holder).title_12.setTextColor(context.getResources().getColor(R.color.text_dark));
        }else if(uploadState.equals("err")){
            ((PusageViewHolder) holder).title_12.setText("上传错误");
            ((PusageViewHolder) holder).title_12.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.pusage_item_type_b,parent,false);
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
        FusageCursorAdapter mAdapter;

        public PusageViewHolder(View itemView,FusageCursorAdapter adapter) {
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
            mAdapter = adapter;
            CardView cv = (CardView) itemView.findViewById(R.id.cv_pu_type_b);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = (Cursor) mAdapter.getItem(getAdapterPosition());

                    FertilizerRecord fertilizerRecord = FertilizerRecord.fromCursor(cursor);
                    Log.e("testbb", "fertilizerRecord " + fertilizerRecord.getLocal_plant_id());
                    if(mAdapter.onItemClickListener!=null){
                        mAdapter.onItemClickListener.onItemClick(fertilizerRecord,getAdapterPosition());
                    }
                }
            });
        }
    }
}
