package model;

import observer.Observer;
import java.util.*;

public class Patron implements Observer {

    private final String patronId;
    private String name;
    private String email;
    private List<Book> borrowingHistory;

    public Patron(String patronId, String name, String email) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.borrowingHistory = new ArrayList<>();
    }

    public String getPatronId() { return patronId; }
    public List<Book> getBorrowingHistory() { return borrowingHistory; }

    public void addToHistory(Book book) {
        borrowingHistory.add(book);
    }

    @Override
    public void update(Book book) {
        System.out.println("📢 Notification to " + name +
                ": Book available -> " + book.getTitle());
    }

    @Override
    public String toString() {
        return name + " (" + patronId + ")";
    }
}
