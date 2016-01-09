package com.app.agriculturalproducts;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.bean.PersticidesUsage;
import com.app.agriculturalproducts.bean.Task;
import com.app.agriculturalproducts.db.PersticidesUsageDataHelper;
import com.app.agriculturalproducts.fragment.DataFragment;
import com.app.agriculturalproducts.fragment.PerticidesFragment;
import com.app.agriculturalproducts.fragment.PerticidesHistoryFragment;
import com.app.agriculturalproducts.util.InputType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PesticidesActivity extends BaseUploadActivity {
    PerticidesFragment perticidesFragment;
    PerticidesHistoryFragment dataFragment;
    Fragment currentFragment;
    boolean hasSaved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

//        if (savedInstanceState == null) {
//            perticidesFragment = new PerticidesFragment();
//            dataFragment = new PerticidesHistoryFragment();
//            currentFragment = perticidesFragment;
//            getSupportFragmentManager().beginTransaction().add(R.id.frame_view,perticidesFragment).commit();
//        }

        perticidesFragment = new PerticidesFragment();
        dataFragment = new PerticidesHistoryFragment();
        currentFragment = perticidesFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frame_view,perticidesFragment).commit();
        toolbar.setOnMenuItemClickListener(itemClick);

        hasSaved = false;
    }

    private Toolbar.OnMenuItemClickListener itemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_upload) {
                new MaterialDialog.Builder(PesticidesActivity.this)
                        .title("暂不支持上传")
                        .positiveText("好的")
                        .show();
            }else if(item.getItemId()==R.id.action_history){
                if(currentFragment == perticidesFragment){
                    FragmentTransaction transaction = getSupportFragmentManager()
                            .beginTransaction();
                    transaction.remove(perticidesFragment);
                    if(dataFragment ==null){
                        dataFragment = new PerticidesHistoryFragment();
                    }
                    transaction.add(R.id.frame_view, dataFragment).commit();
                    currentFragment = dataFragment;
                }
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu_his);
            }else if(item.getItemId()==R.id.action_back){
                if(currentFragment == dataFragment){
                    FragmentTransaction transaction = getSupportFragmentManager()
                            .beginTransaction();
                    transaction.remove(dataFragment);
                    if(perticidesFragment ==null){
                        perticidesFragment = new PerticidesFragment();
                    }
                    transaction.add(R.id.frame_view, perticidesFragment).commit();
                    currentFragment = perticidesFragment;
                }else {
                    perticidesFragment.upload();
                }
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu);
            }else if(item.getItemId()==R.id.action_save){
                if(currentFragment == perticidesFragment){
                    int val = perticidesFragment.upload();
                    if(val == InputType.INPUT_EMPTY){
                        new MaterialDialog.Builder(PesticidesActivity.this)
                                .title("内容不能为空！")
                                .positiveText("好的")
                                .show();
                    }else if(val == InputType.INPUT_SAVE_OK){
                        new MaterialDialog.Builder(PesticidesActivity.this)
                                .title("保存成功！")
                                .positiveText("好的")
                                .show();
                        hasSaved = true;
                    }else if(val == InputType.INPUT_SAVE_ALREADY){
                        new MaterialDialog.Builder(PesticidesActivity.this)
                                .title("已经保存！")
                                .positiveText("好的")
                                .show();
                    }
                }
            }
            return true;
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_pesticides;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
