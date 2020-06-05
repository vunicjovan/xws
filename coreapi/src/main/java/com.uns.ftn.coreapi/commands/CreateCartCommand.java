package com.uns.ftn.coreapi.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class CreateCartCommand {

    @TargetAggregateIdentifier
    private String cartAggregateId;

    private Long userId;

    public CreateCartCommand() {

    }

    public CreateCartCommand(String cartAggregateId, Long userId) {
        this.cartAggregateId = cartAggregateId;
        this.userId = userId;
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
}
