package com.witcher.sellbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.event.CollectionEvent;
import com.witcher.sellbook.module.Book;
import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.NoDoubleClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MyCollectionActivity extends BaseActivity {

    public static void go(Context context) {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        context.startActivity(intent);
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRv;
    private BookAdapter mAdapter;
    private ImageView mIvBack;
    private TextView mTvEmpty;
    private ConstraintLayout mClEmpty;

    private List<Book> mBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mIvBack = findViewById(R.id.iv_back);
        mRv = findViewById(R.id.rv);
        mTvEmpty = findViewById(R.id.tv_empty);
        mTvEmpty.setText("还没有收藏");
        mClEmpty = findViewById(R.id.cl_empty);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new BookAdapter();
        mAdapter.setIsCollection(true);
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
        mBooks = DaoHelper.getInstance().getAllCollectionBook();
        mAdapter.setData(mBooks);
        mAdapter.notifyDataSetChanged();
        if (mBooks == null || mBooks.isEmpty()) {
            mClEmpty.setVisibility(View.VISIBLE);
        } else {
            mClEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CollectionEvent collectionEvent) {
        if (collectionEvent == null || mBooks == null) {
            return;
        }
        int size = mBooks.size();
        for (int i = 0; i < size; ++i) {
            Book book = mBooks.get(i);
            if (book == null) {
                continue;
            }
            if (collectionEvent.getBookId() == book.getId()) {
                mAdapter.notifyItemChanged(i);
                return;
            }
        }
    }
}
