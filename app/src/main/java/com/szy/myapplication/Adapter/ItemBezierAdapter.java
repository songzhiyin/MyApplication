package com.szy.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lib.szy.pullrefresh.PullreFresh.BaseRecyAdapter;
import com.szy.myapplication.R;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class ItemBezierAdapter extends BaseRecyAdapter<ItemBezierAdapter.ViewHoder, String> {
    private OnItemImgClickListner onItemImgClickListner;

    public void setOnItemImgClickListner(OnItemImgClickListner onItemImgClickListner) {
        this.onItemImgClickListner = onItemImgClickListner;
    }

    public ItemBezierAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(mInflater.inflate(R.layout.item_bezier_demo_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHoder viewHoder = (ViewHoder) holder;
        viewHoder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemImgClickListner != null) {
                    onItemImgClickListner.onClick(viewHoder.img_add);
                }
            }
        });
    }

    class ViewHoder extends RecyclerView.ViewHolder {
        private ImageView img_add;

        public ViewHoder(View itemView) {
            super(itemView);
            this.img_add = itemView.findViewById(R.id.img_item_bezier_demo_adapter_add);
        }
    }

    public interface OnItemImgClickListner {
        public void onClick(ImageView imageView);
    }
}
