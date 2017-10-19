package com.qiuyixiao.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.qiuyixiao.adapter.MyPagerAdapter;
import com.qiuyixiao.bean.PagerData;

import java.util.ArrayList;



public class PagerActivity extends Activity{

    ViewPager pager;
    Button button;
    SharedPreferences sh;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pager);
        sh = getSharedPreferences("user", MODE_PRIVATE);
        editor = sh.edit();
        pager = (ViewPager) findViewById(R.id.pager);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PagerActivity.this,
                        MainActivity.class);
                startActivity(intent);
                editor.putBoolean("isRun", true);
                editor.commit();
                finish();
            }
        });
        initPager();

    }

    void initPager() {

        final ArrayList<ImageView> arrayList = PagerData.getData(this);

        pager.setAdapter(new MyPagerAdapter(this, arrayList));

        pager.setCurrentItem(0);

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    SystemClock.sleep(5000);

                    PagerActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pager.setCurrentItem(pager.getCurrentItem() + 1);
                        }
                    });
                }

            }
        }).start();

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                if (arg0 == (arrayList.size() - 1)) {
                    // 给Button重新设置可见性
                    button.setVisibility(View.VISIBLE);

                } else {
                    button.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

}
