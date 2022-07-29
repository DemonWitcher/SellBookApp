package com.witcher.sellbook;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.witcher.sellbook.event.CollectionEvent;
import com.witcher.sellbook.event.LoginEvent;
import com.witcher.sellbook.event.LogoutEvent;
import com.witcher.sellbook.module.Book;
import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.UserHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ShopFragment extends BaseFragment {

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRv;
    private TextView mTvLogin;
    private BookAdapter mAdapter;

    private List<Book> mBooks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.fragment_shop, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mTvLogin = view.findViewById(R.id.tv_login);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        mRv = view.findViewById(R.id.rv);
        mTvLogin.setOnClickListener(mNoDoubleClickListener);
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new BookAdapter();
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
        resetLoginState();
    }

    private final NoDoubleClickListener mNoDoubleClickListener = new NoDoubleClickListener() {

        @SuppressWarnings("ConstantConditions")
        @Override
        public void onNoDoubleClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_login) {
                LoginDialog dialog = new LoginDialog(getContext());
                dialog.show();
            }
        }
    };

    private void initData() {
        mBooks = DaoHelper.getInstance().getAllBook();
        mAdapter.setData(mBooks);
        mAdapter.notifyDataSetChanged();
    }

    private void resetLoginState() {
        if (UserHelper.isLogin()) {
            mTvLogin.setVisibility(View.GONE);
        } else {
            mTvLogin.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent loginEvent) {
        resetLoginState();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LogoutEvent logoutEvent) {
        resetLoginState();
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
