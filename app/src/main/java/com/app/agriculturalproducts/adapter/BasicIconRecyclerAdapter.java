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
import com.app.agriculturalproducts.bean.MyIcon;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ALPHONSO on 2016/1/5.
 */
public class BasicIconRecyclerAdapter extends RecyclerView.Adapter<BasicIconRecyclerAdapter.BasicViewHolder>{

    private List<MyIcon> mData;
    private Context context;

    private OnAdpaterItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnAdpaterItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BasicIconRecyclerAdapter(List<MyIcon> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.icon_item,parent,false);
        BasicViewHolder bvh=new BasicViewHolder(v,this);
        return bvh;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.icon.setImageResource(mData.get(position).getIconID());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class BasicViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.icon_title)
        TextView title;
        @Bind(R.id.icon_img)
        ImageView icon;
        BasicIconRecyclerAdapter mAdapter;

        public BasicViewHolder(View itemView,BasicIconRecyclerAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mAdapter = adapter;
        }
        @OnClick(R.id.icon_view)
        void onItemClick(){
            if(mAdapter.onItemClickListener!=null){
                mAdapter.onItemClickListener.onItemClick(String.valueOf(title.getText()),getAdapterPosition());
            }
        }
    }
}
