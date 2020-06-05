package com.uns.ftn.coreapi.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


public class RollbackSimpleUserCommand {

    @TargetAggregateIdentifier
    private Long userId;
    private String status;

    public RollbackSimpleUserCommand() {

    }

    public RollbackSimpleUserCommand(Long userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
