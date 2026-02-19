package repository;

import model.Book;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {

    private Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void removeBook(String isbn) {
        books.remove(isbn);
    }

    public Optional<Book> findByISBN(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    public List<Book> findByTitle(String title) {
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author) {
        return books.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
}
