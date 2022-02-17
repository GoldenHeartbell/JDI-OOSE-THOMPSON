package thompson.hexagonal.infrastructure.rest.adapter.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.OrderPersistenceImplementation;
import thompson.hexagonal.model.Order;

import java.util.List;

public interface IOrderController {
    @GetMapping("/orders")
    ResponseEntity<List<Order>> getOrders();

    @PutMapping("/order")
    ResponseEntity<Order> updateOrder(@RequestBody Order order);

    @PostMapping("/order")
    ResponseEntity<Order> addOrder(@RequestBody Order order);

    @DeleteMapping("/order/{orderID}")
    ResponseEntity<Void> removeOrder(@PathVariable Integer orderID);

    @GetMapping("/order/{orderId}")
    ResponseEntity<Order> getOrderById(@PathVariable Integer orderId);

    @PostMapping("/order/persistence/implementation/{implementation}")
    ResponseEntity<Void> setOrderPersistenceImplementation(@PathVariable OrderPersistenceImplementation implementation);
}
