package com.witcher.sellbook;

import android.app.Application;

public class BookApp extends Application {


    private static BookApp bookApp;

    /**
     * 设计
     * 页面 开屏页 登录注册页 主页  图书流  个人中心  详情页  购买页  我的订单 我的收藏 设置页
     */

    @Override
    public void onCreate() {
        super.onCreate();
        bookApp = this;
    }

    public static BookApp getApp() {
        return bookApp;
    }
}
