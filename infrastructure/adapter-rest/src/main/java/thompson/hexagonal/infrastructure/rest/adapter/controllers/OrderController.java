package thompson.hexagonal.infrastructure.rest.adapter.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thompson.hexagonal.api.IOrderService;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.OrderPersistenceImplementation;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.interfaces.IOrderController;
import thompson.hexagonal.model.Order;
import thompson.hexagonal.ports.IOrderPersistencePort;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController implements IOrderController {
    private final IOrderService orderService;
    private final Map<String, IOrderPersistencePort> orderPersistencePorts;

    @Autowired
    public OrderController(IOrderService orderService, Map<String, IOrderPersistencePort> orderPersistencePorts) {
        this.orderService = orderService;
        this.orderPersistencePorts = orderPersistencePorts;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @Operation(summary = "Get order endpoint om een lijst van alle orders op te halen")
    @Override
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }


    @RequestMapping(value = "/order", method = RequestMethod.PUT)
    @Operation(summary = "PUT order endpoint om een order toe te updaten", description = "Geef de order mee in de body om deze te updaten")
    @Override
    public ResponseEntity<Order> updateOrder(Order order) {
        boolean update = orderService.updateOrder(order);
        if (!update) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @Operation(summary = "Post order  endpoint om een order toe te voegen", description = "Geef de order mee in de body om deze toe te voegen")
    @Override
    public ResponseEntity<Order> addOrder(Order order) {
        orderService.addOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/order", method = RequestMethod.DELETE)
    @Operation(summary = "Delete order endpoint om een order te verwijderen", description = "Geef de order mee in de body om deze te verwijderen")
    @Override
    public ResponseEntity<Void> removeOrder(@RequestParam("orderID") Integer orderID) {
        boolean successfulDelete = orderService.removeOrder(orderID);
        if (!successfulDelete) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @Operation(summary = "Get orders endpoint om 1 order op te halen", description = "Endpoint om een order uit de database te halen aan de hand van een id")
    @Override
    public ResponseEntity<Order> getOrderById(@RequestParam("orderID") Integer orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @PostMapping("/order/persistence/implementation/{implementation}")
    @Operation(summary = "Endpoint om te wisselen van implementatie", description = "Wisselt de implementatie op runtime door een andere")
    @Override
    public ResponseEntity<Void> setOrderPersistenceImplementation(@PathVariable("implementation") OrderPersistenceImplementation implementation) {
        IOrderPersistencePort port = this.orderPersistencePorts.get(implementation.getBeanName());

        if(port != null) {
            this.orderService.setPort(port);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
