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
import com.qiuyixiao.bean.T1348647909107Bean;
import com.qiuyixiao.myapplication.R;

import java.util.ArrayList;
public class NewsAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<T1348647909107Bean> arrayList;
    private Context mContext;
    OnItemClickListener listener;
    public NewsAdapter(ArrayList<T1348647909107Bean> arrayList, Context context) {
        this.arrayList=arrayList;
        mContext=context;
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void refreshData(ArrayList<T1348647909107Bean> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Picasso.with(mContext)
                .load(arrayList.get(position).getImgsrc())
                .placeholder(R.mipmap.ic_image_loading)//在图片加载之前显示的默认图片
                .error(R.mipmap.ic_image_loadfail)//网络出错是显示的图片
                .into(holder.img);//放图片的控件
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
class MyViewHolder extends RecyclerView.ViewHolder{


    public final ImageView img;
    public final TextView title;
    public final TextView desc;

    public MyViewHolder(View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
        title = itemView.findViewById(R.id.title);
        desc =itemView.findViewById(R.id.desc);
    }
}
