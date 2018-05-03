package com.example.huichuanyi.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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
public class FragmentUtils {

    private Fragment mCurrentFragment;

    private FragmentManager mManager;

    public FragmentUtils(FragmentManager mManager) {
        this.mManager = mManager;
    }

    /**
     * @param contentID 占位布局的id
     * @param clazz     要显示的fragment
     */
    public void showAndHide(int contentID, Class<? extends Fragment> clazz) {
        //判断当前显示的fragment是否和将要显示的fragment是同一个
        //如果是同一个那么久不向下走了，如果不是同一个就向下走
        if (mCurrentFragment != null && mCurrentFragment.getClass().getSimpleName().equals(clazz.getSimpleName())) {
            return;
        }
        //判断是否将fragment添加到事务管理器中
        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = null;
        try {
            /*
            * 获取下当前要显示的frgament
            * */
            Fragment fragmentByTag = mManager.findFragmentByTag(clazz.getSimpleName());
            //如果不为空说明已经添加到了实物管理器中，如果为空需要重新添加
            if (fragmentByTag != null) {
                transaction.show(fragmentByTag);
                transaction.hide(mCurrentFragment);
                mCurrentFragment = fragmentByTag;
            } else {
                //说明将要显示的fragment为空，我们要创建
                //通过无参的 公开的构造函数来创建fragment实例
                fragment = clazz.newInstance();
                //将当前的fragment添加的事物管理器中
                transaction.add(contentID, fragment, clazz.getSimpleName());
                if (mCurrentFragment != null) {
                    transaction.hide(mCurrentFragment);
                }
                mCurrentFragment = fragment;
            }
            transaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
