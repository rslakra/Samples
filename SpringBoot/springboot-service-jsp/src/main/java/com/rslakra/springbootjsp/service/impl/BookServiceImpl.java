package com.rslakra.springbootjsp.service.impl;

import com.rslakra.springbootjsp.dto.Book;
import com.rslakra.springbootjsp.exception.DuplicateBookException;
import com.rslakra.springbootjsp.repository.BookRepository;
import com.rslakra.springbootjsp.repository.model.BookDO;
import com.rslakra.springbootjsp.service.BookService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Collection<Book> getBooks() {
        return bookRepository.findAll()
            .stream()
            .map(BookService::toBook)
            .collect(Collectors.toList());
    }

    @Override
    public Book addBook(Book book) {
        final Optional<BookDO> existingBook = bookRepository.findById(book.getIsbn());
        if (existingBook.isPresent()) {
            throw new DuplicateBookException(book);
        }

        final BookDO savedBook = bookRepository.save(BookService.fromBook(book));
        return BookService.toBook(savedBook);
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn)
                .map(BookService::toBook)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));
    }

    @Override
    public Book updateBook(Book book) {
        BookDO existingBook = bookRepository.findById(book.getIsbn())
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + book.getIsbn()));
        existingBook.setName(book.getName());
        existingBook.setAuthor(book.getAuthor());
        BookDO updatedBook = bookRepository.save(existingBook);
        return BookService.toBook(updatedBook);
    }

    @Override
    public boolean bookExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

}
