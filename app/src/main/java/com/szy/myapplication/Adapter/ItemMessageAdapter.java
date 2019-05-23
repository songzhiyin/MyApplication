package com.szy.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lib.szy.pullrefresh.PullreFresh.BaseRecyAdapter;
import com.szy.lib.network.Glide.GlideHelper;
import com.szy.myapplication.Entity.MessageEntity;
import com.szy.myapplication.R;
import com.szy.myapplication.RecyclerView.ItemTouchHelperAdapterListener;
import com.szy.myapplication.RecyclerView.ItemTouchHelperViewHolderListener;

/**
 * Created by Administrator on 2018/1/12 0012.
 * 消息的adapter类
 */

public class ItemMessageAdapter extends BaseRecyAdapter<ItemMessageAdapter.ViewHoder, MessageEntity> implements ItemTouchHelperAdapterListener {
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
        GlideHelper.showImage(mContext, entity.getUrl(), viewHoder.img_icon, R.mipmap.ic_launcher);


    }

    @Override
    public void onAdapterItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
    }


    class ViewHoder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolderListener {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_time;
        private ImageView img_icon;
        private LinearLayout line_layout;

        public ViewHoder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_item_message_title);
            tv_content = itemView.findViewById(R.id.tv_item_message_conten);
            tv_time = itemView.findViewById(R.id.tv_item_message_time);
            img_icon = itemView.findViewById(R.id.img_item_message_icon);
            line_layout = itemView.findViewById(R.id.line_layout);
        }

        @Override
        public void onSelectedChanged() {
            line_layout.setScaleX(1.2f);
            line_layout.setScaleY(1.2f);
//            line_layout.setBackgroundColor(mContext.getResources().getColor(R.color.homeColor));

        }

        @Override
        public void onFinishChanged() {
            line_layout.setScaleX(1.0f);
            line_layout.setScaleY(1.0f);
//            line_layout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }
}
