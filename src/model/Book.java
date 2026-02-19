package model;

public class Book {

    private final String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private BookStatus status;

    public Book(String isbn, String title, String author, int year) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = year;
        this.status = BookStatus.AVAILABLE;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }

    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    @Override
    public String toString() {
        return title + " by " + author + " [" + status + "]";
    }
}
