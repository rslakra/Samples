package com.rslakra.springbootjsp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rslakra.springbootjsp.dto.Book;
import com.rslakra.springbootjsp.exception.DuplicateBookException;
import com.rslakra.springbootjsp.repository.BookRepository;
import com.rslakra.springbootjsp.repository.model.BookDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        // Clean up and initialize test data
        bookRepository.deleteAll();
        bookRepository.save(new BookDO("ISBN-TEST-1", "Book 1", "Book 1 Author"));
        bookRepository.save(new BookDO("ISBN-TEST-2", "Book 2", "Book 2 Author"));
        bookRepository.save(new BookDO("ISBN-TEST-3", "Book 3", "Book 3 Author"));
    }

    @Test
    @Order(1)
    public void givenNoAddedBooks_whenGetAllBooks_thenReturnInitialBooks() {
        Collection<Book> storedBooks = bookService.getBooks();

        assertEquals(3, storedBooks.size());
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("ISBN-TEST-1"))));
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("ISBN-TEST-2"))));
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("ISBN-TEST-3"))));
    }

    @Test
    @Order(2)
    public void givenBookNotAlreadyExists_whenAddBook_thenReturnSuccessfully() {
        Book bookToBeAdded = new Book("ISBN-ADD-TEST-4", "Added Book 4", "Added Book 4 Author");
        Book storedBook = bookService.addBook(bookToBeAdded);

        assertEquals(bookToBeAdded.getIsbn(), storedBook.getIsbn());
    }

    @Test
    @Order(3)
    public void givenBookAlreadyExists_whenAddBook_thenDuplicateBookException() {
        // First, add the book
        Book bookToBeAdded = new Book("ISBN-ADD-TEST-4", "Added Book 4", "Added Book 4 Author");
        bookService.addBook(bookToBeAdded);
        
        // Now try to add it again - should throw DuplicateBookException
        Book duplicateBook = new Book("ISBN-ADD-TEST-4", "Updated Book 4", "Updated Book 4 Author");
        assertThrows(DuplicateBookException.class, () -> bookService.addBook(duplicateBook));
    }
}
