package com.example.huichuanyi.common_view.holder;

import android.view.View;
import android.widget.Button;

import com.example.huichuanyi.R;
import com.example.huichuanyi.common_view.adapter.MultiTypeAdapter;
import com.example.huichuanyi.common_view.model.SlwSevenModle;
import com.example.huichuanyi.utils.RxBus;

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
public class SlwSevenHolder extends BaseViewHolder<SlwSevenModle> {

    public SlwSevenHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(SlwSevenModle model, int position, MultiTypeAdapter adapter) {
        Button refuse = (Button) getView(R.id.btn_365_seven_refuse_data);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().post(100894);
            }
        });
    }
}
