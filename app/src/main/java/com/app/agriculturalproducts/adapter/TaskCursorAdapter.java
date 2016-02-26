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
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.util.StringUtil;

import java.text.SimpleDateFormat;

import butterknife.OnClick;


public class TaskCursorAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private Context context;

    public TaskCursorAdapter(Context context) {
        super(context, null);
        this.context = context;
    }

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        Task task = Task.fromCursor(cursor);
        ((TaskViewHolder) holder).title.setText(task.getTitle());
        ((TaskViewHolder) holder).detail.setText(task.getDetail());
        int resId = context.getResources().getIdentifier(task.getImgPath(), "drawable", context.getPackageName());
        ((TaskViewHolder) holder).icon.setImageResource(resId);
        ((TaskViewHolder) holder).time.setText(task.getDate());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.task_item,parent,false);
        TaskViewHolder bvh=new TaskViewHolder(v,this);
        return bvh;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView detail;
        TextView title;
        TextView time;
        ImageView icon;
        TaskCursorAdapter mAdapter;
        public TaskViewHolder(View itemView,TaskCursorAdapter adapter) {
            super(itemView);
            detail = (TextView) itemView.findViewById( R.id.task_title_2);
            title = (TextView) itemView.findViewById(R.id.task_title_1);
            time = (TextView) itemView.findViewById(R.id.task_title_3);
            icon = (ImageView) itemView.findViewById(R.id.task_img);
            mAdapter = adapter;
            CardView cv = (CardView) itemView.findViewById(R.id.cv_task);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = (Cursor) mAdapter.getItem(getAdapterPosition());
                    Task task = Task.fromCursor(cursor);
                    if(mAdapter.onItemClickListener!=null){
                        mAdapter.onItemClickListener.onItemClick(task,getAdapterPosition());
                    }
                }
            });
        }
    }
}
