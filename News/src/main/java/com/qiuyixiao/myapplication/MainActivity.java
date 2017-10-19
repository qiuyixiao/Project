package com.qiuyixiao.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiuyixiao.dao.UserDao;
import com.qiuyixiao.fragment.Img;
import com.qiuyixiao.fragment.News;
import com.qiuyixiao.fragment.Weather;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collaps;
    News mf1;
    Img mf2;
    Weather mf3;
    FragmentManager fm;
    FragmentTransaction ft;
    Toolbar toolbar;
    DrawerLayout dw;
    NavigationView navigationView;
    ImageView iv_login;
    TextView tv_login;
    UserDao dao;
    SharedPreferences sp;
    boolean isNight;
    MediaPlayer mediaPlayer;
    int i ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("loonggg", this.MODE_PRIVATE);
        init();
        initFragment();
    }
    private void initFragment() {
        fm =getSupportFragmentManager();
        ft = fm.beginTransaction();
        mf1 = new News();
        mf2 = new Img();
        mf3 = new Weather();
        ft.add(R.id.content, mf1);
        ft.add(R.id.content, mf2);
        ft.add(R.id.content, mf3);
        ft.show(mf1);
        ft.hide(mf2);
        ft.hide(mf3);
        ft.commit();
    }
    private void init() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        View view = navigationView.inflateHeaderView(R.layout.head);
//        tv_login = view.findViewById(R.id.tv_loginname);
//        String countName = getIntent().getStringExtra("count");
//        tv_login.setText(countName);
//        String path = dao.selectCountByName(countName).getCountLogo();
//        Log.d("aaaaaa",path);
        iv_login = view.findViewById(R.id.iv_login);
        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));

            }
        });
//        try {
//      InputStream inputStream = new FileInputStream(path);
//      igLogo.setImageBitmap(BitmapFactory.decodeStream(inputStream));
//            if (path==null){
//                Resources resources = getApplicationContext().getResources();
//                Drawable drawable = resources.getDrawable(R.drawable.logo1);
//                iv_login.setBackgroundDrawable(drawable);
//            }else {
//                iv_login.setImageBitmap(BitmapFactory.decodeFile(path));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        dw = (DrawerLayout) findViewById(R.id.dw);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.daohang);//设置导航图标
        toolbar.setTitle("今日头条");
        toolbar.setTitleMargin(0,18,0,0);//设置文字上下左右间距
        toolbar.setTitleTextAppearance(MainActivity.this,R.style.title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dw.openDrawer(Gravity.LEFT);//打开侧滑页面
            }
        });
        //设置溢出菜单
//        toolbar.setPopupTheme(R.style.PopupMenu);
        toolbar.inflateMenu(R.menu.toobar_menu);//设置menu菜单

        //设置menu菜单点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int item_id = item.getItemId();
                switch (item_id){
                    case R.id.set:
                        startActivity(new Intent(MainActivity.this,SettingActivity.class));
                        break;
                    case R.id.fx:
                        showShare();
                        break;
                    case R.id.logo:
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        break;
                }
                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch(item.getItemId()){
                    case R.id.news:
                        toolbar.setTitle("新闻");
                        ft = fm.beginTransaction();
                        ft.show(mf1);
                        ft.hide(mf2);
                        ft.hide(mf3);
                        ft.commit();
                        dw.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.img:
                        toolbar.setTitle("图片");
                        ft = fm.beginTransaction();
                        ft.show(mf2);
                        ft.hide(mf1);
                        ft.hide(mf3);
                        ft.commit();
                        dw.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.weather:
                        toolbar.setTitle("天气");
                        ft = fm.beginTransaction();
                        ft.show(mf3);
                        ft.hide(mf2);
                        ft.hide(mf1);
                        ft.commit();
                        dw.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_bgmic:
                        music();
                        break;
                    case R.id.nav_night:
                        isNight = sp.getBoolean("night", false);
                        if (isNight) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            sp.edit().putBoolean("night", false).commit();
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            sp.edit().putBoolean("night", true).commit();
                        }
                        recreate();
                        return true;


                }
                return false;
            }
        });
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
    void music(){
//        int posiition =-1;
        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.fushengweixie);
//        if (position==-1){
            mediaPlayer.start();
//        }else {
            mediaPlayer.pause();
//        }
    }
}
