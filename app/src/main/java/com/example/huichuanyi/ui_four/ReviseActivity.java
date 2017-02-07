package com.example.huichuanyi.ui_four;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.base.BaseActivity;
import com.example.huichuanyi.config.NetConfig;
import com.example.huichuanyi.utils.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ReviseActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private EditText mOldPWD,mNewPWD;
    private Button mRevisePWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);
    }

    @Override
    public void initView() {
        mBack = (ImageView) findViewById(R.id.iv_revise_back);
        mOldPWD = (EditText) findViewById(R.id.et_revise_old_pwd);
        mNewPWD = (EditText) findViewById(R.id.et_revise_new_pwd);
        mRevisePWD = (Button) findViewById(R.id.btn_revise_revise_pwd);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void setListener() {
        mBack.setOnClickListener(this);
        mRevisePWD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_revise_back:
                finish();
                break;
            case R.id.btn_revise_revise_pwd:
                revisePWD();
                break;
            default:

                break;
        }
    }

    private void revisePWD() {
        String newPWD = mNewPWD.getText().toString().trim();
        String oldPWD = mOldPWD.getText().toString().trim();
        if (!TextUtils.isEmpty(newPWD)&&!TextUtils.isEmpty(oldPWD)){
            if (newPWD.length()>5&&newPWD.length()<17){
                RequestParams params = new RequestParams(NetConfig.REVISE_PATH);
                params.addBodyParameter("userid",new User(this).getUseId()+"");
                params.addBodyParameter("newpwd",newPWD);
                params.addBodyParameter("oldpwd",oldPWD);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (TextUtils.equals("1",result)){
                            Toast.makeText(ReviseActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                        }else if (TextUtils.equals("0",result)){
                            Toast.makeText(ReviseActivity.this, "密码修改失败，请确认旧密码是否正确", Toast.LENGTH_SHORT).show();
                        }else if (TextUtils.equals("-1",result)){
                            Toast.makeText(ReviseActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
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
            }else{
                Toast.makeText(ReviseActivity.this, "密码长度为6-16位的字符和数字", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ReviseActivity.this, "旧密码和新密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
