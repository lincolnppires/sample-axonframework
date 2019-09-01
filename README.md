# Axon Framework and Spring

## Getting Started

- ``java -jar axonserver.jar``
- ``mvn -Dtest=OrderAggregateTest test``
- ``mvn spring-boot:run``

## Tests

    curl -X POST http://localhost:8080/ship-order
    curl -X POST http://localhost:8080/ship-unconfirmed-order
    curl -X GET http://localhost:8080/all-orders
    
    http://localhost:8024/#commands
    http://localhost:8024/#queries    
    http://localhost:8080/h2/
    
## References

- https://martinfowler.com/bliki/CQRS.html
- https://www.baeldung.com/axon-cqrs-event-sourcing
- https://docs.axoniq.io/reference-guide/