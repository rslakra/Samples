package com.rslakra.springbootjsp.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Optional;

import com.rslakra.springbootjsp.repository.model.BookDO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rslakra.springbootjsp.dto.Book;
import com.rslakra.springbootjsp.exception.DuplicateBookException;
import com.rslakra.springbootjsp.repository.BookRepository;
import com.rslakra.springbootjsp.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplUnitTest {

    @Mock
    private BookRepository bookRepository;

    @Test
    public void whenGetBooks_thenAllBooksReturned() {
        when(bookRepository.findAll()).thenReturn(existingBooks());
        BookService bookService = new BookServiceImpl(bookRepository);

        Collection<Book> storedBooks = bookService.getBooks();
        assertEquals(3, storedBooks.size());
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("isbn1"))));
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("isbn2"))));
        assertThat(storedBooks, hasItem(hasProperty("isbn", equalTo("isbn3"))));
    }

    @Test
    public void whenAddBook_thenAddSuccessful() {
        when(bookRepository.findById(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(any(BookDO.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        BookService bookService = new BookServiceImpl(bookRepository);
        Book book = bookService.addBook(new Book("isbn1", "name1", "author1"));

        assertEquals("isbn1", book.getIsbn());
        assertEquals("name1", book.getName());
        assertEquals("author1", book.getAuthor());
    }

    @Test
    public void givenBookAlreadyExist_whenAddBook_thenDuplicateBookException() {
        BookDO existingBook = new BookDO("isbn1", "name1", "author1");
        when(bookRepository.findById("isbn1")).thenReturn(Optional.of(existingBook));
        BookService bookService = new BookServiceImpl(bookRepository);
        Book bookToBeAdded = new Book("isbn1", "name1", "author1");

        assertThrows(DuplicateBookException.class, () -> bookService.addBook(bookToBeAdded));
    }

    private static List<BookDO> existingBooks() {
        List<BookDO> books = new ArrayList<>();
        books.add(new BookDO("isbn1", "name1", "author1"));
        books.add(new BookDO("isbn2", "name2", "author2"));
        books.add(new BookDO("isbn3", "name3", "author3"));
        return books;
    }
}