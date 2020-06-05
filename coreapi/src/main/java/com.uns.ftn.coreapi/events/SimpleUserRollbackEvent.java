package com.uns.ftn.coreapi.events;


public class SimpleUserRollbackEvent {

    private Long userId;

    public SimpleUserRollbackEvent() {

    }

    public SimpleUserRollbackEvent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
