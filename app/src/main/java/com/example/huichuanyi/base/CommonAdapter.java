package com.example.huichuanyi.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.huichuanyi.utils.ViewHolder;

import java.util.List;

/**
 * Created by 小五 on 2017/2/8.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> data;
    protected int layoutId;
    protected LayoutInflater inflater;

    public CommonAdapter(Context context, List<T> data, int layoutId) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size()==0?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * 这里把getView都封装起来了，因为这里的ViewHolder和返回值都是多次重复的
     * 所以用户只需要实现的是往ViewHolder中的组件设置内容或者监听器这些即可，那
     * 么这里使用了抽象类的回调方法来实现，对外提供一个convert方法，这个方法就是
     * 知道了viewHolder对象，但是工具类的类型不知道，这里我们使用了泛型，用户只
     * 需要实现这个抽象方法，然后怎么设置就要看用户的实际需求了
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, convertView, parent, layoutId, position);
        convert(viewHolder, data.get(position));
        return viewHolder.getConvertView();
    }
    /**
     * 对外提供的抽象方法，用户只需要考虑如何实现这个方法即可
     * @param viewHolder
     * @param t
     */
    public abstract void convert(ViewHolder viewHolder, T t);

}
