package thompson.hexagonal.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import thompson.hexagonal.model.Customer;
import thompson.hexagonal.model.Order;
import thompson.hexagonal.model.Product;
import thompson.hexagonal.ports.IOrderPersistencePort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceAdapterTest {
    private IOrderPersistencePort fixture;
    private OrderServiceAdapter sut;

    private Order order;
    private Order newOrder;

    @BeforeEach
    public void setup() {
        final int orderId = 1;
        final Customer orderCustomer = new Customer();
        final List<Product> orderProducts = new ArrayList<>();

        final int newId = 2;
        final Customer newCustomer = new Customer();
        final List<Product> newProducts = new ArrayList<>();

        order = new Order();
        order.setOrderId(orderId);
        order.setCustomer(orderCustomer);
        order.setProducts(orderProducts);

        newOrder = new Order();
        newOrder.setOrderId(newId);
        newOrder.setCustomer(newCustomer);
        newOrder.setProducts(newProducts);

        fixture = Mockito.mock(IOrderPersistencePort.class);
        sut = new OrderServiceAdapter(fixture);
    }

    @Test
    public void testAddOrder() {
        sut.addOrder(order);
        verify(fixture, times(1)).addOrder(order);
    }

    @Test
    public void testGetOrderById() {
        when(fixture.getOrderById(order.getOrderId())).thenReturn(order);

        Order gottenOrder = sut.getOrderById(order.getOrderId());
        verify(fixture, times(1)).getOrderById(order.getOrderId());
        assertEquals(gottenOrder, order);
    }

    @Test
    public void testUpdateOrder() {
        when(fixture.updateOrder(newOrder)).thenReturn(true);
        boolean success = sut.updateOrder(newOrder);

        verify(fixture, times(1)).updateOrder(newOrder);
        assertTrue(success);
    }

    @Test
    public void testUpdateOrderNoneExisting() {
        when(fixture.updateOrder(newOrder)).thenReturn(false);
        boolean success = sut.updateOrder(newOrder);

        verify(fixture, times(1)).updateOrder(newOrder);
        assertFalse(success);
    }

    @Test
    public void testRemoveOrder() {
        sut.removeOrder(order.getOrderId());
        verify(fixture, times(1)).removeOrder(order.getOrderId());
    }
}
