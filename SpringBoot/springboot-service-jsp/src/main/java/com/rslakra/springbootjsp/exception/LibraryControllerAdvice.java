package com.rslakra.springbootjsp.exception;

import com.rslakra.springbootjsp.exception.DuplicateBookException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class LibraryControllerAdvice {

    @ExceptionHandler(value = DuplicateBookException.class)
    public ModelAndView duplicateBookException(DuplicateBookException e) {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorTitle", "Duplicate Book Error");
        modelAndView.addObject("ref", e.getBook().getIsbn());
        modelAndView.addObject("object", e.getBook());
        modelAndView.addObject("message", "Cannot add an already existing book");
        modelAndView.addObject("backUrl", "/book/addBook");
        modelAndView.setViewName("errorMessage");
        return modelAndView;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView runtimeException(RuntimeException e) {
        final ModelAndView modelAndView = new ModelAndView();
        String message = e.getMessage();
        
        // Check if it's a "Book not found" error
        if (message != null && message.contains("Book not found")) {
            modelAndView.addObject("errorTitle", "Book Not Found");
            // Extract ISBN from message if possible
            if (message.contains("ISBN:")) {
                String isbn = message.substring(message.indexOf("ISBN:") + 5).trim();
                // Clean up any duplicated values (e.g., "rsl-2025,rsl-2025" -> "rsl-2025")
                if (isbn.contains(",")) {
                    isbn = isbn.split(",")[0].trim();
                }
                modelAndView.addObject("ref", isbn);
            }
            modelAndView.addObject("message", message);
            modelAndView.addObject("backUrl", "/book/viewBooks");
        } else {
            modelAndView.addObject("errorTitle", "Runtime Error");
            modelAndView.addObject("message", message != null ? message : "An unexpected error occurred");
        }
        modelAndView.setViewName("errorMessage");
        return modelAndView;
    }
}