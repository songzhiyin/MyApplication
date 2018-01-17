package com.szy.myapplication.Base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义adapter的基类
 * @author Administrator
 *
 * @param <T>
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{
	protected List<T> data;
	protected LayoutInflater inflater;
	protected Context context;
	public MyBaseAdapter(Context context) {
		data=new ArrayList<T>();
		inflater=LayoutInflater.from(context);
		this.context=context;
	}
	public void addDataTop(T t){
		data.add(0, t);
		notifyDataSetChanged();
	}
	/**
	 * 在顶部加载数据
	 * @param list
	 */
	public void addDataTop(List<T> list){
		data.addAll(0, list);
		notifyDataSetChanged();
	}
	public void addDataBottom(T t){
		if (t!=null) {
			data.add(t);
			notifyDataSetChanged();
		}

	}
	/**
	 * 在底部加载新数据
	 * @param list
	 */
	public void addDataBottom(List<T> list){
		data.addAll(list);
		notifyDataSetChanged();
	}
	public void clear(){
		data.clear();
		notifyDataSetChanged();
	}
	public void setdate(List<T> list){
		data.clear();
		data.addAll(list);
		notifyDataSetChanged();
	}
	public List<T> getData(){
		return data;
	}
	@Override
	public int getCount() {
		return data.size();
//		return 10;
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return setView(position, convertView, parent);
	}
	public abstract View setView(int position, View convertView, ViewGroup parent);

}