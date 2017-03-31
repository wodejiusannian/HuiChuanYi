package com.example.huichuanyi.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.custom.seekbar.CenterTipView;
import com.example.huichuanyi.custom.seekbar.Contact;
import com.example.huichuanyi.custom.seekbar.ContactAdapter;
import com.example.huichuanyi.custom.seekbar.Data;
import com.example.huichuanyi.custom.seekbar.RecyclerViewDivider;
import com.example.huichuanyi.custom.seekbar.RightIndexView;
import com.example.huichuanyi.custom.seekbar.T;
import com.example.huichuanyi.custom.seekbar.TinyPY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class StudioSelectCityActivity extends BaseActivity implements RightIndexView.OnRightTouchMoveListener {
    private ArrayList<Contact> mData = new ArrayList<>();//列表展示的数据
    private ArrayList<String> firstList = new ArrayList<>();//字母索引集合
    private HashSet<String> set = new HashSet<>();//中间临时集合
    private ContactAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TextView tvHeader;//固定头view
    private CenterTipView tipView;//中间字母提示view
    private RightIndexView rightContainer;//右侧索引view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studio_select);
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public void initData() {
        if (mData == null)
            mData = new ArrayList<>();
        Contact contact = null;

        //这儿使用的是静态数据
        int size = Data.data.length;

        //是否有非字母数据(拼音首字符不在26个字母范围当中)
        boolean hasIncognizance = false;
        //装载非字母数据的结合
        ArrayList<Contact> incognizanceList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            contact = new Contact();
            contact.name = Data.data[i];
            contact.pinYin = TinyPY.toPinYin(contact.name);
            contact.firstPinYin = TinyPY.firstPinYin(contact.pinYin);
            if (!TextUtils.isEmpty(contact.firstPinYin)) {
                char first = contact.firstPinYin.charAt(0);
                //A(65), Z(90), a(97), z(122) 根据数据的类型分开装进集合
                if (first < 'A' || (first > 'Z' && first < 'a') || first > 'z') {
                    //非字母
                    contact.firstPinYin = "#";
                    //标记含有#集合
                    hasIncognizance = true;
                    //添加数据到#集合
                    incognizanceList.add(contact);
                } else {
                    //字母索引(set可以去重复)
                    set.add(contact.firstPinYin);
                    //添加数据到字母a-z集合
                    mData.add(contact);
                }
            }
        }

        //对contact集合数据排序
        Collections.sort(mData);

        //把排序后的字母顺序装进字母索引集合
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            firstList.add(iterator.next());
        }
        Collections.sort(firstList);

        //最后加上#
        if (hasIncognizance) {
            //把#装进索引集合
            firstList.add("#");
            //把非字母的contact数据装进数据集合
            mData.addAll(incognizanceList);
        }
        //清空中间缓存集合
        incognizanceList.clear();
        set.clear();

    }

    @Override
    public void setData() {
        //设置布局
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //添加分割线
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        adapter = new ContactAdapter(mData);
        recyclerView.setAdapter(adapter);
        //item的点击事件
        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Contact contact) {
                T.show(StudioSelectCityActivity.this, contact.name);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                /**
                 * 查找(width>>1,1)点处的view，差不多是屏幕最上边，距顶部1px
                 * recyclerview上层的header所在的位置
                 */
                View itemView = recyclerView.findChildViewUnder(tvHeader.getMeasuredWidth() >> 1, 1);

                /**
                 * recyclerview中如果有item占据了这个位置，那么header的text就为item的text
                 * 很显然，这个tiem是recyclerview的任意item
                 * 也就是说，recyclerview每滑过一个item，tvHeader就被赋了一次值
                 */
                if (itemView != null && itemView.getContentDescription() != null) {
                    tvHeader.setText(String.valueOf(itemView.getContentDescription()));
                }

                /**
                 * 指定可能印象外层header位置的item范围[-tvHeader.getMeasuredHeight()+1, tvHeader.getMeasuredHeight() + 1]
                 * 得到这个item
                 */
                View transInfoView = recyclerView.findChildViewUnder(
                        tvHeader.getMeasuredWidth() >> 1, tvHeader.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tvHeader.getMeasuredHeight();
                    if (transViewStatus == ContactAdapter.SHOW_HEADER_VIEW) {
                        /**
                         * 如果这个item有tag参数，而且是显示header的，正好是我们需要关注的item的header部分
                         */
                        if (transInfoView.getTop() > 0) {
                            //说明item还在屏幕内，只是占据了外层header部分空间
                            tvHeader.setTranslationY(dealtY);
                        } else {
                            //说明item已经超出了recyclerview上边界，故此时外层的header的位置固定不变
                            tvHeader.setTranslationY(0);
                        }
                    } else if (transViewStatus == ContactAdapter.DISMISS_HEADER_VIEW) {
                        //如果此项的header隐藏了，即与外层的header无关，外层的header位置不变
                        tvHeader.setTranslationY(0);
                    }
                }
            }
        });

        //固定头item
        tvHeader = (TextView) findViewById(R.id.tv_header);
        if (mData != null && mData.size() > 0)
            tvHeader.setText(mData.get(0).firstPinYin);

        //center tip view
        tipView = (CenterTipView) findViewById(R.id.tv_center_tip);

        //右侧字母表索引
        rightContainer = (RightIndexView) findViewById(R.id.vg_right_container);
        rightContainer.setData(firstList);
        //右侧字母索引容器注册touch回调
        rightContainer.setOnRightTouchMoveListener(this);
    }

    @Override
    public void setListener() {

    }

    /**
     * 右侧字母表touch回调
     *
     * @param position 当前touch的位置
     * @param content  当前位置的内容
     * @param isShow   显示与隐藏中间的tip view
     */
    @Override
    public void showTip(int position, final String content, boolean isShow) {
        if (isShow) {
            tipView.setVisibility(View.VISIBLE);
            tipView.setText(content);
        } else {
            tipView.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).firstPinYin.equals(content)) {
                recyclerView.stopScroll();
                int firstItem = layoutManager.findFirstVisibleItemPosition();
                int lastItem = layoutManager.findLastVisibleItemPosition();
                if (i <= firstItem) {
                    recyclerView.scrollToPosition(i);
                } else if (i <= lastItem) {
                    int top = recyclerView.getChildAt(i - firstItem).getTop();
                    recyclerView.scrollBy(0, top);
                } else {
                    recyclerView.scrollToPosition(i);
                }
                break;
            }
        }
    }
}
