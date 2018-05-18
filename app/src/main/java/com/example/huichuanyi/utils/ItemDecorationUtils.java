package com.example.huichuanyi.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Bob on 2016/8/10.
 */
public class ItemDecorationUtils extends RecyclerView.ItemDecoration {

    private int left,top,right,bootom;

    public ItemDecorationUtils(int left, int top, int right, int bootom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bootom = bootom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = bootom;
        outRect.top = top;
        outRect.right = right;
        outRect.left = left;
    }

}
