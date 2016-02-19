package com.jackie.festival.message.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.jackie.festival.message.R;
import com.jackie.festival.message.bean.Festival;
import com.jackie.festival.message.utils.Constant;
import com.jackie.festival.message.utils.FestivalLab;

import java.util.List;

/**
 * Created by Jackie on 2016/2/18.
 * 节日短信Fragment
 */
public class FestivalCategoryFragment extends Fragment {
    private GridView mGridView;
    private List<Festival> mFestivalList;
    private LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        mFestivalList = FestivalLab.getInstance().getFestivals();
        return inflater.inflate(R.layout.fragment_festival_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mGridView = (GridView) view.findViewById(R.id.festival_grid_view);
        mGridView.setAdapter(new ArrayAdapter<Festival>(getContext(), -1, mFestivalList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_festival, parent, false);
                }

                TextView festivalName = (TextView) convertView.findViewById(R.id.festival_name);
                festivalName.setText(getItem(position).getName());
                return convertView;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChooseMessageActivity.class);
                intent.putExtra(Constant.FESTIVAL_ID, mFestivalList.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
