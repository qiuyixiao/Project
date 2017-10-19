package com.qiuyixiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.qiuyixiao.bean.OnItemClickListener;
import com.qiuyixiao.bean.T1350383429665Bean;
import com.qiuyixiao.myapplication.R;

import java.util.ArrayList;
public class JokeAdapter extends RecyclerView.Adapter<MyViewHolder3> {
    private ArrayList<T1350383429665Bean> arrayList;
    private Context mContext;
    OnItemClickListener listener;
    public JokeAdapter(ArrayList<T1350383429665Bean> arrayList, Context context) {
        this.arrayList=arrayList;
        mContext=context;
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void refreshData(ArrayList<T1350383429665Bean> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        MyViewHolder3 myViewHolder3 = new MyViewHolder3(inflate);
        return myViewHolder3;
    }

    @Override
    public void onBindViewHolder(MyViewHolder3 holder, final int position) {
        Picasso.with(mContext)
                .load(arrayList.get(position).getImgsrc())
                .placeholder(R.mipmap.ic_image_loading)
                .error(R.mipmap.ic_image_loadfail)
                .into(holder.img);
        holder.title.setText(arrayList.get(position).getLtitle());
        holder.desc.setText(arrayList.get(position).getDigest());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
class MyViewHolder3 extends RecyclerView.ViewHolder{


    public final ImageView img;
    public final TextView title;
    public final TextView desc;

    public MyViewHolder3(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);
        title = (TextView) itemView.findViewById(R.id.title);
        desc = (TextView) itemView.findViewById(R.id.desc);
    }
}