package com.example.huichuanyi.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huichuanyi.R;
import com.example.huichuanyi.utils.CommonUtils;
import com.example.huichuanyi.utils.UtilsInternet;
import com.facebook.drawee.view.SimpleDraweeView;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterUpPhotoActivity extends AppCompatActivity {
    private String user_id;
    @ViewInject(R.id.et_nick_name)
    private EditText nickName;
    @ViewInject(R.id.sv_photo)
    private SimpleDraweeView mPhoto;

    private UtilsInternet net = UtilsInternet.getInstance();
    private Map<String, String> maps = new HashMap<>();
    private Map<String, File> file = new HashMap<>();

    private static final int REQUEST_CAMERA_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_up_photo);
        x.view().inject(this);
        initData();
    }

    private void initData() {
        int usrId = getIntent().getIntExtra("usrId", 0);
        user_id = intParseString(usrId);
    }

    private String intParseString(int usrId) {
        return usrId + "";
    }

    @Event({R.id.over_register, R.id.sv_photo})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.over_register:
                upUserData();

                break;
            case R.id.sv_photo:
                upLoadingPhoto();
                break;
            default:
                break;
        }
    }

    private void upUserData() {
        String nick_name = nickName.getText().toString().trim();
        if (!CommonUtils.isEmpty(nick_name)) {
            Toast.makeText(this, "在这里我要上传头像还有user_id和nick_name", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(this, RegisterOverActivity.class));
    }

    public void back(View view) {
        finish();
    }

    private void upLoadingPhoto() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(true);
        intent.setMaxTotal(1);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    mPhoto.setImageURI("file://" + list.get(0));
                    break;
            }
        }
    }
}
