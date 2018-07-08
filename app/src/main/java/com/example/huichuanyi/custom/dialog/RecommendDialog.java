package com.example.huichuanyi.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.huichuanyi.R;
import com.example.huichuanyi.bean.ShopItem;
import com.example.huichuanyi.custom.ThreeRecycleColorView;
import com.example.huichuanyi.custom.ThreeRecycleView;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.MUtilsInternet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class RecommendDialog extends Dialog {

    private ThreeRecycleColorView color;

    private ThreeRecycleView size;

    private String clothes_id, stock_id, color_name, size_name, clothes_cz;

    private Map<String, List<ShopItem.BodyBean>> map = new HashMap<>();

    private String one;

    private Button mSure, addCar;

    private MUtilsInternet net = MUtilsInternet.getInstance();

    public RecommendDialog(Context context, String id, String clo) {
        super(context, R.style.ActionSheetDialogStyle);
        clothes_id = id;
        clothes_cz = clo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recommend);
        Window dialogWindow = getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        initView();
        initData();
        setListener();
    }

    private InformationData mInfromation;

    public void setInfomation(InformationData information) {
        mInfromation = information;
    }

    private void setListener() {
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfromation.getInformation(1, color_name, stock_id);
            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfromation.getInformation(2, color_name, stock_id);
            }
        });
    }

    private void initData() {
        Map<String, String> map1 = new HashMap();
        map1.put("clothes_id", clothes_id);
        net.post2("http://hmyc365.net:8084/HM/stu/mall/clothes/getClothesStockInfo.do", map1, new MUtilsInternet.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    Gson gson = new Gson();
                    ShopItem shopitem = gson.fromJson(result, ShopItem.class);
                    Set<String> colors = new HashSet<>();
                    List<String> co = new ArrayList<>();

                    for (ShopItem.BodyBean bean : shopitem.getBody()) {
                        colors.add(bean.getColor().trim());
                    }

                    Iterator c = colors.iterator();
                    int sum = 0;
                    while (c.hasNext()) {
                        List<ShopItem.BodyBean> list = new ArrayList<>();
                        String s = (String) c.next();
                        if (sum == 0)
                            one = s;
                        co.add(s);
                        for (ShopItem.BodyBean bean : shopitem.getBody()) {
                            if (s.equals(bean.getColor().trim())) {
                                list.add(bean);
                            }
                        }
                        map.put(s, list);
                    }
                    color.setFreshData(co, new ThreeRecycleColorView.SelectItem() {
                        @Override
                        public void selectItem(String s) {
                            size.reset(map.get(s));
                            color_name = s;
                            if (!CommonUtils.isEmpty(color_name) && !CommonUtils.isEmpty(stock_id)) {
                                mSure.setEnabled(true);
                                addCar.setEnabled(true);
                            }
                        }
                    });
                    size.setFreshData(map.get(one), new ThreeRecycleView.SelectItem() {
                        @Override
                        public void selectItem(ShopItem.BodyBean s) {
                            size_name = s.getSize();
                            stock_id = s.getStock_id();
                            if (!CommonUtils.isEmpty(color_name) && !CommonUtils.isEmpty(stock_id)) {
                                mSure.setEnabled(true);
                                addCar.setEnabled(true);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void initView() {
        color = (ThreeRecycleColorView) this.findViewById(R.id.three_recycle_color);
        size = (ThreeRecycleView) this.findViewById(R.id.three_recycle_size);
        mSure = (Button) this.findViewById(R.id.dialog_recommend_gobuy);
        addCar = (Button) this.findViewById(R.id.dialog_recommend_addcar);
    }

    public interface InformationData {
        void getInformation(int tag, String color_name, String stock_id);
    }
}
