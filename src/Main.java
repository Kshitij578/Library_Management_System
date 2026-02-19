import model.*;
import repository.*;
import service.*;
import factory.*;
import strategy.*;
import util.LoggerConfig;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static Map<String, LibraryBranch> branches = new HashMap<>();
    private static LibraryBranch currentBranch;

    private static PatronService patronService = new PatronService();
    private static LendingService lendingService = new LendingService();
    private static ReservationService reservationService = new ReservationService();
    private static RecommendationStrategy recommendationStrategy =
            new AuthorBasedRecommendation();

    public static void main(String[] args) {

        LoggerConfig.configure();
        initializeBranches();

        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = getIntInput();

            switch (choice) {

                case 1 -> addBranch();
                case 2 -> switchBranch();
                case 3 -> addBook();
                case 4 -> searchMenu();
                case 5 -> addPatron();
                case 6 -> checkoutBook();
                case 7 -> returnBook();
                case 8 -> reserveBook();
                case 9 -> recommendBooks();
                case 10 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }

        System.out.println("System exited.");
    }

    // ========================= MENUS ============================

    private static void printMainMenu() {
        System.out.println("\n===== LIBRARY SYSTEM =====");
        System.out.println("Current Branch: " + currentBranch);
        System.out.println("1. Add Branch");
        System.out.println("2. Switch Branch");
        System.out.println("3. Add Book");
        System.out.println("4. Search Book");
        System.out.println("5. Add Patron");
        System.out.println("6. Checkout Book");
        System.out.println("7. Return Book");
        System.out.println("8. Reserve Book");
        System.out.println("9. Recommend Books");
        System.out.println("10. Exit");
        System.out.print("Choose option: ");
    }

    private static void searchMenu() {
        System.out.println("\nSearch By:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. ISBN");
        System.out.print("Choice: ");

        int choice = getIntInput();
        BookService bookService = new BookService(currentBranch.getRepository());

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Title: ");
                String title = scanner.nextLine();
                bookService.searchByTitle(title)
                        .forEach(System.out::println);
            }
            case 2 -> {
                System.out.print("Enter Author: ");
                String author = scanner.nextLine();
                currentBranch.getRepository()
                        .findByAuthor(author)
                        .forEach(System.out::println);
            }
            case 3 -> {
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                currentBranch.getRepository()
                        .findByISBN(isbn)
                        .ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("Not found.")
                        );
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // ========================= FEATURES ============================

    private static void addBranch() {

        System.out.print("Enter Branch ID: ");
        String id = scanner.nextLine();

        if (branches.containsKey(id)) {
            System.out.println("Branch ID already exists.");
            return;
        }

        System.out.print("Enter Branch Name: ");
        String name = scanner.nextLine();

        LibraryBranch branch =
                new LibraryBranch(id, name,
                        new InMemoryBookRepository());

        branches.put(id, branch);

        System.out.println("Branch added successfully.");
    }

    private static void initializeBranches() {
        branches.put("B1", new LibraryBranch("B1", "Main Branch",
                new InMemoryBookRepository()));
        branches.put("B2", new LibraryBranch("B2", "City Branch",
                new InMemoryBookRepository()));

        currentBranch = branches.get("B1");
    }

    private static void switchBranch() {
        System.out.println("Available Branches:");
        branches.values().forEach(System.out::println);

        System.out.print("Enter Branch ID: ");
        String id = scanner.nextLine();

        if (branches.containsKey(id)) {
            currentBranch = branches.get(id);
            System.out.println("Switched to " + currentBranch);
        } else {
            System.out.println("Invalid branch.");
        }
    }

    private static void addBook() {
        try {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();

            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Author: ");
            String author = scanner.nextLine();

            System.out.print("Year: ");
            int year = getIntInput();

            Book book = BookFactory.createBook(isbn, title, author, year);

            new BookService(currentBranch.getRepository()).addBook(book);

            System.out.println("Book added.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private static void addPatron() {
        System.out.print("Patron ID: ");
        String id = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        patronService.addPatron(new Patron(id, name, email));
        System.out.println("Patron added.");
    }

    private static void checkoutBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Patron ID: ");
        String pid = scanner.nextLine();

        Optional<Book> book =
                currentBranch.getRepository().findByISBN(isbn);
        Optional<Patron> patron =
                patronService.getPatron(pid);

        if (book.isPresent() && patron.isPresent()) {
            lendingService.checkoutBook(book.get(), patron.get());
        } else {
            System.out.println("Invalid book or patron.");
        }
    }

    private static void returnBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        currentBranch.getRepository()
                .findByISBN(isbn)
                .ifPresentOrElse(
                        lendingService::returnBook,
                        () -> System.out.println("Book not found.")
                );
    }

    private static void reserveBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Patron ID: ");
        String pid = scanner.nextLine();

        Optional<Patron> patron =
                patronService.getPatron(pid);

        if (patron.isPresent()) {
            reservationService.reserveBook(isbn, patron.get());
            System.out.println("Book reserved.");
        } else {
            System.out.println("Invalid patron.");
        }
    }

    private static void recommendBooks() {
        System.out.print("Patron ID: ");
        String id = scanner.nextLine();

        Optional<Patron> patron =
                patronService.getPatron(id);

        if (patron.isPresent()) {
            recommendationStrategy
                    .recommend(patron.get(),
                            currentBranch.getRepository().findAll())
                    .forEach(System.out::println);
        } else {
            System.out.println("Patron not found.");
        }
    }

    // ========================= UTIL ============================

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}
