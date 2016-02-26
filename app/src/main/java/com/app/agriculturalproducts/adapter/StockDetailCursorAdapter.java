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
import com.app.agriculturalproducts.bean.PersonalStockDetail;
import com.app.agriculturalproducts.bean.Task;

import butterknife.OnClick;


public class StockDetailCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private Context context;

    public StockDetailCursorAdapter(Context context) {
        super(context, null);
        this.context = context;
    }

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        PersonalStockDetail ps = PersonalStockDetail.fromCursor(cursor);
        ((PusageViewHolder) holder).title_1.setText("名称:"+ps.getPersonalstockdetail_goods_name());
        ((PusageViewHolder) holder).title_2.setText("类型:"+ps.getPersonalstockdetail_goods_type());
        ((PusageViewHolder) holder).title_3.setText("订单号:"+ps.getPersonalstockdetail_orderno());
        ((PusageViewHolder) holder).title_4.setText("数量:"+ps.getPersonalstockdetail_num());
        ((PusageViewHolder) holder).title_5.setText("单价:"+ps.getPersonalstockdetail_price());
        ((PusageViewHolder) holder).title_6.setText("总价:"+ps.getPersonalstockdetail_total());
        ((PusageViewHolder) holder).title_7.setText("出库时间:" + ps.getPersonalstockdetail_date());
        ((PusageViewHolder) holder).title_8.setText("已入库:" + ps.getPersonalstockdetail_type());
        ((PusageViewHolder) holder).title_9.setText("规格:"+ps.getSpec());
        ((PusageViewHolder) holder).title_10.setText("来源:"+ps.getProducer());
        ((PusageViewHolder) holder).title_11.setText("企业:"+ps.getMember_name());
        ((PusageViewHolder) holder).title_12.setVisibility(View.GONE);
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
        StockDetailCursorAdapter mAdapter;
        public PusageViewHolder(View itemView,StockDetailCursorAdapter adapter) {
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
