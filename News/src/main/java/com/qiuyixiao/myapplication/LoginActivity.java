package com.qiuyixiao.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qiuyixiao.dao.UserDao;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_uname;
    EditText et_psd;
    Button login;
    TextView register;
    CheckBox cb_readpsd;
    CheckBox autologin;
    String username;
    String password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog dialog;
    UserDao dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dao = new UserDao(LoginActivity.this);
        sharedPreferences = getSharedPreferences("first",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        init();
        restoreUIbySharedPreferences();

    }

    void init(){
        //查找控件
        et_uname = (EditText) findViewById(R.id.un_login);
        et_psd = (EditText) findViewById(R.id.psd_login);
        login = (Button) findViewById(R.id.button_login);
        register = (TextView) findViewById(R.id.tv_register);
        cb_readpsd = (CheckBox) findViewById(R.id.cb_readpsd);
        autologin = (CheckBox) findViewById(R.id.cb_autologin);
        //给登录注册按钮设置点击事件
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        cb_readpsd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                参数b表示选中状态  true选中  false  取消选中
//                cb_readpsd.isChecked();//判断是否被选中 返回值true选中  false取消选中
                if (b==false){
                    autologin.setChecked(false);
                }
            }
        });
        autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//               写判断语句时， 在没有给boolean赋值时，Boolean的默认值是true，因为只有那样判断语句才可以运行
                if (b==true){
                    cb_readpsd.setChecked(true);//通过代码设置复选框为选中状态
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                //带有返回值的跳转
                startActivityForResult(i,100);
                break;
            case R.id.button_login:
                login();
                break;
        }
    }
    //用户点击登录按钮后的事件
    void login(){
        username = et_uname.getText().toString();
        password = et_psd.getText().toString();
        if ((username!=null&&!username.equals(""))&&(password!=null&&!password.equals(""))){
            String data = dao.userisExit(username,password);
            Toast.makeText(LoginActivity.this,data,Toast.LENGTH_LONG).show();
            if (data.equals("登陆成功")){
                //登录成功后，再考虑2个复选框的状态
                judgeCheckBoxIsChecked();
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                i.putExtra("count",username);
                startActivity(i);
                finish();
            }
        }else {
            Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();

        }
    }
    void judgeCheckBoxIsChecked(){
        //根据逻辑上的优先级  先判断自动登陆的复选框 再判断保存账号
        if(autologin.isChecked()){
            editor.putString("name",et_uname.getText().toString());
            editor.putString("psd",et_psd.getText().toString());
            editor.putBoolean("readpsd",true);
            editor.putBoolean("autologin",true);
            editor.commit();
        }else if(cb_readpsd.isChecked()){
            editor.putString("name",et_uname.getText().toString());
            editor.putString("psd",et_psd.getText().toString());
            editor.putBoolean("readpsd",true);
            editor.putBoolean("autologin",false);
            editor.commit();
        }
    }
    //接收返回的消息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //验证请求码和返回码
        if(requestCode == 100 && resultCode == 101){
            //接收返回的信息
            String count = data.getStringExtra("name");
            String psw = data.getStringExtra("psd");
            et_uname.setText(count);
            et_psd.setText(psw);
        }
    }
    void restoreUIbySharedPreferences(){
        if(sharedPreferences.getBoolean("autologin",false)){
            et_uname.setText(sharedPreferences.getString("name",""));
            et_psd.setText(sharedPreferences.getString("psd",""));
            cb_readpsd.setChecked(true);
            autologin.setChecked(true);
            dialog = ProgressDialog.show(LoginActivity.this,"自动登录中...",null);
            dialog.setCanceledOnTouchOutside(false);
            new Thread(){
                public void run(){
                    try {
                        sleep(5000);
                        runOnUiThread(new Thread(){
                            public void run(){
                                dialog.dismiss();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                i.putExtra("name",sharedPreferences.getString("name",""));
                                Log.d("data","fasong:"+sharedPreferences.getString("name",""));
                                startActivity(i);
                                finish();
                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }else if(sharedPreferences.getBoolean("readpsd",false)){
            et_uname.setText(sharedPreferences.getString("name",""));
            et_psd.setText(sharedPreferences.getString("psd",""));
            cb_readpsd.setChecked(true);
        }

    }

    
}
