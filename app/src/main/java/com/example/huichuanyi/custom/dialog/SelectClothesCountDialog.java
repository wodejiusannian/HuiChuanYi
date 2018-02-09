package com.example.huichuanyi.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.RecycleSingleAdapter;
import com.example.huichuanyi.ui.modle.OrderGoDoorPrice;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.ItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 小五 on 2016/12/26.
 */
public class SelectClothesCountDialog extends Dialog {

    private List<OrderGoDoorPrice> mData;

    private String gradle;

    public SelectClothesCountDialog(Context context, List<OrderGoDoorPrice> data, String gradle) {
        super(context, R.style.MySelfDialog);
        mData = data;
        this.gradle = gradle;
    }

    private Unbinder unbinder;

    @BindView(R.id.rv_dialog_select_clothes_count)
    RecyclerView context;

    private RecycleSingleAdapter adapter;


    private ItemData mListener;

    private String mCount, mPrice;

    public void setOnItemGetData(ItemData itemData) {
        mListener = itemData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_colthes_count);
        unbinder = ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        initData();
    }


    private void initData() {
        adapter = new RecycleSingleAdapter(getContext(), mData);
        context.setLayoutManager(new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false));
        context.setAdapter(adapter);
        context.addItemDecoration(new ItemDecoration(8));
        adapter.setOnItemGetData(new RecycleSingleAdapter.ItemData() {
            @Override
            public void getItemData(OrderGoDoorPrice str) {
                switch (gradle) {
                    case "1":
                        mCount = str.priceRange + "件";
                        mPrice = str.gradeOne;
                        break;
                    case "2":
                        mCount = str.priceRange + "件";
                        mPrice = str.gradeTwo;
                        break;
                    case "3":
                        mCount = str.priceRange + "件";
                        mPrice = str.gradeThree;
                        break;
                    case "4":
                        mCount = str.priceRange + "件";
                        mPrice = str.gradeFour;
                        break;
                    case "5":
                        mCount = str.priceRange + "件";
                        mPrice = str.gradeFive;
                        break;
                    case "6":
                        mCount = str.priceRange + "件";
                        mPrice = str.gradeSix;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_select_clothes_count_sure})
    public void onEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_select_clothes_count_sure:
                if (!CommonUtils.isEmpty(mCount) && !CommonUtils.isEmpty(mPrice)) {
                    mListener.getItemData(mCount, mPrice);
                    dismiss();
                }
                break;
            default:
                break;
        }
    }


    public interface ItemData {
        void getItemData(String count, String price);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        unbinder.unbind();
    }
}
