package com.app.agriculturalproducts;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.bean.UserInfo;
import com.app.agriculturalproducts.presenter.UserInfoPresenter;
import com.app.agriculturalproducts.util.EditTextUtil;
import com.app.agriculturalproducts.util.IMGUtil;
import com.app.agriculturalproducts.util.InputType;
import com.app.agriculturalproducts.view.UserInfoSimpleView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A login screen that offers login via email/password.
 */
public class SignActivity extends AppCompatActivity implements UserInfoSimpleView{
    @Bind(R.id.signImgView)
    CircleImageView imgView;
    @Bind(R.id.signNameText)
    EditText name;
    @Bind(R.id.signPhoneText)
    EditText phone;
    @Bind(R.id.signIDText)
    EditText id;
    @Bind(R.id.signCOOPText)
    EditText coop;
    @Bind(R.id.signPWDText)
    EditText pwd;
    @Bind(R.id.signPWDAGText)
    EditText pwdAG;
    UserInfo userinfo;
    UserInfoPresenter mUserInfoPresenter;
    Uri imgUri;
    Bitmap photo;
    private AlertDialog dialog;
    private int crop = 180;
    private static int output_X = 480;
    private static int output_Y = 480;
    private static final int CODE_GALLERY_REQUEST  = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    dialog = new AlertDialog.Builder(SignActivity.this).setItems(new String[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                imgUri = Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "myapp_" + String.valueOf(System.currentTimeMillis()) + ".png"));
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                                startActivityForResult(intent, CODE_CAMERA_REQUEST);
                            } else {
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*");
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
                            }
                        }
                    }).create();
                }
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        });
        mUserInfoPresenter = new UserInfoPresenter(getApplicationContext(),this);
    }

    @OnClick(R.id.sign_enter_button)
    void sign(){
        String val = checkInput();
        if(InputType.INPUT_CHECK_OK.equals(val)){
            saveImg();
            mUserInfoPresenter.setUserInfo(userinfo);
            new MaterialDialog.Builder(this)
                    .title("注册成功")
                    .positiveText("好的").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    finish();
                }
            }).show();
            return;
        }
        new MaterialDialog.Builder(this)
                .title("填写出错")
                .content(val)
                .positiveText("好的")
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 用户没有进行有效的设置操作，返回
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode){
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(data.getData());
                break;
            case CODE_RESULT_REQUEST:
                setImgeView(data);
                break;
            case CODE_CAMERA_REQUEST:
                doCrop();
                break;
        }
    }

    private void doCrop() {
        modifyImg();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, 0);
        intent.setData(imgUri);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        Intent i = new Intent(intent);
        ResolveInfo res = list.get(0);
        i.setComponent(new ComponentName(res.activityInfo.packageName,
                res.activityInfo.name));
        startActivityForResult(i, CODE_RESULT_REQUEST);
    }

    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    public void setImgeView(Intent intent){
        Bundle extras = intent.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            imgView.setImageBitmap(photo);
        }
    }

    @Override
    protected void onDestroy() {
        if(photo!=null){
            photo.recycle();
        }
        super.onDestroy();
    }

    private void modifyImg(){
        int orientation = IMGUtil.readPictureDegree(imgUri.getPath());//获取旋转角度
        Bitmap picture = BitmapFactory.decodeFile(imgUri.getPath());
        Bitmap resizePicture = IMGUtil.rotatePicture(picture, orientation);
        IMGUtil.saveBmpToPath(resizePicture, imgUri.getPath());
        picture.recycle();
        resizePicture.recycle();
    }


    private void saveImg(){
        //创建缓存目录，系统一运行就得创建缓存目录的，
        File cache = new File(Environment.getExternalStorageDirectory(), this.getString(R.string.img_path));
        if(!cache.exists()){
            cache.mkdirs();
        }
        String path = cache.getAbsolutePath()+ "/"+System.currentTimeMillis() + ".jpg";
        userinfo.setPath(path);
        IMGUtil.saveBmpToPath(photo, path);
    }

    private String checkInput() {
        userinfo = new UserInfo();
        String nameStr = name.getEditableText().toString();
        if(TextUtils.isEmpty(nameStr)){
            return "用户名不能为空！";
        }
        String phoneStr = phone.getEditableText().toString();
        if(!EditTextUtil.isPhoneNumber(phoneStr)){
            return "手机号不对！";
        }
        String IDStr = id.getEditableText().toString();
        try {
            if(!InputType.INPUT_CHECK_OK.equals(EditTextUtil.IDCardValidate(IDStr))){
                Log.e("testbb",EditTextUtil.IDCardValidate(IDStr)+" "+id+" "+id.length());
                return "身份证号不对！";
            }
        } catch (ParseException e) {
            return "身份证号不对！";
        }
        String coopStr = coop.getEditableText().toString();
        if(TextUtils.isEmpty(coopStr)){
            return "合作社不能为空！";
        }
        String pwdStr = pwd.getEditableText().toString();
        String pwdAGStr = pwdAG.getEditableText().toString();
        if(TextUtils.isEmpty(pwdStr)){
            return "密码不能为空";
        }
        Log.e("testbb",pwdAGStr+" "+pwdStr);
        if(!pwdAGStr.equals(pwdStr)){
            return "密码不相同";
        }
        if(photo==null){
            return "请选择头像";
        }
        userinfo.setName(nameStr);
        userinfo.setPhone(phoneStr);
        userinfo.setId(IDStr);
        userinfo.setCoop(coopStr);
        userinfo.setPwd(pwdStr);
        return InputType.INPUT_CHECK_OK;

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setImg(String path) {

    }

    @Override
    public void setPhone(String phone) {

    }

    @Override
    public void setCoop(String coop) {

    }

    @Override
    public void setId(String id) {

    }
}

