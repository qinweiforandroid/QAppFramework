package com.qw.framework.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qw.framework.core.R;

import java.util.ArrayList;

/**
 * Created by qinwei on 2017/2/19.
 */

public class FragmentListActivity extends BaseActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.base_support_fragment_activity, true);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ArrayList<ContainerFragmentActivity.Clazz> cs = (ArrayList<ContainerFragmentActivity.Clazz>) getIntent().getSerializableExtra("cs");
        if (getSupportFragmentManager().findFragmentByTag("SupportListFragment") == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, SupportListFragment.newInstance(cs), "SupportListFragment").commit();
        }
    }

    @Override
    protected boolean isDisplayHomeAsUpEnabled() {
        return getIntent().getBooleanExtra("isDisplayHomeAsUpEnabled", false);
    }

    public static void startActivity(Context context, ArrayList<ContainerFragmentActivity.Clazz> cs) {
        startActivity(context, cs, true);
    }

    public static void startActivity(Context context, ArrayList<ContainerFragmentActivity.Clazz> cs, boolean isDisplayHomeAsUpEnabled) {
        Intent intent = new Intent(context, FragmentListActivity.class);
        intent.putExtra("cs", cs);
        intent.putExtra("isDisplayHomeAsUpEnabled", isDisplayHomeAsUpEnabled);
        context.startActivity(intent);
    }
}