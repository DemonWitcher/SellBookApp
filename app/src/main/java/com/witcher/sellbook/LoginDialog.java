package com.witcher.sellbook;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.witcher.sellbook.module.User;
import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.ToastUtil;
import com.witcher.sellbook.util.UserHelper;

import androidx.annotation.NonNull;

public class LoginDialog extends Dialog {

    public static LoginDialog newInstance(Context context) {
        return new LoginDialog(context);
    }

    public LoginDialog(@NonNull Context context) {
        super(context, R.style.commonDialog);
    }

    private EditText mEtPhone, mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);
        initView();
    }

    private void initView() {
        mEtPhone = findViewById(R.id.et_phone);
        mEtPassword = findViewById(R.id.et_password);
        findViewById(R.id.tv_login).setOnClickListener(mNoDoubleClickListener);
        findViewById(R.id.iv_close).setOnClickListener(mNoDoubleClickListener);
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_login) {
                login();
            } else if (id == R.id.iv_close) {
                dismiss();
            }
        }
    };

    private void login() {
        String phone = mEtPhone.getText().toString();
        String password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast(getContext(), "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast(getContext(), "请输入密码");
            return;
        }
        User user = DaoHelper.getInstance().getUserByPhone(phone);
        if (user == null) {
            //注册
            UserHelper.register(phone, password);
            ToastUtil.toast(getContext(), "登录成功");
            dismiss();
        } else {
            //登录
            if (password.equals(user.getPassword())) {
                UserHelper.login(user);
                ToastUtil.toast(getContext(), "登录成功");
                dismiss();
            } else {
                ToastUtil.toast(getContext(), "密码错误");
            }
        }
    }


}
