package com.qw.framework.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qw.framework.uitls.L;

/**
 * fragment封装
 * Created by qinwei on 2017/4/16.
 */

public abstract class BaseFragment extends Fragment implements IFragment {

    public BaseFragment() {
        super();
    }

    public BaseFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    private final String TAG = getClass().getSimpleName();
    /**
     * 数据加载状态
     */
    protected boolean isDataInitialed;
    private boolean isLazyLoadData;

    public void enableLazyLoadData() {
        isLazyLoadData = true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        d("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d("onCreate");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        d("onViewCreated");
        initView(view);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        if (!isLazyLoadData && !isDataInitialed) {
            initData();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        d("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        d("onResume");
        if (!isDataInitialed && isLazyLoadData) {
            initData();
            isDataInitialed = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        d("onPause");
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        d("onDestroyOptionsMenu");
    }

    @Override
    public void onStop() {
        super.onStop();
        d("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        d("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        d("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        d("onDetach");
    }

    protected void d(String msg) {
        L.d("fragment " + TAG + "->" + msg);
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        d("onSaveInstanceState");
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

    }
}
