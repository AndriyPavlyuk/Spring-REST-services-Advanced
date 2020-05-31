package it.discovery.monitoring;

import it.discovery.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookShopHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        if(bookRepository.findAll().isEmpty()) {
            return Health.down().withDetail("Shop status", "No books present").build();
        }
        return Health.up().build();
    }
}
