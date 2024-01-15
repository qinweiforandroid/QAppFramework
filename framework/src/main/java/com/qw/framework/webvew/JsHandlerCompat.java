package com.qw.framework.webvew;

import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.collection.ArrayMap;

import com.qw.framework.webvew.annotation.JsHandler;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by qinwei on 2021/5/26 14:58
 */
public class JsHandlerCompat {
    private final ArrayMap<String, Class<? extends AbsJsHandler>> mHandlers = new ArrayMap<>();

    public void addJsHandler(Class<? extends AbsJsHandler> clazz) {
        JsHandler handler = clazz.getAnnotation(JsHandler.class);
        if (handler == null) return;
        String path = handler.path();
        if (!mHandlers.containsKey(path)) {
            mHandlers.put(path, clazz);
        }
    }

    public void callJsFunction(WebView webView, String function, ValueCallback<String> callback) {
        webView.evaluateJavascript("javascript:" + function, callback);
    }

    public void handler(WebView view, String url) {
        Scheme scheme = Scheme.parse(url);
        Class<? extends AbsJsHandler> clazz = mHandlers.get(scheme.getPath());
        try {
            AbsJsHandler handler = clazz.getConstructor(WebView.class).newInstance(view);
            handler.handler(scheme);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSupported(String url) {
        Scheme scheme = Scheme.parse(url);
        if (scheme == null) {
            return false;
        }
        return mHandlers.containsKey(scheme.getPath());
    }
}
