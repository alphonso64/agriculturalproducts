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
import com.app.agriculturalproducts.bean.OtherInfo;
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
        OtherInfo oi = OtherInfo.fromCursor(cursor);
        ((PusageViewHolder) holder).title_1.setText(oi.getSituation());
        long time = oi.getTime();
        String ISO_FORMAT = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
        ((PusageViewHolder) holder).title_2.setText(sdf.format(time));
        ((PusageViewHolder) holder).title_3.setText(oi.getLocation());
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
        OtherInfoCursorAdapter mAdapter;


        public PusageViewHolder(View itemView,OtherInfoCursorAdapter adapter) {
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
