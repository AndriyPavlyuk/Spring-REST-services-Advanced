package it.discovery.api;

import it.discovery.model.Book;

import java.net.URI;
import java.util.List;

public interface BookClient {

    List<Book> findAll();

    Book findById(int id);

    URI save(Book book);

    void delete(int id);

    //TODO add search books operation
}
