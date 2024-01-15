package com.qw.framework;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import java.util.Map;

/**
 * Created by qinwei on 2019-11-06 17:39
 * email: qinwei_it@163.com
 */
public class App {
    /**
     * app初始化
     */
    public static final int STATE_INIT = 100;
    /**
     * 在线
     */
    public static final int STATE_ONLINE = 101;

    public static final String KEY_ACTION = "key_action";
    /**
     * 重新启动
     */
    public static final int ACTION_RESTART_APP = 0;
    /**
     * 登出
     */
    public static final int ACTION_LOGOUT = 1;
    /**
     * 被踢
     */
    public static final int ACTION_KICK_OUT = 2;
    /**
     * 退回首页
     */
    public static final int ACTION_BACK_TO_HOME = 3;


    private static App mInstance;
    /**
     * 上下文
     */
    private final Context context;
    /**
     * 主页面类对象
     */
    private Class<? extends Activity> mainActivityClass;
    /**
     * 渠道名称
     */
    private String channel;
    /**
     * 版本名称
     */
    private String versionName;
    /**
     * 版本号
     */
    private int versionCode;

    private boolean isDebug;
    /**
     * 是否启用后台强杀保护功能
     */
    private boolean protectEnabled;
    private String applicationId;
    /**
     * 全局配置参数
     */
    private ArrayMap<String, String> global = new ArrayMap<>();

    /**
     * 默认初始化状态
     */
    private int state = STATE_INIT;

    private App(Context context) {
        this.context = context.getApplicationContext();
    }

    public static void init(Builder builder) {
        if (mInstance == null) {
            mInstance = builder.build();
        }
    }


    public static Context getContext() {
        checkInit();
        return mInstance.context;
    }

    public static Class<? extends Activity> getMainActivityClass() {
        checkInit();
        return mInstance.mainActivityClass;
    }

    public static String getChannel() {
        checkInit();
        return mInstance.channel;
    }

    public static String getVersionName() {
        checkInit();
        return mInstance.versionName;
    }

    public static String getApplicationId() {
        checkInit();
        return mInstance.applicationId;
    }

    public static int getVersionCode() {
        checkInit();
        return mInstance.versionCode;
    }

    public static boolean isProtectEnabled() {
        checkInit();
        return mInstance.protectEnabled;
    }

    private static void checkInit() {
        if (mInstance == null) {
            throw new IllegalArgumentException("you must call init");
        }
    }

    @NonNull
    public static String getParam(String key) {
        if (mInstance.global.containsKey(key)) {
            return mInstance.global.get(key);
        }
        return "";
    }

    public static void online() {
        checkInit();
        mInstance.state = STATE_ONLINE;
    }

    public static boolean isOffline() {
        checkInit();
        return mInstance.state == STATE_INIT;
    }

    public static boolean isDebug() {
        checkInit();
        return mInstance.isDebug;
    }

    public static class Builder {
        private final Context context;
        private ArrayMap<String, String> global = new ArrayMap<>();
        private Class<? extends Activity> mainActivityClass;
        private String channel;
        private String versionName;
        private String applicationId;
        private boolean isDebug;
        private int versionCode;
        private boolean protectEnabled;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setApplicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder setMainActivityClass(Class<? extends Activity> mainActivityClass) {
            this.mainActivityClass = mainActivityClass;
            return this;
        }

        public Builder setVersionName(String versionName) {
            this.versionName = versionName;
            return this;
        }

        public Builder setVersionCode(int versionCode) {
            this.versionCode = versionCode;
            return this;
        }

        public Builder setProtectEnabled(boolean protectEnabled) {
            this.protectEnabled = protectEnabled;
            return this;
        }

        public Builder setDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public Builder setGlobal(ArrayMap<String, String> global) {
            this.global = global;
            return this;
        }

        private App build() {
            checkParams();
            App app = new App(context);
            app.channel = channel;
            app.versionCode = versionCode;
            app.versionName = versionName;
            app.mainActivityClass = mainActivityClass;
            app.protectEnabled = protectEnabled;
            app.applicationId = applicationId;
            app.isDebug = isDebug;
            for (Map.Entry<String, String> entry : global.entrySet()) {
                app.global.put(entry.getKey(), entry.getValue());
            }
            return app;
        }

        private void checkParams() {
            if (context == null) {
                throw new IllegalArgumentException("context must be init");
            }
        }
    }
}