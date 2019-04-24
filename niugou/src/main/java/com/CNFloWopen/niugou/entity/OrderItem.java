package com.CNFloWopen.niugou.entity;

public class OrderItem {
    private Long orderItemid;
    private Long productId;
    private Long orderId;
    private Integer buypoint;

    public Long getOrderItemid() {
        return orderItemid;
    }

    public void setOrderItemid(Long orderItemid) {
        this.orderItemid = orderItemid;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getBuypoint() {
        return buypoint;
    }

    public void setBuypoint(Integer buypoint) {
        this.buypoint = buypoint;
    }
}
