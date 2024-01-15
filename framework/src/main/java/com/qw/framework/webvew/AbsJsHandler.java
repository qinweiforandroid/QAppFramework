package com.qw.framework.webvew;

import android.webkit.WebView;

import androidx.annotation.NonNull;

/**
 * Created by qinwei on 2021/5/27 14:17
 */
public abstract class AbsJsHandler {
    @NonNull
    protected WebView webView;

    public AbsJsHandler(@NonNull WebView webView) {
        this.webView = webView;
    }

    public abstract void handler(Scheme scheme);

    public void callJsFunction(String fun) {
        JsBridge.callJsFunction(webView, fun);
    }
}