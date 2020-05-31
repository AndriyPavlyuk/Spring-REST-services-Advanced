package it.discovery.monitoring;

import it.discovery.model.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookCreatedEvent {

    private final Book book;
}
