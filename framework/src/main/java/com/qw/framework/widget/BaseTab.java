package com.qw.framework.widget;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


/**
 * Created by qinwei on 2021/6/22 14:55
 */
public abstract class BaseTab {
    /**
     * 角标数量
     */
    protected int badge;
    /**
     * 菜单信息
     */
    protected int menuId;
    /**
     * 文本信息
     */
    protected int labelResId;
    /**
     * fragment class
     */
    protected Class<? extends Fragment> clazz;

    protected Bundle args;

    public Bundle getArgs() {
        return args;
    }

    public int getBadge() {
        return badge;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getLabelResId() {
        return labelResId;
    }

    public Class<? extends Fragment> getClazz() {
        return clazz;
    }

    /**
     * 是否支持可用
     */
    protected boolean enable = true;


    public boolean isEnable() {
        return enable;
    }

    public abstract Class<? extends BaseTabView> getTabViewClass();


}