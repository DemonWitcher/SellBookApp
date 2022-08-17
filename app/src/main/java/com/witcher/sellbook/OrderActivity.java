package com.witcher.sellbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.event.BuyEvent;
import com.witcher.sellbook.event.UpdateUserEvent;
import com.witcher.sellbook.module.Book;
import com.witcher.sellbook.module.Order;
import com.witcher.sellbook.util.CommonUtil;
import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.ToastUtil;
import com.witcher.sellbook.util.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class OrderActivity extends BaseActivity {

    public static final int FROM_DETAILS = 1;
    public static final int FROM_MY_ORDER = 2;

    /**
     * 提供给图书详情页使用
     */
    public static void goFromDetails(Context context, long bookId) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("from", FROM_DETAILS);
        context.startActivity(intent);
    }

    /**
     * 提供给我的订单页使用
     */
    public static void goFromMyOrder(Context context, String orderId) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("from", FROM_MY_ORDER);
        context.startActivity(intent);
    }

    private ImageView mIvCover, mIvBack;
    private TextView mTvName, mTvInfo, mTvPrice, mTvNo, mTvStatus, mTvCreateTime, mTvFinishTime, mTvAddress, mTvBuy;
    private ConstraintLayout mClAddress;

    private Book mBook;
    private Order mOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initData();
        resetUI();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void resetUI() {
        mTvName.setText(mBook.getName());
        CommonUtil.loadCover(mIvCover, mBook.getCover());
        mTvInfo.setText(CommonUtil.getBookInfo(mBook));
        mTvPrice.setText(CommonUtil.formatPrice(mBook.getPrice()));
        mTvNo.setText(String.format("订单编号: %s", mOrder.getOrderId()));
        resetAddress();
        if (mOrder.getStatus() == Order.STATUS_PAY) {
            mTvStatus.setText("待付款");
            mTvStatus.setTextColor(getResources().getColor(R.color.main_bg));
            mTvBuy.setVisibility(View.VISIBLE);
            mTvFinishTime.setVisibility(View.INVISIBLE);
            mTvFinishTime.setText("");
        } else if (mOrder.getStatus() == Order.STATUS_FINISH) {
            mTvStatus.setText("已完成");
            mTvStatus.setTextColor(getResources().getColor(R.color.green));
            mTvBuy.setVisibility(View.GONE);
            mTvFinishTime.setVisibility(View.VISIBLE);
            mTvFinishTime.setText(String.format("完成时间: %s", CommonUtil.formatTime(mOrder.getFinishTime())));
        }
        mTvCreateTime.setText(String.format("创建时间: %s", CommonUtil.formatTime(mOrder.getCreateTime())));
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        int from = intent.getIntExtra("from", FROM_DETAILS);
        if (from == FROM_DETAILS) {
            long bookId = intent.getLongExtra("bookId", -1);
            if (bookId < 0) {
                return;
            }
            //创建新订单
            mOrder = new Order();
            mOrder.setStatus(Order.STATUS_PAY);
            mOrder.setCreateTime(System.currentTimeMillis());
            mOrder.setBookId(bookId);
            mOrder.setUid(UserHelper.getUser().getId());
            mOrder.setOrderId(UserHelper.getUser().getId() + "" + System.currentTimeMillis());
            DaoHelper.getInstance().addOrder(mOrder);
            mBook = DaoHelper.getInstance().getBook(bookId);
        } else if (from == FROM_MY_ORDER) {
            //读取老订单
            String orderId = intent.getStringExtra("orderId");
            mOrder = DaoHelper.getInstance().getOrder(orderId);
            if (mOrder == null) {
                return;
            }
            mBook = DaoHelper.getInstance().getBook(mOrder.getBookId());
        }
    }

    private void initView() {
        mIvBack = findViewById(R.id.iv_back);
        mIvCover = findViewById(R.id.iv_cover);
        mTvName = findViewById(R.id.tv_name);
        mTvInfo = findViewById(R.id.tv_info);
        mTvPrice = findViewById(R.id.tv_price);
        mTvNo = findViewById(R.id.tv_no);
        mTvStatus = findViewById(R.id.tv_status);
        mTvCreateTime = findViewById(R.id.tv_create_time);
        mTvFinishTime = findViewById(R.id.tv_finish_time);
        mTvAddress = findViewById(R.id.tv_address);
        mTvBuy = findViewById(R.id.tv_buy);
        mClAddress = findViewById(R.id.cl_bottom);

        mTvBuy.setOnClickListener(mNoDoubleClickListener);
        mClAddress.setOnClickListener(mNoDoubleClickListener);
        mIvBack.setOnClickListener(mNoDoubleClickListener);
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_buy) {
                buy();
            } else if (id == R.id.cl_bottom) {
                goAddress();
            } else if (id == R.id.iv_back) {
                finish();
            }
        }
    };

    private void goAddress() {
        MyAddressActivity.go(this);
    }

    private void buy() {
        if (TextUtils.isEmpty(UserHelper.getUser().getAddress())) {
            ToastUtil.toast(this, "请先输入地址");
            return;
        }
        mOrder.setStatus(Order.STATUS_FINISH);
        mOrder.setFinishTime(System.currentTimeMillis());
        DaoHelper.getInstance().updateOrder(mOrder);
        ToastUtil.toast(this, "购买成功");
        resetUI();
        EventBus.getDefault().post(new BuyEvent(mOrder.getOrderId()));
    }

    private void resetAddress() {
        mTvAddress.setText(String.format("我的地址: %s", UserHelper.getUser().getAddress()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdateUserEvent event) {
        resetAddress();
    }

}
