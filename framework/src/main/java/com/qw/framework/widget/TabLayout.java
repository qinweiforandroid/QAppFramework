package com.qw.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import java.util.ArrayList;

/**
 * Created by qinwei on 2016/10/17 11:30
 * email:qinwei_it@163.com
 */

public class TabLayout extends LinearLayout implements View.OnClickListener {
    public static final int ID_PREFIX = 1000;
    private OnTabClickListener listener;
    private int currentIndex = -1;

    public TabLayout(Context context) {
        super(context);
        initView(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);
    }

    public void initData(ArrayList<BaseTab> tabs, OnTabClickListener listener) {
        this.listener = listener;
        if (tabs == null || tabs.size() == 0) {
            throw new IllegalArgumentException("tabs can't be null");
        }
        BaseTabView tabView;
        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        for (int i = 0; i < tabs.size(); i++) {
            tabView = TabFactory.create(getContext(), tabs.get(i).getTabViewClass());
            assert tabView != null;
            tabView.setId(i + ID_PREFIX);
            tabView.initData(tabs.get(i));
            tabView.setOnClickListener(this);
            addView(tabView, layoutParams);
        }
    }

    public void notifyDataChanged(int index, int badgeCount) {
        ((BaseTabView) findViewById(index + ID_PREFIX)).onBadgeChanged(badgeCount);
    }

    public void setCurrentTab(int index) {
        if (index != currentIndex) {
            onClick(findViewById(index + ID_PREFIX));
        }
    }

    @Override
    public void onClick(View v) {
        int newIndex = v.getId() - ID_PREFIX;
        int oldIndex = currentIndex;
        if (newIndex != oldIndex) {
            if (listener.onTabItemClick(newIndex)) {
                View from = findViewById(oldIndex + ID_PREFIX);
                View to = findViewById(newIndex + ID_PREFIX);
                if (from != null) {
                    from.setSelected(false);
                }
                to.setSelected(true);
                currentIndex = newIndex;
                listener.onTabSwitched(newIndex, oldIndex);
            }
        }
    }

    public interface OnTabClickListener {
        boolean onTabItemClick(int currentIndex);

        void onTabSwitched(int newIndex, int oldIndex);
    }
}