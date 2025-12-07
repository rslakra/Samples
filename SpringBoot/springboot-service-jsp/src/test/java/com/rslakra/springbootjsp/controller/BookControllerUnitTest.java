package com.rslakra.springbootjsp.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.rslakra.springbootjsp.dto.Book;
import com.rslakra.springbootjsp.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(BookController.class)
class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void whenViewBooks_thenReturnBooksView() throws Exception {
        when(bookService.getBooks()).thenReturn(existingBooks());
        ResultActions viewBooksResult = mockMvc.perform(get("/book/viewBooks"));

        viewBooksResult.andExpect(view().name("book/listBooks"))
            .andExpect(model().attribute("books", hasSize(3)));
    }

    @Test
    public void whenAddBookView_thenReturnEditBookView() throws Exception {
        ResultActions addBookViewResult = mockMvc.perform(get("/book/addBook"));

        addBookViewResult.andExpect(view().name("book/editBook"))
            .andExpect(model().attribute("book", isA(Book.class)))
            .andExpect(model().attribute("isEditMode", false));
    }

    @Test
    public void whenEditBookView_thenReturnEditBookView() throws Exception {
        Book existingBook = new Book("isbn1", "name1", "author1");
        when(bookService.getBookByIsbn("isbn1")).thenReturn(existingBook);
        
        ResultActions editBookViewResult = mockMvc.perform(get("/book/editBook").param("isbn", "isbn1"));

        editBookViewResult.andExpect(view().name("book/editBook"))
            .andExpect(model().attribute("book", isA(Book.class)))
            .andExpect(model().attribute("isEditMode", true));
    }

    @Test
    public void whenSaveBookPost_thenRedirectToViewBooks() throws Exception {
        when(bookService.bookExists(any(String.class))).thenReturn(false);
        when(bookService.addBook(any(Book.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        
        MockHttpServletRequestBuilder saveBookRequest = MockMvcRequestBuilders.post("/book/saveBook")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("isbn", "isbn1")
            .param("name", "name1")
            .param("author", "author1");
        ResultActions saveBookResult = mockMvc.perform(saveBookRequest);

        saveBookResult.andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/book/viewBooks"))
            .andExpect(flash().attribute("savedBook", hasProperty("isbn", equalTo("isbn1"))))
            .andExpect(flash().attribute("addBookSuccess", true));
    }

    private static Collection<Book> existingBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("isbn1", "name1", "author1"));
        books.add(new Book("isbn2", "name2", "author2"));
        books.add(new Book("isbn3", "name3", "author3"));
        return books;
    }
}
