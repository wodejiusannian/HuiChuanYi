package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.SlwFourModel;
import com.example.huichuanyi.utils.RxBus;

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
public class SlwFourHolder extends BaseViewHolder<SlwFourModel> {

    public SlwFourHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(SlwFourModel model, int position, MultiTypeAdapter adapter) {
        final List<SlwFourModel.Four> data = model.getData();
        LinearLayout viewGroup = (LinearLayout) getView(R.id.ll_item_365_four_data);
        viewGroup.removeAllViews();
        Context context = viewGroup.getContext();
        for (int i = 0; i < data.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_365_four_child, null);
            final SlwFourModel.Four four = data.get(i);
            final RxBus aDefault = RxBus.getDefault();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type = four.type;
                    aDefault.post(type);
                }
            });
            ImageView service = (ImageView) view.findViewById(R.id.iv_365_four_service);
            ImageView isService = (ImageView) view.findViewById(R.id.iv_365_four_is_service);
            isService.setImageResource(four.showResID);
            service.setImageResource(four.resID);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            view.setLayoutParams(lp);
            viewGroup.addView(view);
        }
    }
}
