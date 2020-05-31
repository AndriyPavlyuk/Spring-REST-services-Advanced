package it.discovery.dto;

import com.github.dozermapper.core.Mapping;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Schema(name = "book", title = "Element of book library")
public class BookDTO {

    @Schema(name = "Unique book identifier", required = true, example = "1")
    private int id;

    @NotNull
    @Size(min = 4, max = 40)
    @Schema(name = "Book author", required = true)
    private String author;

    @Mapping("name")
    @Schema(name = "Book title", required = true)
    private String title;

    @Min(1800)
    @Schema(name = "Book publishing year", required = false)
    private int year;

    private Integer version;
}
