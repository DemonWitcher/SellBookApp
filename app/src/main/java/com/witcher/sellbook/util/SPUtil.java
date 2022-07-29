package com.witcher.sellbook.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.witcher.sellbook.BookApp;

public class SPUtil {

    private volatile static SharedPreferences sp;

    private static void init() {
        if (sp == null) {
            synchronized (SPUtil.class) {
                if (sp == null) {
                    sp = BookApp.getApp().getSharedPreferences("sellbook", Context.MODE_PRIVATE);
                }
            }
        }
    }

    public static void saveInitData() {
        init();
        sp.edit().putBoolean("dataInit", true).apply();
    }

    public static boolean isDataInit() {
        init();
        return sp.getBoolean("dataInit", false);
    }

    public static void saveLoginState(long uid){
        sp.edit().putLong("loginUid", uid).apply();
    }

    public static long getLoginUid() {
        init();
        return sp.getLong("loginUid", -1);
    }
}
