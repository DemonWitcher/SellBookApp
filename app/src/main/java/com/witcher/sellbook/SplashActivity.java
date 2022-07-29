package com.witcher.sellbook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.DataInitUtil;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.SPUtil;
import com.witcher.sellbook.util.UserHelper;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

public class SplashActivity extends BaseActivity {

    private static final int GO_NEXT = 1;

    private TimeHandler timeHandler;

    private TextView mTvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mTvSkip = findViewById(R.id.tv_skip);
        mTvSkip.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                goNext();
            }
        });

        timeHandler = new TimeHandler(this);
        timeHandler.sendEmptyMessageDelayed(GO_NEXT, 3000);

        initBookData();
        UserHelper.resumeLoginState();
    }

    private void initBookData() {
        if (SPUtil.isDataInit()) {
            return;
        }
        DaoHelper.getInstance().addBook(DataInitUtil.getBooks());
        SPUtil.saveInitData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeHandler != null) {
            timeHandler.removeMessages(GO_NEXT);
        }
    }

    private void goNext() {
        if (timeHandler != null) {
            timeHandler.removeMessages(GO_NEXT);
        }
        MainActivity.go(this);
        finish();
    }

    private static class TimeHandler extends Handler {

        private final WeakReference<SplashActivity> activityWeakReference;

        @SuppressWarnings("deprecation")
        private TimeHandler(SplashActivity splashActivity) {
            activityWeakReference = new WeakReference<>(splashActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == GO_NEXT) {
                SplashActivity splashActivity = activityWeakReference.get();
                if (splashActivity != null) {
                    splashActivity.goNext();
                }
            }
        }
    }
}
