package com.app.agriculturalproducts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.app.agriculturalproducts.fragment.BaseMapFragment;
import com.app.agriculturalproducts.fragment.BaseUploadFragment;
import com.app.agriculturalproducts.fragment.PerticidesHistoryFragment;
import com.app.agriculturalproducts.util.InputType;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseUploadActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    Fragment currentFragment;
    BaseUploadFragment editFragment;
    Fragment dataFragment;
    boolean hasSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        hasSaved = false;
    }

    public void initToolBar(String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);//toolbar支持
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );
        toolbar.setOnMenuItemClickListener(itemClick);
    }

    private Toolbar.OnMenuItemClickListener itemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId()==R.id.action_history){
                if(currentFragment == editFragment){
                    chageFragment(editFragment,dataFragment,R.id.frame_view);
                    currentFragment = dataFragment;
                }
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu_his);
            }else if(item.getItemId()==R.id.action_back){
                if(currentFragment == dataFragment){
                    chageFragment(dataFragment,editFragment,R.id.frame_view);
                    currentFragment = editFragment;
                }
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu_a);
            }else if(item.getItemId()==R.id.action_save){
                if(currentFragment == editFragment){
                    int val = editFragment.save();
                    if(val == InputType.INPUT_EMPTY){
                        new MaterialDialog.Builder(BaseUploadActivity.this)
                                .title("内容不能为空！")
                                .positiveText("好的")
                                .show();
                    }else if(val == InputType.INPUT_SAVE_OK){
                        new MaterialDialog.Builder(BaseUploadActivity.this)
                                .title("保存成功！")
                                .positiveText("好的")
                                .show();
                        hasSaved = true;
                    }else if(val == InputType.INPUT_SAVE_ALREADY){
                        new MaterialDialog.Builder(BaseUploadActivity.this)
                                .title("已经保存！")
                                .positiveText("好的")
                                .show();
                    }
                }
            }
            return true;
        }
    };


    private void chageFragment(Fragment from ,Fragment to,int containerID){
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if(!to.isAdded()){
            transaction.hide(from).add(containerID, to).commit();
        }else{
            transaction.hide(from).show(to).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_a, menu);
        return true;
    }
}
