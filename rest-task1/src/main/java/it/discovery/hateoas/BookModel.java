package it.discovery.hateoas;

import it.discovery.controller.BookController;
import it.discovery.model.Book;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class BookModel extends RepresentationModel<BookModel> {

    private int id;

    private int year;

    private String author;

    private String title;

    public BookModel(Book book) {
        id = book.getId();
        year = book.getYear();
        author = book.getAuthor();
        title = book.getName();
        add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
    }
}
