package com.witcher.sellbook;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.UserHelper;

import androidx.annotation.NonNull;

public class LogoutDialog extends Dialog {

    public LogoutDialog(@NonNull Context context) {
        super(context);
    }

    private TextView mTvOk, mTvCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        initView();
    }

    private void initView() {
        mTvOk = findViewById(R.id.tv_ok);
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvOk.setOnClickListener(mNoDoubleClickListener);
        mTvCancel.setOnClickListener(mNoDoubleClickListener);
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_ok) {
                UserHelper.loginOut();
                dismiss();
            } else if (id == R.id.tv_cancel) {
                dismiss();
            }
        }
    };

}
