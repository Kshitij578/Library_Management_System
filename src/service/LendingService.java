package service;

import model.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LendingService {

    private static final Logger logger =
            Logger.getLogger(LendingService.class.getName());

    private Map<String, Loan> activeLoans = new HashMap<>();

    public void checkoutBook(Book book, Patron patron) {

        try {
            if (book.getStatus() != BookStatus.AVAILABLE) {
                throw new IllegalStateException("Book not available");
            }

            book.setStatus(BookStatus.BORROWED);
            Loan loan = new Loan(book, patron);
            activeLoans.put(book.getIsbn(), loan);
            patron.addToHistory(book);

            logger.info("Book checked out: " + book.getTitle() +
                    " by " + patron);

        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "Error during checkout for book: " + book.getTitle(), e);
            throw e;
        }
    }

    public void returnBook(Book book) {

        book.setStatus(BookStatus.AVAILABLE);
        activeLoans.remove(book.getIsbn());

        logger.info("Book returned successfully: " + book.getTitle());
    }
}
