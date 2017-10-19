package com.qiuyixiao.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.support.design.widget.CollapsingToolbarLayout;

import org.xutils.image.ImageOptions;
import org.xutils.x;


public class OnClickActivity extends AppCompatActivity {

    private ImageView img;
    private CollapsingToolbarLayout CollapsingToolbarLayout;//伸缩折叠
    private WebView webView;
    private ProgressBar progressBar;
    Toolbar toolbar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onclick_item);
        init();
    }
    void init(){
        img = (ImageView) findViewById(R.id.onclick_img);
        CollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout);
     toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.onclick_progressBar);
        setContent();
    }
    void setContent(){
        Intent intent = getIntent();
        String url = intent.getStringExtra("body");
        String imgUrl = intent.getStringExtra("imgUrl");
        String title = intent.getStringExtra("title");
        CollapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#FF969190"));
        CollapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#FFFFFF"));
        CollapsingToolbarLayout.setTitle("☚  "+title);
        CollapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickActivity.this.finish();
            }
        });
        toolbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickActivity.this.finish();
            }
        });
//        XUtils框架加载图片
        ImageOptions.Builder builder = new ImageOptions.Builder();
        ImageOptions build = builder
                .setFailureDrawable(getResources().getDrawable(R.mipmap.ic_image_loadfail))
                .setLoadingDrawable(getResources().getDrawable(R.mipmap.ic_image_loading))
                .build();
        x.image().bind(img,imgUrl,build);
        webView.loadUrl(url);
//        用webView加载页面
        WebSettings webSettings = webView.getSettings();
        //设置兼容JS
        webSettings.setJavaScriptEnabled(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
