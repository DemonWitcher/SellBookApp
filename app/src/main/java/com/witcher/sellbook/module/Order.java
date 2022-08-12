package com.witcher.sellbook.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 订单实体类
 */
@Entity
public class Order {

    /**
     * 订单状态 1待付款 2已完成
     */
    public static final int STATUS_PAY = 1;
    public static final int STATUS_FINISH = 2;
    /**
     * 订单ID 主键
     */
    @Id
    private String orderId;
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
     * 完成时间
     */
    private long finishTime;
    /**
     * 1 待付款
     * 2 已完成
     */
    private int status;

    @Generated(hash = 1788133623)
    public Order(String orderId, long uid, long bookId, long createTime,
            long finishTime, int status) {
        this.orderId = orderId;
        this.uid = uid;
        this.bookId = bookId;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.status = status;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
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

    public long getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
