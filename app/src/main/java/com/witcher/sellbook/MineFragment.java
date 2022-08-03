package com.witcher.sellbook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.event.LoginEvent;
import com.witcher.sellbook.event.UpdateUserEvent;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.ToastUtil;
import com.witcher.sellbook.util.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    private TextView mTvName;
    private TextView mTvPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_mine, null);
        initView(view);
        resetUI();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initView(View view) {
        mTvName = view.findViewById(R.id.tv_name);
        mTvPhone = view.findViewById(R.id.tv_phone);
        TextView tvAddress = view.findViewById(R.id.tv_address);
        TextView tvCollection = view.findViewById(R.id.tv_collection);
        TextView tvOrder = view.findViewById(R.id.tv_order);
        TextView tvLogout = view.findViewById(R.id.tv_logout);
        ImageView ivNameArrow = view.findViewById(R.id.iv_name_arrow);

        mTvName.setOnClickListener(mNoDoubleClickListener);
        mTvPhone.setOnClickListener(mNoDoubleClickListener);
        tvCollection.setOnClickListener(mNoDoubleClickListener);
        tvOrder.setOnClickListener(mNoDoubleClickListener);
        tvLogout.setOnClickListener(mNoDoubleClickListener);
        tvAddress.setOnClickListener(mNoDoubleClickListener);
        ivNameArrow.setOnClickListener(mNoDoubleClickListener);
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_collection) {
                MyCollectionActivity.go(getContext());
            } else if (id == R.id.tv_order) {
                MyOrderActivity.go(getContext());
            } else if (id == R.id.tv_logout) {
                LogoutDialog logoutDialog = new LogoutDialog(getContext());
                logoutDialog.show();
            } else if (id == R.id.tv_name || id == R.id.iv_name_arrow) {
                MyNameActivity.go(getContext());
            } else if (id == R.id.tv_address) {
                MyAddressActivity.go(getContext());
            } else if (id == R.id.tv_phone) {
                ToastUtil.toast(getContext(), "手机号不可修改");
            }
        }
    };

    private void resetUI() {
        if (UserHelper.isLogin()) {
            if (TextUtils.isEmpty(UserHelper.getUser().getNickName())) {
                mTvName.setText("昵称: 请去设置");
            } else {
                mTvName.setText(String.format("昵称: %s", UserHelper.getUser().getNickName()));
            }
            mTvPhone.setText(String.format("手机号: %s", UserHelper.getUser().getPhoneNumber()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdateUserEvent event) {
        resetUI();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent loginEvent) {
        resetUI();
    }

}
