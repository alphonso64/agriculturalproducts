package com.app.agriculturalproducts.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.PersonalStock;
import com.app.agriculturalproducts.bean.PlanterRecord;
import com.app.agriculturalproducts.bean.Task;

import butterknife.OnClick;


public class StockCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private Context context;

    public StockCursorAdapter(Context context) {
        super(context, null);
        this.context = context;
    }

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        PersonalStock ps = PersonalStock.fromCursor(cursor);
        ((PusageViewHolder) holder).title_1.setText("类型:"+ps.getPersonalstock_goods_type());
        ((PusageViewHolder) holder).title_2.setText("名称:"+ps.getPersonalstock_goods_name());
        ((PusageViewHolder) holder).title_3.setText("数量:"+ps.getPersonalstock_num());
        ((PusageViewHolder) holder).title_4.setText("规格:"+ps.getSpec());
        ((PusageViewHolder) holder).title_5.setText("来源:"+ps.getProducer());
        ((PusageViewHolder) holder).title_6.setText("企业："+ps.getMember_name());
        ((PusageViewHolder) holder).title_7.setVisibility(View.GONE);
        ((PusageViewHolder) holder).title_8.setVisibility(View.GONE);;
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
        StockCursorAdapter mAdapter;
        public PusageViewHolder(View itemView,StockCursorAdapter adapter) {
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
        }
        @OnClick(R.id.cv_task)
        void onItemClick() {
            Task task = Task.fromCursor((Cursor) mAdapter.getItem(getAdapterPosition()));
            Log.d("testbb", "onClick--> position = " + getAdapterPosition() + task.getTitle());
            if(mAdapter.onItemClickListener!=null){
                mAdapter.onItemClickListener.onItemClick(task,getAdapterPosition());
            }
        }
    }
}
