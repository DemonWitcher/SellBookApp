package com.witcher.sellbook.util;

import android.view.View;

public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 300;
    private long lastClickTime = 0;

    private int delayTime = MIN_CLICK_DELAY_TIME;

    public abstract void onNoDoubleClick(View v);

    public NoDoubleClickListener() {
    }

    public NoDoubleClickListener(int delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > delayTime) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
}