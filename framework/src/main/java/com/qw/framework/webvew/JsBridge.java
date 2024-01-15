package com.qw.framework.webvew;

import android.annotation.SuppressLint;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.collection.ArrayMap;

import java.util.Map;


/**
 * js native通讯组件
 * Created by qinwei on 2021/5/26 14:31
 */
public class JsBridge {
    private static JsBridge bridge;
    private final ArrayMap<Object, String> mJavascriptInterfaces = new ArrayMap<>(2);
    private final JsHandlerCompat mUrlHandlerCompat;
    private String scheme;

    private JsBridge(String scheme) {
        this.scheme = scheme;
        mUrlHandlerCompat = new JsHandlerCompat();
    }

    public static void init(String scheme) {
        if (bridge == null) {
            bridge = new JsBridge(scheme);
        }
    }

    public static void addJavascriptInterface(Object obj, String fun) {
        checkInit();
        bridge.mJavascriptInterfaces.put(obj, fun);
    }

    @SuppressLint("JavascriptInterface")
    public static void configJavascriptInterface(WebView webView) {
        checkInit();
        for (Map.Entry<Object, String> entry : bridge.mJavascriptInterfaces.entrySet()) {
            webView.addJavascriptInterface(entry.getKey(), entry.getValue());
        }
    }

    public static boolean isSupported(String url) {
        checkInit();
        return url.startsWith(bridge.scheme) && bridge.mUrlHandlerCompat.isSupported(url);
    }

    public static void handler(WebView view, String url) {
        checkInit();
        bridge.mUrlHandlerCompat.handler(view, url);
    }

    public static void addJsHandler(Class<? extends AbsJsHandler> clazz) {
        checkInit();
        bridge.mUrlHandlerCompat.addJsHandler(clazz);
    }

    public static void callJsFunction(WebView webView, String function) {
        callJsFunction(webView, function, null);
    }

    public static void callJsFunction(WebView webView, String function, ValueCallback<String> callback) {
        checkInit();
        bridge.mUrlHandlerCompat.callJsFunction(webView, function, callback);
    }

    private static void checkInit() {
        if (bridge == null) {
            throw new IllegalArgumentException("you must be call init method to init");
        }
    }
}