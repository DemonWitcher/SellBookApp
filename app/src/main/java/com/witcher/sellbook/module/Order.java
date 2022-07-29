package com.witcher.sellbook.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Order {

    @Id(autoincrement = true)
    private long orderId;

    /**
     * 从属uid
     */
    private long uid;

    /**
     * 书id
     */
    private long bookId;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 1 待付款
     * 2 已完成
     */
    private int status;

    @Generated(hash = 2034242642)
    public Order(long orderId, long uid, long bookId, long createTime, int status) {
        this.orderId = orderId;
        this.uid = uid;
        this.bookId = bookId;
        this.createTime = createTime;
        this.status = status;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
