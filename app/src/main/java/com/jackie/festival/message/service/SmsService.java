package com.jackie.festival.message.service;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.telephony.SmsManager;

import com.jackie.festival.message.bean.SentMessage;
import com.jackie.festival.message.utils.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * Created by Jackie on 2016/2/19.
 * 发送短信
 */
public class SmsService {
    private Context mContext;
    public SmsService(Context context) {
        this.mContext = context;
    }

    public int sendMessage(String number, String message, PendingIntent sentIntent, PendingIntent deliverIntent) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(message);
        for (String content : contents) {
            smsManager.sendTextMessage(number, null, content, sentIntent, deliverIntent);
        }

        return contents.size();
    }

    public int sendMessage(Set<String> numbers, SentMessage sentMessage, PendingIntent sentIntent, PendingIntent deliverIntent) {
        save(sentMessage);
        int result = 0;
        for (String number : numbers) {
            int count = sendMessage(number, sentMessage.getContent(), sentIntent, deliverIntent);
            result += count;
        }

        return result;
    }

    private void save(SentMessage sentMessage) {
        sentMessage.setDate(new Date());

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.COLUMN_CONTENT, sentMessage.getContent());
        contentValues.put(Constant.COLUMN_CONTACT_NAME, sentMessage.getContactName());
        contentValues.put(Constant.COLUMN_CONTACT_NUMBER, sentMessage.getContactNumber());
        contentValues.put(Constant.COLUMN_FESTIVAL_NAME, sentMessage.getFestivalName());
        contentValues.put(Constant.COLUMN_DATE, sentMessage.getDateString());

        mContext.getContentResolver().insert(Uri.parse("content://" + Constant.AUTHORITY + "/" + Constant.SMS_ALL), contentValues);
    }
}