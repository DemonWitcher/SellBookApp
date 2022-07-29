package com.witcher.sellbook;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class BookActivity extends BaseActivity{

    /**
     * 封面
     * 书名
     * 作者
     * 出版社
     * 初版日期
     * 标签
     * 详情
     *
     * 收藏
     * 购买
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
    }
}
