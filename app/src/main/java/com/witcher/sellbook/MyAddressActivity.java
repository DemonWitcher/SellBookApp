package com.witcher.sellbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.event.UpdateUserEvent;
import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.ToastUtil;
import com.witcher.sellbook.util.UserHelper;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;

public class MyAddressActivity extends BaseActivity {

    public static void go(Context context) {
        Intent intent = new Intent(context, MyAddressActivity.class);
        context.startActivity(intent);
    }

    private EditText mEtAddress;
    private ImageView mIvBack;
    private TextView mTvOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        initView();
        initData();
    }

    private void initData() {
        mEtAddress.setText(UserHelper.getUser().getAddress());
    }

    private void initView() {
        mEtAddress = findViewById(R.id.et_address);
        mIvBack = findViewById(R.id.iv_back);
        mTvOk = findViewById(R.id.tv_ok);

        mTvOk.setOnClickListener(mNoDoubleClickListener);
        mIvBack.setOnClickListener(mNoDoubleClickListener);
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.iv_back) {
                finish();
            } else if (id == R.id.tv_ok) {
                update();
            }
        }
    };

    private void update() {
        String address = mEtAddress.getText().toString();
        if(TextUtils.isEmpty(address)){
            ToastUtil.toast(this, "地址不可为空");
            return;
        }
        UserHelper.getUser().setAddress(address);
        DaoHelper.getInstance().updateUser(UserHelper.getUser());
        ToastUtil.toast(this, "修改成功");
        EventBus.getDefault().post(new UpdateUserEvent());
        finish();
    }
}
