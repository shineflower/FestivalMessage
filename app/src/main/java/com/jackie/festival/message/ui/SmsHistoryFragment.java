package com.jackie.festival.message.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jackie.festival.message.R;
import com.jackie.festival.message.utils.Constant;
import com.jackie.festival.message.view.FlowLayout;

/**
 * Created by Jackie on 2016/2/20.
 * 发送记录
 */
public class SmsHistoryFragment extends ListFragment {
    private LayoutInflater mInflater;
    private CursorAdapter mCursorAdapter;

    private static final int LOADER_ID = 1;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInflater = LayoutInflater.from(getContext());
        initLoader();
        setupListAdapter();
    }

    private void initLoader() {
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader cursorLoader = new CursorLoader(getContext(), Uri.parse("content://" + Constant.AUTHORITY + "/" + Constant.SMS_ALL), null, null, null, null);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if (loader.getId() == LOADER_ID) {
                    mCursorAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                if (loader.getId() == LOADER_ID) {
                    mCursorAdapter.swapCursor(null);
                }
            }
        });
    }

    private void setupListAdapter() {
        mCursorAdapter = new CursorAdapter(getContext(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return mInflater.inflate(R.layout.item_sent_message, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView content = (TextView) view.findViewById(R.id.content);
                FlowLayout flowLayout = (FlowLayout) view.findViewById(R.id.layout_contact);
                TextView festival = (TextView) view.findViewById(R.id.festival);
                TextView date = (TextView) view.findViewById(R.id.date);

                content.setText(cursor.getString(cursor.getColumnIndex(Constant.COLUMN_CONTENT)));
                festival.setText(cursor.getString(cursor.getColumnIndex(Constant.COLUMN_FESTIVAL_NAME)));
                date.setText(cursor.getString(cursor.getColumnIndex(Constant.COLUMN_DATE)));

                String names = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_CONTACT_NAME));
                if (TextUtils.isEmpty(names)) {
                    return;
                }
                flowLayout.removeAllViews();
                for (String name : names.split(":")) {
                    addContact(name, flowLayout);
                }
            }
        };

        setListAdapter(mCursorAdapter);
    }

    private void addContact(String name, FlowLayout flowLayout) {
        TextView textView = (TextView) mInflater.inflate(R.layout.contact_name, flowLayout, false);
        textView.setText(name);
        flowLayout.addView(textView);
    }
}
