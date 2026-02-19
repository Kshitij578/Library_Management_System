package service;

import repository.BookRepository;
import model.Book;

import java.util.List;
import java.util.logging.Logger;
import java.util.Optional;

public class BookService {

    private static final Logger logger =
            Logger.getLogger(BookService.class.getName());

    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void addBook(Book book) {
        repository.addBook(book);
        logger.info("Book added successfully: " + book.getTitle());
    }

    public Optional<Book> getBook(String isbn) {
        logger.info("Searching book with ISBN: " + isbn);
        return repository.findByISBN(isbn);
    }

    public List<Book> searchByTitle(String title) {
        return repository.findByTitle(title);
    }

}


