package com.jackie.festival.message.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.jackie.festival.message.utils.Constant;

/**
 * Created by Jackie on 2016/2/19.
 * 短信内容提供者
 */
public class SmsProvider extends ContentProvider {
    private final static int SMS_ALL = 1;
    private final static int SMS_ONE = 2;

    private final static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        mUriMatcher.addURI(Constant.AUTHORITY, Constant.SMS_ALL, SMS_ALL);
        mUriMatcher.addURI(Constant.AUTHORITY, Constant.SMS, SMS_ONE);
    }

    private SmsOpenHelper mSmsOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;

    @Override
    public boolean onCreate() {
        mSmsOpenHelper = SmsOpenHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (mUriMatcher.match(uri)) {
            case SMS_ALL:
                break;
            case SMS_ONE:
                long id = ContentUris.parseId(uri);
                selection = "_id = ?";
                selectionArgs = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Wrong URI " + uri);
        }

        mSQLiteDatabase = mSmsOpenHelper.getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.query(Constant.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        mSQLiteDatabase = mSmsOpenHelper.getWritableDatabase();
        long rowId = mSQLiteDatabase.insert(Constant.TABLE_NAME, null, values);
        if (rowId > 0) {
            //向外界通知该ContentProvider里的数据发生了变化 ,以便ContentObserver作出相应
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, rowId);
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
