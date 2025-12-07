package com.rslakra.springbootjsp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rslakra.springbootjsp.repository.BookRepository;
import com.rslakra.springbootjsp.repository.model.BookDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        // Clean up before each test
        bookRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void whenAddBook_thenBookSaved() throws Exception {
        MockHttpServletRequestBuilder saveBookRequest = MockMvcRequestBuilders.post("/book/saveBook")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("isbn", "isbn1")
            .param("name", "name1")
            .param("author", "author1");
        mockMvc.perform(saveBookRequest)
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/book/viewBooks"))
            .andReturn();

        Optional<BookDO> storedBookOpt = bookRepository.findById("isbn1");
        assertTrue(storedBookOpt.isPresent(), "Book should be saved in database");
        assertEquals("name1", storedBookOpt.get().getName());
        assertEquals("author1", storedBookOpt.get().getAuthor());
    }

    @Test
    @Order(2)
    public void givenAlreadyExistingBook_whenSaveBook_thenUpdateBook() throws Exception {
        // First add a book
        bookRepository.save(new BookDO("isbn1", "name1", "author1"));

        // Try to save the same book again with different name/author (this will update it)
        MockHttpServletRequestBuilder saveBookRequest = MockMvcRequestBuilders.post("/book/saveBook")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("isbn", "isbn1")
            .param("name", "updatedName")
            .param("author", "updatedAuthor");
        mockMvc.perform(saveBookRequest)
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/book/viewBooks"))
            .andReturn();

        // Verify the book was updated
        Optional<BookDO> updatedBookOpt = bookRepository.findById("isbn1");
        assertTrue(updatedBookOpt.isPresent());
        assertEquals("updatedName", updatedBookOpt.get().getName());
        assertEquals("updatedAuthor", updatedBookOpt.get().getAuthor());
    }
}
