package com.example.huichuanyi.fragment_second;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.SeeCarAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.bean.SeeCar;
import com.example.huichuanyi.custom.MyListView;
import com.example.huichuanyi.utils.Utils;
import com.example.huichuanyi.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeeCarActivity extends BaseActivity implements UtilsInternet.XCallBack, SwipeRefreshLayout.OnRefreshListener {
    private UtilsInternet internet = UtilsInternet.getInstance();
    private Map<String, String> map = new HashMap<>();

    private SeeCarAdapter mAdapter;
    private List<SeeCar> mData = new ArrayList<>();

    private MyListView mShow;
    private SwipeRefreshLayout mRefresh;
    //电商ID
    private String EBusinessID = "1278922";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey = "ca65d522-2df2-4209-a054-11acb7b3c1b1";
    //请求url
    private String ReqURL = "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_car);
    }


    @Override
    public void initView() {
        mShow = (MyListView) findViewById(R.id.lv_see_car);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.sf_see_car);
    }


    @Override
    public void initData() {
        mAdapter = new SeeCarAdapter(this, mData, R.layout.item_see_car);
        try {
            String requestData = "{'OrderCode':'','ShipperCode':'" + "YTO" + "','LogisticCode':'" + "884277852077063671 " + "'}";
            String dataSign = encrypt(requestData, AppKey, "UTF-8");
            map.put("RequestData", urlEncoder(requestData, "UTF-8"));
            map.put("EBusinessID", EBusinessID);
            map.put("RequestType", "1002");
            map.put("DataSign", urlEncoder(dataSign, "UTF-8"));
            map.put("DataType", "2");
            sendPost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPost() {
        internet.post(ReqURL, map, this);
    }

    @Override
    public void setData() {
        mShow.setAdapter(mAdapter);
    }

    @Override
    public void setListener() {
        sendPost();
        mRefresh.setOnRefreshListener(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onResponse(String result) {
        try {
            mData.clear();
            JSONObject object = new JSONObject(result);
            JSONArray traces = object.getJSONArray("Traces");
            for (int i = 0; i < traces.length(); i++) {
                JSONObject obj = traces.getJSONObject(i);
                SeeCar seeCar = new SeeCar();
                seeCar.time = obj.getString("AcceptTime");
                seeCar.address = obj.getString("AcceptStation");
                mData.add(seeCar);
            }
            Collections.reverse(mData);
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    private static char[] base64EncodeChars = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * 电商Sign签名生成
     *
     * @param content  内容
     * @param keyValue Appkey
     * @param charset  编码方式
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    @SuppressWarnings("unused")
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }


    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(false);
    }
}
