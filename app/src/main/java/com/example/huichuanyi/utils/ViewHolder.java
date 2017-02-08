package com.example.huichuanyi.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by 小五 on 2017/2/8.
 */

public class ViewHolder {

    private SparseArray<View> views;
    /*
     * position的保存为了以后如果要保存的是CheckBox这样的组件的时候，
     * 会出现选中一个就选中了多个的现象。
     */
    private int position;
    /*
     * 保存BaseAdapter中getView方法的convertView，用来在这里findViewById组件
     */
    private View convertView;

    /**
     * 构造方法，这里接收的参数都是在BaseAdapter的getView方法中的参数。
     * @param context
     * @param parent
     * @param layoutId
     * @param position
     */
    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        /*
         * 完成各种初始化
         */
        this.position = position;
        views = new SparseArray<View>();
        convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        /*
         * 把传统的setTag放在，因为第一次初始化ViewHolder的时候
         * convertView也是第一次创建。
         */
        convertView.setTag(this);
    }

    /**
     * 得到ViewHolder
     * @param context 传入的context，如果没有创建过convertView和ViewHolder，那么就要用到这个参数来创建ViewHolder和convertView
     * @param convertView getView中的convertView
     * @param parent getView中的ViewGroup，用来初始化convertView
     * @param layoutId ListView中的每一个Item布局
     * @param position 表示这是第几个列表项
     * @return ViewHolder对象
     */
    public static ViewHolder getViewHolder(Context context, View convertView
            , ViewGroup parent, int layoutId, int position) {
        /*
         * 如果convertView为空，那么构造一个ViewHolder就行了，因为构造方法里实现了convertView的初始化
         */
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            /*
             * 如果不为空，那么只需要getTag即可
             */
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.position = position;
            return holder;
        }
    }

    /**
     * 通过组件的id获取组件，因为组件的父类都是View，所以这里用了泛型
     * @param viewId 组件的id
     * @return 该组件View
     */
    public <T extends View> T getView(int viewId) {
        /*
         * 如果改组件已经初始化，那么SparseArray一定保存了改组件，
         * 所以只需要判断从SparseArray获取指定viewId的组件如果不为空，就新建，否则就返回即可
         */
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.append(viewId, view);
        }
        view.setTag(position);
        return (T) view;
    }

    /**
     * 返回convertView，因为BaseAdapter中的getView方法最后要返回convertView，
     * 所以也把返回封装到这里来
     * @return convertView
     */
    public View getConvertView() {
        return convertView;
    }

    /**
     * 为指定组件TextView来setText，这里使用了链式编程，返回的是ViewHolder本身
     * @param id 该TextView的id
     * @param str 要设置的内容
     * @return ViewHolder
     */
    public ViewHolder setText(int id, String str) {
        ((TextView)getView(id)).setText(str);
        return this;
    }

    /**
     * 为CheckBox设置内容
     * @param id
     * @param str
     * @return
     */
    public ViewHolder setCheckBoxText(int id, String str) {
        ((CheckBox)getView(id)).setText(str);
        return this;
    }
    /**
     * 为Button设置内容
     * @param id
     * @param str
     * @return
     */
    public ViewHolder setButtonText(int id, String str) {
        ((Button)getView(id)).setText(str);
        return this;
    }

}
