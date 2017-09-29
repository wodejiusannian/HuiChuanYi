package com.example.huichuanyi.ui.activity.lanyang;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.ClosetAdapter;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.ui.base.BaseActivity;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
public class LyShopListActivity extends BaseActivity implements UtilsInternet.XCallBack {
    @BindView(R.id.tb_ly_shoplist)
    TabLayout tb;

    @BindView(R.id.vp_ly_shoplist)
    ViewPager vp;

    private UtilsInternet net = UtilsInternet.getInstance();

    private ClosetAdapter adapter;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_shop_list);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        adapter = new ClosetAdapter(getSupportFragmentManager(), fragments, titles);
        tb.setupWithViewPager(vp);
        vp.setAdapter(adapter);
    }

    @Override
    protected void setData() {
        pos = getIntent().getIntExtra("pos", 0);
        initNet();
    }

    private void initNet() {
        net.post(NetConfig.LY_MAIN_DATA, null, this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onResponse(String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONObject body = json.getJSONObject("body");
            JSONArray type = body.getJSONArray("type");
            for (int i = 0; i < type.length(); i++) {
                JSONObject item3 = type.getJSONObject(i);
                titles.add(item3.getString("type_name"));
                LyShopListFragment lyShopListFragment = new LyShopListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", item3.getString("type_id"));
                lyShopListFragment.setArguments(bundle);
                fragments.add(lyShopListFragment);
            }

            adapter.notifyDataSetChanged();
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    vp.setCurrentItem(pos);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
