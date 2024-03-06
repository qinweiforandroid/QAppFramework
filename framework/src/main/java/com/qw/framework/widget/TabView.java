package com.qw.framework.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.qw.framework.R;


/**
 * Created by qinwei on 2016/10/17 11:20
 * email:qinwei_it@163.com
 */

public class TabView extends BaseTabView {
    private ImageView mTabImg;
    private TextView mTabLabel;

    public TabView(Context context) {
        super(context);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_tab_view, this, true);
        mTabImg = (ImageView) findViewById(R.id.mTabImg);
        mTabLabel = (TextView) findViewById(R.id.mTabLabel);
    }

    @Override
    public void initData(BaseTab baseTab) {
        Tab tab = (Tab) baseTab;
        mTabImg.setBackgroundResource(tab.getImgResId());
        mTabLabel.setText(tab.getLabelResId());
        ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), tab.getLabelColorResId());
        if (colorStateList != null) {
            mTabLabel.setTextColor(colorStateList);//设置按钮文字颜色
        }
        onBadgeChanged(tab.getBadge());
        setEnabled(baseTab.isEnable());
    }

    @Override
    protected void onSelectedChanged(boolean selected) {

    }

    @Override
    public void onBadgeChanged(int badge) {

    }

    public static class Tab extends BaseTab {
        private int imgResId;
        private int labelColorResId;

        @Override
        public Class<? extends BaseTabView> getTabViewClass() {
            return TabView.class;
        }

        public int getImgResId() {
            return imgResId;
        }


        public int getLabelColorResId() {
            return labelColorResId;
        }

        public static class Builder {
            /**
             * 角标数量
             */
            private int badge;
            /**
             * 菜单信息
             */
            private int menuId;
            /**
             * 文本信息
             */
            private int labelResId;
            /**
             * fragment class
             */
            private Class<? extends Fragment> clazz;
            private int imgResId;
            private int labelColorResId;
            private boolean enable = true;
            private Bundle args = new Bundle();

            public Builder setBadge(int badge) {
                this.badge = badge;
                return this;
            }

            public Builder setMenuId(int menuId) {
                this.menuId = menuId;
                return this;
            }

            public Builder setEnable(boolean enable) {
                this.enable = enable;
                return this;
            }

            public Builder setLabelResId(int labelResId) {
                this.labelResId = labelResId;
                return this;
            }

            public Builder setClazz(Class<? extends Fragment> clazz) {
                this.clazz = clazz;
                return this;
            }

            public Builder setImgResId(int imgResId) {
                this.imgResId = imgResId;
                return this;
            }

            public Builder setArguments(@Nullable Bundle args) {
                this.args = args;
                return this;
            }

            public Builder setLabelColorResId(int labelColorResId) {
                this.labelColorResId = labelColorResId;
                return this;
            }

            public Tab builder() {
                Tab tab = new Tab();
                tab.imgResId = imgResId;
                tab.labelResId = labelResId;
                tab.labelColorResId = labelColorResId;
                tab.menuId = menuId;
                tab.badge = badge;
                tab.clazz = clazz;
                tab.enable = enable;
                tab.args = args;
                return tab;
            }
        }
    }
}