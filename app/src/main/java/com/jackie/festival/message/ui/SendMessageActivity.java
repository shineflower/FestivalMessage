package com.jackie.festival.message.ui;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jackie.festival.message.R;
import com.jackie.festival.message.bean.SentMessage;
import com.jackie.festival.message.service.SmsService;
import com.jackie.festival.message.utils.Constant;
import com.jackie.festival.message.utils.FestivalLab;
import com.jackie.festival.message.view.FlowLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jackie on 2016/2/18.
 * 发送短信界面
 */
public class SendMessageActivity extends AppCompatActivity {
    private int mFestivalId;
    private int mMessageId;

    private EditText mContent;
    private Button mContact;
    private FlowLayout mContactLayout;
    private FloatingActionButton mFabToSend;
    private FrameLayout mStatusLayout;
    private LayoutInflater mInflater;

    private Set<String> mContactNameSet = new HashSet<>();
    private Set<String> mContactNumberSet = new HashSet<>();

    private PendingIntent mSentIntent;
    private PendingIntent mDeliverIntent;
    private BroadcastReceiver mSentReceiver;
    private BroadcastReceiver mDeliverReceiver;

    private SmsService mSmsService;

    private int mTotalCount;
    private int mSentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            mFestivalId = getIntent().getIntExtra(Constant.FESTIVAL_ID, -1);
            mMessageId = getIntent().getIntExtra(Constant.MESSAGE_ID, -1);
        }

        mInflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_send_message);

        setTitle(FestivalLab.getInstance().getFestivalById(mFestivalId).getName());
        initView();
        initEvent();
        initReceiver();
    }

    private void initView() {
        mContent = (EditText) findViewById(R.id.content);
        if (mMessageId != -1) {
            mContent.setText(FestivalLab.getInstance().getMessageById(mMessageId).getContent());
        }

        mContact = (Button) findViewById(R.id.contact);
        mContactLayout = (FlowLayout) findViewById(R.id.layout_contacts);
        mFabToSend = (FloatingActionButton) findViewById(R.id.fab_to_send);
        mStatusLayout = (FrameLayout) findViewById(R.id.layout_status);
        mStatusLayout.setVisibility(View.GONE);

        mSmsService = new SmsService(this);
    }

    private void initEvent() {
        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动系统联系人
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, RESULT_FIRST_USER);
            }
        });

        mFabToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContactNumberSet.size() == 0) {
                    Toast.makeText(SendMessageActivity.this, "请选择联系人", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mContent.getText().toString())) {
                    Toast.makeText(SendMessageActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mStatusLayout.setVisibility(View.VISIBLE);
                mTotalCount = mSmsService.sendMessage(mContactNumberSet, buildSentMessage(mContent.getText().toString()), mSentIntent, mDeliverIntent);
                mSentCount = 0;
            }
        });
    }

    private SentMessage buildSentMessage(String content) {
        SentMessage sentMessage = new SentMessage();
        sentMessage.setContent(content);
        String names = "";
        for (String name : mContactNameSet) {
            names += name + ":";
        }
        sentMessage.setContactName(names.substring(0, names.length() - 1));
        String numbers = "";
        for (String number : mContactNumberSet) {
            numbers += number + ":";
        }
        sentMessage.setContactNumber(numbers.substring(0, numbers.length() - 1));
        return sentMessage;
    }

    private void initReceiver() {
        Intent sentIntent = new Intent(Constant.ACTION_SEND_MSG);
        mSentIntent = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        Intent deliverIntent = new Intent(Constant.ACTION_DELIVER_MSG);
        mDeliverIntent = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        registerReceiver(mSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mSentCount++;
                Toast.makeText(SendMessageActivity.this, mSentCount + "/" + mTotalCount + "短信发送成功", Toast.LENGTH_SHORT).show();
                if (mSentCount == mTotalCount) {
                    finish();
                }
            }
        }, new IntentFilter(Constant.ACTION_SEND_MSG));

        registerReceiver(mDeliverReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            }
        }, new IntentFilter(Constant.ACTION_DELIVER_MSG));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSentReceiver);
        unregisterReceiver(mDeliverReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_FIRST_USER) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor contactCursor = getContentResolver().query(contactUri, null, null, null, null);
                if (contactCursor.moveToFirst()) {
                    String contactName = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    //获取相应联系人的电话号码
                    String contactNumber = getContactNumber(contactCursor);
                    if (!TextUtils.isEmpty(contactNumber)) {
                        mContactNameSet.add(contactName);
                        mContactNumberSet.add(contactNumber);

                        addContact(contactName);
                    }
                }
            }
        }
    }

    private String getContactNumber(Cursor cursor) {
        String contactNumber = null;
        int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        if (numberCount > 0) {
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor numberCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { String.valueOf(contactId) }, null);
            if (numberCursor.moveToFirst()) {
                contactNumber = numberCursor.getString(numberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            numberCursor.close();
        }

        cursor.close();
        return contactNumber;
    }

    private void addContact(String contactName) {
        //防止每次都添加相同的联系人
        int childCount  = mContactLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            String childText = ((TextView)mContactLayout.getChildAt(i)).getText().toString();
            if (contactName.equals(childText)) {
                return;
            }
        }

        TextView textView = (TextView) mInflater.inflate(R.layout.contact_name, mContactLayout, false);
        textView.setText(contactName);
        mContactLayout.addView(textView);
    }
}