package it.discovery.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.model.Book;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpringBookClient implements BookClient {
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public SpringBookClient(String baseUrl) {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(
                new DefaultUriBuilderFactory(baseUrl));
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<Book> findAll() {
        //TODO handle case when service is not available
        List<Map<?, ?>> books = restTemplate.getForObject("", List.class);
        return books
                .stream().map(map -> objectMapper.convertValue(map, Book.class))
                .collect(Collectors.toList());
    }

    @Override
    public Book findById(int id) {
        //TODO implement
        //TODO add Traverson support
        return null;
    }

    @Override
    public URI save(Book book) {
        //TODO handle validation errors
        return restTemplate.postForLocation("", book);
    }

    @Override
    public void delete(int id) {
//TODO implement
    }

    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080/books";
        BookClient client = new SpringBookClient(baseUrl);

        Book book = new Book();
        book.setName("Spring REST");
        book.setAuthor("Rod Johnson");
        book.setYear(2002);
        System.out.println("Book URI = " + client.save(book));
        System.out.print("Books = " + client.findAll());
    }
}
