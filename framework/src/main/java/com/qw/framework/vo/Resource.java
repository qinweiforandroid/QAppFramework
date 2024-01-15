package com.qw.framework.vo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by qinwei on 2020/4/26 3:14 PM
 * email: qinwei_it@163.com
 */
public class Resource<T> implements Serializable {
    public static final int IDLE = 0;
    public static final int ING = 1;
    public static final int SUCCESS = 2;
    public static final int FAIL = 3;
    @NonNull
    private int status;
    @NonNull
    private int code;
    @Nullable
    public T data;
    @NonNull
    private String msg = "";

    public Resource() {
    }

    public static <T> Resource<T> success(T data) {
        Resource<T> resource = new Resource<>();
        resource.data = data;
        resource.status = SUCCESS;
        return resource;
    }

    public static <T> Resource<T> loading() {
        return loading(null);
    }

    public static <T> Resource<T> loading(T t) {
        Resource<T> resource = new Resource<>();
        resource.status = ING;
        resource.data = t;
        return resource;
    }

    public static <T> Resource<T> idel() {
        Resource<T> resource = new Resource<>();
        resource.status = IDLE;
        resource.data = null;
        return resource;
    }


    public static <T> Resource<T> fail(int code, String msg) {
        Resource<T> resource = new Resource<>();
        resource.code = code;
        resource.msg = msg;
        resource.status = FAIL;
        return resource;
    }

    public static <T> Resource<T> fail(String msg) {
        return fail(-1, msg);
    }


    public boolean isLoading() {
        return status == ING;
    }

    public boolean isSuccessful() {
        return status == SUCCESS;
    }

    public boolean isFail() {
        return status == FAIL;
    }

    @NonNull
    public int getCode() {
        return code;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @NonNull
    public String getMsg() {
        return msg;
    }

    @NonNull
    public int getStatus() {
        return status;
    }
}