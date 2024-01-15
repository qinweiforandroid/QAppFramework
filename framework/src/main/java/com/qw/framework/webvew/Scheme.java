package com.qw.framework.webvew;

import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by qinwei on 2021/5/19 13:51
 */
public class Scheme {
    public String url;
    private String scheme;
    private String host;
    private int port;
    private String path;
    private final HashMap<String, String> params = new HashMap<>();

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public int getIntParam(String key) {
        String v = params.get(key);
        try {
            if (v != null) {
                return Integer.parseInt(v);
            }
        } catch (Exception e) {
        }
        return -1;
    }

    public static Scheme parse(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Uri uri = Uri.parse(url);
        Scheme scheme = new Scheme();
        scheme.url = url;
        scheme.scheme = uri.getScheme();
        scheme.host = uri.getHost();
        scheme.path = uri.getPath();
        scheme.port = uri.getPort();
        for (String name : uri.getQueryParameterNames()) {
            scheme.addParam(name, uri.getQueryParameter(name));
        }
        return scheme;
    }


    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Scheme{" +
                "url='" + url + '\'' +
                ", scheme='" + scheme + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", params=" + params +
                '}';
    }
}