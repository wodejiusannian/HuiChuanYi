package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.OrderStudioHozAdapter;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.OrderStudioHoz;
import com.example.huichuanyi.common_view.model.OrderStudioIntroduceOpenModel;

import java.util.List;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class OrderStudioIntroduceOpenHolder extends BaseViewHolder<OrderStudioIntroduceOpenModel> {

    public OrderStudioIntroduceOpenHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(OrderStudioIntroduceOpenModel model, int position, final MultiTypeAdapter adapter) {
        final RecyclerView rv = (RecyclerView) getView(R.id.rv_item_order_studio_open);
        ImageView bg = (ImageView) getView(R.id.iv_bg);
        Context context = rv.getContext();
        List<OrderStudioHoz> list = model.list;
        if (list != null && list.size() > 0) {
            bg.setVisibility(View.GONE);

            final OrderStudioHozAdapter adapter1 = new OrderStudioHozAdapter(context, list);
            rv.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false));
            rv.setAdapter(adapter1);
            adapter1.setOnItemClickListener(adapter.getmOnclick());
            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    //super.onScrollStateChanged(recyclerView, newState);
                    boolean slideToBottom = isSlideToBottom(rv);
                    if (slideToBottom) {
                        adapter.getScroll().onScrollBottom();
                    }
                }
            });
        }else{
            bg.setVisibility(View.VISIBLE);
        }
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
