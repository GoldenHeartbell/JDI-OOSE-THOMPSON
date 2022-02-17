package thompson.hexagonal.infrastructure.persistence.jpa.adapter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Customer;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Order;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.Product;
import thompson.hexagonal.infrastructure.persistence.jpa.entity.ProductInOrder;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IOrderRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IProductInOrderRepository;
import thompson.hexagonal.infrastructure.persistence.jpa.repository.IProductRepository;
import thompson.hexagonal.ports.IOrderPersistencePort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class OrderJpaAdapter implements IOrderPersistencePort {
    private IOrderRepository orderRepository;
    private IProductRepository productRepository;
    private IProductInOrderRepository productInOrderRepository;

    public OrderJpaAdapter(IOrderRepository orderRepository, IProductInOrderRepository productInOrderRepository, IProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productInOrderRepository = productInOrderRepository;
        this.productRepository = productRepository;
    }

    @Autowired
    public void setOrderRepository(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private thompson.hexagonal.model.Order entityToModel(Order entity) {
        thompson.hexagonal.model.Order order = new thompson.hexagonal.model.Order();

        order.setOrderId(entity.getOrderId());

        if(entity.getCustomer() != null) {
            thompson.hexagonal.model.Customer customer = new thompson.hexagonal.model.Customer();
            BeanUtils.copyProperties(entity.getCustomer(), customer);
            order.setCustomer(customer);
        }

        if(entity.getProducts() != null) {
            order.setProducts(entity.getProducts().stream().map(p -> {
                thompson.hexagonal.model.Product product = new thompson.hexagonal.model.Product();
                BeanUtils.copyProperties(p, product);
                return product;
            }).collect(Collectors.toList()));
        }

        return order;
    }

    @Override
    public void addOrder(thompson.hexagonal.model.Order order) {
        Order newOrder = new Order();

        List<Product> productEntities = new ArrayList<>();
        for(thompson.hexagonal.model.Product product : order.getProducts()) {
            Product productEntity = productRepository.findByProductId(product.getProductId());
            productEntity.setProductQuantity(productEntity.getProductQuantity() - product.getProductQuantity());

            productEntities.add(productEntity);

        }
        newOrder.setProducts(productEntities);



        Customer customer = new Customer();
        BeanUtils.copyProperties(order.getCustomer(), customer);
        newOrder.setCustomer(customer);

        orderRepository.save(newOrder);

        for(Product product : newOrder.getProducts()) {
            ProductInOrder productInOrder = productInOrderRepository.findByOrderIdAndProductId(newOrder.getOrderId(), product.getProductId());

            productInOrder.setProductPrice(product.getProductPrice());
            productInOrder.setProductQuantity(product.getProductQuantity());

            productInOrderRepository.save(productInOrder);
        }
    }

    @Override
    public boolean updateOrder(thompson.hexagonal.model.Order order) {
        Order existing = orderRepository.findByOrderId(order.getOrderId());
        if(existing == null) {
            return false;
        }

        this.addOrder(order);
        return true;
    }

    @Override
    public boolean removeOrder(Integer orderId) {
        Order existing = orderRepository.findByOrderId(orderId);
        if(existing == null) {
            return false;
        }

        orderRepository.deleteById(orderId);
        return true;
    }

    @Override
    public List<thompson.hexagonal.model.Order> getOrders() {
        List<thompson.hexagonal.model.Order> orderList = new ArrayList<>();
        List<Order> orderEntityList = StreamSupport.stream(orderRepository.findAll().spliterator(), false).collect(Collectors.toList());
        for(Order orderEntity : orderEntityList) {
            orderList.add(entityToModel(orderEntity));
        }
        return orderList;
    }

    @Override
    public thompson.hexagonal.model.Order getOrderById(Integer orderId) {
        Order orderEntity = orderRepository.findByOrderId(orderId);
        if(orderEntity == null){
            return null;
        }

        return entityToModel(orderEntity);
    }
}
