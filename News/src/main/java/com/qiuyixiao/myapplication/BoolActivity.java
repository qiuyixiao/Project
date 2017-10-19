package com.qiuyixiao.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
public class BoolActivity extends Activity{

    ImageView ig,ig2;
    SharedPreferences sp;
    AlphaAnimation alpha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bool);
        sp = getSharedPreferences("user", MODE_PRIVATE);
        init();
    }

    void init() {
        ig = (ImageView) findViewById(R.id.imageView1);
//        ig2 = findViewById(R.id.imageView2);
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
//        alpha = (AlphaAnimation) AnimationUtils.loadAnimation(
//                BoolActivity.this, R.anim.alpha);
        aa.setDuration(5000);
        ig.startAnimation(aa);
//        ig2.startAnimation(alpha);
        new Thread(){
            @Override
            public void run() {

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (sp.getBoolean("isRun", false)) {
                    Intent intent = new Intent(BoolActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(BoolActivity.this, PagerActivity.class);
                    startActivity(intent);
                }


            };
        }.start();

    }

}
