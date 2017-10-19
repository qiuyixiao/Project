package com.qiuyixiao.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by lenovo on 2017/10/15.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_music,btn_night,btn_loginOut;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.setting);
        init();
    }
    void init(){
        btn_music = (Button) findViewById(R.id.button_music);
        btn_night = (Button) findViewById(R.id.button_night);
        btn_loginOut = (Button) findViewById(R.id.button_loginOut);
        btn_music.setOnClickListener(this);
        btn_night.setOnClickListener(this);
        btn_loginOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_music:
                music();
                break;
            case R.id.button_night:
                night();
                break;
            case R.id.button_loginOut:
                loginOut();
                break;
        }
    }
    void music(){


    }
    void night(){

    }
    void loginOut(){

    }
}
