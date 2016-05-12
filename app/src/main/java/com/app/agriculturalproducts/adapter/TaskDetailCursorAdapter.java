package com.app.agriculturalproducts.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.agriculturalproducts.R;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.bean.TaskRecord;


public class TaskDetailCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private Context context;

    public TaskDetailCursorAdapter(Context context) {
        super(context, null);
        this.context = context;
    }

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        TaskRecord record = TaskRecord.fromCursor(cursor);
        ((TaskViewHolder) holder).title.setText(record.getWorktask_name());
        ((TaskViewHolder) holder).detail.setText(record.getWorktask_content());
        String type = record.getWorktask_type();
        if(type.equals("种植录入")){
            int resId = context.getResources().getIdentifier("t_a", "drawable", context.getPackageName());
            ((TaskViewHolder) holder).icon.setImageResource(resId);
        }else if(type.equals("肥料施用")){
            int resId = context.getResources().getIdentifier("tb", "drawable", context.getPackageName());
            ((TaskViewHolder) holder).icon.setImageResource(resId);
        }else if(type.equals("农药使用")){
            int resId = context.getResources().getIdentifier("tc", "drawable", context.getPackageName());
            ((TaskViewHolder) holder).icon.setImageResource(resId);
        }else if(type.equals("其他记录")){
            int resId = context.getResources().getIdentifier("td", "drawable", context.getPackageName());
            ((TaskViewHolder) holder).icon.setImageResource(resId);
        }else if(type.equals("采摘记录")){
            int resId = context.getResources().getIdentifier("te", "drawable", context.getPackageName());
            ((TaskViewHolder) holder).icon.setImageResource(resId);
        }
        String val = record.getWorktasklist_status();
        if(val.equals("已完成")){
            ((TaskViewHolder) holder).state.setText("完成");
        }else {
            ((TaskViewHolder) holder).state.setText("未完成");
            ((TaskViewHolder) holder).state.setTextColor(context.getResources().getColor(R.color.text_red));
        }
        ((TaskViewHolder) holder).time.setText(record.getWorktask_publish_date());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.task_item_detail,parent,false);
        TaskViewHolder bvh=new TaskViewHolder(v,this);
        return bvh;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView detail;
        TextView title;
        TextView time;
        TextView state;
        ImageView icon;
        TaskDetailCursorAdapter mAdapter;
        public TaskViewHolder(View itemView,TaskDetailCursorAdapter adapter) {
            super(itemView);
            detail = (TextView) itemView.findViewById( R.id.task_title_2);
            title = (TextView) itemView.findViewById(R.id.task_title_1);
            time = (TextView) itemView.findViewById(R.id.task_title_3);
            state = (TextView) itemView.findViewById(R.id.task_title_4);
            icon = (ImageView) itemView.findViewById(R.id.task_img);
            mAdapter = adapter;
            CardView cv = (CardView) itemView.findViewById(R.id.cv_task);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = (Cursor) mAdapter.getItem(getAdapterPosition());

                    TaskRecord task = TaskRecord.fromCursor(cursor);
                    if(mAdapter.onItemClickListener!=null){
                        mAdapter.onItemClickListener.onItemClick(task,getAdapterPosition());
                    }
                }
            });
        }
    }
}
