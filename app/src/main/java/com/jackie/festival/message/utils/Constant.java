package com.jackie.festival.message.utils;

/**
 * Created by Jackie on 2016/2/18.
 * 常量
 */
public class Constant {
    public static final String FESTIVAL_ID = "festival_id";
    public static final String MESSAGE_ID = "message_id";

    public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";

    public static final String DB_NAME = "sent_sms.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "sent_sms";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_CONTACT_NAME = "contact_name";
    public static final String COLUMN_CONTACT_NUMBER = "contact_number";
    public static final String COLUMN_FESTIVAL_NAME = "festival_name";
    public static final String COLUMN_DATE = "date";

    public static final String AUTHORITY = "com.jackie.sms.provider";
    public static final String SMS_ALL = "sms";
    public static final String SMS = "sms/#";
}
