package thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types;

public enum CustomerPersistenceImplementation implements IImplementationType {
    CUSTOMER_IN_MEMORY_ADAPTER("CustomerInMemoryAdapter"),
    CUSTOMER_JPA_ADAPTER("CustomerJpaAdapter"),
    CUSTOMER_IN_MEMORY_EVENT_STORE_ADAPTER("CustomerInMemoryEventStoreAdapter"),
    CUSTOMER_JPA_EVENT_STORE_ADAPTER("CustomerJpaEventStoreAdapter");

    private final String beanName;

    CustomerPersistenceImplementation(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
