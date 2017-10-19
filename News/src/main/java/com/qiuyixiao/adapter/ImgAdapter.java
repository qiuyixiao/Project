package com.qiuyixiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.qiuyixiao.bean.ImgBean;
import com.qiuyixiao.myapplication.R;

import java.util.ArrayList;

public class ImgAdapter extends RecyclerView.Adapter<MyViewHolder4>{
    Context c;
    ArrayList<ImgBean> list;
    public ImgAdapter(Context c, ArrayList<ImgBean> list){
        this.c = c;
        this.list = list;
    }

    @Override
    public MyViewHolder4 onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder4 myviewholder6 =  new MyViewHolder4(LayoutInflater.from(c).inflate(R.layout.tpadapter,parent,false));
        return myviewholder6;
    }

    @Override
    public void onBindViewHolder(MyViewHolder4 holder, int position) {
        holder.tptv1.setText(list.get(position).getTitle());
        String imgsrc = list.get(position).getSourceurl();
        Picasso
                .with(c)
                .load(imgsrc)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.tpimg1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class MyViewHolder4 extends RecyclerView.ViewHolder{
    TextView tptv1;
    ImageView tpimg1;
    public MyViewHolder4(View itemView) {
        super(itemView);
        tptv1 = (TextView) itemView.findViewById(R.id.tptv1);
        tpimg1 = (ImageView) itemView.findViewById(R.id.tpimg1);
    }
}