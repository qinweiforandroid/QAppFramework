package com.qw.framework.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class EmptyTabView extends BaseTabView {
    public EmptyTabView(@NonNull Context context) {
        super(context);
    }

    public EmptyTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectedChanged(boolean selected) {

    }

    @Override
    public void initData(BaseTab tab) {
        setEnabled(false);
    }

    public static class Tab extends BaseTab {

        public static Tab newTab() {
            return new Tab();
        }

        @Override
        public Class<? extends BaseTabView> getTabViewClass() {
            return EmptyTabView.class;
        }
    }

    @Override
    public void onBadgeChanged(int badge) {

    }
}