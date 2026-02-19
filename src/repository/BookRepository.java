package repository;

import model.Book;
import java.util.*;

public interface BookRepository {

    void addBook(Book book);
    void removeBook(String isbn);
    Optional<Book> findByISBN(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findAll();
}
