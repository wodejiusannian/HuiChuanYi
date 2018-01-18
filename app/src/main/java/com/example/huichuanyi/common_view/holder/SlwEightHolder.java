package com.example.huichuanyi.common_view.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.SlwEightModel;
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
public class SlwEightHolder extends BaseViewHolder<SlwEightModel> {

    public SlwEightHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(SlwEightModel model, int position, MultiTypeAdapter adapter) {
        final List<SlwEightModel.Eight> data = model.getData();
        LinearLayout viewGroup = (LinearLayout) getView(R.id.ll_item_365_four_data);
        viewGroup.removeAllViews();
        Context context = viewGroup.getContext();
        TextView tip = (TextView) getView(R.id.tv_item_365_four_tip);
        tip.setText(model.getTitle());
        for (int i = 0; i < data.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_365_eight_child, null);
            final SlwEightModel.Eight four = data.get(i);
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
            TextView title = (TextView) view.findViewById(R.id.tv_365_four_is_title);
            TextView count = (TextView) view.findViewById(R.id.tv_365_four_is_count);
            int count1 = four.count;
            if (count1 > 0) {
                isService.setVisibility(View.GONE);
                count.setVisibility(View.VISIBLE);
                if (count1 < 99) {
                    count.setText(count1 + "");
                } else {
                    count.setText("99+");
                }
            } else {
                count.setVisibility(View.GONE);
                isService.setVisibility(View.VISIBLE);
            }
            title.setText(four.title);
            isService.setImageResource(four.showResID);
            service.setImageResource(four.resID);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            view.setLayoutParams(lp);
            viewGroup.addView(view);
        }
    }
}
