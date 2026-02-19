package service;

import observer.Observer;
import model.Book;

import java.util.*;
import java.util.logging.Logger;

public class ReservationService {

    private static final Logger logger =
            Logger.getLogger(ReservationService.class.getName());

    private Map<String, List<Observer>> reservations = new HashMap<>();

    public void reserveBook(String isbn, Observer observer) {

        reservations.computeIfAbsent(isbn, k -> new ArrayList<>())
                .add(observer);

        logger.info("Reservation added for ISBN: " + isbn);
    }

    public void notifyObservers(Book book) {

        List<Observer> observers = reservations.get(book.getIsbn());

        if (observers != null) {
            logger.info("Notifying observers for book: " + book.getTitle());

            for (Observer o : observers) {
                o.update(book);
            }
        }
    }
}
