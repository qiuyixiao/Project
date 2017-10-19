package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.NewsBean;
import com.example.test111.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by wangye on 2017/8/2.
 */

public class NewsAdapter extends BaseAdapter {

    Context c;
    NewsBean newsBean;

    public NewsAdapter(Context c, NewsBean newsBean) {
        this.c = c;
        this.newsBean = newsBean;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.ic_launcher)// 加载开始默认的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)     //url爲空會显示该图片，自己放在drawable里面的
                .showImageOnFail(R.mipmap.ic_launcher)                //加载图片出现问题，会显示该图片
                .cacheInMemory()                                               //缓存用
                .cacheOnDisc()                                                  //缓存用
                .displayer(new RoundedBitmapDisplayer(10))       //图片圆角显示，值为整数
                .build();

        ImageLoaderConfiguration config2 = new ImageLoaderConfiguration.Builder(c)
                .defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//        .enableLogging() //  1.8.6,把这句删除
                .build();
        ImageLoader.getInstance().init(config2);
    }

    @Override
    public int getCount() {
        if (newsBean == null) {
            return 0;
        }
        Log.d("d","getResult():"+newsBean.getResult());
        return newsBean.getResult().getData().size();
    }

    @Override
    public Object getItem(int i) {
        return newsBean.getResult().getData().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            view = View.inflate(c,R.layout.item_list,null);
            holder = new ViewHolder();
            holder.tv_title = view.findViewById(R.id.text_title);
            holder.iv_01 = view.findViewById(R.id.iv_01);
            holder.iv_02 = view.findViewById(R.id.iv_02);
            holder.iv_03 = view.findViewById(R.id.iv_03);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_title.setText(newsBean.getResult().getData().get(i).getTitle());
//    holder.txTime.setText(newsBean.getResult().getData().get(i).getDate());
//    holder.txAuthor.setText(newsBean.getResult().getData().get(i).getAuthor_name());
        ImageLoader.getInstance().displayImage(
                newsBean.getResult().getData().get(i).getThumbnail_pic_s()
                ,holder.iv_01);
        ImageLoader.getInstance().displayImage(
                newsBean.getResult().getData().get(i).getThumbnail_pic_s02()
                ,holder.iv_02);
        ImageLoader.getInstance().displayImage(
                newsBean.getResult().getData().get(i).getThumbnail_pic_s03()
                ,holder.iv_03);

        return view;
    }
    class ViewHolder{
        //    private ImageView imageView;
//    private TextView txTitle,txTime,txAuthor;
        TextView tv_title;
        ImageView iv_01,iv_02,iv_03;
    }

    public void updateAdapter(NewsBean newsBean){
        this.newsBean = newsBean;
        notifyDataSetChanged();
        Log.d("ddd","刷新  刷新");
    }


}
