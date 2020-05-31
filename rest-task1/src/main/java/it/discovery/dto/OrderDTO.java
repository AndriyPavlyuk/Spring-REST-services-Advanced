package it.discovery.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private String id;

    @Mapping("book.id")
    private int bookId;

    private String createdAt;

    private int amount;

    @Mapping("book.name")
    private String title;
}
