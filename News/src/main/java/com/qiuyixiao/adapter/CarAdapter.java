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
import com.qiuyixiao.bean.T1348654060988Bean;
import com.qiuyixiao.myapplication.R;

import java.util.ArrayList;
public class CarAdapter extends RecyclerView.Adapter<MyViewHolder2>{
    Context mContext;
    ArrayList<T1348654060988Bean> arrayList;
    OnItemClickListener listener;
    public CarAdapter(Context mContext,ArrayList<T1348654060988Bean> arrayList){
        this.mContext = mContext;
        this.arrayList = arrayList;
    }
    public void refreshData(ArrayList<T1348654060988Bean> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        MyViewHolder2 myViewHolder2 = new MyViewHolder2(inflate);
        return myViewHolder2;
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, final int position) {
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
                listener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
class MyViewHolder2 extends RecyclerView.ViewHolder{


    public final ImageView img;
    public final TextView title;
    public final TextView desc;

    public MyViewHolder2(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.img);
        title = (TextView) itemView.findViewById(R.id.title);
        desc = (TextView) itemView.findViewById(R.id.desc);
    }
}
