package com.szy.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.szy.pullrefresh.PullreFresh.BaseRecyAdapter;
import com.szy.myapplication.Entity.MessageEntity;
import com.szy.myapplication.R;

/**
 * Created by Administrator on 2018/1/12 0012.
 * 消息的adapter类
 */

public class ItemMessageAdapter extends BaseRecyAdapter<ItemMessageAdapter.ViewHoder, MessageEntity> {
    public ItemMessageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(mInflater.inflate(R.layout.item_message_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHoder viewHoder = (ViewHoder) holder;
        MessageEntity entity = getItemData(position);
        viewHoder.tv_time.setText(entity.getTime());
        viewHoder.tv_title.setText(entity.getTitle());
        viewHoder.tv_content.setText(entity.getContent());


    }

    class ViewHoder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_time;
        private ImageView img_icon;

        public ViewHoder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_item_message_title);
            tv_content = itemView.findViewById(R.id.tv_item_message_conten);
            tv_time = itemView.findViewById(R.id.tv_item_message_time);
            img_icon = itemView.findViewById(R.id.img_item_message_icon);
        }

    }
}
