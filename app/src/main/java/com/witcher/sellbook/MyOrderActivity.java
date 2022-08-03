package com.witcher.sellbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.event.BuyEvent;
import com.witcher.sellbook.module.Order;
import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MyOrderActivity extends BaseActivity {

    public static void go(Context context) {
        Intent intent = new Intent(context, MyOrderActivity.class);
        context.startActivity(intent);
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRv;
    private OrderAdapter mAdapter;
    private ImageView mIvBack;
    private TextView mTvEmpty;
    private ConstraintLayout mClEmpty;

    private List<Order> mOrders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mIvBack = findViewById(R.id.iv_back);
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mRv = findViewById(R.id.rv);
        mTvEmpty = findViewById(R.id.tv_empty);
        mTvEmpty.setText("还没有订单");
        mClEmpty = findViewById(R.id.cl_empty);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new OrderAdapter();
        mRv.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mIvBack.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mOrders = DaoHelper.getInstance().getOrderByUid(UserHelper.getUser().getId());
        mAdapter.setData(mOrders);
        mAdapter.notifyDataSetChanged();
        if (mOrders == null || mOrders.isEmpty()) {
            mClEmpty.setVisibility(View.VISIBLE);
        } else {
            mClEmpty.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BuyEvent event) {
        if (event == null || mOrders == null) {
            return;
        }
        int size = mOrders.size();
        for (int i = 0; i < size; ++i) {
            Order order = mOrders.get(i);
            if (order == null) {
                continue;
            }
            if (event.getOrderId().equals(order.getOrderId())) {
                order.setStatus(Order.STATUS_FINISH);
                mAdapter.notifyItemChanged(i);
                return;
            }
        }
    }
}
