package com.witcher.sellbook.event;

public class BuyEvent {

    public BuyEvent(String orderId) {
        this.orderId = orderId;
    }

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
