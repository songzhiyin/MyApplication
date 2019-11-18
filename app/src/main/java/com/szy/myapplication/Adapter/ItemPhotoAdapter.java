package com.szy.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.szy.pullrefresh.PullreFresh.BaseRecyAdapter;
import com.szy.lib.network.Glide.GlideHelper;
import com.szy.myapplication.R;

public class ItemPhotoAdapter extends BaseRecyAdapter<ItemPhotoAdapter.ViewHoder, String> {
    public ItemPhotoAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(mInflater.inflate(R.layout.item_belle_phone_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String entity = getItemData(position);
        ViewHoder viewHoder = (ViewHoder) holder;
        if (entity != null && entity.length() > 0) {
            GlideHelper.showImage(mContext, entity, viewHoder.img_phone, R.mipmap.banner_white);
//            viewHoder.tv_name.setText(entity);
        }

    }

    class ViewHoder extends RecyclerView.ViewHolder {
        private ImageView img_phone;
        private TextView tv_name;

        public ViewHoder(View itemView) {
            super(itemView);
            img_phone = itemView.findViewById(R.id.img_photo_icon);
            tv_name = itemView.findViewById(R.id.tv_photo_name);
        }
    }
}
