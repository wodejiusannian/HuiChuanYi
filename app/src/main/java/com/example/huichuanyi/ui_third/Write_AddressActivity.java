package com.example.huichuanyi.ui_third;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.modle.CityModel;
import com.example.huichuanyi.modle.DistrictModel;
import com.example.huichuanyi.modle.ProvinceModel;
import com.example.huichuanyi.service.XmlParserHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import widget.OnWheelChangedListener;
import widget.WheelView;
import widget.adapters.ArrayWheelAdapter;

public class Write_AddressActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {
    private Intent intent;
    private TextView mSave, mSelect;
    private EditText mName, mPhone, mStreet;
    private LinearLayout mAddress;
    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName = "";

    protected String mCurrentZipCode = "";

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

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
        mAddress = (LinearLayout) findViewById(R.id.ll_write_address);
    }

    @Override
    public void initData() {
        intent = getIntent();
        initProvinceDatas();
    }

    @Override
    public void setData() {
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        mViewProvince.setVisibleItems(4);
        mViewCity.setVisibleItems(4);
        mViewDistrict.setVisibleItems(4);
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
                initAddress();
                break;

            default:
                break;
        }
    }

    private void initAddress() {
        mAddress.setVisibility(View.VISIBLE);
    }

    private void transmissionInfo() {
        String name = mName.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String street = mStreet.getText().toString().trim();
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("street", street);
        setResult(1001, intent);
        finish();
    }


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
}
