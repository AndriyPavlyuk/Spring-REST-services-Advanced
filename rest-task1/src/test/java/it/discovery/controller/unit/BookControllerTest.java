package it.discovery.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.RestApplication;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(RestApplication.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookRepository bookRepository;

    static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void findAll_shopIsNotEmpty_singleBookReturned() throws Exception {
        Book book = new Book();
        book.setName("Java");
        book.setAuthor("James Gosling");
        given(bookRepository.findAll()).willReturn(List.of(book));

        ResultActions actions = mockMvc.perform(get("/books"));

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(document("{class-name}/{method-name}", responseFields(fieldWithPath("[].id")
                                .description("Book unique identifier"),
                        fieldWithPath("[].author").description("Book author"),
                        fieldWithPath("[].title").description("Book title"),
                        fieldWithPath("[].year").description("Book publishing year"))));
    }

    @Test
    void saveBook_bookValid_bookSaved() {
        //TODO implement
    }

    @Test
    void saveBook_missingAuthor_errorReturned() {
        //TODO implement
    }

}

