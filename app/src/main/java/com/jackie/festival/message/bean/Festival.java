package com.jackie.festival.message.bean;

/**
 * Created by Jackie on 2016/2/18.
 * 节日实体类
 */
public class Festival {
    private int mId;
    private String mName;

    public Festival(int id, String name) {
        this.mId = id;
        this.mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
