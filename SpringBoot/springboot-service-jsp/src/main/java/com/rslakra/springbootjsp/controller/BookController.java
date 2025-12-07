package com.rslakra.springbootjsp.controller;

import com.rslakra.springbootjsp.dto.Book;
import com.rslakra.springbootjsp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    /**
     * @param bookService
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * @param model
     * @return
     */
    @GetMapping("/viewBooks")
    public String viewBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "book/listBooks";
    }

    /**
     * Unified form for both adding and editing books
     * @param isbn Optional ISBN parameter. If provided, loads existing book for editing
     * @param model
     * @return
     */
    @GetMapping({"/addBook", "/editBook"})
    public String bookFormView(@org.springframework.web.bind.annotation.RequestParam(value = "isbn", required = false) String isbn, Model model) {
        Book book;
        boolean isEditMode = false;
        
        if (isbn != null && !isbn.trim().isEmpty()) {
            // Check if book exists
            try {
                book = bookService.getBookByIsbn(isbn);
                isEditMode = true;
            } catch (RuntimeException e) {
                // Book not found, treat as new book
                book = new Book();
                book.setIsbn(isbn);
            }
        } else {
            // New book
            book = new Book();
        }
        
        model.addAttribute("book", book);
        model.addAttribute("isEditMode", isEditMode);
        return "book/editBook";
    }

    /**
     * Unified save endpoint for both adding and updating books
     * Automatically determines if it's an add or update based on whether the book exists
     * @param book
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/saveBook")
    public RedirectView saveBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/book/viewBooks", true);
        
        // Check if book exists by trying to find it
        boolean isEdit = bookService.bookExists(book.getIsbn());
        
        if (isEdit) {
            // Update existing book
            Book updatedBook = bookService.updateBook(book);
            redirectAttributes.addFlashAttribute("updateBookSuccess", true);
            redirectAttributes.addFlashAttribute("updatedBook", updatedBook);
        } else {
            // Add new book
            Book savedBook = bookService.addBook(book);
            redirectAttributes.addFlashAttribute("addBookSuccess", true);
            redirectAttributes.addFlashAttribute("savedBook", savedBook);
        }
        
        return redirectView;
    }

    /**
     * @param isbn
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/deleteBook")
    public RedirectView deleteBook(@org.springframework.web.bind.annotation.RequestParam("isbn") String isbn, RedirectAttributes redirectAttributes) {
        bookService.deleteBook(isbn);
        redirectAttributes.addFlashAttribute("deleteBookSuccess", true);
        redirectAttributes.addFlashAttribute("deletedIsbn", isbn);
        return new RedirectView("/book/viewBooks", true);
    }
}