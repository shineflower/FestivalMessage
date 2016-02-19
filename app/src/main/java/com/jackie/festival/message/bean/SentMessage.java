package com.jackie.festival.message.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jackie on 2016/2/19.
 * 发送的短信
 */
public class SentMessage {
    private int mId;
    private String mContent;
    private String mContactNumber;
    private String mContactName;
    private String mFestivalName;
    private Date mDate;
    private String mDateString;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getContactNumber() {
        return mContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.mContactNumber = mContactNumber;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String contactName) {
        this.mContactName = contactName;
    }

    public String getFestivalName() {
        return mFestivalName;
    }

    public void seFestivalName(String festivalName) {
        this.mFestivalName = festivalName;
    }

    public String getDateString() {
        mDateString = format.format(mDate);
        return mDateString;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }
}
