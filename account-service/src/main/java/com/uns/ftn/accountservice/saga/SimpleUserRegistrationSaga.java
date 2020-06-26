package com.uns.ftn.accountservice.saga;

import com.uns.ftn.coreapi.commands.CreateCartCommand;
import com.uns.ftn.coreapi.commands.RollbackSimpleUserCommand;
import com.uns.ftn.coreapi.events.CartCreatedEvent;
import com.uns.ftn.coreapi.events.CartCreatedFailedEvent;
import com.uns.ftn.coreapi.events.SimpleUserCreatedEvent;
import com.uns.ftn.coreapi.events.SimpleUserRollbackEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;

@Saga
public class SimpleUserRegistrationSaga {

    @Inject
    private transient CommandGateway commandGateway;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @StartSaga
    @SagaEventHandler(associationProperty = "userId")
    public void handle(SimpleUserCreatedEvent simpleUserCreatedEvent) {
        logger.debug("Starting saga");
        System.out.println("Saga invoked");

        String cartAggregateId = UUID.randomUUID().toString();

        SagaLifecycle.associateWith("cartAggregateId", cartAggregateId);

        commandGateway.send(new CreateCartCommand(cartAggregateId, simpleUserCreatedEvent.getUserId()));
    }

    @SagaEventHandler(associationProperty = "cartAggregateId")
    public void handle(CartCreatedEvent cartCreatedEvent) {
        logger.debug("Cart has been created, finishing saga");
        System.out.println("Saga finishing, both simple user and cart created!");

        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "cartAggregateId")
    public void handle(CartCreatedFailedEvent cartCreatedFailedEvent) {
        logger.error("Card could not be created");
        System.out.println("Saga declined, starting rollback!");

        commandGateway.send(new RollbackSimpleUserCommand(cartCreatedFailedEvent.getUserId(),
                cartCreatedFailedEvent.getReason()));
    }

    @SagaEventHandler(associationProperty = "userId")
    public void handle(SimpleUserRollbackEvent simpleUserRollbackEvent) {
        logger.debug("Finishing saga on rollback");
        System.out.println("Saga finishing!");

        SagaLifecycle.end();
    }
}
