package com.example.huichuanyi.ui.fragment;

import android.support.v4.app.FragmentManager;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base_2.BaseFragment;
import com.example.huichuanyi.utils.FragmentUtils;

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
public class FragmentMainShopCar extends BaseFragment {

    private FragmentUtils fragmentUtils;

    @Override
    protected void initData() {
        super.initData();
        FragmentManager manager = getFragmentManager();
        fragmentUtils = new FragmentUtils(manager);
        fragmentUtils.showAndHide(R.id.rl_mainshopcar_content, FragmentMainChildShopCarSelf.class);
    }


    @Override
    protected int layoutInflaterId() {
        return R.layout.fragment_mainshopcar;
    }

    public void setChange() {
        isChange = !isChange;
        if (isChange) {
            fragmentUtils.showAndHide(R.id.rl_mainshopcar_content, FragmentMainChildShopCarSelf.class);
        } else {
            fragmentUtils.showAndHide(R.id.rl_mainshopcar_content, FragmentMainChildShopCarAccurate.class);
        }
    }

    private boolean isChange = true;
}
