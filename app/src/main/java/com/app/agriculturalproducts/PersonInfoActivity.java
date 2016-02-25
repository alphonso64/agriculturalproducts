package com.app.agriculturalproducts;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.agriculturalproducts.bean.EmployeeInfo;
import com.app.agriculturalproducts.model.EmployeeInfoModel;
import com.app.agriculturalproducts.util.IMGUtil;
import com.app.agriculturalproducts.util.InputType;
import com.app.agriculturalproducts.util.StringUtil;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonInfoActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personInfoImgView)
    CircleImageView imgView;
    @Bind(R.id.personInfoNameText)
    TextView nameText;
    @Bind(R.id.personInfoPhoneText)
    TextView phoneText;
    @Bind(R.id.personInfoIDText)
    TextView idText;
    @Bind(R.id.personInfoCOOPText)
    TextView coopText;

    Uri imgUri;
    Bitmap photo;
    private AlertDialog dialog;
    private int crop = 180;
    private static int output_X = 480;
    private static int output_Y = 480;
    private static final int CODE_GALLERY_REQUEST  = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolBar(toolbar, getResources().getString(R.string.person_info));
        setPersonInfo();
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    dialog = new AlertDialog.Builder(PersonInfoActivity.this).setTitle("更换头像")
                            .setItems(new String[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
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
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_person_info;
    }

    private void setPersonInfo(){
        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(this);
        EmployeeInfo employeeInfo = employeeInfoModel.getEmployeeInfo();

        nameText.setText(employeeInfo.getEmployee_name());
        coopText.setText(employeeInfo.getMember_name());
        {
            String phone = employeeInfo.getEmployee_passport();
           // phoneText.setText(StringUtil.getMaskedStr(phone,4,'*'));
            if(phone == null){
                phoneText.setText("无");
            }else{
                phoneText.setText(phone);
            }

        }
        {
            String id =  employeeInfo.getEmployee_tel();
          //  idText.setText(StringUtil.getMaskedStr(id,8,'*'));
            if(id == null){
                idText.setText("无");
            }else{
                idText.setText(id);
            }
        }

        String path = employeeInfo.getPath();
        if(path!=null){
            Bitmap picture = BitmapFactory.decodeFile(path);
            imgView.setImageBitmap(picture);
        }
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
                saveImg();
                saveInfo();
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
        path = cache.getAbsolutePath()+ "/"+System.currentTimeMillis() + ".jpg";
        IMGUtil.saveBmpToPath(photo, path);
    }

    private void saveInfo() {
        EmployeeInfoModel employeeInfoModel = new EmployeeInfoModel(this);
        EmployeeInfo employeeInfo = employeeInfoModel.getEmployeeInfo();
        employeeInfo.setPath(path);
        employeeInfoModel.setEmployeeInfo(employeeInfo);
    }

}
