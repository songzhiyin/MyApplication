package com.lib.szy.pullrefresh.PullreFresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songzhiiyn on 2017/10/9.
 * 自定义recycleview的adapter适配器基类
 */

public abstract class BaseRecyAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter {
    protected List<T> data = null;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongListener onItemLongListener;

    public BaseRecyAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        data = new ArrayList<>();
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<T> getData() {
        return data;
    }

    /**
     * 将旧数据清除，添加新数据
     *
     * @param datas
     */
    public void setDataList(List<T> datas) {
        data.clear();
        data.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 在列表底部添加新的数据集合
     *
     * @param newData
     */
    public void addBottonDatas(List<T> newData) {
        data.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * 根据item的位置获取item对应的数据
     *
     * @param position
     * @return
     */
    public T getItemData(int position) {
        if (position < data.size()) {
            return data.get(position);
        } else {
            return null;
        }
    }

    /**
     * 设置item的长按事件
     *
     * @param onItemLongListener
     */
    public void setOnItemLongListener(OnItemLongListener onItemLongListener) {
        this.onItemLongListener = onItemLongListener;
    }

    /**
     * 设置item的点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * 在列表底部添加新的数据
     *
     * @param entity
     */
    public void addBottonData(T entity) {
        data.add(entity);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        public void Onclick(int position, Object data);
    }

    public interface OnItemLongListener {
        public void Onlong(int position, Object data);
    }
}
