package com.example.huichuanyi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RadioGroup;

import com.example.huichuanyi.R;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.ui.fragment.SlwFragmentGuidance;
import com.example.huichuanyi.ui.fragment.SlwFragmentManager;
import com.example.huichuanyi.ui.fragment.SlwFragmentRecommend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class PrivateManagerActivity extends BaseActivity {


    private FragmentManager mManager;

    private Fragment mCurrentFragment;

    private int userEvent;

    private String studio_name, studio_id;

    private int day;

    @BindView(R.id.rg_main_navigation)
    RadioGroup rg;



    public void back(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privite_manager);


    }

    @Override
    protected void setListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_private_recommend:
                        showAndHide(R.id.rl_private_show, SlwFragmentRecommend.class);
                        break;
                    case R.id.rb_private_guidance:
                        showAndHide(R.id.rl_private_show, SlwFragmentGuidance.class);
                        break;
                    case R.id.rb_private_manager:
                        showAndHide(R.id.rl_private_show, SlwFragmentManager.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        mManager = getSupportFragmentManager();
        Intent intent = getIntent();
        userEvent = intent.getIntExtra("userEvent", 0);
        String vip_endDate = intent.getStringExtra("vip_endDate");
        studio_name = intent.getStringExtra("studio_name");
        studio_id = intent.getStringExtra("studio_id");
        day = differentDaysByMillisecond(vip_endDate);
        if (10081 == userEvent || 10082 == userEvent) {
            rg.check(R.id.rb_private_guidance);
            showAndHide(R.id.rl_private_show, SlwFragmentGuidance.class);
        } else if (10087 == userEvent || 10088 == userEvent) {
            rg.check(R.id.rb_private_manager);
            showAndHide(R.id.rl_private_show, SlwFragmentManager.class);
        } else {
            showAndHide(R.id.rl_private_show, SlwFragmentRecommend.class);
        }
    }

    @Override
    protected void setData() {

    }


    /**
     * @param contentID 占位布局的id
     * @param clazz     要显示的fragment
     */
    private void showAndHide(int contentID, Class<? extends Fragment> clazz) {
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
                Bundle bundle = new Bundle();
                bundle.putInt("userEvent", userEvent);
                bundle.putInt("day", day);
                bundle.putString("studio_name", studio_name);
                bundle.putString("studio_id", studio_id);
                fragment.setArguments(bundle);
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

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @return
     */
    public int differentDaysByMillisecond(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateStr);
            int days = (int) ((date.getTime() - new Date().getTime()) / (1000 * 3600 * 24));
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 10;
    }


}
