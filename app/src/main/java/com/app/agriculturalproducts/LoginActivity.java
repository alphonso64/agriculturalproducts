package com.app.agriculturalproducts;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.agriculturalproducts.util.InputType;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.loginNameText)
    AutoCompleteTextView nameText;
    @Bind(R.id.loginPasswordText)
    EditText pwdText;
    @Bind(R.id.loginlayout)
    LinearLayout lyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        lyLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    if (nameText.isFocused()) {
                        Rect outRect = new Rect();
                        nameText.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) arg1.getRawX(), (int) arg1.getRawY())) {
                            nameText.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(nameText.getWindowToken(), 0);
                        }
                    }
                    if (pwdText.isFocused()) {
                        Rect outRect = new Rect();
                        pwdText.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) arg1.getRawX(), (int) arg1.getRawY())) {
                            pwdText.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(pwdText.getWindowToken(), 0);
                        }
                    }
                }

                return false;
            }
        });
    }

    private void checkLogin() {
        SharedPreferences sp = getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        if(InputType.INPUT_CHECK_OK.equals(sp.getString("isLogin",null))){
            Intent intent = new Intent(this, MainDrawlayoutActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.loginbutton)
    void login(){

        String name = nameText.getEditableText().toString();
        String pwd = pwdText.getText().toString();
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "请输入用户名或密码", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        SharedPreferences sp = getSharedPreferences(InputType.loginInfoDB,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        String nameSave= sp.getString("name", null);
        String pwdSave= sp.getString("pwd", null);
        if(!name.equals(nameSave)|| !pwd.equals(pwdSave)){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "用户名或密码出错", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        ed.putString("isLogin",InputType.INPUT_CHECK_OK);
        ed.commit();
        Intent intent = new Intent(this, MainDrawlayoutActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.signbutton)
    void sign(){
        Intent intent = new Intent(this, SignActivity.class);
        startActivity(intent);
    }
}

