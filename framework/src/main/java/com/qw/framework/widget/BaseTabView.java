package com.qw.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by qinwei on 2021/6/22 14:47
 */
public abstract class BaseTabView extends LinearLayout {
    public BaseTabView(@NonNull Context context) {
        super(context);
    }

    public BaseTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        onSelectedChanged(selected);
    }

    protected abstract void onSelectedChanged(boolean selected);

    public abstract void initData(BaseTab tab);

    public abstract void onBadgeChanged(int badge);
}