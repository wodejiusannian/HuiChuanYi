package com.example.huichuanyi.common_view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.huichuanyi.common_view.Type.TypeFactory;
import com.example.huichuanyi.common_view.Type.TypeFactoryForList;
import com.example.huichuanyi.common_view.holder.BaseViewHolder;
import com.example.huichuanyi.common_view.model.Visitable;

import java.util.List;


/**
 * Created by yq05481 on 2016/12/30.
 */

public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private TypeFactory typeFactory;
    private List<Visitable> models;
    private View.OnClickListener mOnclick;

    public MultiTypeAdapter(List<Visitable> models) {
        this.models = models;
        this.typeFactory = new TypeFactoryForList();

    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = View.inflate(context, viewType, null);

        return typeFactory.createViewHolder(viewType, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.setUpView(models.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        if (null == models) {
            return 0;
        }
        return models.size();
    }


    @Override
    public int getItemViewType(int position) {
        return models.get(position).type(typeFactory);
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        mOnclick = onItemClickListener;
    }

    private Scroll mScroll;

    public void OnScroll(Scroll scroll) {
        mScroll = scroll;
    }

    public interface Scroll {
        void onScrollBottom();
    }

    public Scroll getScroll() {
        if (mScroll != null) {
            return mScroll;
        } else {
            return null;
        }
    }

    public View.OnClickListener getmOnclick() {
        if (mOnclick != null) {
            return mOnclick;
        } else {
            return null;
        }
    }
}
