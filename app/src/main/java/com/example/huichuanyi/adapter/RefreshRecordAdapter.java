package com.example.huichuanyi.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.CommonAdapter;
import com.example.huichuanyi.bean.RecordRefresh;
import com.example.huichuanyi.utils.ViewHolder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 小五 on 2017/2/8.
 */

public class RefreshRecordAdapter extends CommonAdapter<RecordRefresh> {

    public RefreshRecordAdapter(Context context, List<RecordRefresh> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder viewHolder, RecordRefresh s) {
        TextView date = viewHolder.getView(R.id.tv_item_refresh_record_date);
        date.setText("09/2");
        LinearLayout pics = viewHolder.getView(R.id.ll_item_refresh_record);
        List<RecordRefresh.RefreshBean> list = s.getList();
        for (int i = 0; i < list.size(); i++) {
            SimpleDraweeView pic = new SimpleDraweeView(context);
            pic.setMinimumWidth(200);
            pic.setMinimumHeight(200);
            pic.setPaddingRelative(10, 10, 10, 10);
            GenericDraweeHierarchy build = GenericDraweeHierarchyBuilder.newInstance(context.getResources()).
                    setRoundingParams(RoundingParams.fromCornersRadius(20)).build();
            pic.setHierarchy(build);
            if (i % 5 == 0) {
                pic.setImageURI("http://a.hiphotos.baidu.com/image/pic/item/78310a55b319ebc497ee99908026cffc1e171620.jpg");
            } else {
                pic.setImageURI("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
            }
            pics.addView(pic);
        }
    }


}
