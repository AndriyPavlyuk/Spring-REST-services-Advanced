package it.discovery.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import it.discovery.RestApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@SpringJUnitWebConfig(RestApplication.class)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void findAll_storageIsEmpty_noBooksReturned() {
        given().
                when().get("/books")
                .then().statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);
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

