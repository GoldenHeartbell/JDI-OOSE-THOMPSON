package thompson.hexagonal.infrastructure.rest.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import thompson.hexagonal.adapter.OrderServiceAdapter;
import thompson.hexagonal.infrastructure.persistence.inmemory.adapter.OrderInMemoryAdapter;
import thompson.hexagonal.infrastructure.persistence.jpa.adapter.OrderJpaAdapter;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.OrderController;
import thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types.OrderPersistenceImplementation;
import thompson.hexagonal.model.Customer;
import thompson.hexagonal.model.Order;
import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IOrderPersistencePort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class OrderControllerTest {
    private OrderController sut;
    private OrderServiceAdapter fixture;
    private Order mockOrder;

    public final int orderId = 1;
    public final Customer customer = new Customer();
    public final List<Product> products = new ArrayList<>();

    @BeforeEach
    void setup() {
        fixture = Mockito.mock(OrderServiceAdapter.class);

        HashMap<String, IOrderPersistencePort> portHashMap = new HashMap<>();
        portHashMap.put(OrderPersistenceImplementation.ORDER_IN_MEMORY_ADAPTER.getBeanName(), Mockito.mock(OrderInMemoryAdapter.class));
        portHashMap.put(OrderPersistenceImplementation.ORDER_JPA_ADAPTER.getBeanName(), Mockito.mock(OrderJpaAdapter.class));

        sut = new OrderController(fixture, portHashMap);

        mockOrder = new Order();
        mockOrder.setOrderId(orderId);
        mockOrder.setCustomer(customer);
        mockOrder.setProducts(products);
    }


    @Test
    void testGetAllOrdersOK() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(mockOrder);
        Mockito.when(fixture.getOrders()).thenReturn(orderList);

        ResponseEntity<List<Order>> response = sut.getOrders();

        Assertions.assertEquals(response.getBody(), orderList);
    }

    @Test
    void testGetOrderByIdOK() {
        Mockito.when(fixture.getOrderById(mockOrder.getOrderId())).thenReturn(mockOrder);

        ResponseEntity<Order> response = sut.getOrderById(mockOrder.getOrderId());

        Assertions.assertEquals(response.getBody(), mockOrder);
    }

    @Test
    void testUpdateOrderOK() {
        Mockito.when(fixture.updateOrder(mockOrder)).thenReturn(true);

        ResponseEntity<Order> response = sut.updateOrder(mockOrder);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateOrderBADREQUEST() {
        Mockito.when(fixture.updateOrder(mockOrder)).thenReturn(false);

        ResponseEntity<Order> response = sut.updateOrder(mockOrder);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addOrder() {
        ResponseEntity<Order> response = sut.addOrder(mockOrder);

        Mockito.verify(fixture).addOrder(mockOrder);

        Assertions.assertEquals(response.getBody(), mockOrder);
    }

    @Test
    void testSetImplementationOkStatus() {
        ResponseEntity<Void> response = sut.setOrderPersistenceImplementation(OrderPersistenceImplementation.ORDER_IN_MEMORY_ADAPTER);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSetImplementationBadStatus() {
        OrderPersistenceImplementation implementation = Mockito.mock(OrderPersistenceImplementation.class);
        Mockito.when(implementation.getBeanName()).thenReturn("none");

        ResponseEntity<Void> response = sut.setOrderPersistenceImplementation(implementation);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
