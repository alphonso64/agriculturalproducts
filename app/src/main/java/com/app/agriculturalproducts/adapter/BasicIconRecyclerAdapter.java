package com.app.agriculturalproducts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.agriculturalproducts.R;

import java.util.List;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class BasicIconRecyclerAdapter extends RecyclerView.Adapter<BasicIconRecyclerAdapter.BasicViewHolder>{

    private List<MyIcon> mData;
    private Context context;

    private OnItemClickListener onItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int p);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BasicIconRecyclerAdapter(List<MyIcon> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.icon_item,parent,false);
        BasicViewHolder bvh=new BasicViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,position);
            }
        });
        holder.title.setText(mData.get(position).getTitle());
        holder.icon.setImageResource(mData.get(position).getIconID());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class BasicViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        public BasicViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.icon_title);
            icon = (ImageView)itemView.findViewById(R.id.icon_img);
        }
    }
}
