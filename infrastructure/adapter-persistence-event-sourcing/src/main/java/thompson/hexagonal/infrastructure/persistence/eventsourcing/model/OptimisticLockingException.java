package thompson.hexagonal.infrastructure.persistence.eventsourcing.model;

public class OptimisticLockingException extends RuntimeException {

    public OptimisticLockingException(String message) {
        super(message);
    }
}
