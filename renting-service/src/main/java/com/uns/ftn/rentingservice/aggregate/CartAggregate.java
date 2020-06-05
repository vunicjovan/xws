package com.uns.ftn.rentingservice.aggregate;

import com.uns.ftn.coreapi.commands.CreateCartCommand;
import com.uns.ftn.coreapi.events.CartCreatedEvent;
import com.uns.ftn.coreapi.events.CartCreatedFailedEvent;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.service.CartService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class CartAggregate {

    @AggregateIdentifier
    private String cartAggregateId;

    public CartAggregate() {

    }

    @CommandHandler
    public CartAggregate(CreateCartCommand createCartCommand, CartService cartService) {
        try {
            cartService.createCart(createCartCommand.getUserId());

            AggregateLifecycle.apply(new CartCreatedEvent(createCartCommand.getCartAggregateId()));
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());

            AggregateLifecycle.apply(new CartCreatedFailedEvent(createCartCommand.getCartAggregateId(),
                    createCartCommand.getUserId(), e.getMessage()));
        }
    }

    @EventSourcingHandler
    protected void on(CartCreatedEvent cartCreatedEvent) {
        this.cartAggregateId = cartCreatedEvent.getCartAggregateId();
    }

    @EventSourcingHandler
    protected void on(CartCreatedFailedEvent cartCreatedFailedEvent) {
        this.cartAggregateId = cartCreatedFailedEvent.getCartAggregateId();
    }
}
