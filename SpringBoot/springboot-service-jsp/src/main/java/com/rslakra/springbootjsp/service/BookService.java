package com.rslakra.springbootjsp.service;

import com.rslakra.springbootjsp.dto.Book;
import com.rslakra.springbootjsp.repository.model.BookDO;

import java.util.Collection;

public interface BookService {

    /**
     * @return
     */
    Collection<Book> getBooks();

    /**
     * @param book
     * @return
     */
    Book addBook(Book book);

    /**
     * @param isbn
     */
    void deleteBook(String isbn);

    /**
     * @param isbn
     * @return
     */
    Book getBookByIsbn(String isbn);

    /**
     * @param book
     * @return
     */
    Book updateBook(Book book);

    /**
     * @param isbn
     * @return true if book exists, false otherwise
     */
    boolean bookExists(String isbn);

    /**
     * @param bookDO
     * @return
     */
    static Book toBook(BookDO bookDO) {
        return new Book(bookDO.getIsbn(), bookDO.getName(), bookDO.getAuthor());
    }

    /**
     * @param book
     * @return
     */
    static BookDO fromBook(Book book) {
        return new BookDO(book.getIsbn(), book.getName(), book.getAuthor());
    }
}