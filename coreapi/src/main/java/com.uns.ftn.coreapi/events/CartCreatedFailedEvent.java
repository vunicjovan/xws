package com.uns.ftn.coreapi.events;


public class CartCreatedFailedEvent {

    private String cartAggregateId;
    private Long userId;
    private String reason;

    public CartCreatedFailedEvent() {

    }

    public CartCreatedFailedEvent(String cartAggregateId, Long userId, String reason) {
        this.cartAggregateId = cartAggregateId;
        this.userId = userId;
        this.reason = reason;
    }

    public String getCartAggregateId() {
        return cartAggregateId;
    }

    public void setCartAggregateId(String cartAggregateId) {
        this.cartAggregateId = cartAggregateId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
