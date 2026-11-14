package restaurantrush.model;

public enum OrderStatus {
    WAITING,       // taken, no preparation unit done yet
    PREPARING,     // some units done
    READY,         // all units done, waiting for the Waiter
    SERVED,        // customer is eating
    READY_TO_PAY,  // customer finished eating
    PAID,
    CANCELLED      // customer left before being served
}
 