package com.qiuyixiao.test;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qiuyixiao.adapter.ContentAdapter;
import com.qiuyixiao.bean.NewsBean;
import com.qiuyixiao.fragment.ContentFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    NewsBean newsBean;
    ContentAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        adapter = new ContentAdapter(MainActivity.this,newsBean);
        listView.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }
    void getData(String path){

    }

}
