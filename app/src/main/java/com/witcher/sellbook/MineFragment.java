package com.witcher.sellbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.witcher.sellbook.event.LoginEvent;
import com.witcher.sellbook.event.LogoutEvent;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.UserHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    private TextView mTvName, mTvPhone, mTvCollection, mTvOrder, mTvLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        resetLoginState();
        return view;
    }

    private void initView(View view) {
        mTvName = view.findViewById(R.id.tv_name);
        mTvPhone = view.findViewById(R.id.tv_phone);
        mTvCollection = view.findViewById(R.id.tv_collection);
        mTvOrder = view.findViewById(R.id.tv_order);
        mTvLogout = view.findViewById(R.id.tv_logout);

        mTvCollection.setOnClickListener(mNoDoubleClickListener);
        mTvOrder.setOnClickListener(mNoDoubleClickListener);
        mTvLogout.setOnClickListener(mNoDoubleClickListener);
        mTvName.setOnClickListener(mNoDoubleClickListener);
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_collection) {
                if (UserHelper.isLogin()) {
                    MyCollectionActivity.go(getContext());
                } else {
                    LoginDialog dialog = new LoginDialog(getContext());
                    dialog.show();
                }
            } else if (id == R.id.tv_order) {
                if (UserHelper.isLogin()) {
                    MyOrderActivity.go(getContext());
                } else {
                    LoginDialog dialog = new LoginDialog(getContext());
                    dialog.show();
                }
            } else if (id == R.id.tv_logout) {
                LogoutDialog logoutDialog = new LogoutDialog(getContext());
                logoutDialog.show();
            } else if (id == R.id.tv_name) {
                if (!UserHelper.isLogin()) {
                    LoginDialog dialog = new LoginDialog(getContext());
                    dialog.show();
                }
            }
        }
    };

    private void resetLoginState() {
        if (UserHelper.isLogin()) {
            mTvName.setText(UserHelper.getUser().getNickName());
            mTvPhone.setText(UserHelper.getUser().getPhoneNumber());
            mTvLogout.setVisibility(View.VISIBLE);
        } else {
            mTvName.setText("请先登录");
            mTvPhone.setText("");
            mTvLogout.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent loginEvent) {
        resetLoginState();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LogoutEvent logoutEvent) {
        resetLoginState();
    }

}
