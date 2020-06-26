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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aggregate
public class SimpleUserAggregate {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @AggregateIdentifier
    private Long userId;

    public SimpleUserAggregate() {

    }

    @CommandHandler
    public SimpleUserAggregate(CreateSimpleUserCommand createSimpleUserCommand) {
        logger.debug("Executing CreateSimpleUserCommand");
        AggregateLifecycle.apply(new SimpleUserCreatedEvent(createSimpleUserCommand.getUserId()));
    }

    @EventSourcingHandler
    public void on(SimpleUserCreatedEvent simpleUserCreatedEvent) {
        this.userId = simpleUserCreatedEvent.getUserId();
    }

    @CommandHandler
    public void on(RollbackSimpleUserCommand rollbackSimpleUserCommand, UserService userService) {
        logger.debug("Rolling back simple user command");
        userService.createSimpleUserRollback(rollbackSimpleUserCommand.getUserId());
        AggregateLifecycle.apply(new SimpleUserRollbackEvent(rollbackSimpleUserCommand.getUserId()));
    }
}
