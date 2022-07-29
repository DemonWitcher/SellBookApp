package com.witcher.sellbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.UserHelper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends BaseActivity {

    public static void go(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private TextView mTvShop, mTvMine;
    private ImageView mIvShop, mIvMine;
    private ViewPager2 mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mTvShop = findViewById(R.id.tv_shop);
        mTvMine = findViewById(R.id.tv_mine);
        mIvShop = findViewById(R.id.iv_shop);
        mIvMine = findViewById(R.id.iv_mine);
        mVp = findViewById(R.id.vp);
        mTvShop.setOnClickListener(mNoDoubleClickListener);
        mTvMine.setOnClickListener(mNoDoubleClickListener);
        mVp.setAdapter(new MainVpAdapter(this));
        mVp.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                resetTabColor();
            }
        });
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_shop) {
                setVpCurrent(0);
            } else if (id == R.id.tv_mine) {
                if (UserHelper.isLogin()) {
                    setVpCurrent(1);
                } else {
                    LoginDialog dialog = new LoginDialog(MainActivity.this);
                    dialog.show();
                }
            }
        }
    };

    private void setVpCurrent(int current) {
        mVp.setCurrentItem(current);
        resetTabColor();
    }

    private void resetTabColor() {
        if (mVp.getCurrentItem() == 0) {
            mTvShop.setTextColor(getResources().getColor(R.color.main_bg));
            mTvMine.setTextColor(getResources().getColor(R.color.main_tv));
            mIvShop.setImageResource(R.mipmap.shop_tab2);
            mIvMine.setImageResource(R.mipmap.mine_tab1);
        } else {
            mTvShop.setTextColor(getResources().getColor(R.color.main_tv));
            mTvMine.setTextColor(getResources().getColor(R.color.main_bg));
            mIvShop.setImageResource(R.mipmap.shop_tab1);
            mIvMine.setImageResource(R.mipmap.mine_tab2);
        }
    }

    private static class MainVpAdapter extends FragmentStateAdapter {

        public MainVpAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return ShopFragment.newInstance();
            } else {
                return MineFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}