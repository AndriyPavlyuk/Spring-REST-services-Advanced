package it.discovery.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.RestApplication;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringJUnitWebConfig(RestApplication.class)
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
                .andExpect(jsonPath("$", hasSize(1)));
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

