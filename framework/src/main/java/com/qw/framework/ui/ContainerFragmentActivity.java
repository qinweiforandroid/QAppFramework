package com.qw.framework.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.qw.framework.core.R;

import java.io.Serializable;


/**
 * Created by qinwei on 2017/4/4.
 */
public class ContainerFragmentActivity extends BaseActivity {
    public static final String KEY_FRAGMENT_CLAZZ = "key_fragment_clazz";
    public static final String KEY_FRAGMENT_ARGS = "key_fragment_args";
    private Clazz clazz;
    private BaseFragment fragment;

    @Override
    protected void setContentView() {
        clazz = (Clazz) getIntent().getSerializableExtra(KEY_FRAGMENT_CLAZZ);
        setContentView(R.layout.base_container_activity, clazz.hasTitle);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle args = getIntent().getBundleExtra(KEY_FRAGMENT_ARGS);
        if (clazz.hasTitle) {
            setTitle(clazz.title);
        }
        try {
            fragment = clazz.clazz.newInstance();
            if (args != null) {
                fragment.setArguments(args);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public static Intent getIntent(Context context, Clazz clazz, Bundle args) {
        Intent intent = new Intent(context, ContainerFragmentActivity.class);
        intent.putExtra(KEY_FRAGMENT_CLAZZ, clazz);
        if (args != null) {
            intent.putExtra(KEY_FRAGMENT_ARGS, args);
        }
        return intent;
    }

    public static Intent getIntent(Context context, Clazz clazz) {
        return getIntent(context, clazz, null);
    }


    public static class Clazz implements Serializable {
        public Class<? extends BaseFragment> clazz;
        public boolean hasTitle;
        public String title;

        public Clazz(String title, Class<? extends BaseFragment> clazz) {
            this.clazz = clazz;
            this.hasTitle = true;
            this.title = title;
        }
    }
}