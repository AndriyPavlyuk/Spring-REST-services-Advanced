package it.discovery.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BookDTO {

    private int id;

    @NotNull
    @Size(min = 4, max = 40)
    private String author;

    @Mapping("name")
    private String title;

    @Min(1800)
    private int year;
}
