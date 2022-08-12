package com.witcher.sellbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.event.CollectionEvent;
import com.witcher.sellbook.event.LoginEvent;
import com.witcher.sellbook.module.Book;
import com.witcher.sellbook.util.CommonUtil;
import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.ToastUtil;
import com.witcher.sellbook.util.UserHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;

public class BookActivity extends BaseActivity {

    public static void go(Context context, long bookId) {
        Intent intent = new Intent(context, BookActivity.class);
        intent.putExtra("bookId", bookId);
        context.startActivity(intent);
    }

    private ImageView mIvCover, mIvBack;
    private TextView mTvName, mTvInfo, mTvTime, mTvDetails, mTvCollection;
    private FrameLayout mFlCollection, mFlBuy;
    private Book mBook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        Intent intent = getIntent();
        long bookId = -1;
        if (intent != null) {
            bookId = getIntent().getLongExtra("bookId", -1);
        }
        mBook = DaoHelper.getInstance().getBook(bookId);
        if (mBook == null) {
            ToastUtil.toast(this, "没有此书");
            finish();
            return;
        }
        mTvName.setText(mBook.getName());
        mTvInfo.setText(CommonUtil.getBookInfo(mBook));
        mTvTime.setText(String.format("出版日期: %s", mBook.getPublishData()));
        mTvDetails.setText(mBook.getDetails());
        CommonUtil.loadCover(mIvCover, mBook.getCover());
        resetCollection();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void resetCollection() {
        if (UserHelper.isCollection(mBook.getId())) {
            mTvCollection.setText("已收藏");
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_details_collection1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvCollection.setCompoundDrawables(null, null, drawable, null);
            mTvCollection.setTextColor(getResources().getColor(R.color.main_bg));
        } else {
            mTvCollection.setText("收藏");
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_details_collection2);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvCollection.setCompoundDrawables(null, null, drawable, null);
            mTvCollection.setTextColor(getResources().getColor(R.color.main_tv));
        }
    }

    private void initView() {
        mIvBack = findViewById(R.id.iv_back);
        mIvCover = findViewById(R.id.iv_cover);
        mTvName = findViewById(R.id.tv_name);
        mTvInfo = findViewById(R.id.tv_info);
        mTvTime = findViewById(R.id.tv_time);
        mTvDetails = findViewById(R.id.tv_details);
        mTvCollection = findViewById(R.id.tv_collection);
        mFlCollection = findViewById(R.id.fl_collection);
        mFlBuy = findViewById(R.id.fl_buy);

        mFlCollection.setOnClickListener(mNoDoubleClickListener);
        mFlBuy.setOnClickListener(mNoDoubleClickListener);
        mIvBack.setOnClickListener(mNoDoubleClickListener);
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.fl_buy) {
                if (UserHelper.isLogin()) {
                    toOrder();
                } else {
                    LoginDialog.newInstance(BookActivity.this).show();
                }
            } else if (id == R.id.fl_collection) {
                if (UserHelper.isLogin()) {
                    collection();
                } else {
                    LoginDialog.newInstance(BookActivity.this).show();
                }
            } else if (id == R.id.iv_back) {
                finish();
            }
        }
    };

    private void toOrder() {
        OrderActivity.goFromDetails(this, mBook.getId());
    }

    private void collection() {
        if (UserHelper.isCollection(mBook.getId())) {
            UserHelper.removeCollection(mBook.getId());
            EventBus.getDefault().post(new CollectionEvent(mBook.getId(), false));
        } else {
            UserHelper.addCollection(mBook.getId());
            EventBus.getDefault().post(new CollectionEvent(mBook.getId(), true));
        }
        resetCollection();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent loginEvent) {
        resetCollection();
    }
}
