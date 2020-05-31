package it.discovery.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import io.micrometer.core.annotation.Timed;
import it.discovery.dto.BookDTO;
import it.discovery.dto.OrderDTO;
import it.discovery.exception.BookNotFoundException;
import it.discovery.hateoas.BookModel;
import it.discovery.model.Book;
import it.discovery.model.pagination.PaginationCriteria;
import it.discovery.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    @GetMapping
    @Timed("books.findAll")
    @Cacheable(cacheNames = "books")
    public ResponseEntity<List<?>> findAll(@Valid PaginationCriteria paginationCriteria) {
        if (paginationCriteria.getSize() == 0) {
            return ResponseEntity.ok(bookRepository.findAll().stream()
                    .map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList()));
        } else {
            Page<Book> page = bookRepository.findAll(PageRequest.of(paginationCriteria.getPage(), 
            		paginationCriteria.getSize()));

            return ResponseEntity.ok().header("X-TOTAL-COUNT", String.valueOf(page.getTotalElements()))
                    .body(page.getContent());
        }
    }

    @GetMapping("{id}")
    public BookModel findById(@PathVariable int id) {
        return bookRepository.findById(id).map(BookModel::new).orElseThrow(() -> new BookNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void save(@Valid @RequestBody Book book) {
        bookRepository.save(book);
        //TODO return book location
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Book update(@Valid @RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @GetMapping("orders")
    public List<OrderDTO> findOrders() {
        return bookRepository.findAll().stream()
                .flatMap(book -> book.getOrders().stream().map(order -> mapper.map(order, OrderDTO.class)))
                .collect(Collectors.toList());
    }

    @PostMapping("{bookId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@PathVariable int bookId) {
        bookRepository.findById(bookId).ifPresent(book -> {
            book.addOrder(1);
            bookRepository.save(book);
        });
    }

    @DeleteMapping("{bookId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable int bookId, @PathVariable String orderId) {
        bookRepository.findById(bookId).ifPresent(book -> {
            book.cancelOrder(orderId);
            bookRepository.save(book);
        });
    }

}
