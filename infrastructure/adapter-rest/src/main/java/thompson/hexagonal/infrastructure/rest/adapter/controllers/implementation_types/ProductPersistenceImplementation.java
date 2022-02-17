package thompson.hexagonal.infrastructure.rest.adapter.controllers.implementation_types;

public enum ProductPersistenceImplementation implements IImplementationType {
    PRODUCT_IN_MEMORY_ADAPTER("ProductInMemoryAdapter"),
    PRODUCT_JPA_ADAPTER("ProductJpaAdapter"),
    PRODUCT_IN_MEMORY_EVENT_STORE_ADAPTER("ProductInMemoryEventStoreAdapter"),
    PRODUCT_JPA_EVENT_STORE_ADAPTER("ProductJpaEventStoreAdapter");

    private final String beanName;

    ProductPersistenceImplementation(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
