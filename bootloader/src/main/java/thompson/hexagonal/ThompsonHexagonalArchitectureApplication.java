package thompson.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"thompson.hexagonal.infrastructure"})
public class ThompsonHexagonalArchitectureApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThompsonHexagonalArchitectureApplication.class, args);
    }
}
