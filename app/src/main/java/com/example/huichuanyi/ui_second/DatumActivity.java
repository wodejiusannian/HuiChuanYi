package com.example.huichuanyi.ui_second;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.huichuanyi.R;
import com.example.huichuanyi.adapter.EventExpectAdapter;
import com.example.huichuanyi.adapter.EventAdapter;
import com.example.huichuanyi.adapter.EventSelfAdapter;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.User;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class DatumActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEditTextName,mEditTextSex,mEditTextAge,
            mEditTextCity,mEditTextWork,mEditTextBusiness,
            mEditTextContact,mEditTextRelax,mEditTextHeight,mEditTextWeight,mEditTextNeck,mEditTextBear,mEditTextBosom,mEditTextNates,mEditTextWaist;
    private TextView mTextViewBirthDay;
    private ImageView mImageViewBack;
    private RecyclerView mRecyclerView,mRecyclerViewselfdom,
            mRecyclerViewExpect;
    private EventAdapter mVerticalAdapter;
    private EventSelfAdapter mSelfAdapter;
    private EventExpectAdapter mExpectAdapter;
    private Button mButtonSubject;
    private String pos11,pos21,pos31;
    private String[] arr = {"棕色","橙色","灰色","白色","米色","粉色","紫色","红色","紫色","红色","绿色","蓝色","透明","黄色","黑色"};
    private String[] arrSelf = {"可爱","精致","飘逸","温婉","朴实","干练","独特","华丽","典雅","大气","威严","摩登","中性"};
    private String[] arrExpect = {"年轻","成熟","内敛","热情","时尚","传统","随意","端庄","干练","沉稳","权威","亲和","知性","艺术","洒脱"};
    private List<Integer> isContains,mIntegersSelf,mIntegersExpect;
    private int isEdit = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String name = data.getString("name");
            String sex = data.getString("sex");
            String age = data.getString("age");
            String city = data.getString("city");
            String height = data.getString("height");
            String weight = data.getString("weight");
            String occupation = data.getString("occupation");
            String birthday = data.getString("birthday");
            String neck_circumference = data.getString("neck_circumference");
            String shoulder_circumference = data.getString("shoulder_circumference");
            String bust = data.getString("bust");
            pos11 = data.getString("pos1");
            pos21 = data.getString("pos2");
            pos31 = data.getString("pos3");
            String waistline = data.getString("waistline");
            String hipline = data.getString("hipline");
            String orientate = data.getString("orientate");
            if(!TextUtils.equals("null",name)&&!TextUtils.equals(" ",name)) {
                mEditTextName.setText(name);
            }
            if(!TextUtils.equals("null",sex)&&!TextUtils.equals(" ",sex)){
               mEditTextSex.setText(sex);
            }
            if(!TextUtils.equals("null",pos11)&&!TextUtils.isEmpty(pos11)&&!TextUtils.equals(" ",pos11)) {
                String[] split = pos11.split("\\|");
                for(int i = 0; i < split.length ; i++) {
                    int i1 = Integer.parseInt(split[i]);
                    mRecyclerView.getChildAt(i1).setSelected(true);
                }
            }
            if(!TextUtils.equals("null",pos21)&&!TextUtils.isEmpty(pos21)&&!TextUtils.equals(" ",pos21)) {

                String[] split = pos21.split("\\|");
                for(int i = 0; i < split.length ; i++) {
                    int i1 = Integer.parseInt(split[i]);
                    mRecyclerViewselfdom.getChildAt(i1).setSelected(true);
                }
            }
            if(!TextUtils.equals("null",pos31)&&!TextUtils.isEmpty(pos31)&&!TextUtils.equals(" ",pos31)) {
                String[] split = pos31.split("\\|");
                for(int i = 0; i < split.length ; i++) {
                    int i1 = Integer.parseInt(split[i]);
                    mRecyclerViewExpect.getChildAt(i1).setSelected(true);
                }
            }
            if(!TextUtils.equals("null",age)&&!TextUtils.isEmpty(age)&&!TextUtils.equals(" ",age)) {
                mEditTextAge.setText(age);
            }
            if(!TextUtils.equals("null",occupation)&&!TextUtils.isEmpty(occupation)&&!TextUtils.equals(" ",occupation)) {
                mEditTextWork.setText(occupation);
            }
            if(!TextUtils.equals("null",orientate)&&!TextUtils.equals(" ",orientate)&&!TextUtils.isEmpty(orientate)&&!TextUtils.equals(" ",pos11)){
                String[] split = orientate.split("\\|");
                mEditTextBusiness.setText(split[0]);
                mEditTextContact.setText(split[1]);
                mEditTextRelax.setText(split[2]);
            }
            if(!TextUtils.equals("null",city)&&!TextUtils.isEmpty(city)&&!TextUtils.equals(" ",city)) {
                mEditTextCity.setText(city);
            }
            if(!TextUtils.equals("null",height)&&!TextUtils.isEmpty(height)&&!TextUtils.equals(" ",height)) {
                mEditTextHeight.setText(height);
            }
            if(!TextUtils.equals("null",weight)&&!TextUtils.isEmpty(weight)&&!TextUtils.equals(" ",weight)) {
                mEditTextWeight.setText(weight);
            }
            if(!TextUtils.equals("null",occupation)&&!TextUtils.isEmpty(occupation)&&!TextUtils.equals(" ",occupation)) {
                mEditTextWork.setText(occupation);
            }
            if(!TextUtils.equals("null",birthday)&&!TextUtils.isEmpty(birthday)&&!TextUtils.equals(" ",birthday)) {
                mTextViewBirthDay.setText(birthday);
            }
            if(!TextUtils.equals("null",neck_circumference)&&!TextUtils.isEmpty(neck_circumference)&&!TextUtils.equals(" ",neck_circumference)) {
                mEditTextNeck.setText(neck_circumference);
            }
            if(!TextUtils.equals("null",shoulder_circumference)&&!TextUtils.isEmpty(shoulder_circumference)&&!TextUtils.equals(" ",shoulder_circumference)) {
                mEditTextBear.setText(shoulder_circumference);
            }
            if(!TextUtils.equals("null",bust)&&!TextUtils.isEmpty(bust)&&!TextUtils.equals(" ",bust)) {
                mEditTextBosom.setText(bust);
            }
            if(!TextUtils.equals("null",waistline)&&!TextUtils.isEmpty(waistline)&&!TextUtils.equals(" ",waistline)) {
                mEditTextNates.setText(waistline);
            }
            if(!TextUtils.equals("null",hipline)&&!TextUtils.isEmpty(hipline)&&!TextUtils.equals(" ",hipline)) {
                mEditTextWaist.setText(hipline);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datum);

    }

    @Override
    public void initView() {
        mEditTextName = (EditText) findViewById(R.id.et_datum_name);
        mEditTextSex = (EditText) findViewById(R.id.et_datum_sex);
        mEditTextAge = (EditText) findViewById(R.id.et_datum_age);
        mEditTextCity= (EditText) findViewById(R.id.et_datum_city);
        mEditTextWork = (EditText) findViewById(R.id.et_datum_work);
        mEditTextBusiness = (EditText) findViewById(R.id.et_datum_business);
        mEditTextContact = (EditText) findViewById(R.id.et_datum_contact);
        mEditTextRelax= (EditText) findViewById(R.id.et_datum_relax);
        mEditTextHeight = (EditText) findViewById(R.id.et_datum_height);
        mEditTextWeight = (EditText) findViewById(R.id.et_datum_weight);
        mImageViewBack = (ImageView) findViewById(R.id.iv_datum_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_datum_color);
        mRecyclerViewselfdom = (RecyclerView) findViewById(R.id.rv_datum_selfdom);
        mRecyclerViewExpect = (RecyclerView) findViewById(R.id.rv_datum_expect);
        mEditTextNeck = (EditText) findViewById(R.id.et_datum_neck);
        mEditTextBear = (EditText) findViewById(R.id.et_datum_bear);
        mEditTextBosom = (EditText) findViewById(R.id.et_datum_bosom);
        mEditTextNates = (EditText) findViewById(R.id.et_datum_nates);
        mEditTextWaist = (EditText) findViewById(R.id.et_datum_waist);
        mTextViewBirthDay = (TextView) findViewById(R.id.tv_datum_nian_yue_ri);
        mButtonSubject = (Button) findViewById(R.id.btn_datum_subject);
    }

    @Override
    public void initData() {
        isContains = new ArrayList<>();
        mIntegersSelf = new ArrayList<>();
        mIntegersExpect = new ArrayList<>();
        mSelfAdapter = new EventSelfAdapter(this,arrSelf);
        mExpectAdapter = new EventExpectAdapter(this,arrExpect);
        mVerticalAdapter = new EventAdapter(this,arr);
        RequestParams params = new RequestParams(NetConfig.GET_INFORMATION);
        params.addBodyParameter("userid",new User(this).getUseId()+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(TextUtils.equals("0",result)) {
                    return;
                }
                try {
                    JSONObject object = new JSONObject(result);
                    Message mMessage = Message.obtain();
                    Bundle bundle = new Bundle();
                    String name = object.getString("name");
                    String sex = object.getString("sex");
                    String age = object.getString("age");
                    String city = object.getString("city");
                    String height =object.getString("height");
                    String weight =object.getString("weight");
                    String occupation = object.getString("occupation");
                    String orientate = object.getString("orientate");
                    String birthday =object.getString("birthday");
                    String pos1 = object.getString("pos1");
                    String pos2 = object.getString("pos2");
                    String pos3 =object.getString("pos3");
                    String neck_circumference =object.getString("neck_circumference");
                    String shoulder_circumference =object.getString("shoulder_circumference");
                    String bust = object.getString("bust");
                    String waistline =object.getString("waistline");
                    String hipline =object.getString("hipline");
                    bundle.putString("orientate",orientate);
                    bundle.putString("name",name);
                    bundle.putString("sex",sex);
                    bundle.putString("pos2",pos2);
                    bundle.putString("pos3",pos3);
                    bundle.putString("age",age);
                    bundle.putString("city",city);
                    bundle.putString("pos1",pos1);
                    bundle.putString("height",height);
                    bundle.putString("weight",weight);
                    bundle.putString("occupation",occupation);
                    bundle.putString("birthday",birthday);
                    bundle.putString("neck_circumference",neck_circumference);
                    bundle.putString("shoulder_circumference",shoulder_circumference);
                    bundle.putString("bust",bust);
                    bundle.putString("waistline",waistline);
                    bundle.putString("hipline",hipline);
                    mMessage.setData(bundle);
                    mHandler.sendMessage(mMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void setData() {
        GridLayoutManager layoutManager4 = new GridLayoutManager(this,4,LinearLayoutManager.VERTICAL,false);
        mRecyclerViewExpect.setLayoutManager(layoutManager4);
        mRecyclerViewExpect.setAdapter(mExpectAdapter);
        GridLayoutManager layoutManager3 = new GridLayoutManager(this,4,LinearLayoutManager.VERTICAL,false);
        mRecyclerViewselfdom.setLayoutManager(layoutManager3);
        mRecyclerViewselfdom.setAdapter(mSelfAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mVerticalAdapter);
    }

    @Override
    public void setListener() {
        mImageViewBack.setOnClickListener(this);
        mVerticalAdapter.setOnItemClickListener(this);
        mSelfAdapter.setOnItemClickListener(this);
        mExpectAdapter.setOnItemClickListener(this);
        mButtonSubject.setOnClickListener(this);
        mTextViewBirthDay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_datum_back:
                finish();
                break;
            case R.id.tv_item_recycler:
                selectColor(mRecyclerView,v);
                break;
            case R.id.tv_expect_recycler:
                selectColorExpect(mRecyclerViewExpect,v);
                break;
            case R.id.tv_self_recycler:
                selectColorSelf(mRecyclerViewselfdom,v);
                break;
            case R.id.btn_datum_subject:
                upDataTo();
                break;
            case R.id.tv_datum_nian_yue_ri:
                new DatePickerDialog(DatumActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTextViewBirthDay.setText(String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth));
                    }
                },2016,7,8).show();
                break;
        }
    }

    private void selectColorExpect(RecyclerView recyclerViewExpect, View v) {
        Integer position = (Integer) v.getTag();
        if (position!=null) {
            if (mIntegersExpect.contains(position)) {
                recyclerViewExpect.getChildAt(position).setSelected(false);
                mIntegersExpect.remove(position);
                return;
            }
            if (mIntegersExpect.size() == 3) {
                Toast.makeText(DatumActivity.this, "最多选择3个哦，亲", Toast.LENGTH_SHORT).show();
                return;
            }
            recyclerViewExpect.getChildAt(position).setSelected(true);

            mIntegersExpect.add(position);
        }
    }

    private void selectColorSelf(RecyclerView recyclerViewselfdom, View v) {
        Integer position = (Integer) v.getTag();
        if (position!=null) {
            if (mIntegersSelf.contains(position)) {
                recyclerViewselfdom.getChildAt(position).setSelected(false);
                mIntegersSelf.remove(position);
                return;
            }
            if (mIntegersSelf.size() == 3) {
                Toast.makeText(DatumActivity.this, "最多选择3个哦，亲", Toast.LENGTH_SHORT).show();
                return;
            }
            recyclerViewselfdom.getChildAt(position).setSelected(true);

            mIntegersSelf.add(position);
        }
    }

    private void upDataTo() {
        String name = mEditTextName.getText().toString().trim();
        String sex = mEditTextSex.getText().toString().trim();
        String age = mEditTextAge.getText().toString().trim();
        String city = mEditTextCity.getText().toString().trim();
        String height = mEditTextHeight.getText().toString().trim();
        String weight = mEditTextWeight.getText().toString().trim();
        String trim1 = mEditTextBusiness.getText().toString().trim();
        String trim2 = mEditTextContact.getText().toString().trim();
        String trim3 = mEditTextRelax.getText().toString().trim();
        String orientate = null;
        if(!TextUtils.isEmpty(trim1)&&!TextUtils.isEmpty(trim2)&&!TextUtils.isEmpty(trim3)) {
            if(Integer.parseInt(trim1)+Integer.parseInt(trim2)+Integer.parseInt(trim3)!=100) {
                Toast.makeText(DatumActivity.this, "定位的值相加要等于100", Toast.LENGTH_SHORT).show();
                return;
            }
            orientate = trim1+"|"+trim2
                    +"|"+trim3;
        }else{
            Toast.makeText(DatumActivity.this, "三个定位的值不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String birthday = mTextViewBirthDay.getText().toString().trim();
        String neck_circumference = mEditTextNeck.getText().toString().trim();
        String shoulder_circumference = mEditTextBear.getText().toString().trim();
        String bust = mEditTextBosom.getText().toString().trim();
        String waistline = mEditTextNates.getText().toString().trim();
        String hipline = mEditTextWaist.getText().toString().trim();
        String occupation = mEditTextWork.getText().toString().trim();
        StringBuffer colour_tag = new StringBuffer();
        StringBuffer pos1 = new StringBuffer();

        for(int q = 0; q < isContains.size(); q++) {
            Integer integer = isContains.get(q);
            pos1.append(integer);
            colour_tag.append(arr[integer]);
            if(q!=isContains.size()) {
                colour_tag.append("|");
                pos1.append("|");
            }
        }
        if(TextUtils.isEmpty(pos1)) {
            pos1.append(pos11);
        }
        StringBuffer character = new StringBuffer();
        StringBuffer pos2 = new StringBuffer();
        for(int w = 0; w < mIntegersSelf.size(); w++) {
            Integer integer = mIntegersSelf.get(w);
            pos2.append(integer);
            character.append(arrSelf[integer]);
            if(w!=mIntegersSelf.size()) {
                character.append("|");
                pos2.append("|");
            }

        }
        if(TextUtils.isEmpty(pos2)) {
            pos2.append(pos21);
        }
        StringBuffer desired_image = new StringBuffer();
        StringBuffer pos3 = new StringBuffer();
        for(int w = 0; w < mIntegersExpect.size(); w++) {
            Integer integer = mIntegersExpect.get(w);
            pos3.append(integer);
            desired_image.append(arrExpect[integer]);
            if(w!=mIntegersExpect.size()) {
                desired_image.append("|");
                pos3.append("|");
            }
        }
        if(TextUtils.isEmpty(pos3)) {
            pos3.append(pos31);
        }
        RequestParams params = new RequestParams(NetConfig.ME_INFORMATION);
        params.addBodyParameter("id",new User(this).getUseId()+"");
        params.addBodyParameter("name",name);
        params.addBodyParameter("sex",sex);
        params.addBodyParameter("age",age);
        params.addBodyParameter("city",city);
        params.addBodyParameter("height",height);
        params.addBodyParameter("weight",weight);
        params.addBodyParameter("orientate",orientate);
        params.addBodyParameter("birthday",birthday);
        params.addBodyParameter("occupation",occupation);
        params.addBodyParameter("colour_tag",colour_tag.toString());
        params.addBodyParameter("neck_circumference",neck_circumference);
        params.addBodyParameter("shoulder_circumference",shoulder_circumference);
        params.addBodyParameter("bust",bust);
        params.addBodyParameter("waistline",waistline);
        params.addBodyParameter("hipline",hipline);
        params.addBodyParameter("character",character.toString());
        params.addBodyParameter("desired_image",desired_image.toString());
        params.addBodyParameter("pos1",pos1.toString());
        params.addBodyParameter("pos2",pos2.toString());
        params.addBodyParameter("pos3",pos3.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(TextUtils.equals("1",result)) {
                    Toast.makeText(DatumActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DatumActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(DatumActivity.this, "网络错误，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public  void  selectColor(RecyclerView mRecycle,View v){
        Integer position = (Integer) v.getTag();
        if (position!=null) {
            if (isContains.contains(position)) {
                mRecycle.getChildAt(position).setSelected(false);
                isContains.remove(position);
                return;
            }
            if (isContains.size() == 3) {
                Toast.makeText(DatumActivity.this, "最多选择3个哦，亲", Toast.LENGTH_SHORT).show();
                return;
            }
            mRecycle.getChildAt(position).setSelected(true);

            isContains.add(position);
        }
    }


}
