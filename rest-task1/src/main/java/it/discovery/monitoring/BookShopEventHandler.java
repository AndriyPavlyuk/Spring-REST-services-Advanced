package it.discovery.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookShopEventHandler {

    private final Counter createdBooksCounter;

    public BookShopEventHandler(MeterRegistry meterRegistry) {
        createdBooksCounter = meterRegistry.counter("book.save.requests");
    }

    @EventListener
    public void handleEvents(BookCreatedEvent event) {
        createdBooksCounter.increment();
    }
}
