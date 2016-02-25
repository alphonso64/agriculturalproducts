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
import com.app.agriculturalproducts.bean.Field;
import com.app.agriculturalproducts.bean.Task;

import java.text.SimpleDateFormat;

import butterknife.OnClick;


public class FieldCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private Context context;

    public FieldCursorAdapter(Context context) {
        super(context, null);
        this.context = context;
    }

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        Field field = Field.fromCursor(cursor);
        ((PusageViewHolder) holder).title_1.setText(field.getField_name());
        ((PusageViewHolder) holder).title_3.setText(field.getField_type());
        ((PusageViewHolder) holder).title_2.setText(field.getField_status());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.pusage_item,parent,false);
        PusageViewHolder bvh=new PusageViewHolder(v,this);
        return bvh;
    }

    public static class PusageViewHolder extends RecyclerView.ViewHolder {
        TextView title_1;
        TextView title_2;
        TextView title_3;
        FieldCursorAdapter mAdapter;


        public PusageViewHolder(View itemView,FieldCursorAdapter adapter) {
            super(itemView);
            title_1 = (TextView) itemView.findViewById(R.id.pu_title_1);
            title_2 = (TextView) itemView.findViewById(R.id.pu_title_2);
            title_3 = (TextView) itemView.findViewById(R.id.pu_title_3);
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
