package it.discovery.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @CacheResult(cacheName = "books")
    @Operation(summary = "Returns book by its identifier", responses = {
            @ApiResponse(description = "Book exists", responseCode = "200"),
            @ApiResponse(description = "Book not found", responseCode = "404")
    })
    public BookDTO findById(@PathVariable int id) {
        return bookRepository.findById(id).map(book -> mapper.map(book, BookDTO.class))
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void save(@Valid @RequestBody Book book) {
        bookRepository.save(book);
        //TODO return book location
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CachePut(cacheName = "books")
    public BookDTO update(@CacheValue @Valid @RequestBody BookDTO dto, @PathVariable int id) {
        Book book = mapper.map(dto, Book.class);
        bookRepository.save(book);
        return mapper.map(book, BookDTO.class);
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
