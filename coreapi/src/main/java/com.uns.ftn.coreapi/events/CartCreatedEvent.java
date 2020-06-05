package com.uns.ftn.coreapi.events;


public class CartCreatedEvent {

    String cartAggregateId;

    public CartCreatedEvent() {

    }

    public CartCreatedEvent(String cartAggregateId) {
        this.cartAggregateId = cartAggregateId;
    }

    public String getCartAggregateId() {
        return cartAggregateId;
    }

    public void setCartAggregateId(String cartAggregateId) {
        this.cartAggregateId = cartAggregateId;
    }
}
