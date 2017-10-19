package com.example.test111;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.NewsAdapter;
import com.example.bean.NewsBean;
import com.example.test111.R;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

public class MainActivity extends AppCompatActivity {
    NewsBean newsBean = null;
    NewsAdapter newsAdapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public  void init(){
        lv = (ListView) findViewById(R.id.lv_news);
        newsAdapter = new NewsAdapter(MainActivity.this, newsBean);
        lv.setAdapter(newsAdapter);
        new Thread() {
            public void run() {
                getData("http://v.juhe.cn/toutiao/index?type=shishang&key=2ca3a5b1cb6edf55250bff550ac34325");
            }
        }.start();
    }
    public void getData(String path) {
        try {
            //NOHTTP 添加依赖 请求数据
            //创建请求对象 设置泛型
            Request<String> request = NoHttp.createStringRequest(path,
                    RequestMethod.POST);
            request.add("type", "shishang");
            request.add("key", "2ca3a5b1cb6edf55250bff550ac34325");
            request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
            //创建请求队列，添加到队列中
            RequestQueue requestQueue = NoHttp.newRequestQueue();
            requestQueue.add(0, request, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    Gson gson = new Gson();
                    newsBean = gson.fromJson(response.get(), NewsBean.class);
                    Log.d("d", "response.get():" + response.get());
                    runOnUiThread(new Thread() {
                        public void run() {
                            newsAdapter.updateAdapter(newsBean);
                        }
                    });
                }

                @Override
                public void onFailed(int what, Response<String> response) {

                }

                @Override
                public void onFinish(int what) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
