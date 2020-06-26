package com.uns.ftn.rentingservice.aggregate;

import com.uns.ftn.coreapi.commands.CreateCartCommand;
import com.uns.ftn.coreapi.events.CartCreatedEvent;
import com.uns.ftn.coreapi.events.CartCreatedFailedEvent;
import com.uns.ftn.rentingservice.exceptions.BadRequestException;
import com.uns.ftn.rentingservice.exceptions.NotFoundException;
import com.uns.ftn.rentingservice.service.CartService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aggregate
public class CartAggregate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartAggregate.class);

    @AggregateIdentifier
    private String cartAggregateId;

    public CartAggregate() {

    }

    @CommandHandler
    public CartAggregate(CreateCartCommand createCartCommand, CartService cartService) {
        try {
            LOGGER.info("Started CreateCartCommand cart[userId={}]", createCartCommand.getUserId());
            cartService.createCart(createCartCommand.getUserId());

            AggregateLifecycle.apply(new CartCreatedEvent(createCartCommand.getCartAggregateId()));
            LOGGER.info("Finished CreateCartCommand cart[userId={}]", createCartCommand.getUserId());
        } catch (Exception e) {
            LOGGER.error("Error occurred during execution of CreateCartCommand", e);
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
