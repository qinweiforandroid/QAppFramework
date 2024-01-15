package com.qw.framework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.qw.framework.App;
import com.qw.framework.core.R;
import com.qw.framework.ThemeCompat;
import com.qw.framework.uitls.L;

import java.util.Objects;

/**
 * 解決应用强杀导致的null指针错误
 * Created by qinwei on 2016/9/24 21:17
 * email:qinwei_it@163.com
 *
 * @author qinwei
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected MaterialToolbar mToolbar;
    private int mMenuId;
    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        d("onCreate");
        super.onCreate(savedInstanceState);
        setCustomTheme();
        d("后台强杀保护开启状态:" + App.isProtectEnabled());
        if (protect()) {
            protectApp();
        } else {
            setContentView();
            initToolbar();
            initView();
            initData(savedInstanceState);
        }
    }

    private boolean protect() {
        return App.isProtectEnabled() && App.isOffline();
    }

    protected void setCustomTheme() {
        ThemeCompat.setTheme(this);
    }

    /**
     * 应用被强杀重启app
     */
    protected void protectApp() {
        L.d("protectApp " + getClass().getSimpleName() + " killed will do reStart");
        Intent intent = new Intent(this, App.getMainActivityClass());
        intent.putExtra(App.KEY_ACTION, App.ACTION_RESTART_APP);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    /**
     * 设置视图
     */
    protected abstract void setContentView();

    /**
     * 初始化toolbar
     */
    protected void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            Objects.requireNonNull(getSupportActionBar())
                    .setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
        }
    }

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 处理数据
     *
     * @param savedInstanceState 数据状态恢复
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        this.setContentView(layoutResID, false);
    }

    public void setContentView(@LayoutRes int layoutResID, boolean hasTitle) {
        setContentView(getLayoutInflater().inflate(layoutResID, null), hasTitle);
    }

    public void setContentView(View view, boolean hasTitle) {
        if (hasTitle) {
            ViewGroup root = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.base_title_layout, null);
            root.addView(view, findViewById(android.R.id.content).getLayoutParams());
            super.setContentView(root);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    protected void onStart() {
        d("onStart");
        super.onStart();
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        d("onCreateOptionsMenu");
        if (mMenuId != 0) {
            getMenuInflater().inflate(mMenuId, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        d("onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                finishActivity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setMenuId(int menuId) {
        int tempMenuId = mMenuId;
        this.mMenuId = menuId;
        if (tempMenuId != mMenuId) {
            invalidateOptionsMenu();
        }
    }

    protected boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    public void finishActivity() {
        finish();
    }

    @Override
    protected void onResume() {
        d("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        d("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        d("onStop");
        super.onStop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        d("onWindowFocusChanged:" + hasFocus);
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onDestroy() {
        d("onDestroy");
        super.onDestroy();
    }

    private void d(String msg) {
        if (App.isDebug()) {
            L.d("activity " + TAG + "->" + msg);
        }
    }
}