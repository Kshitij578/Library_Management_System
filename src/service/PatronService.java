package service;

import model.Patron;
import java.util.*;

public class PatronService {

    private Map<String, Patron> patrons = new HashMap<>();

    public void addPatron(Patron patron) {
        patrons.put(patron.getPatronId(), patron);
    }

    public Optional<Patron> getPatron(String id) {
        return Optional.ofNullable(patrons.get(id));
    }
}
