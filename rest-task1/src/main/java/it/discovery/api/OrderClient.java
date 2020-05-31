package it.discovery.api;

import it.discovery.model.Order;

import java.net.URI;
import java.util.List;

//TODO add implementation
public interface OrderClient {

    URI create(int bookId);

    List<Order> findAll();

    void cancel(String id);
}
