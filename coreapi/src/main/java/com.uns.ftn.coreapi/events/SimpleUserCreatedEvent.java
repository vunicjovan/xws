package com.uns.ftn.coreapi.events;


public class SimpleUserCreatedEvent {

    private Long userId;

    public SimpleUserCreatedEvent() {

    }

    public SimpleUserCreatedEvent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
