package com.uns.ftn.accountservice.aggregates;

import com.uns.ftn.accountservice.service.UserService;
import com.uns.ftn.coreapi.commands.CreateSimpleUserCommand;
import com.uns.ftn.coreapi.commands.RollbackSimpleUserCommand;
import com.uns.ftn.coreapi.events.SimpleUserCreatedEvent;
import com.uns.ftn.coreapi.events.SimpleUserRollbackEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class SimpleUserAggregate {

    @AggregateIdentifier
    private Long userId;

    public SimpleUserAggregate() {

    }

    @CommandHandler
    public SimpleUserAggregate(CreateSimpleUserCommand createSimpleUserCommand) {
        AggregateLifecycle.apply(new SimpleUserCreatedEvent(createSimpleUserCommand.getUserId()));
    }

    @EventSourcingHandler
    public void on(SimpleUserCreatedEvent simpleUserCreatedEvent) {
        this.userId = simpleUserCreatedEvent.getUserId();
    }

    @CommandHandler
    public void on(RollbackSimpleUserCommand rollbackSimpleUserCommand, UserService userService) {
        userService.createSimpleUserRollback(rollbackSimpleUserCommand.getUserId());
        AggregateLifecycle.apply(new SimpleUserRollbackEvent(rollbackSimpleUserCommand.getUserId()));
    }
}
