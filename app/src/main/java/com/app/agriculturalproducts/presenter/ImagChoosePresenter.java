package com.app.agriculturalproducts.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by ALPHONSO on 2016/1/15.
 */
public class ImagChoosePresenter {
    private AppCompatActivity activity;
    private ImageView img;
    private AlertDialog dialog;
    public ImagChoosePresenter(AppCompatActivity activity) {
        this.activity = activity;
    }
    Uri imgUri;
    private int crop = 180;
    private static int output_X = 480;
    private static int output_Y = 480;
    private static final int CODE_GALLERY_REQUEST  = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    public void setImgListner(String title){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    dialog = new AlertDialog.Builder(activity).setItems(new String[]{"相机", "相册"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                imgUri = Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "myapp_" + String.valueOf(System.currentTimeMillis()) + ".png"));
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                                activity.startActivityForResult(intent, CODE_CAMERA_REQUEST);
                            } else {
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*");
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                activity.startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
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

    public void setImg(ImageView img) {
        this.img = img;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }
}
