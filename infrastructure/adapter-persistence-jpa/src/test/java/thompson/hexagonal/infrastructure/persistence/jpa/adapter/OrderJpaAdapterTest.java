package thompson.hexagonal.infrastructure.persistence.jpa.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Customer;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Order;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Product;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IOrderRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IProductInOrderRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IProductRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class OrderJpaAdapterTest {
    private IOrderRepository mockOrderRepository;
    private IProductInOrderRepository mockProductInOrderRepository;
    private IProductRepository mockProductRepository;
    private OrderJpaAdapter sut;
    private Order mockOrder;
    private thompson.hexagonal.model.Order inputOrder;

    public final int orderId = 1;
    public final Customer entityCustomer = new Customer();
    public final List<Product> entityProducts  = new ArrayList<>();

    public final thompson.hexagonal.model.Customer modelCustomer = new thompson.hexagonal.model.Customer();
    public final List<thompson.hexagonal.model.Product> modelProducts  = new ArrayList<>();

    @BeforeEach
    void setup() {
        mockOrderRepository = Mockito.mock(IOrderRepository.class);
        mockProductInOrderRepository = Mockito.mock(IProductInOrderRepository.class);
        sut = new OrderJpaAdapter(mockOrderRepository, mockProductInOrderRepository, mockProductRepository);

        mockOrder = new Order();
        mockOrder.setOrderId(orderId);
        mockOrder.setCustomer(entityCustomer);
        mockOrder.setProducts(entityProducts);

        inputOrder = new thompson.hexagonal.model.Order();
        inputOrder.setOrderId(orderId);
        inputOrder.setCustomer(modelCustomer);
        inputOrder.setProducts(modelProducts);
    }

    @Test
    void updateExistingOrder() {
        Mockito.when(mockOrderRepository.findByOrderId(orderId)).thenReturn(mockOrder);

        boolean response = sut.updateOrder(inputOrder);

        Assertions.assertTrue(response);
    }

    @Test
    void updateNotExistingOrder() {
        Mockito.when(mockOrderRepository.findByOrderId(orderId)).thenReturn(null);

        boolean response = sut.updateOrder(inputOrder);

        Assertions.assertFalse(response);
    }
    @Test
    void createNewOrder(){
        ArgumentCaptor<Order> OrderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        sut.addOrder(inputOrder);

        verify(mockOrderRepository, times(1)).save(OrderArgumentCaptor.capture());//checked save method wordt aangeroepen en of Order wordt meegegeven als argument.
    }

    @Test
    void testRemoveOrder(){
        Mockito.when(mockOrderRepository.findByOrderId(orderId)).thenReturn(mockOrder);

        Assertions.assertTrue(sut.removeOrder(1));
    }

    @Test
    void testRemoveOrderFalse(){
        Mockito.when(mockOrderRepository.findByOrderId(orderId)).thenReturn(null);

        Assertions.assertFalse(sut.removeOrder(1));
    }

    @Test
    void testGetOrderByID(){
        Mockito.when(mockOrderRepository.findByOrderId(orderId)).thenReturn(mockOrder);

        Assertions.assertEquals(orderId, sut.getOrderById(orderId).getOrderId());
    }

    @Test
    void testGetOrderByIDNotFound(){
        Mockito.when(mockOrderRepository.findByOrderId(orderId)).thenReturn(null);

        Assertions.assertNull(sut.getOrderById(orderId));
    }

    @Test
    void testGetAllOrders(){
        List<Order> OrderEntityList = new ArrayList<>();
        OrderEntityList.add(mockOrder);
        Mockito.when(mockOrderRepository.findAll()).thenReturn(OrderEntityList);

        thompson.hexagonal.model.Order returnOrder = sut.getOrders().get(0);

        Assertions.assertEquals(inputOrder.getOrderId(), returnOrder.getOrderId());
    }
}
