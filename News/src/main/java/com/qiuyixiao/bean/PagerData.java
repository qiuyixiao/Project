package com.qiuyixiao.bean;

import android.content.Context;
import android.widget.ImageView;


import com.qiuyixiao.myapplication.R;

import java.util.ArrayList;


public class PagerData {
    public static ArrayList<ImageView> getData(Context context) {

        int[] imgs = new int[]{
                R.drawable.timg1,
                R.drawable.timg2,
                R.drawable.timg3,
        };

        ArrayList<ImageView> list = new ArrayList<ImageView>();

        ImageView iv;
        for (int i = 0; i < imgs.length; i++) {
            iv = new ImageView(context);
            iv.setBackgroundResource(imgs[i]);

            list.add(iv);
        }
        return list;
    }

}
