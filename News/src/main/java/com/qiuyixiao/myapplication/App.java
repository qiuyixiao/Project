package com.qiuyixiao.myapplication;

import android.app.Application;

import com.yanzhenjie.nohttp.NoHttp;

import org.xutils.*;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(org.xutils.BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        NoHttp.initialize(this);
    }
}
