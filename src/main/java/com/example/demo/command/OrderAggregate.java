package com.example.demo.command;

import com.example.demo.core.commandmodel.ConfirmOrderCommand;
import com.example.demo.core.commandmodel.PlaceOrderCommand;
import com.example.demo.core.commandmodel.ShipOrderCommand;
import com.example.demo.core.events.OrderConfirmedEvent;
import com.example.demo.core.events.OrderPlacedEvent;
import com.example.demo.core.events.OrderShippedEvent;
import com.example.demo.core.exceptions.UnconfirmedOrderException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    protected OrderAggregate(){};

    @CommandHandler
    public OrderAggregate(PlaceOrderCommand command){
        apply(new OrderPlacedEvent(command.getOrderId(), command.getProduct()));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command){
        apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) throws UnconfirmedOrderException {
        if(!orderConfirmed){
            throw new UnconfirmedOrderException();
        }
        apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent event){
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event){
        orderConfirmed = true;
    }






}
