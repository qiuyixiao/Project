package com.qiuyixiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiuyixiao.bean.NewsBean;
import com.qiuyixiao.test.R;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by lenovo on 2017/10/17.
 */

public class ContentAdapter extends BaseAdapter {
    Context context;
    NewsBean newsBean;
    public ContentAdapter(Context context,NewsBean news){
        this.context = context;
        newsBean = news;

    }
    @Override
    public int getCount() {
        if (newsBean==null){
            return 0;
        }else{
            return newsBean.getResult().getData().size();
        }

    }

    @Override
    public Object getItem(int position) {
        return newsBean.getResult().getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_list,null);
            holder = new MyHolder();
            holder.tv_title = convertView.findViewById(R.id.text_title);
            holder.iv_01 = convertView.findViewById(R.id.iv_01);
            holder.iv_02 = convertView.findViewById(R.id.iv_02);
            holder.iv_03 = convertView.findViewById(R.id.iv_03);
            convertView.setTag(holder);
        }else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.tv_title.setText(newsBean.getResult().getData().get(position).getTitle());
        Picasso.with(context)
                .load(newsBean.getResult().getData().get(position).getThumbnail_pic_s())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.iv_01);
        Picasso.with(context)
                .load(newsBean.getResult().getData().get(position).getThumbnail_pic_s02())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.iv_01);
        Picasso.with(context)
                .load(newsBean.getResult().getData().get(position).getThumbnail_pic_s03())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.iv_01);
        return convertView;


    }
    class MyHolder{
        TextView tv_title;
        ImageView iv_01,iv_02,iv_03;
    }
    public void updataDate(NewsBean bean){
        newsBean = bean;
        notifyDataSetChanged();
    }
}
