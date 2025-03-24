package com.qw.framework.ui.tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.qw.framework.R;
import com.qw.framework.ui.BaseActivity;
import com.qw.framework.ui.BaseFragment;
import com.qw.framework.ui.IFragment;

import java.lang.reflect.InvocationTargetException;


/**
 * Created by qinwei on 2017/4/4.
 */
public class ContainerFragmentActivity extends BaseActivity {
    public static final String KEY_FRAGMENT_CLAZZ = "key_fragment_clazz";
    public static final String KEY_FRAGMENT_ARGS = "key_fragment_args";
    private ContainerClazz clazz;
    private Fragment fragment;

    @Override
    protected void setContentView() {
        clazz = (ContainerClazz) getIntent().getSerializableExtra(KEY_FRAGMENT_CLAZZ);
        setContentView(R.layout.base_container_activity, !TextUtils.isEmpty(clazz.getTitle()));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle args = getIntent().getBundleExtra(KEY_FRAGMENT_ARGS);
        if (!TextUtils.isEmpty(clazz.getTitle())) {
            setTitle(clazz.getTitle());
        }

        try {
            fragment = getSupportFragmentManager().findFragmentByTag("ContainerFragment");
            if (fragment == null) {
                fragment = clazz.getClazz().getDeclaredConstructor().newInstance();
                if (args != null) {
                    fragment.setArguments(args);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "ContainerFragment").commit();
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof IFragment && !((IFragment) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    public static Intent getIntent(Context context, ContainerClazz clazz, Bundle args) {
        Intent intent = new Intent(context, ContainerFragmentActivity.class);
        intent.putExtra(KEY_FRAGMENT_CLAZZ, clazz);
        if (args != null) {
            intent.putExtra(KEY_FRAGMENT_ARGS, args);
        }
        return intent;
    }

    public static Intent getIntent(Context context, ContainerClazz clazz) {
        return getIntent(context, clazz, null);
    }
}