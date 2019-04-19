package com.tedu.pdapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2019/2/14.
 */

/**
 * 手机数据库的管理类
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * 构造方法:为父类的4参数构造赋值,其中3个为确定值
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, "pd.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String sql = "create table pd_user(username varchar(50))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
