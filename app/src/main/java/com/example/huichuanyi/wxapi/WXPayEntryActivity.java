package com.example.huichuanyi.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.huichuanyi.secondui.PayOrderActivity;
import com.example.huichuanyi.ui.activity.Item_DetailsActivity;
import com.example.huichuanyi.ui.activity.MyOrderActivity;
import com.example.huichuanyi.ui.activity.My_365Activity;
import com.example.huichuanyi.ui.activity.Write_OrderActivity;
import com.example.huichuanyi.utils.ActivityUtils;
import com.example.huichuanyi.utils.CommonStatic;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.MySharedPreferences;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0:
                    switch (CommonStatic.wechatType) {
                        case "1":
                            closeActivity();
                            ActivityUtils.switchTo(this, MyOrderActivity.class);
                            break;
                        case "2":
                            MySharedPreferences.save365(this, "365");
                            sendBroad();
                            ActivityUtils.switchTo(this, My_365Activity.class);
                            break;
                        case "3":
                            closeActivity();
                            break;
                        default:
                            break;
                    }
                    CommonUtils.Toast(this, "支付成功");
                case -1:
                    CommonUtils.Toast(this, "支付失败");
                    break;
                case -2:
                    CommonUtils.Toast(this, "支付取消");
                    break;
            }
            finish();
        }
    }

    public void sendBroad() {
        Intent intent = new Intent();
        intent.setAction("action.refreshFriend");
        sendBroadcast(intent);
    }

    private void closeActivity() {
        if (PayOrderActivity.payOrderActivity != null) {
            PayOrderActivity.payOrderActivity.finish();
            PayOrderActivity.payOrderActivity = null;
        }
        if (Write_OrderActivity.write_orderActivity != null) {
            Write_OrderActivity.write_orderActivity.finish();
            Write_OrderActivity.write_orderActivity = null;
        }
        if (Item_DetailsActivity.item_detailsActivity != null) {
            Item_DetailsActivity.item_detailsActivity.finish();
            Item_DetailsActivity.item_detailsActivity = null;
        }
    }
}
