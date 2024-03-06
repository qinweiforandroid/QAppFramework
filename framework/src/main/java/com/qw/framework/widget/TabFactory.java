package com.qw.framework.widget;

import android.content.Context;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TabFactory {

    public static BaseTabView create(Context context, Class<? extends BaseTabView> type) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor(Context.class);
            constructor.setAccessible(true);
            return (BaseTabView) constructor.newInstance(new Object[]{context});
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
