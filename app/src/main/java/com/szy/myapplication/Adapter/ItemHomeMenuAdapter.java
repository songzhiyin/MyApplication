package com.szy.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szy.myapplication.Base.MyBaseAdapter;
import com.szy.myapplication.R;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class ItemHomeMenuAdapter extends MyBaseAdapter<String> {
    public ItemHomeMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        HoderView hoderView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_home_menu_adapter, null);
            hoderView = new HoderView(convertView);
            convertView.setTag(hoderView);
        } else {
            hoderView = (HoderView) convertView.getTag();
        }
        hoderView.tv_name.setText((String) getItem(position));
        return convertView;
    }

    private class HoderView {
        private TextView tv_name;

        public HoderView(View view) {
            this.tv_name = view.findViewById(R.id.tv_item_home_name);
        }
    }
}
