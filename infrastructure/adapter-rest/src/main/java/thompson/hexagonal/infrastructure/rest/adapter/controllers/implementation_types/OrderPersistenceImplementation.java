package thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types;

public enum OrderPersistenceImplementation implements IImplementationType {
    ORDER_IN_MEMORY_ADAPTER("OrderInMemoryAdapter"),
    ORDER_JPA_ADAPTER("OrderJpaAdapter"),
    ORDER_IN_MEMORY_EVENT_STORE_ADAPTER("OrderInMemoryEventStoreAdapter"),
    ORDER_JPA_EVENT_STORE_ADAPTER("OrderJpaEventStoreAdapter");

    private final String beanName;

    OrderPersistenceImplementation(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
