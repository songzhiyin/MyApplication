package com.szy.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lib.szy.pullrefresh.PullreFresh.BaseRecyAdapter;
import com.szy.lib.network.Glide.GlideHelper;
import com.szy.myapplication.Entity.BellePhoneEntity;
import com.szy.myapplication.R;

public class ItemBellePhoneAdapter extends BaseRecyAdapter<ItemBellePhoneAdapter.ViewHoder, BellePhoneEntity.ResultsBean> {
    public ItemBellePhoneAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(mInflater.inflate(R.layout.item_belle_phone_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BellePhoneEntity.ResultsBean entity = getItemData(position);
        ViewHoder viewHoder = (ViewHoder) holder;
        if (entity.getUrl() != null && entity.getUrl().length() > 0) {
            GlideHelper.showImage(mContext, entity.getUrl(), viewHoder.img_phone, R.mipmap.banner2);
        }
    }

    class ViewHoder extends RecyclerView.ViewHolder {
        private ImageView img_phone;

        public ViewHoder(View itemView) {
            super(itemView);
            img_phone = itemView.findViewById(R.id.img_item_phone);
        }
    }
}
