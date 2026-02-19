package strategy;

import model.*;
import java.util.*;
import java.util.stream.Collectors;

public class AuthorBasedRecommendation implements RecommendationStrategy {

    public List<Book> recommend(Patron patron, List<Book> allBooks) {

        Set<String> authorsRead = patron.getBorrowingHistory()
                .stream()
                .map(Book::getAuthor)
                .collect(Collectors.toSet());

        return allBooks.stream()
                .filter(b -> authorsRead.contains(b.getAuthor()))
                .collect(Collectors.toList());
    }
}
