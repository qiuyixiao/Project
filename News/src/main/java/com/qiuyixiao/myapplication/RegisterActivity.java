package com.qiuyixiao.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.qiuyixiao.dao.UserDao;

import java.io.FileInputStream;
import java.io.InputStream;
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_name,et_psd;
    ImageView logo_img;
    Button logl_btn,btn_commit;
    UserDao dao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        dao = new UserDao(RegisterActivity.this);
        init();
    }
    void init(){
        et_name = (EditText) findViewById(R.id.un_register);
        et_psd = (EditText) findViewById(R.id.psd_register);
        logo_img = (ImageView) findViewById(R.id.logo_img);
        //选择头像
        logl_btn = (Button) findViewById(R.id.logo_btn);
        logl_btn.setOnClickListener(this);
        //确定按钮
        btn_commit = (Button) findViewById(R.id.button_commit);
        btn_commit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logo_btn:
                Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, 110);
                break;
            case  R.id.button_commit:
                //判断账号不为空
                String username = et_name.getText().toString();
                if (username!=null&&!username.equals("")){
                    String password = et_psd.getText().toString();
                    boolean isExist = dao.insert(username,password,picturePath);
                    if (isExist){//默认为true，表示注册成功
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.putExtra("name", username);
                        intent.putExtra("psd",password);
                        //回传信息  对应的是前一个Activity的startActivityForResult()方法
                        //这个方法不带有跳转效果
                        setResult(101, intent);
//                        startActivity(intent);
                        //杀死当前页面 从内存的栈中取出登录页面
                        finish();
                    }else {
                        Toast.makeText(this, "账号已存在，请重新注册", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    String picturePath = "file:///android_assets/tim.jpg";//默认图片路径
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if(requestCode == 110 && resultCode == Activity.RESULT_OK && null != intent){

            Uri selectedImage = intent.getData();
            String[] filePathColumns={MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage,
                    filePathColumns, null,null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            picturePath= c.getString(columnIndex);
            c.close();
            try {
                InputStream is = new FileInputStream(picturePath);
                logo_img.setImageBitmap(BitmapFactory.decodeStream(is));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
