package com.witcher.sellbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.event.CollectionEvent;
import com.witcher.sellbook.module.Book;
import com.witcher.sellbook.util.CommonUtil;
import com.witcher.sellbook.util.NoDoubleClickListener;
import com.witcher.sellbook.util.UserHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    private List<Book> mBooks;
    private boolean mIsUseInCollectionPage;

    public void setData(List<Book> books) {
        this.mBooks = books;
    }

    public void setIsCollection(boolean isCollection) {
        this.mIsUseInCollectionPage = isCollection;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookHolder holder, int position) {
        Book book = mBooks.get(position);
        CommonUtil.loadCover(holder.ivCover, book.getCover());
        holder.tvInfo.setText(CommonUtil.getBookInfo(book));
        holder.tvName.setText(book.getName());
        holder.tvPrice.setText(CommonUtil.formatPrice(book.getPrice()));
        if (UserHelper.isCollection(book.getId())) {
            holder.ivCollection.setImageResource(R.mipmap.ic_details_collection1);
        } else {
            holder.ivCollection.setImageResource(R.mipmap.ic_details_collection2);
        }
        holder.ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCollection(book, holder.ivCollection);
            }
        });
        holder.clRoot.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                BookActivity.go(holder.clRoot.getContext(), book.getId());
            }
        });
    }

    private void onClickCollection(Book book, ImageView ivCollection) {
        if (!UserHelper.isLogin()) {
            LoginDialog.newInstance(ivCollection.getContext()).show();
            return;
        }
        boolean isCollection;
        if (UserHelper.isCollection(book.getId())) {
            UserHelper.removeCollection(book.getId());
            ivCollection.setImageResource(R.mipmap.ic_details_collection2);
            isCollection = false;
        } else {
            UserHelper.addCollection(book.getId());
            ivCollection.setImageResource(R.mipmap.ic_details_collection1);
            isCollection = true;
        }
        if (mIsUseInCollectionPage) {
            EventBus.getDefault().post(new CollectionEvent(book.getId(), isCollection));
        }
    }

    @Override
    public int getItemCount() {
        if (mBooks == null) {
            return 0;
        }
        return mBooks.size();
    }

    public static class BookHolder extends RecyclerView.ViewHolder {

        ImageView ivCover, ivCollection;
        TextView tvName, tvInfo, tvPrice;
        ConstraintLayout clRoot;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivCollection = itemView.findViewById(R.id.iv_collection);
            clRoot = itemView.findViewById(R.id.cl_root);
        }
    }
}
