package com.jackie.festival.message.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jackie.festival.message.R;
import com.jackie.festival.message.bean.Message;
import com.jackie.festival.message.utils.Constant;
import com.jackie.festival.message.utils.FestivalLab;

import java.util.List;

/**
 * Created by Jackie on 2016/2/18.
 * 发送短信界面
 */
public class ChooseMessageActivity extends AppCompatActivity {
    private int mFestivalId;
    private ListView mListView;
    private FloatingActionButton mFabToSend;
    private LayoutInflater mInflater;

    private List<Message> mMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            mFestivalId = getIntent().getIntExtra(Constant.FESTIVAL_ID, -1);
            mMessageList = FestivalLab.getInstance().getMessageByFestivalId(mFestivalId);
        }
        mInflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_choose_message);

        setTitle(FestivalLab.getInstance().getFestivalById(mFestivalId).getName());
        initView();
        initEvent();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.message_list_view);
        mFabToSend = (FloatingActionButton) findViewById(R.id.fab_to_send);

        mListView.setAdapter(new ArrayAdapter<Message>(this, -1, mMessageList) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_message, parent, false);
                }

                TextView content = (TextView) convertView.findViewById(R.id.content);
                Button toSend = (Button) convertView.findViewById(R.id.to_send);

                content.setText(getItem(position).getContent());
                toSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChooseMessageActivity.this, SendMessageActivity.class);
                        intent.putExtra(Constant.FESTIVAL_ID, mFestivalId);
                        intent.putExtra(Constant.MESSAGE_ID, getItem(position).getId());
                        startActivity(intent);
                    }
                });
                return convertView;
            }
        });
    }

    private void initEvent() {
        mFabToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseMessageActivity.this, SendMessageActivity.class);
                intent.putExtra(Constant.FESTIVAL_ID, mFestivalId);
                intent.putExtra(Constant.MESSAGE_ID, -1);
                startActivity(intent);
            }
        });
    }
}
