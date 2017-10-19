package com.qiuyixiao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserHelper extends SQLiteOpenHelper {
	public SQLiteDatabase db = getWritableDatabase();

	public UserHelper(Context context) {
		super(context, "user.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	// 创建数据库 默认的只在数据库创建时执行一次
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table users(_id integer primary key autoincrement," +
				"name text not null,psd text,countlogo text)";
		db.execSQL(sql);
	}

	@Override
	// 刷新数据库
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
