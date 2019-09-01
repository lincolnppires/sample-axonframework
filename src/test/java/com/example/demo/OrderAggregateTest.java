package com.example.demo;

import com.example.demo.command.OrderAggregate;
import com.example.demo.core.commandmodel.PlaceOrderCommand;
import com.example.demo.core.commandmodel.ShipOrderCommand;
import com.example.demo.core.events.OrderConfirmedEvent;
import com.example.demo.core.events.OrderPlacedEvent;
import com.example.demo.core.events.OrderShippedEvent;
import com.example.demo.core.exceptions.UnconfirmedOrderException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrderAggregateTest {

    private FixtureConfiguration<OrderAggregate> fixture;
    String orderId = UUID.randomUUID().toString();
    String product = "Deluxe Chair";

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
        orderId = UUID.randomUUID().toString();
        product = "Deluxe Chair";
    }

    @Test
    public void whenAggregateHandlesPlaceOrderCommandShouldProduceAnOrderPlaceEvent() {
        fixture.givenNoPriorActivity()
                .when(new PlaceOrderCommand(orderId, product))
                .expectEvents(new OrderPlacedEvent(orderId, product));
    }

    @Test
    public void whenBeingAbleToShipAnOrderShouldExpectAnException() {
        fixture.given(new OrderPlacedEvent(orderId, product))
                .when(new ShipOrderCommand(orderId))
                .expectException(UnconfirmedOrderException.class);
    }

    @Test
    public void whenBeingAbleToShipAnOrderShouldProcudeOrderShippedEvent() {
        fixture.given(new OrderPlacedEvent(orderId, product), new OrderConfirmedEvent(orderId))
                .when(new ShipOrderCommand(orderId))
                .expectEvents(new OrderShippedEvent(orderId));
    }
}
