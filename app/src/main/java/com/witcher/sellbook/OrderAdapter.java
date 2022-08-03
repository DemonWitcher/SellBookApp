package com.witcher.sellbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.witcher.sellbook.module.Book;
import com.witcher.sellbook.module.Order;
import com.witcher.sellbook.util.CommonUtil;
import com.witcher.sellbook.util.DaoHelper;
import com.witcher.sellbook.util.NoDoubleClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    private List<Order> mOrders;

    public void setData(List<Order> list) {
        this.mOrders = list;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order = mOrders.get(position);
        Book book = DaoHelper.getInstance().getBook(order.getBookId());
        CommonUtil.loadCover(holder.ivCover, book.getCover());
        holder.tvInfo.setText(CommonUtil.getBookInfo(book));
        holder.tvName.setText(book.getName());
        holder.tvPrice.setText(CommonUtil.formatPrice(book.getPrice()));
        if (order.getStatus() == Order.STATUS_PAY) {
            holder.tvStatus.setText("待付款");
            holder.tvStatus.setTextColor(holder.tvPrice.getResources().getColor(R.color.main_bg));
        } else if (order.getStatus() == Order.STATUS_FINISH) {
            holder.tvStatus.setText("已完成");
            holder.tvStatus.setTextColor(holder.tvPrice.getResources().getColor(R.color.green));
        }
        holder.clRoot.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                OrderActivity.goFromMyOrder(holder.clRoot.getContext(), order.getOrderId());
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mOrders == null) {
            return 0;
        }
        return mOrders.size();
    }

    public static class OrderHolder extends RecyclerView.ViewHolder {

        ImageView ivCover;
        TextView tvName, tvInfo, tvPrice, tvStatus;
        ConstraintLayout clRoot;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            clRoot = itemView.findViewById(R.id.cl_root);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
