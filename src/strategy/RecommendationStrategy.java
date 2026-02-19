package strategy;

import model.*;
import java.util.*;

public interface RecommendationStrategy {
    List<Book> recommend(Patron patron, List<Book> allBooks);
}
