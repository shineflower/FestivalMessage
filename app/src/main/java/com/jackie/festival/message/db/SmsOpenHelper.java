package com.jackie.festival.message.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jackie.festival.message.utils.Constant;

/**
 * Created by Jackie on 2016/2/19.
 * 数据库操作相关的类
 */
public class SmsOpenHelper extends SQLiteOpenHelper {
    private SmsOpenHelper(Context context) {
        super(context.getApplicationContext(), Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    private static SmsOpenHelper mInstance;

    public static SmsOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SmsOpenHelper.class) {
                if (mInstance == null) {
                    mInstance = new SmsOpenHelper(context);
                }
            }
        }

        return mInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Constant.TABLE_NAME + "(" + "_id integer primary key autoincrement,"
                     + Constant.COLUMN_CONTENT + " text, "
                     + Constant.COLUMN_CONTACT_NAME + " text,"
                     + Constant.COLUMN_CONTACT_NUMBER + " text, "
                     + Constant.COLUMN_FESTIVAL_NAME + " text, "
                     + Constant.COLUMN_DATE + " integer" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
