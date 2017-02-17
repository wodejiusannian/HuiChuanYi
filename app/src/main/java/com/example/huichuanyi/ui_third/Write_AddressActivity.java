package com.example.huichuanyi.ui_third;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.modle.CityModel;
import com.example.huichuanyi.modle.DistrictModel;
import com.example.huichuanyi.modle.ProvinceModel;
import com.example.huichuanyi.service.XmlParserHandler;
import com.example.huichuanyi.utils.MyJson;
import com.example.huichuanyi.utils.User;
import com.example.huichuanyi.utils.UtilsInternet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import widget.OnWheelChangedListener;
import widget.WheelView;
import widget.adapters.ArrayWheelAdapter;

public class Write_AddressActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener, UtilsInternet.XCallBack {
    private Intent intent;
    private TextView mSave, mSelect, mCancel, mSure;
    private EditText mName, mPhone, mStreet;
    private UtilsInternet instance = UtilsInternet.getInstance();
    private RelativeLayout mAddress;
    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    Map<String, String> dataMap = new HashMap<>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName = "";
    protected String mCurrentZipCode = "";
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private String type, city, name, phone, address, receive_name, receive_phone, receive_city, receive_address;
    private int tag, updateOrAdd;
    private String userID, addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_address);
    }


    @Override
    public void initView() {
        mSave = (TextView) findViewById(R.id.tv_write_address_save);
        mName = (EditText) findViewById(R.id.et_write_address_name);
        mPhone = (EditText) findViewById(R.id.et_write_address_phone);
        mStreet = (EditText) findViewById(R.id.et_write_address_street);
        mSelect = (TextView) findViewById(R.id.tv_write_address_address);
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mAddress = (RelativeLayout) findViewById(R.id.ll_write_address);
        mCancel = (TextView) findViewById(R.id.tv_write_address_cancel);
        mSure = (TextView) findViewById(R.id.tv_write_address_sure);
    }

    @Override
    public void initData() {
        intent = getIntent();
        type = intent.getStringExtra("type");
        tag = intent.getIntExtra("tag", 0);
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        city = intent.getStringExtra("city");
        address = intent.getStringExtra("address");
        userID = new User(this).getUseId() + "";
        addressId = intent.getStringExtra("addressId");
        initProvinceDatas();
    }

    @Override
    public void setData() {
        if (TextUtils.equals("9001", type)) {
            mSelect.setText(city);
            mName.setText(name);
            mPhone.setText(phone);
            mStreet.setText(address);
        }
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void setListener() {
        mSave.setOnClickListener(this);
        mSelect.setOnClickListener(this);
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_write_address_save:
                transmissionInfo();
                break;
            case R.id.tv_write_address_address:
                closeKeyboard();
                initAddress();
                break;
            case R.id.tv_write_address_cancel:
                mAddress.setVisibility(View.GONE);
                break;
            case R.id.tv_write_address_sure:
                setCity();
                break;
            default:
                break;
        }
    }

    /*
    * 选择城市后，进行设置
    * */
    private void setCity() {
        city = mCurrentProviceName + "," + mCurrentCityName + ","
                + mCurrentDistrictName;
        mSelect.setText(city);
        mAddress.setVisibility(View.GONE);
    }

    /*
    * 显示地址选择器
    * */
    private void initAddress() {
        mAddress.setVisibility(View.VISIBLE);
    }

    /*
    * 点击保存,判断这几个值都不为空才可以保存
    * */
    private void transmissionInfo() {
        receive_name = mName.getText().toString().trim();
        receive_phone = mPhone.getText().toString().trim();
        receive_city = mStreet.getText().toString().trim();
        receive_address = mSelect.getText().toString().trim();
        if (TextUtils.isEmpty(receive_name) || TextUtils.isEmpty(receive_phone) || TextUtils.isEmpty(receive_city) || TextUtils.isEmpty(receive_address)) {
            Toast.makeText(this, "亲，请填全地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(type, "9001")) {
            saveAddress();
        } else {
            updateAddress();
        }
    }

    /**
     * tag修改某个位置的地址
     */
    private void toUpActivity() {
        intent.putExtra("isSuccess", "success");
        intent.putExtra("type", type);
        intent.putExtra("name", receive_name);
        intent.putExtra("phone", receive_phone);
        intent.putExtra("street", receive_address);
        intent.putExtra("city", receive_city);
        intent.putExtra("tag", tag);
        setResult(1001, intent);
        finish();
    }


    /*
    * 如果type为9001那么就是修改地址，进行添加地址操作
    * */
    private void updateAddress() {
        dataMap.put("user_id", userID);
        dataMap.put("id", addressId);
        dataMap.put("receive_name", receive_name);
        dataMap.put("receive_phone", receive_phone);
        dataMap.put("receive_city", receive_city);
        dataMap.put("receive_address", receive_address);
        instance.post(NetConfig.UPDATE_PERSON_ADDRESS, dataMap, this);
    }

    /*
    * 如果type不为9001那么就是添加地址，进行添加地址操作
    * */
    private void saveAddress() {
        dataMap.put("user_id", userID);
        dataMap.put("receive_name", receive_name);
        dataMap.put("receive_phone", receive_phone);
        dataMap.put("receive_city", receive_city);
        dataMap.put("receive_address", receive_address);
        instance.post(NetConfig.ADD_PERSON_ADDRESS, dataMap, this);
    }


    @Override
    public void onResponse(String result) {
        Log.i("TAG", "-----------------"+result);
        String ret = MyJson.getRet(result);
        if (TextUtils.equals("0", ret)) {
            if (TextUtils.equals("9001", type)) {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }
            toUpActivity();
        }
    }

    /*
    * 初始化地址选择器
    * */
    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    /*
    * 初始化地址选择器
    * */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /*
    * 填充地址选择器
    * */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    /*
    * 地址选择器转变
    * */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /*
    * 关闭软键盘
    * */
    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
