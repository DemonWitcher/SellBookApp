package com.witcher.sellbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.witcher.sellbook.module.Book;
import com.witcher.sellbook.util.DaoHelper;

import java.util.List;

import androidx.annotation.Nullable;
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

    private List<Book> mBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
    }

    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mRv = findViewById(R.id.rv);
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
    }

    private void initData() {
        mBooks = DaoHelper.getInstance().getAllCollectionBook();
        mAdapter.setData(mBooks);
        mAdapter.notifyDataSetChanged();
    }

}
