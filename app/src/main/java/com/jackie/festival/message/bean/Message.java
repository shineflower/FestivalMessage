package com.jackie.festival.message.bean;

/**
 * Created by Administrator on 2016/2/18.
 */
public class Message {
    private int mId;
    private int mFestivalId;
    private String mContent;

    public Message(int id, int festivalId, String content) {
        this.mId = id;
        this.mFestivalId = festivalId;
        this.mContent = content;
    }

    public int getId() {
        return mId;
    }

    public int getFestivalId() {
        return mFestivalId;
    }

    public String getContent() {
        return mContent;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setFestivalId(int festivalId) {
        this.mFestivalId = festivalId;
    }

    public void setContent(String content) {
        this.mContent = content;
    }
}
