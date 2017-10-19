package com.qiuyixiao.dao;

import android.content.Context;
import android.database.Cursor;

import com.qiuyixiao.bean.UserBean;
import com.qiuyixiao.helper.UserHelper;

import java.util.ArrayList;

public class UserDao {
    UserHelper helper;

    public UserDao(Context c) {

        helper = new UserHelper(c);
    }

    //插入用户数据
    public boolean insert(String name, String psd, String path) {
        //在插入数据前，先判断数据库中是否有相同的，是否已经存在
        boolean isExit = false;
        ArrayList<UserBean> list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                isExit = true;
                break;
            }
        }
        if (isExit) {
            return false;//账号存在，插入失败
        } else {
            String sql = "insert into users(name,psd,countlogo) values(?,?,?)";
            helper.db.execSQL(sql, new String[]{name, psd, path});
            return true;//插入成功
        }

    }

    //查询所有数据
    public ArrayList<UserBean> selectAll() {
        ArrayList<UserBean> allList = new ArrayList<>();
        String sql = "select * from users";
        Cursor c = helper.db.rawQuery(sql, null);//null表示所有
        //判断表中是否有数据
        if (c.getCount() != 0) {
            //将游标对象指向第一条数据之前
            c.moveToPrevious();
            while (c.moveToNext()) {
                UserBean ub = new UserBean();
                ub.setName(c.getString(c.getColumnIndex("name")));
                ub.setPsd(c.getString(c.getColumnIndex("psd")));
                ub.setCountLogo(c.getString(c.getColumnIndex("countlogo")));
                allList.add(ub);

            }
        }
        return allList;
    }

    //判断用户是否存在
    public String userisExit(String name, String psd) {
        boolean isExits = false;
        ArrayList<UserBean> list = selectAll();//把数据库中查询到的所有数据赋值给集合
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                isExits = true;
                break;
            }
        }
        //判断账号是否存在
        if (isExits) {//存在。
            //账号存在的情况下，再判断密码是否正确
            String count = judgeCountPsd(name, psd);
            return count;
        } else {//用户不存在
            return "账号不存在";

        }
    }

    //用户存在 判断密码是否正确 judge判断的英文
    public String judgeCountPsd(String name, String psd) {
        //账号已经存在  只需根据当前的账号  查找出数据库中对应账号密码  然后跟psw比较就可以了
        String sql = "select * from users where name = ?";
        Cursor cursor = helper.db.rawQuery(sql, new String[]{name});
        cursor.moveToFirst();//对于只有一条的数据 将游标对象的指针强制指向第一条
        String countPsw = cursor.getString(cursor.getColumnIndex("psd"));
        if (countPsw.equals(psd)) {
            return "登陆成功";
        } else {
            return "密码错误";
        }
    }

    //根据用户名 查找该数据在数据库中的所有信息
    public UserBean selectCountByName(String countName) {
        UserBean userBean = new UserBean();
        String sql = "select * from users where name = ?";
        Cursor cursor = helper.db.rawQuery(sql, new String[]{countName});
        cursor.moveToFirst();
        userBean.setCountLogo(cursor.getString(cursor.getColumnIndex("countlogo")));
        return userBean;

    }



//    // 删除用户
//    public void deleteUser(int id) {
//        String sql = "delete from users where id=?";
//        uh.db.execSQL(sql, new String[] { id + "" });
//    }
//
//    // 修改用户信息
//    public void updateUser(String name, String password, int id) {
//        String sql = "update users set name=?,password=? where id=?";
//        uh.db.execSQL(sql, new String[] { name, password, id + "" });
//    }
}
