package com.uns.ftn.coreapi.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class CreateSimpleUserCommand {

    @TargetAggregateIdentifier
    private Long userId;

    public CreateSimpleUserCommand() {

    }

    public CreateSimpleUserCommand(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
