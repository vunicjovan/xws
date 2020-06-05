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

import javax.inject.Inject;
import java.util.UUID;

@Saga
public class SimpleUserRegistrationSaga {

    @Inject
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "userId")
    public void handle(SimpleUserCreatedEvent simpleUserCreatedEvent) {
        System.out.println("Saga invoked");

        String cartAggregateId = UUID.randomUUID().toString();

        SagaLifecycle.associateWith("cartAggregateId", cartAggregateId);

        commandGateway.send(new CreateCartCommand(cartAggregateId, simpleUserCreatedEvent.getUserId()));
    }

    @SagaEventHandler(associationProperty = "cartAggregateId")
    public void handle(CartCreatedEvent cartCreatedEvent) {
        System.out.println("Saga finishing, both simple user and cart created!");

        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "cartAggregateId")
    public void handle(CartCreatedFailedEvent cartCreatedFailedEvent) {
        System.out.println("Saga declined, starting rollback!");

        commandGateway.send(new RollbackSimpleUserCommand(cartCreatedFailedEvent.getUserId(),
                cartCreatedFailedEvent.getReason()));
    }

    @SagaEventHandler(associationProperty = "userId")
    public void handle(SimpleUserRollbackEvent simpleUserRollbackEvent) {
        System.out.println("Saga finishing!");

        SagaLifecycle.end();
    }
}
