package model;

import repository.BookRepository;

public class LibraryBranch {

    private final String branchId;
    private final String branchName;
    private final BookRepository bookRepository;

    public LibraryBranch(String branchId, String branchName,
                         BookRepository repository) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.bookRepository = repository;
    }

    public String getBranchId() { return branchId; }
    public String getBranchName() { return branchName; }
    public BookRepository getRepository() { return bookRepository; }

    @Override
    public String toString() {
        return branchName + " (" + branchId + ")";
    }
}
